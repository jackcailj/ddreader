package com.dangdang.readerV5.read_plan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.*;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.DateUtil;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.*;
import com.dangdang.readerV5.reponse.PlanCelebrateReponse;
import org.apache.commons.lang3.time.DateUtils;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cailianjie on 2016-5-4.
 */
public class PlanCelebrate extends FixtureBase{

    ReponseV2<PlanCelebrateReponse> reponseResult;

    public PlanCelebrate(){}

    public ReponseV2<PlanCelebrateReponse> getReponseResult() {
        return reponseResult;
    }


    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<PlanCelebrateReponse>>(){});
    }


    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            List<PlanProcess> planProcesses = PlanProcessDb.getCompletePlans(paramMap.get("planId"));

            PlanCelebrateReponse planCelebrateReponse = new PlanCelebrateReponse();

            List<TrainingCompleteInfo> completeInfos = new ArrayList<>();

            TrainingCompleteInfo myRankInfo = new TrainingCompleteInfo();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for(int i=0;i< planProcesses.size();i++){
                PlanProcess planProcess = planProcesses.get(i);

                TrainingCompleteInfo completeInfo = new TrainingCompleteInfo();

                long days = DateUtil.getDays(sdf.parse(sdf.format(planProcess.getBeginTime())),sdf.parse(sdf.format(planProcess.getEndTime())));
                //int days =(int) ((planProcess.getEndTime().getTime()-planProcess.getBeginTime().getTime())/(1000*3600*24));
                completeInfo.setDays(days==0?1: (int) days);
                completeInfo.setFinishReadRate(planProcess.getFinishReadRate());

                completeInfos.add(completeInfo);

                if(planProcess.getCustId().toString().equals(login.getCustId())){
                    myRankInfo.setFinishReadRate(planProcess.getFinishReadRate());
                    myRankInfo.setDays(completeInfo.getDays());
                    myRankInfo.setMyRankIndex(i+1);
                    planCelebrateReponse.setMyTrainingRank(completeInfo);
                }
            }

            planCelebrateReponse.setMyTrainingRank(myRankInfo);
            planCelebrateReponse.setTrainingTops(completeInfos);
            planCelebrateReponse.setTotalFinishCount((long) planProcesses.size());

            //获取计划中训练完成情况
            List<PlanProcessDetail> planProcessDetails = PlanProcessDetailDb.getPlanProcessByProcessId(paramMap.get("processId"));



            //计算reward
            List<String> trainingIds = Util.getFields(planProcessDetails,"trainingId");
            List<MediaTraining> mediaTrainings = MediaTrainingDb.getTrainings(trainingIds,true,-1);


            TrainingRewardInfo reward = new TrainingRewardInfo();
            Map<Long,MediaTraining> mediaTrainingMap = new HashMap<>();
            for(MediaTraining training:mediaTrainings){
                mediaTrainingMap.put(training.getMtId(),training);
            }



            List<PlanCelebrateTraining> planCelebrateTrainings = new ArrayList<>();
            for(PlanProcessDetail detail:planProcessDetails){
                PlanCelebrateTraining planCelebrateTraining = new PlanCelebrateTraining();
                planCelebrateTraining.setTotalFinishRate(detail.getFinishReadRate());
                int useDays = (int) ((detail.getEndTime().getTime()-detail.getStartTime().getTime())/(1000*3600*24));
                planCelebrateTraining.setUseDays(useDays==0?1:useDays);

                Media media = MediaDb.getMedia(detail.getMediaId().toString());
                /*String title = media.getTitle();
                int nIndex= title.indexOf("(");
                if(nIndex!=-1){
                    title=title.substring(0,nIndex);
                }*/
                planCelebrateTraining.setTitle(media.getTitle());

                planCelebrateTrainings.add(planCelebrateTraining);

                MediaTraining mediaTraining = mediaTrainingMap.get(detail.getTrainingId());
                reward.setExperience((long) (mediaTraining.getBaseExperience()*detail.getFinishReadRate()*0.5));
                reward.setSilverBell((long) (mediaTraining.getBaseMoney()*detail.getFinishReadRate()*0.5));
                reward.setPlanId(Long.parseLong(paramMap.get("planId")));
            }

            //planCelebrateReponse.setReward(reward);
            planCelebrateReponse.setTrainings(planCelebrateTrainings);



        dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData(),planCelebrateReponse,true));

        }
        super.dataVerify();
    }
}
