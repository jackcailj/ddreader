package com.dangdang.db.comment;

import com.dangdang.BaseComment.meta.CloudExperienceInfo;
import com.dangdang.BaseComment.meta.CloudReadingTime;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.personal_center.cloud_sync_read.CloudExperienceInfoEx;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by cailianjie on 2016-1-19.
 */
public class CloudExperienceInfoDb {


    /*
    获取指定类型的阅历数据
    Args:
        custId:用户custId
        typeList:ExperienceEnum定义的阅历类型
    return:
        返回阅历相关数据列表
     */
    public static List<CloudExperienceInfo> getCloudExperienceInfo(String custId,String typeList) throws Exception {

        String selectString="select * from cloud_experience_info where cust_id="+custId + (StringUtils.isEmpty(typeList)?"":" and type in("+typeList+")") +"  ORDER BY record_time DESC";
        List<CloudExperienceInfo> cloudReadingProgresses = DbUtil.selectList(Config.BSAECOMMENT,selectString,CloudExperienceInfo.class);

        return cloudReadingProgresses;
    }

    /*
    获取指定类型的阅历数据
    Args:
        custId:用户custId
        typeList:ExperienceEnum定义的阅历类型
    return:
        返回阅历相关数据列表
     */
    public static List<CloudExperienceInfoEx> getCloudExperienceInfo(String custId, String typeList, int PageSize,Long lastRecordTime) throws Exception {

        String selectString="select experience_id as id,cust_id as custId,product_id as productId,record_time as recordTime,type,remarks,device_type as deviceType from cloud_experience_info where cust_id="+custId +((lastRecordTime==null || lastRecordTime==0)?"":" and record_time<"+lastRecordTime)+ (StringUtils.isEmpty(typeList)?"":" and type in("+typeList+")") +"  ORDER BY record_time desc limit "+PageSize ;
        List<CloudExperienceInfoEx> cloudReadingProgresses = DbUtil.selectList(Config.BSAECOMMENT,selectString,CloudExperienceInfoEx.class);

        return cloudReadingProgresses;
    }


    /*
    获取最早一条阅历数据
    Args:
        custId:用户custId
        typeList:ExperienceEnum定义的阅历类型
    return:
        返回阅历相关数据列表
     */
    public static CloudExperienceInfoEx getFirstCloudExperienceInfo(String custId) throws Exception {

        String selectString="select experience_id as id,cust_id as custId,product_id as productId,record_time as recordTime,type,remarks,device_type as deviceType from cloud_experience_info where cust_id="+custId+"    ORDER BY record_time ASC  limit 1" ;
        List<CloudExperienceInfoEx> cloudReadingProgresses = DbUtil.selectList(Config.BSAECOMMENT,selectString,CloudExperienceInfoEx.class);
        if(cloudReadingProgresses.size()==0){
            return null;
        }
        return cloudReadingProgresses.get(0);
    }

}
