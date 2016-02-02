package com.dangdang.db.comment;

import com.dangdang.BaseComment.meta.CloudReadingProgress;
import com.dangdang.BaseComment.meta.CloudReadingTime;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

import java.util.List;

/**
 * Created by cailianjie on 2016-1-19.
 */
public class CloudReadingTimeDb {

    /*
    获取用户阅读时长
      return：如果没有记录，返回null
     */
    public static CloudReadingTime getReadingTime(String custId) throws Exception {
        String selectString="select * from cloud_reading_time where cust_id="+custId;
        List<CloudReadingTime> cloudReadingProgresses = DbUtil.selectList(Config.BSAECOMMENT,selectString,CloudReadingTime.class);
        if(cloudReadingProgresses.size()>1){
            throw new Exception("cloud_reading_time中存在多条用户数据(id:"+custId+")");
        }

        if(cloudReadingProgresses.size()==1){
            return cloudReadingProgresses.get(0);
        }

        return null;
    }
}
