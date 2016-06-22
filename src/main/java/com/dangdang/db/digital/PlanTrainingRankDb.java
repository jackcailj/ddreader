package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.PlanTrainingRank;

import java.util.List;

/**
 * Created by cailianjie on 2016-6-16.
 */
public class PlanTrainingRankDb {

    public static PlanTrainingRank getWeekRank(String custId, String zhouyiDate) throws Exception {
        String selectString = "";

        List<PlanTrainingRank> planTrainingRanks = DbUtil.selectList(Config.YCDBConfig,selectString,PlanTrainingRank.class);

        if(planTrainingRanks.size()==0){
            return null;
        }
        return planTrainingRanks.get(0);
    }

}
