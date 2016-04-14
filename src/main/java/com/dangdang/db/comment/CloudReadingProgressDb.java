package com.dangdang.db.comment;

import com.dangdang.BaseComment.meta.CloudReadingInterval;
import com.dangdang.BaseComment.meta.CloudReadingProgress;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

import java.util.List;

/**
 * Created by cailianjie on 2016-3-24.
 */
public class CloudReadingProgressDb {

    /*
    获取用户最后阅读的一本书
     */
    public static Long getLastReadBook(String custId) throws Exception {
        String selectString="select * from cloud_reading_progress where cust_id="+custId+" order by last_modified_time desc limit 1";
        List<CloudReadingProgress> cloudReadingProgresses = DbUtil.selectList(Config.BSAECOMMENT,selectString,CloudReadingProgress.class);
        if(cloudReadingProgresses.size()==0){
            return null;
        }
        else
            return cloudReadingProgresses.get(0).getProductId();
    }
}
