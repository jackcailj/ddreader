package com.dangdang.readerV5.read_plan;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.PlanDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.util.StringUtil;
import com.dangdang.digital.meta.Plan;
import org.apache.commons.lang3.StringUtils;


/**
 * Created by cailianjie on 2016-4-20.
 */
public class UpdatePlan extends FixtureBase{

    public UpdatePlan(){}


    @Override
    protected void dataVerify() throws Exception {
        if(StringUtils.isNotBlank(paramMap.get("planId"))) {
            Plan plan=null;
            try {
                plan = PlanDb.getPlan(paramMap.get("planId"));
            }catch (Exception e){

            }
            if(plan!=null) {
                dataVerifyManager.add(new ValueVerify<String>(plan.getName(), paramMap.get("name")));
                dataVerifyManager.add(new ValueVerify<String>(plan.getDesc(), paramMap.get("desc")));
                if(StringUtils.isNotBlank(paramMap.get("imgUrl"))) {
                    dataVerifyManager.add(new ValueVerify<String>(plan.getImgUrl(), paramMap.get("imgUrl")));
                }
            }
        }

   /*         if(StringUtils.isNotBlank(paramMap.get("planId"))){
                Plan plan = PlanDb.getPlan(paramMap.get("planId"));
                dataVerifyManager.add(new ValueVerify<String>(plan.getName(),paramMap.get("name")));
                dataVerifyManager.add(new ValueVerify<String>(plan.getDesc(),paramMap.get("desc")));
                dataVerifyManager.add(new ValueVerify<String>(plan.getImgUrl(),paramMap.get("imgUrl")));
            }*/


        super.dataVerify();
    }
}
