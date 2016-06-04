package com.dangdang.readerV5.read_plan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.PlanProcessDetailLogDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.PlanProcessDetailLog;
import com.dangdang.readerV5.reponse.ReadTrainingTopReponse;
import com.dangdang.readerV5.reponse.TrainingNewsInfo;
import com.dangdang.readerV5.reponse.TrainingRank;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cailianjie on 2016-4-14.
 */
public class ReadTrainingTop extends FixtureBase {

    ReponseV2<ReadTrainingTopReponse> reponseResult;

    public ReadTrainingTop(){}

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<ReadTrainingTopReponse>>(){});
    }


    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){


            Date curDate=new Date();
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            String greateDate= df.format(new Date((curDate.getTime()/1000-30*24*60*60)*1000));

            TrainingRank mytrainingRank=null;

            List<ReadTrainingTopInfo> planProcessDetailLogs = PlanProcessDetailLogDb.getReadTrainingWeekRankList(paramMap.get("processId"),
                    paramMap.get("trainingId"));


           /* List<TrainingNewsInfo> expectTrainingInfos = new ArrayList<>();


            if(planProcessDetailLogs.size()>0) {
                mytrainingRank = new TrainingRank();

                for (int i = 0; i < planProcessDetailLogs.size(); i++) {
                    PlanProcessDetailLog log = planProcessDetailLogs.get(i);

                    if (log.getCustId().toString().equals(login.getCustId())) {
                        mytrainingRank.setMyTodayFinishRate(log.getFinishReadRateToday());
                        mytrainingRank.setMyRankIndex(i + 1);
                    }

                    TrainingNewsInfo trainingNewsInfo = new TrainingNewsInfo();
                    trainingNewsInfo.setTrainingId(log.getTrainingId());
                    trainingNewsInfo.setCustId(log.getCustId());
                    trainingNewsInfo.setMediaId(log.getMediaId());
                    trainingNewsInfo.setTodayFinishRate(log.getFinishReadRateToday());
                    trainingNewsInfo.setProcessId(log.getProcessId());

                    expectTrainingInfos.add(trainingNewsInfo);

                }
                mytrainingRank.setRankList(expectTrainingInfos);
            }*/

                if(planProcessDetailLogs.size()==0){
                    dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getTrainingTops(), null).setVerifyContent("验证动态信息是否正确"));
                }
                else {
                    dataVerifyManager.add(new ListVerify(reponseResult.getData().getTrainingTops(), planProcessDetailLogs, true).setVerifyContent("验证动态信息是否正确"));
                }

        }
        super.dataVerify();
    }
}
