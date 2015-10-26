package com.dangdang.db.comment;

import com.dangdang.BaseComment.meta.CloudBookMark;
import com.dangdang.BaseComment.meta.CloudBookNote;
import com.dangdang.BaseComment.meta.CloudExperienceInfo;
import com.dangdang.BaseComment.meta.CloudReadingProgress;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-9-23.
 */
public class CloudSyncSql {

    /*
    获取某个人的某本书的阅读
     */
    public static CloudReadingProgress getCloudReadingProgress(String custId,String productId) throws Exception {
        String selectString="select * from cloud_reading_progress where cust_id="+custId+" and product_id="+productId;
        List<CloudReadingProgress> cloudReadingProgresses = DbUtil.selectList(Config.BSAECOMMENT,selectString,CloudReadingProgress.class);
        if(cloudReadingProgresses.size()>1){
            throw new Exception("getCloudReadingProgress异常:"+"返回了多条阅读记录，应只返回一条");
        }
        if(cloudReadingProgresses.size()==0){
            return null;
        }

        return cloudReadingProgresses.get(0);
    }

    /*
    获取一个人一本书的阅读进度
     */
    public static List<CloudReadingProgress> getCloudReadingProgresses(String custId,String productId) throws Exception {
        String selectString="select * from cloud_reading_progress where cust_id="+custId+" and product_id="+productId;
        List<CloudReadingProgress> cloudReadingProgresses = DbUtil.selectList(Config.BSAECOMMENT,selectString,CloudReadingProgress.class);

        return cloudReadingProgresses;
    }

    /*
    获取一个人标签列表
     */
    public static List<CloudBookMark> getCloudBookMarks(String custId,String productId) throws Exception {
        String selectString="select * from cloud_book_mark where cust_id="+custId+" and product_id="+productId+" and status=1";
        List<CloudBookMark> cloudBookMarks = DbUtil.selectList(Config.BSAECOMMENT,selectString,CloudBookMark.class);
        return cloudBookMarks;
    }

    /*
   获取一个人笔记列表
    */
    public static List<CloudBookNote> getCloudBookNotes(String custId,String productId) throws Exception {
        String selectString="select * from cloud_book_note where cust_id="+custId+" and product_id="+productId+" and status=1";
        List<CloudBookNote> cloudBookNotes = DbUtil.selectList(Config.BSAECOMMENT,selectString,CloudBookNote.class);
        return cloudBookNotes;
    }

    /*
    获取笔记数量
     */
    public static Integer getPersonalNoteCount(String custId) throws Exception {
        String selectString="select count(*) as count from cloud_book_note where cust_id="+custId+" and status=1";
        Map<String,Object> result= DbUtil.selectOne(Config.BSAECOMMENT, selectString);
        return Integer.parseInt(result.get("count").toString());
    }


    /*
    获取阅历数据
    Args:
        custId:用户custId
        pageSize:page页大小
        recordTime:操作时间，用来分页,null,表示从第一页开始
        isIncrement：true:降序，false:升序
     */
    public static List<CloudExperienceInfo> getExperienceInfos(String custId,Integer pageSize,Long recordTime, boolean isIncrement ) throws Exception {
        String selectString="select * from cloud_experience_info where cust_id="+custId+ (recordTime==null?"":(isIncrement?" and record_time<"+recordTime:" and record_time>"+recordTime)) + (isIncrement?" order by record_time desc":" order by record_time ") + "  limit "+pageSize;
        return DbUtil.selectList(Config.BSAECOMMENT,selectString,CloudExperienceInfo.class);
    }

}
