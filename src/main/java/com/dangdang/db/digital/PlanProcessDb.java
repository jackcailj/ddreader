package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.PlanProcess;
import com.dangdang.readerV5.reponse.MyPlanListInfo;

import javax.print.attribute.standard.PagesPerMinute;
import java.util.List;

/**
 * Created by cailianjie on 2016-4-18.
 */
public class PlanProcessDb {

    /*
    根据id获取planProcess信息
     */
    public static PlanProcess getPlanProcessById(String ppId) throws Exception {
        String selectString ="select * from plan_process where pp_id="+ppId;
        PlanProcess planProcess = DbUtil.selectOne(Config.YCDBConfig,selectString,PlanProcess.class);

        return planProcess;
    }


    /*
   根据id获取planProcess信息
    */
    public static PlanProcess getPlanProcessByPlanId(String custId,String planId) throws Exception {
        String selectString ="select * from plan_process where plan_id="+planId+" and cust_id="+custId;
        PlanProcess planProcess = DbUtil.selectOne(Config.YCDBConfig,selectString,PlanProcess.class);

        return planProcess;
    }


    /*
   获取用户计划信息
    */
    public static List<MyPlanListInfo> getUserPlans(String custId,String status,Long pageDate,Integer pageSize) throws Exception {
        String selectString ="select pp.plan_id as planId, pp.pp_id as processId, pp.finish_read_rate as finishReadRate, pp.read_time as planReadTime,  pp.plan_finish_time as planFinishTime, p.name, p.desc,  p.is_recommended as isRecommended, p.is_free as isFree,  p.creator_id as creatorId, p.is_public as isPublic, p.discount \n" +
                "from plan_process as pp, plan as p\n" +
                "where p.plan_id = pp.plan_id and pp.cust_id ="+custId+" and pp.status in ("+status+") and UNIX_TIMESTAMP(pp.create_time)*1000 < "+pageDate+" order by pp.create_time desc" + (pageSize==null?"":" limit "+ pageSize);

        List<MyPlanListInfo> planProcesses = DbUtil.selectList(Config.YCDBConfig,selectString,MyPlanListInfo.class);

        return planProcesses;
    }


    /*
   根据活动id获取参与活动人员信息
    */
    public static List<PlanProcess> getPlanProcessByActivityId(String activityId,int page,int pageSize) throws Exception {
        String selectString ="select * from plan_process where activity_id = "+activityId+" ORDER BY join_activity_time desc limit "+(page-1)*pageSize+","+pageSize;
        List<PlanProcess> planProcess = DbUtil.selectList(Config.YCDBConfig,selectString,PlanProcess.class);

        return planProcess;
    }


    /*
    获取用户参与的读书活动
    */
    public static List<PlanProcess> getUserJoinActivitys(String custId,int page,int pageSize) throws Exception {
        String selectString ="select * from plan_process where activity_id is not null and cust_id="+custId+" ORDER BY join_activity_time  limit "+(page-1)*pageSize+","+pageSize;
        List<PlanProcess> planProcess = DbUtil.selectList(Config.YCDBConfig,selectString,PlanProcess.class);

        return planProcess;
    }

    /*
      获取已结束的读书计划信息。
      */
    public static List<PlanProcess> getCompletePlans(String planId) throws Exception {
        String selectString ="SELECT * from plan_process where plan_id ="+planId+" and `status` =2 order by finish_read_rate desc,to_days(end_time)-to_days(begin_time) asc;";
        List<PlanProcess> planProcess = DbUtil.selectList(Config.YCDBConfig,selectString,PlanProcess.class);

        return planProcess;
    }

}
