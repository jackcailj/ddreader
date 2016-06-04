package com.dangdang.readerV5.read_plan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.db.digital.MediaTrainingDb;
import com.dangdang.ddframework.dataverify.RecordExVerify;
import com.dangdang.ddframework.dataverify.RecordVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.Plan;
import com.dangdang.digital.meta.PlanDetail;
import com.dangdang.readerV5.reponse.CreatePlanReponse;
import com.dangdang.readerV5.reponse.ReadPlanInfo;
import com.dangdang.readerV5.reponse.RecommendPlanReponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by cailianjie on 2016-4-18.
 */
public class CreatePlan extends FixtureBase{

    ReponseV2<CreatePlanReponse> reponseResult;

    public CreatePlan(){}


    @Override
    protected void beforeParseParam() throws Exception {
        super.beforeParseParam();

        if(login!=null){
            Plan plan = new Plan();
            plan.setCreatorId(Long.parseLong(login.getCustId()));
            plan.setDesc(paramMap.get("desc"));
            plan.setName(paramMap.get("name"));


            List<PlanDetail> planDetails = new ArrayList<>();
            if(StringUtils.isNotBlank(paramMap.get("mtIds"))) {
                String[] mtids = paramMap.get("mtIds").split(",");
                for(String id:mtids) {
                    PlanDetail planDetail = new PlanDetail();
                    planDetail.setTrainingId(Long.parseLong(id));

                    //dataVerifyManager.add(new RecordExVerify(Config.YCDBConfig,planDetail,"pd_id"," from plan_detail "));
                    planDetails.add(planDetail);
                }
            }

            dataVerifyManager.add(new RecordExVerify(Config.YCDBConfig,plan,"plan_id"," from plan ",planDetails,"plan_id"));


        }
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<CreatePlanReponse>>(){});
    }

    public Long getPlanId(){
        return reponseResult.getData().getPlan().getPlanId();
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            ReadPlanInfo readPlanInfo = new ReadPlanInfo();
            readPlanInfo.setName(paramMap.get("name"));
            readPlanInfo.setDesc(paramMap.get("desc"));

            String mtIds= paramMap.get("mtIds");

            List<String> mtIdList = new ArrayList<String>();
            mtIdList.addAll(mtids.)
            MediaTrainingDb.getTrainings();
        }
        super.dataVerify();
    }
}
