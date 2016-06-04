package com.dangdang.readerV5.read_plan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.db.digital.PlanProcessDetailDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.DateUtil;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.PlanProcessDetail;
import com.dangdang.readerV5.reponse.TrainingCelebrateReponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2016-5-3.
 */
public class TrainingCelebrate extends FixtureBase{

    ReponseV2<TrainingCelebrateReponse> reponseResult;

    public TrainingCelebrate(){}


    public ReponseV2<TrainingCelebrateReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<TrainingCelebrateReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            Map<String,Object> param = new HashMap<>();
            param.put("training_id",paramMap.get("trainingId"));
            param.put("status",2);
            List<PlanProcessDetail> planProcessDetails = PlanProcessDetailDb.getTrainingCelebrateInfo(param);

            TrainingCelebrateReponse reponse = new TrainingCelebrateReponse();

            //TrainingCompleteInfo myRankInfo = new TrainingCompleteInfo();

            List<TrainingCompleteInfo> completeInfos = new ArrayList<>();
            int myRank=0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for(int i=0; i< planProcessDetails.size();i++){
                PlanProcessDetail detail  =planProcessDetails.get(i);

                TrainingCompleteInfo completeInfo = new TrainingCompleteInfo();



                long useDays = DateUtil.getDays(sdf.parse(sdf.format(detail.getStartTime())),sdf.parse(sdf.format(detail.getEndTime())));
                completeInfo.setDays(useDays==0?1: (int) useDays);
                completeInfo.setFinishReadRate(detail.getFinishReadRate());

                completeInfos.add(completeInfo);
               /* if(planProcessDetails.get(i).getCustId().toString().equals(login.getCustId())){

                    myRankInfo.setDays(completeInfo.getDays());
                    myRankInfo.setFinishReadRate(detail.getFinishReadRate());
                    myRankInfo.setMyRankIndex(i+1);
                }*/




            }

            Media media = MediaDb.getMedia(paramMap.get("mediaId"));
            MediaBaseInfo mediaBaseInfo = new MediaBaseInfo();
            mediaBaseInfo.setMediaId(media.getMediaId());
            mediaBaseInfo.setSaleId(media.getSaleId());
            mediaBaseInfo.setTitle(media.getTitle());

            reponse.setMedia(mediaBaseInfo);
            //reponse.setMyTrainingRank(myRankInfo);
            reponse.setTrainingTops(completeInfos);

            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData(),reponse,true));

        }
        super.dataVerify();
    }
}
