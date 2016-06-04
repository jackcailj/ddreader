package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.PlanProcessDetail;
import com.dangdang.digital.meta.PlanProcessDetailLog;
import com.dangdang.readerV5.read_plan.ReadTrainingTopInfo;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by cailianjie on 2016-4-14.
 */
public class PlanProcessDetailLogDb {


    /*
    获取今天计划中训练动态
     */
    public static List<PlanProcessDetailLog> getTrainingNews(String progressId,String trainingId,String lastModifyTime,int pageSize,boolean desc) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = sdf.format(new Date());

        String selectString ="select ppdl.* from plan_process_detail_log ppdl" +
                " where  ppdl.process_id ="+progressId+" and ppdl.training_id="+trainingId+" and ppdl.read_date= '"+todayString+"'" +
                (StringUtils.isNotBlank(lastModifyTime)?"  and UNIX_TIMESTAMP(ppdl.last_modify_time)*1000<"+lastModifyTime:"") +
                " ORDER BY ppdl.ppdl_id "+(desc?" desc ":"")+" limit "+pageSize;

        List<PlanProcessDetailLog> planProcessDetailLogs = DbUtil.selectList(Config.YCDBConfig,selectString,PlanProcessDetailLog.class);

        return planProcessDetailLogs;
    }


    /*
   获取训练动态
    */
    public static List<PlanProcessDetailLog> getTrainingNews(String trainingId,String lastModifyTime,int pageSize,String createDate,boolean desc) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = sdf.format(new Date());


        String selectString ="select ppdl.* from plan_process_detail_log ppdl" +
                " where  ppdl.training_id="+trainingId+
                (StringUtils.isNotBlank(lastModifyTime)?"   and UNIX_TIMESTAMP(ppdl.last_modify_time)*1000<"+lastModifyTime:"") +
                " and ppdl.finish_read_rate_today is not NULL " +
                " and ppdl.finish_read_rate_today != 0  "+
                " and ppdl.create_time>'"+createDate+"' "+
                " GROUP BY  ppdl.cust_id, ppdl.training_id, ppdl.read_date "+
                " ORDER BY ppdl.last_modify_time "+(desc?" desc ":"")+" limit "+pageSize;

        List<PlanProcessDetailLog> planProcessDetailLogs = DbUtil.selectList(Config.YCDBConfig,selectString,PlanProcessDetailLog.class);

        return planProcessDetailLogs;
    }


    /*
 获取训练排名
  */
    public static List<ReadTrainingTopInfo> getReadTrainingWeekRankList(String processId,String trainingId) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = sdf.format(new Date());

        Date date = new Date((new Date().getTime()/1000-(7*24*60*60))*1000);
        String weekDate = sdf.format(date);


        String selectString ="    select\n" +
                "        ppdl.cust_id as custId, ppdl.process_id as processId, ppdl.training_id trainingId , ppdl.media_id mediaId, ppdl.finish_read_rate_today todayFinishRate, max(ppdl.last_modify_time) as last_modify_time , ppdl.description ,sum(ppdl.finish_read_rate_today) as weekFinishRate,sum(ppdl.read_time) as totalWeekReadTime\n" +
                "    from\n" +
                "        plan_process_detail_log ppdl \n" +
                "    where\n" +
                "\t\t\t\tppdl.process_id=\n" +processId+
                "        and ppdl.training_id= \n" +trainingId+
                "\t\t\t\tand ppdl.read_date > '"+weekDate+"'\n" +
                "\t\t\t\tand ppdl.read_date <= '"+todayString+"'    \n" +
                "    GROUP BY ppdl.cust_id,ppdl.training_id ,ppdl.process_id \n" +
                "\t\thaving (weekFinishRate is not NULL  and weekFinishRate != 0 ) \n" +
                "  \torder by ppdl.training_id ,weekFinishRate desc,  totalWeekReadTime ASC ;\n";

        List<ReadTrainingTopInfo> planProcessDetailLogs = DbUtil.selectList(Config.YCDBConfig,selectString,ReadTrainingTopInfo.class);

        return planProcessDetailLogs;
    }


    /*
    获取今天计划中训练动态
     */
    public static PlanProcessDetailLog getTodayPlanProcessLog(String custId,String progressId,String trainingId) throws Exception {

        String selectString ="SELECT * from plan_process_detail_log WHERE cust_id="+custId+" and process_id="+progressId+" and training_id="+trainingId+" and DATE_FORMAT(read_date,'%y-%m-%d')=DATE_FORMAT(Now(),'%y-%m-%d')";

        List<PlanProcessDetailLog> planProcessDetailLogs = DbUtil.selectList(Config.YCDBConfig,selectString,PlanProcessDetailLog.class);
        if(planProcessDetailLogs.size()==0){
            return null;
        }
        else
            return planProcessDetailLogs.get(0);
    }

    /*
    获取今天计划中训练动态
     */
    public static List<PlanProcessDetailLog> getTodayPlanProcessLog(String custId,List<String> progressIds,String trainingId) throws Exception {

        String selectString ="SELECT * from plan_process_detail_log WHERE cust_id="+custId+" and process_id in("+StringUtils.join(progressIds,",")+") and training_id="+trainingId+" and DATE_FORMAT(read_date,'%y-%m-%d')=DATE_FORMAT(Now(),'%y-%m-%d')";

        List<PlanProcessDetailLog> planProcessDetailLogs = DbUtil.selectList(Config.YCDBConfig,selectString,PlanProcessDetailLog.class);
        return planProcessDetailLogs;
    }

    /*
 获取今天计划中训练动态
  */
    public static UserTrainingReadProgress getUserTrainingProgress(String custId,String trainingId,Date readDate) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String readDateStr = sdf.format(readDate);

        String selectString ="select ppd.ppd_id as ppdId, ppd.cust_id as custId, ppd.training_id as trainingId, ppd.media_id as mediaId, ppd.finish_read_rate as totalFinishRate, ppd.chapter_start as chapterStart, ppd.chapter_offset_start as chapterOffsetStart,\n" +
                "    ppd.chapter, ppd.chapter_offset as chapterOffset, ppd.last_syn_time as lastSynTime, ppd.read_detail as readDetail, ppd.status, ppdl.finish_read_rate_today as todayFinishRate\n" +
                "    from plan_process_detail as ppd left join plan_process_detail_log as ppdl on ppd.process_id = ppdl.process_id and ppd.training_id=ppdl.training_id" +
                "    where ppd.cust_id = "+custId+" and ppd.training_id ="+trainingId+" and ppdl.read_date = '"+readDateStr+"'";

        List<UserTrainingReadProgress> planProcessDetailLogs = DbUtil.selectList(Config.YCDBConfig,selectString,UserTrainingReadProgress.class);
        if(planProcessDetailLogs.size()==0){
            return null;
        }
        else
            return planProcessDetailLogs.get(0);
    }





}
