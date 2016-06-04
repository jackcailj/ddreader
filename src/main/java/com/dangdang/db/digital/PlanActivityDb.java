package com.dangdang.db.digital;

import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.PlanActivitiy;
import com.dangdang.readerV5.read_activitiy.PlanActivityVo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-27.
 */
public class PlanActivityDb {


    /*
    通过digestId获取读书活动信息
     */
    public static PlanActivitiy getPlanActivitiy(String digestId) throws Exception {
        String selectString = "select * from plan_activitiy where digest_id="+digestId;
        PlanActivitiy planActivitiy = DbUtil.selectOne(Config.YCDBConfig,selectString,PlanActivitiy.class);

        return planActivitiy;
    }


    /*
   通过活动id获取读书活动信息
    */
    public static List<PlanActivitiy> getPlanActivitiys(List<String> activityIds) throws Exception {
        String activityIdsStr=StringUtils.join(activityIds,",");
        String selectString = "select * from plan_activitiy where pa_id in("+activityIdsStr +") order by field(pa_id,"+activityIdsStr+")";
        List<PlanActivitiy> planActivitiys = DbUtil.selectList(Config.YCDBConfig,selectString,PlanActivitiy.class);

        return planActivitiys;
    }
}
