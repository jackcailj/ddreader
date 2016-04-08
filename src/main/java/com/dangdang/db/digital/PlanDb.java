package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Plan;
import com.dangdang.enumeration.ReadPlanFeeStatus;
import com.dangdang.enumeration.ReadPlanLifeStatus;
import com.dangdang.enumeration.ReadPlanStatus;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-6.
 */
public class PlanDb {

    /*
    获取计划列表
    参数：
        shelfStatus：上下架状态
        feeStatus：付费状态
        lifeStatus：生命周期状态
     */
    public static List<Plan> getPlans(ReadPlanStatus shelfStatus, ReadPlanFeeStatus feeStatus, ReadPlanLifeStatus lifeStatus) throws Exception {
        String selectString ="select * from plan where `status`="+lifeStatus.toString()+" and is_free="+feeStatus.toString();

        List<Plan> plans = DbUtil.selectList(Config.YCDBConfig,selectString,Plan.class);

        return plans;
    }

    /*
    获取未参加计划列表
    参数：
        custId:用户Id
        shelfStatus：上下架状态
        feeStatus：付费状态
        lifeStatus：生命周期状态
     */
    public static List<Plan> getUnJoinPlans(String custId,ReadPlanStatus shelfStatus, ReadPlanFeeStatus feeStatus, ReadPlanLifeStatus lifeStatus) throws Exception {
        String selectString ="select * from plan where `status`="+lifeStatus.toString()+" and is_free="+feeStatus.toString() +" and plan_id not in (select plan_id from plan_process where cust_id="+custId+")";

        List<Plan> plans = DbUtil.selectList(Config.YCDBConfig,selectString,Plan.class);

        return plans;
    }


    /*
    获取未参加计划列表
    参数：
        custId:用户Id
        shelfStatus：上下架状态
        feeStatus：付费状态
        lifeStatus：生命周期状态
     */
    public static List<Plan> getJoinPlans(String custId, ReadPlanLifeStatus lifeStatus) throws Exception {
        String selectString ="select * from plan where `status`="+lifeStatus.toString()+" and plan_id  in (select plan_id from plan_process where cust_id="+custId+")";

        List<Plan> plans = DbUtil.selectList(Config.YCDBConfig,selectString,Plan.class);

        return plans;
    }

}
