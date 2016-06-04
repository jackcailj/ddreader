package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaTraining;
import com.dangdang.digital.meta.PlanProcess;
import com.dangdang.digital.meta.PlanProcessDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2016-4-19.
 */
public class PlanProcessDetailDb {

    /*
    根据id获取planProcessDetail信息
     */
    public static List<PlanProcessDetail> getPlanProcessByProcessId(String processId) throws Exception {
        String selectString ="select * from plan_process_detail where process_id="+processId;
        List<PlanProcessDetail> planProcess = DbUtil.selectList(Config.YCDBConfig,selectString,PlanProcessDetail.class);

        return planProcess;
    }


    /*
   根据id获取训练信息
    */
    public static List<MediaTraining> getTrainings(String processId) throws Exception {
        String selectString ="select mt.* from plan_process_detail ppd \n" +
                "left join media_training mt on ppd.training_id=mt.mt_id\n" +
                "where ppd.process_id="+processId;
        List<MediaTraining> trainings = DbUtil.selectOne(Config.YCDBConfig,selectString,MediaTraining.class);

        return trainings;
    }



    /*
  获取所有计划训练信息
   */
    public static List<UserReadProgressTrainingInfoEx> getAllUserReadProcessTrainings(String custId,String status) throws Exception {
        String selectString ="select pp.pp_id as progressId,pp.plan_id as planId,  pp.finish_read_rate as finishReadRate,p.name, p.desc,   pp.cust_id as custId,pp.`status`,p.img_url as imgUrl,ppd.chapter,ppd.chapter_offset as chapterOffset,ppd.chapter_offset_start as chapterOffsetStart,ppd.chapter_start as chapterStart,\n" +
                "ppd.last_syn_time as lastSynTime,ppd.media_id as mediaId,ppd.read_time as readTime,ppd.finish_read_rate as todayFinishRate,ppd.training_id as trainingId\n" +
                "from plan_process as pp \n" +
                "left join  plan as p on p.plan_id = pp.plan_id \n" +
                "LEFT JOIN plan_process_detail ppd on ppd.process_id=pp.pp_id\n" +
                "where   pp.cust_id = "+custId+" and pp.status in (  "+status+"  ) and ppd.training_id is not null "+(status.trim().equals("1")?" order by pp.pp_id , ppd.training_id ":" order by pp.finish_read_rate asc, pp.pp_id, ppd.training_id " );
        List<UserReadProgressTrainingInfoEx> trainings = DbUtil.selectList(Config.YCDBConfig,selectString,UserReadProgressTrainingInfoEx.class);

        return trainings;
    }

    /*
    获取所有训练所属的所有未结束的计划
     */
    public static List<PlanProcessDetail> getUserPlanProcessByTrainingId(String custId,String trainingId) throws Exception {
        String selectString ="select * from plan_process_detail where cust_id="+custId+" and training_id="+trainingId+" and `status`!=2";
        List<PlanProcessDetail> trainings = DbUtil.selectList(Config.YCDBConfig,selectString,PlanProcessDetail.class);

        return trainings;
    }



    /*
   获取所有训练所属的所有未结束的计划
    */
    public static List<PlanProcessDetail> getTrainingCelebrateInfo(Map<String,Object> param) throws Exception {
        String selectString ="select * from plan_process_detail where ";

        selectString += DbUtil.ConvertFilterString(param);

        selectString+=" "+"order by finish_read_rate desc, to_days(end_time)-to_days(start_time) asc";

        List<PlanProcessDetail> trainings = DbUtil.selectList(Config.YCDBConfig,selectString,PlanProcessDetail.class);

        return trainings;
    }

}
