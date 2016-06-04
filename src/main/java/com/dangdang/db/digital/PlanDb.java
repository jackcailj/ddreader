package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Plan;
import com.dangdang.enumeration.ReadPlanFeeStatus;
import com.dangdang.enumeration.ReadPlanLifeStatus;
import com.dangdang.enumeration.ReadPlanStatus;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-6.
 */
public class PlanDb {



    /*
   根据计划id获取计划信息
   参数：
        planId:计划id
    */
    public static Plan getPlan(String planId) throws Exception {
        String selectString ="select * from plan where plan_id="+planId;

        Plan plan = DbUtil.selectOne(Config.YCDBConfig,selectString,Plan.class);

        return plan;
    }

    /*
    *获取自己创建的计划,或者其他人创建的计划
     */
    public static Plan getPlan(String custId,boolean creator) throws Exception {
        String selectString ="SELECT * from plan where  "+(creator?" creator_id="+custId:" creator_id!="+custId)+" limit 1 ";

        Plan plan = DbUtil.selectOne(Config.YCDBConfig,selectString,Plan.class);

        return plan;
    }

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
    public static List<Plan> getUnJoinPlans(String custId) throws Exception {
        String selectString ="select * from plan where `status`=1 and plan_id not in (select plan_id from plan_process where cust_id="+custId+")";

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
        //String selectString ="select * from plan where `status`="+lifeStatus.toString()+" and plan_id  in (select plan_id from plan_process where cust_id="+custId+")";

        String selectString ="select * from plan_process where cust_id="+custId+" and `status`="+lifeStatus.toString();

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
    public static List<Plan> getPlans(List<String> planIds) throws Exception {
        String selectString ="SELECT * from plan where plan_id in("+ StringUtils.join(planIds,",")+")";

        List<Plan> plans = DbUtil.selectList(Config.YCDBConfig,selectString,Plan.class);

        return plans;
    }

}
