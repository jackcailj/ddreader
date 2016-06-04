package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.MediaTraining;
import com.dangdang.digital.meta.PlanProcess;
import com.dangdang.digital.meta.PlanProcessDetail;
import com.dangdang.enumeration.TrainingStatus;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-19.
 */
public class MediaTrainingDb {


    /*
    获取训练信息
     */
    public static List<MediaTraining> getTrainings(List<String> trainingIds,boolean isIn,Integer num) throws Exception {
        String trainingIdsStr=StringUtils.join(trainingIds,",");
        String selectString ="select * from media_training where mt_id "+(isIn?" in ":" not in ")+"("+trainingIdsStr+") order by field(mt_id,"+trainingIdsStr+")"
                +(num ==-1? " ":" limit "+num);
        List<MediaTraining> trainings = DbUtil.selectList(Config.YCDBConfig,selectString,MediaTraining.class);

        return trainings;
    }

    /*
    获取训练信息
     */
    public static List<MediaTraining> getTrainings(List<String> trainingIds,boolean isIn,Integer pageNo,Integer pageSize) throws Exception {
        String trainingIdsStr=StringUtils.join(trainingIds,",");
        String selectString ="select * from media_training where mt_id "+(isIn?" in ":" not in ")+"("+trainingIdsStr+") order by field(mt_id,"+trainingIdsStr+")"
                +" limit "+pageNo*pageSize+","+pageSize;
        List<MediaTraining> trainings = DbUtil.selectList(Config.YCDBConfig,selectString,MediaTraining.class);

        return trainings;
    }


    /*
    获取训练信息
     */
    public static List<MediaTraining> getTrainings(TrainingStatus status,Integer num,String custId) throws Exception {

        List<MediaTraining> trainings=null;
        if(status == TrainingStatus.VALID || status== TrainingStatus.XIAJIA) {
            String selectString = "select mt.* from media_training mt\n" +
                    "LEFT JOIN media m on mt.media_id=m.media_id\n" +
                    "LEFT JOIN media_sale ms on m.sale_id=ms.sale_id\n" +
                    "where ms.shelf_status="+(status==TrainingStatus.VALID?"1":"0")+ " limit "+num;
            trainings = DbUtil.selectList(Config.YCDBConfig, selectString, MediaTraining.class);
        }
        else if(status == TrainingStatus.JOINED || status == TrainingStatus.UNJOIN){
            //获取已经参加的训练
            List<UserReadProgressTrainingInfoEx> planProcessDetails = PlanProcessDetailDb.getAllUserReadProcessTrainings(custId,"0,1,2");
            List<String> traingIds = Util.getFields(planProcessDetails,"trainingId");


            trainings = MediaTrainingDb.getTrainings(traingIds, status==TrainingStatus.JOINED?true:false, num);

        }
        return trainings;
    }
}
