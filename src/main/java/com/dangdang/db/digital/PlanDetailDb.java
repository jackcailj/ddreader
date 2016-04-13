package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.PlanDetail;
import com.dangdang.readerV5.read_plan.PlanDetailInfo;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-13.
 */
public class PlanDetailDb {


    /*
    获取计划详情信息。
     */
    public static List<PlanDetailInfo> getPlanDetails(String planId) throws Exception {
        String selectString="select pd.pd_id as pdId,pd.plan_id as planId,pd.training_id as trainingId,pd.discount as discount,pd.order_index as orderIndex,pd.create_time as createTime,pd.media_id as mediaId,m.price*pd.discount as salePrice from plan_detail pd\n" +
                "LEFT JOIN media_training mt on pd.training_id=mt.mt_id\n" +
                "LEFT JOIN media m on mt.media_id=m.media_id\n" +
                "where plan_id=  "+planId;
        List<PlanDetailInfo> details = DbUtil.selectList(Config.YCDBConfig,selectString,PlanDetailInfo.class);

        return details;
    }
}
