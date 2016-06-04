package com.dangdang.readerV5.read_activitiy;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.PlanActivityDb;
import com.dangdang.db.digital.PlanProcessDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.PropertyUtils;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.PlanActivitiy;
import com.dangdang.digital.meta.PlanProcess;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.readerV5.read_plan.PlanVo;
import com.dangdang.readerV5.reponse.MyActivityListReponse;

import java.util.*;

/**
 * Created by cailianjie on 2016-4-27.
 */
public class MyActivityList extends FixtureBase{

    ReponseV2<MyActivityListReponse> reponseResult;

    public ReponseV2<MyActivityListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<MyActivityListReponse>>(){});
    }

    public MyActivityList(){}


    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            Integer page = Integer.parseInt(paramMap.get("page"));
            Integer pageSize = Integer.parseInt(paramMap.get("pageSize"));
            List<PlanProcess> planProcesses = PlanProcessDb.getUserJoinActivitys(login.getCustId(),page,pageSize);
            List<String> activityIds = Util.getFields(planProcesses,"activityId");

            List<PlanActivitiy> planActivitiys = PlanActivityDb.getPlanActivitiys(activityIds);


            List<PlanVo> planVos= PlanVo.getJoinPlanVos(planProcesses);
            Map<Long,PlanVo> planVoMap = new HashMap<>();
            for(PlanVo planVo:planVos){
                planVoMap.put(planVo.getPlanId(),planVo);
            }

            List<PlanActivityVo> planActivityVos = new ArrayList<>();
            for(PlanActivitiy planActivitiy:planActivitiys){
                PlanActivityVo planActivityVo = new PlanActivityVo();

                PropertyUtils.copyProperty(planActivitiy,planActivityVo);

                PlanVo planVo = planVoMap.get(planActivitiy.getPlanId());

                planActivityVo.setPlanName(planVo.getName());
                planActivityVo.setPlanPrice(planVo.getPlanPrice());
                planActivityVo.setTrainings(planVo.getTrainings());

                planActivityVos.add(planActivityVo);
            }


            dataVerifyManager.add(new ListVerify(reponseResult.getData().getActivityList(),planActivityVos,true));
        }
        super.dataVerify();
    }
}
