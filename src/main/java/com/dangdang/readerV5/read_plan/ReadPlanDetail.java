package com.dangdang.readerV5.read_plan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.authority.meta.MediaAuthority;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.db.digital.*;
import com.dangdang.ddframework.dataverify.RecordExVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.DateUtil;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.*;
import com.dangdang.readerV5.reponse.PlanInfoEx;
import com.dangdang.readerV5.reponse.ReadPlanDetailReponse;
import com.dangdang.readerV5.reponse.TrainingInfoEx;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cailianjie on 2016-4-18.
 */
public class ReadPlanDetail extends FixtureBase{

    ReponseV2<ReadPlanDetailReponse> reponseResult;

    public ReadPlanDetail(){}

    public ReponseV2<ReadPlanDetailReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<ReadPlanDetailReponse>>(){});
    }

    @Override
    protected void beforeParseParam() throws Exception {
        super.beforeParseParam();
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){

            Boolean bToday = true;
            if(StringUtils.isNotBlank(paramMap.get("pageDate"))) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date pageDate = new Date(Long.parseLong(paramMap.get("pageDate")));

                Date curDate = new Date();
                String curDateStr = sdf.format(new Date());
                String pageDateStr = sdf.format(pageDate);

                bToday = curDateStr.equals(pageDateStr)?true:false;
            }


            Plan plan = PlanDb.getPlan(paramMap.get("planId"));

            if(plan.getStatus()==2) {
                //已结束计划不返回信息
                dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getPlanDetail(),null));

            }
            else {
                PlanProcess planProcess = null;
                PlanProcessDetail planProcessDetail = null;
                Map<Long,PlanProcessDetail> planDetailInfoMap= new HashMap<>();
                try {
                    planProcess = PlanProcessDb.getPlanProcessByPlanId(login.getCustId(), paramMap.get("planId"));
                    List<PlanProcessDetail> planProcessDetails = PlanProcessDetailDb.getPlanProcessByProcessId(planProcess.getPpId().toString());

                    for(PlanProcessDetail detail:planProcessDetails){
                        planDetailInfoMap.put(detail.getTrainingId(),detail);
                    }

                } catch (Exception e) {

                }

                    //计算价格
                    //  获取计划价格
                    Long originPrice = 0l;
                    Double sourcePrice = 0d;
                    List<PlanDetailInfo> details = PlanDetailDb.getPlanDetails(paramMap.get("planId"), false,planProcess==null?true:false);//planProcess==null?true:false);

                    List<String> mediaIds = Util.getFields(details, "mediaId");
                    List<String> trainingIds = new ArrayList<>();
                    for (PlanDetailInfo detail : details) {
                        //if (!mediaIds.contains(detail.getMediaId().toString())) {
                        sourcePrice += detail.getSalePrice();
                        sourcePrice += detail.getSalePrice() * detail.getDiscount();
                        // }

                        trainingIds.add(detail.getTrainingId().toString());

                    }


                    //获取训练信息
                    List<MediaTraining> trainings = MediaTrainingDb.getTrainings(trainingIds, true, -1);


                    PlanInfoEx expectPlanInfo = new PlanInfoEx();
                    //expectPlanInfo.setPlanPrice(planPrice.longValue());
                    //expectPlanInfo.setOriginPrice();
                    expectPlanInfo.setPlanId(plan.getPlanId());

                if(planProcess!=null) {
                    if (planProcess.getStatus() == 0) {
                        expectPlanInfo.setPlanRemainDay(0l);
                        expectPlanInfo.setTotalFinishTime(planProcess.getPlanFinishTime() / (60 * 24));
                    } else {
                        if (planProcess == null || planProcess.getBeginTime() == null) {
                            expectPlanInfo.setPlanRemainDay(plan.getTotalFinishTime() / (60 * 24));
                        } else if (planProcess.getReadTime() > planProcess.getPlanFinishTime()) {
                            //expectPlanInfo.setPlanRemainDay();
                            expectPlanInfo.setPlanRemainDay(0l);

                        } else {
                            long diffDate = DateUtil.getDays(planProcess.getBeginTime(), new Date());
                            Long totalDay = planProcess.getPlanFinishTime() / 1440;
                            expectPlanInfo.setPlanRemainDay(totalDay - diffDate);
                            expectPlanInfo.setTotalFinishTime(planProcess.getPlanFinishTime() / (60 * 24));
                        }
                    }

                    expectPlanInfo.setReadTime(planProcess == null ? 0l : planProcess.getReadTime());
                }

                    //expectPlanInfo.setCreatorId(plan.getCreatorId());
                    expectPlanInfo.setIsFree(plan.getIsFree());
                    expectPlanInfo.setIsPublic(plan.getIsPublic());
                    expectPlanInfo.setIsOwner((plan.getCreatorId() != null && plan.getCreatorId().toString().equals(login.getCustId())) ? 1 : 0);

                    List<TrainingInfoEx> trainingInfoExes = new ArrayList<>();

                    expectPlanInfo.setTrainings(trainingInfoExes);

                    long sumMaxTime = 0l;
                    for (MediaTraining training : trainings) {

                        if (planDetailInfoMap.size()>0 && !planDetailInfoMap.containsKey(training.getMtId())) {
                            continue;
                        }

                        TrainingInfoEx trainingInfoEx = new TrainingInfoEx();
                        trainingInfoEx.setMediaId(training.getMediaId());
                        trainingInfoEx.setMtId(training.getMtId());
                        trainingInfoEx.setTotalFinishTime(training.getTotalFinishTime() / (60 * 24));

                        if(planDetailInfoMap.size()>0) {
                            trainingInfoEx.setTotalFinishRate(planDetailInfoMap.get(training.getMtId()).getFinishReadRate());
                            BigDecimal b = new BigDecimal(Double.valueOf("1.00") / trainingInfoEx.getTotalFinishTime());
                            trainingInfoEx.setDailyTargetFinishRate(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }



                        if (planProcess!=null && bToday) {
                            PlanProcessDetailLog detailLog = PlanProcessDetailLogDb.getTodayPlanProcessLog(login.getCustId(), planProcess.getPpId().toString(), training.getMtId().toString());
                            if (detailLog == null) {
                                trainingInfoEx.setTodayFinishRate(0.00);
                            } else {
                                trainingInfoEx.setTodayFinishRate(detailLog.getFinishReadRateToday());
                            }
                        }


                        sumMaxTime += training.getMaxFinishTime() / (60 * 24);
                        trainingInfoEx.setMaxFinishTime(training.getMaxFinishTime() / (60 * 24));

                        trainingInfoExes.add(trainingInfoEx);
                    }


                    expectPlanInfo.setMaxFinishTiem(sumMaxTime);

                    dataVerifyManager.add(new ValueVerify<PlanInfoEx>(reponseResult.getData().getPlanDetail(), expectPlanInfo, true));
                }


        }
        super.dataVerify();
    }
}
