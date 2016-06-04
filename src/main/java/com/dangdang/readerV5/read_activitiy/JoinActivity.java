package com.dangdang.readerV5.read_activitiy;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.PlanActivityDb;
import com.dangdang.db.digital.PlanProcessDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.digital.meta.PlanActivitiy;
import com.dangdang.digital.meta.PlanProcess;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cailianjie on 2016-4-27.
 */
public class JoinActivity extends FixtureBase{

    PlanActivitiy planActivitiy=null;

    public JoinActivity(){}

    @Override
    protected void genrateVerifyData() throws Exception {

        try {
            if(StringUtils.isNotBlank(paramMap.get("mediaDigestId"))) {
                planActivitiy = PlanActivityDb.getPlanActivitiy(paramMap.get("mediaDigestId"));
            }
        }catch (Exception e){

        }
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){

            if(planActivitiy!=null) {
                PlanProcess planProcess = PlanProcessDb.getPlanProcessByPlanId(login.getCustId(), "" + planActivitiy.getPlanId());

                //验证planProcess joinActivityTime被设置

                if(paramMap.get("type").equals("1")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String joinTime = sdf.format(planProcess.getJoinActivityTime());
                    String excpectTime = sdf.format(new Date());
                    dataVerifyManager.add(new ValueVerify<String>(joinTime, excpectTime)
                            .setVerifyContent("加入读书活动，验证plan_process表join_activity_time设置为当前时间"));

                    //验证参加人数加1
                    PlanActivitiy curPlanActivitiy = PlanActivityDb.getPlanActivitiy(paramMap.get("mediaDigestId"));
                    dataVerifyManager.add(new ValueVerify<Long>(curPlanActivitiy.getJoinPeople(), planActivitiy.getJoinPeople() + 1)
                            .setVerifyContent("加入读书活动，验证参与人数+1"));
                }
                else if(paramMap.get("type").equals("0")){

                    dataVerifyManager.add(new ValueVerify<Date>(planProcess.getJoinActivityTime(), null)
                            .setVerifyContent("退出读书活动，验证验证plan_process表join_activity_time设置为null"));

                    //验证参加人数加1
                    PlanActivitiy curPlanActivitiy = PlanActivityDb.getPlanActivitiy(paramMap.get("mediaDigestId"));
                    dataVerifyManager.add(new ValueVerify<Long>(curPlanActivitiy.getJoinPeople(), planActivitiy.getJoinPeople()-1)
                            .setVerifyContent("退出读书活动，验证参与人数-1"));
                }
            }


        }

        super.dataVerify();
    }
}
