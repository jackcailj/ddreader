package com.dangdang.readerV5.read_activitiy;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.PlanActivityDb;
import com.dangdang.db.digital.PlanProcessDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.PlanActivitiy;
import com.dangdang.digital.meta.PlanProcess;
import com.dangdang.readerV5.reponse.ActivityUserListReponse;
import com.dangdang.readerV5.reponse.JoinActivityUserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cailianjie on 2016-4-27.
 */
public class ActivityUserList extends FixtureBase{


    ReponseV2<ActivityUserListReponse> reponseResult;

    public ActivityUserList(){}


    public ReponseV2<ActivityUserListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<ActivityUserListReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){

            List<JoinActivityUserInfo> expectedInfos = null;
            try {
                PlanActivitiy planActivitiy = PlanActivityDb.getPlanActivitiy(paramMap.get("mediaDigestId"));

                List<PlanProcess> planProcesses = PlanProcessDb.getPlanProcessByActivityId(planActivitiy.getPaId().toString(),
                        Integer.parseInt(paramMap.get("page")), Integer.parseInt(paramMap.get("pageSize")));

                expectedInfos = new ArrayList<>();
                for (PlanProcess planProcess : planProcesses) {
                    JoinActivityUserInfo joinActivityUserInfo = new JoinActivityUserInfo();
                    joinActivityUserInfo.setCustId(planProcess.getCustId());
                    joinActivityUserInfo.setActivityId(planProcess.getActivityId());
                    joinActivityUserInfo.setJoinActivityTime(planProcess.getJoinActivityTime().getTime());

                    expectedInfos.add(joinActivityUserInfo);

                }

            }catch (Exception e){

            }

            dataVerifyManager.add(new ListVerify(reponseResult.getData().getUserList(), expectedInfos, true));

        }
        else{
            dataVerifyManager.add(new ListVerify(reponseResult.getData().getUserList(), null, true), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
