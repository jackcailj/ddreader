package com.dangdang.readerV5.read_plan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.PlanProcessDetailLogDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.DesUtils;
import com.dangdang.digital.meta.PlanProcessDetailLog;
import com.dangdang.readerV5.reponse.GetUserReadProgressReponse;
import com.dangdang.db.digital.UserTrainingReadProgress;

import java.util.Date;

/**
 * Created by cailianjie on 2016-4-22.
 */
public class GetUserReadProgress extends FixtureBase{

    ReponseV2<GetUserReadProgressReponse> reponseResult;


    public GetUserReadProgress(){}


    public ReponseV2<GetUserReadProgressReponse> getReponseResult() {
        return reponseResult;
    }


    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<GetUserReadProgressReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            UserTrainingReadProgress userReadProgress = PlanProcessDetailLogDb.getUserTrainingProgress(login.getCustId(),paramMap.get("trainingId"),new Date());
            if(userReadProgress != null) {
                userReadProgress.setCustId(DesUtils.encryptCustId(Long.parseLong(userReadProgress.getCustId())));
                dataVerifyManager.add(new ValueVerify<UserTrainingReadProgress>(reponseResult.getData().getReadProgressInfo(), userReadProgress, true));
            }
            else{
                dataVerifyManager.add(new ValueVerify<UserTrainingReadProgress>(reponseResult.getData().getReadProgressInfo(), null));
            }
        }
        else{
            dataVerifyManager.add(new ValueVerify<UserTrainingReadProgress>(reponseResult.getData().getReadProgressInfo(), null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
