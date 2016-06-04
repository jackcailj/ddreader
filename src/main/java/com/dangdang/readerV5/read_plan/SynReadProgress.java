package com.dangdang.readerV5.read_plan;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.config.Config;
import com.dangdang.db.digital.PlanProcessDetailDb;
import com.dangdang.db.digital.PlanProcessDetailLogDb;
import com.dangdang.ddframework.dataverify.RecordExVerify;
import com.dangdang.ddframework.dataverify.RecordVerify;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.PlanProcessDetail;
import com.dangdang.digital.meta.PlanProcessDetailLog;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.nio.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2016-4-20.
 * 进入会同步到训练所处的所有未结束的process中
 */
public class SynReadProgress extends FixtureBase{

    public SynReadProgress(){}


    @Override
    protected void beforeParseParam() throws Exception {
        super.beforeParseParam();

        if(StringUtils.isNotBlank(paramMap.get("readCoverage"))){
            byte[] bytes= paramMap.get("readCoverage").getBytes();

            byte[] datas = new byte[125];
            for(int i=0; i< bytes.length;i++){
                datas[i]=bytes[i];
            }

            String readCoverageStr=Base64Utils.encodeBytes(datas,0,datas.length,Base64Utils.URL_SAFE);

            paramMap.put("readCoverage", readCoverageStr);

        }
    }


    @Override
    protected void genrateVerifyData() throws Exception {

        if (login != null && StringUtils.isNotBlank(paramMap.get("trainingId")) && StringUtils.isNotBlank(paramMap.get("trainingId"))) {
            //获取此训练所属的未结束的计划
            List<PlanProcessDetail> planProcessDetails = PlanProcessDetailDb.getUserPlanProcessByTrainingId(login.getCustId(), paramMap.get("trainingId"));

            List<String> processIds = Util.getFields(planProcessDetails, "processId");

            //获取此训练今天完成的进度
            List<PlanProcessDetailLog> planProcessDetailLogs = PlanProcessDetailLogDb.getTodayPlanProcessLog(login.getCustId(), processIds, paramMap.get("trainingId"));

            Map<Long, PlanProcessDetail> planProcessDetailMap = new HashMap<>();
            for (PlanProcessDetail planProcessDetail : planProcessDetails) {


                try {
                    long setChapter = Long.parseLong(paramMap.get("chapter"));
                    if(planProcessDetail.getChapter()<setChapter) {
                        planProcessDetail.setChapter(setChapter);
                    }
                }catch (Exception e){

                }
                try {
                    Long setChapterOffset=Long.parseLong(paramMap.get("chapterOffSet"));
                    if(planProcessDetail.getChapterOffset()<setChapterOffset) {
                        planProcessDetail.setChapterOffset(setChapterOffset);
                    }
                }
                catch (Exception e){

                }
                try {
                    Long setChapterStart=Long.parseLong(paramMap.get("chapterStart"));
                    if(planProcessDetail.getChapterStart()<setChapterStart) {
                        planProcessDetail.setChapterStart(setChapterStart);
                    }
                }catch (Exception e){

                }
                try {
                    Long setCOffStart=Long.parseLong(paramMap.get("chapterOffSetStart"));
                    if(planProcessDetail.getChapterOffsetStart()<setCOffStart) {
                        planProcessDetail.setChapterOffsetStart(setCOffStart);
                    }
                }catch (Exception e){

                }

                Boolean bupdateLog=false;
                try {
                    Double rate= Double.parseDouble(paramMap.get("totalFinishRate"));
                    if(planProcessDetail.getFinishReadRate()<rate) {
                        planProcessDetail.setFinishReadRate(rate);
                        bupdateLog=true;
                    }
                }catch (Exception e){

                }

                for (PlanProcessDetailLog detailLog : planProcessDetailLogs) {
                    //if (detailLog.getProcessId() == planProcessDetail.getProcessId()) {
                        //有记录了，需要updage，增加记录
                        if(bupdateLog) {
                            try {

                                detailLog.setReadTime(detailLog.getReadTime() + Long.parseLong(paramMap.get("daliyReadTime")));
                            } catch (Exception e) {

                            }
                            detailLog.setReadTimes(detailLog.getReadTimes() + 1);
                            try {
                                detailLog.setFinishReadRateToday(Double.parseDouble(paramMap.get("todayFinishRate")));
                            } catch (Exception e) {

                            }
                        }
                        detailLog.setReadDetail(null);
                        detailLog.setLastModifyTime(null);
                        planProcessDetailMap.put(planProcessDetail.getProcessId(), planProcessDetail);

                        dataVerifyManager.add(new RecordVerify(Config.YCDBConfig, detailLog).setVerifyContent("验证PlanProcessDetailLog数据更新是否正确"));

                    //}
                }
                /*try {
                    planProcessDetail.setReadTime(planProcessDetail.getReadTime() + Long.parseLong(paramMap.get("daliyReadTime")));
                }catch (Exception e){

                }*/
                planProcessDetail.setReadDetail(null);
                planProcessDetail.setLastModifyTime(null);
                planProcessDetail.setLastSynTime(null);

                dataVerifyManager.add(new RecordVerify(Config.YCDBConfig, planProcessDetail).setVerifyContent("验证PlanProcessDetail数据更新是否正确"));
            }

            //新增记录
            for (PlanProcessDetail planProcessDetail : planProcessDetails) {
                if (!planProcessDetailMap.containsKey(planProcessDetail.getProcessId())) {
                    PlanProcessDetailLog planProcessDetailLog = new PlanProcessDetailLog();


                    try {
                        planProcessDetailLog.setReadTime(Long.parseLong(paramMap.get("daliyReadTime")));
                    }catch (Exception e){

                    }
                    planProcessDetailLog.setTrainingId(planProcessDetail.getTrainingId());
                    planProcessDetailLog.setProcessId(planProcessDetail.getProcessId());
                    planProcessDetailLog.setCustId(Long.parseLong(login.getCustId()));
                    planProcessDetailLog.setMediaId(planProcessDetail.getMediaId());
                    try {
                        planProcessDetailLog.setFinishReadRateToday(Double.parseDouble(paramMap.get("todayFinishRate")));
                    }catch (Exception e){

                    }
                    planProcessDetailLog.setReadDetail(null);
                    planProcessDetailLog.setLastModifyTime(null);

                    dataVerifyManager.add(new RecordVerify(Config.YCDBConfig, planProcessDetailLog).setVerifyContent("验证PlanProcessDetailLog数据更新是否正确"));
                }
            }

        }
    }
}
