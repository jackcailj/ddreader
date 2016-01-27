package com.dangdang.db.comment;

import com.dangdang.BaseComment.meta.CloudReadingInterval;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.personal_center.cloud_sync_read.CloudExperienceInfoEx;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by cailianjie on 2016-1-21.
 */
public class CloudReadingIntervalDb {

    /*
   获取阅读时间
   Args:
       custId:用户custId
       typeList:ExperienceEnum定义的阅历类型
   return:
       返回阅历相关数据列表
    */
    public static List<CloudReadingInterval> getCloudReadingInterval(String custId) throws Exception {

        String selectString="select * from cloud_reading_interval where cust_id="+custId ;
        List<CloudReadingInterval> cloudReadingProgresses = DbUtil.selectList(Config.BSAECOMMENT,selectString,CloudReadingInterval.class);

        return cloudReadingProgresses;
    }
}
