package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.enumeration.StoreUpType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-8-3.
 */
public class MediaDigestDb {

    /*
    获取我的正常帖子列表
     */
    public static List<MediaDigest> getMediaDigest(String custId, StoreUpType type,Integer lastMediadistId) throws Exception {
        String selectString="select id,author,media_id,media_chapter_id,media_name,bar_id,first_catetory_id,first_catetory_name,content,type*1 as type,column_id,column_name,stars,review_cnt,collect_cnt,share_cnt,\t\n" +
                "click_cnt,top_cnt,card_title,card_remark,card_type*1 as card_type,pic1_path,small_pic1_path,small_pic2_path,small_pic3_path,show_start_date,create_date,title,\n" +
                "is_show,is_del,sign_ids,day_or_night,mood,weight,operator,sort_page,is_paper_book from media_digest where type in ("+type.getDigestType()+") and author like \"%"+custId+"%\" and is_del=0 and is_show=1 "+(lastMediadistId==null?"":" and id>"+lastMediadistId) +" ORDER BY create_date desc limit "+10 ;
        List<MediaDigest> medias = DbUtil.selectList(Config.YCDBConfig, selectString, MediaDigest.class);
        return medias;
    }


    /*
    获取我创建的文章和帖子列表
     */
    public static List<MediaDigest> getMyCreateMediaDigest(String custId,int pageSize) throws Exception {
        String selectString="select id,author,media_id,media_chapter_id,media_name,bar_id,first_catetory_id,first_catetory_name,content,type*1 as type,column_id,column_name,stars,review_cnt,collect_cnt,share_cnt," +
                "click_cnt,top_cnt,card_title,card_remark,card_type*1 as card_type,pic1_path,small_pic1_path,small_pic2_path,small_pic3_path,show_start_date,create_date,title,"+
                "is_show,is_del,sign_ids,day_or_night,mood,weight,operator,sort_page,is_paper_book from media_digest where creator_cust_id ="+custId+" and is_del=0 and is_show=1 and type in (3,4) ORDER BY id DESC limit "+pageSize ;
        List<MediaDigest> mediaDigests = DbUtil.selectList(Config.YCDBConfig,selectString,MediaDigest.class);
        return mediaDigests;
    }
    
    /*
              获取某个文章或帖子信息
     */
    public static MediaDigest getMediaDigest(int id) throws Exception {
        String selectString="select id,author,media_id,media_chapter_id,media_name,bar_id,first_catetory_id,first_catetory_name,content,type*1 as type,column_id,column_name,stars,review_cnt,collect_cnt,share_cnt," +
                "click_cnt,top_cnt,card_title,card_remark,card_type*1 as card_type,pic1_path,small_pic1_path,small_pic2_path,small_pic3_path,show_start_date,create_date,title,"+
                "is_show,is_del,sign_ids,day_or_night,mood,weight,operator,sort_page,is_paper_book,creator_cust_id from media_digest where id="+id;
        MediaDigest mediaDigests = DbUtil.selectOne(Config.YCDBConfig,selectString,MediaDigest.class);
        return mediaDigests;
    }
    
    /**
     * 当前时间到上次更新时间之间的翻篇儿新增文章条数
     * 当前时间到上次更新时间之间的抢先读新增文章条数
     * @param
     *       type: 文章类型，1. 翻篇儿  2 抢先读
     *       time： 当前时间 
     * */    
    public static  List<MediaDigest> getNewDigest(String type, String time) throws Exception{
    	String selectString="select id,author,media_id,media_chapter_id,media_name,bar_id,first_catetory_id,first_catetory_name,content,type*1 as type,column_id,column_name,stars,review_cnt,collect_cnt,share_cnt," +
                "click_cnt,top_cnt,card_title,card_remark,card_type*1 as card_type,pic1_path,small_pic1_path,small_pic2_path,small_pic3_path,show_start_date,create_date,title,"+
                "is_show*1 as is_show,is_del*1 as is_del,sign_ids,day_or_night,mood*1,weight,operator,sort_page,is_paper_book*1 as is_paper_book from media_digest "
                + "where is_show=1 and is_del=0 and type="+type+" and last_update_date>'"+time+"'";
        List<MediaDigest> mediaDigests = DbUtil.selectList(Config.YCDBConfig,selectString, MediaDigest.class);
        return mediaDigests;
    }
    
    
    //Add guohaiying
    //获取频道中的文章
    //SELECT * FROM `media_digest` WHERE id IN (SELECT  digest_id FROM `channel_articles_digest` WHERE is_publish=1 AND channel_id IS NOT NULL AND `status` IN (0, 1, 2) ORDER BY articles_id);
    public static  List<String> getDigestId(String type) throws Exception{
    	int _type = Integer.valueOf(type);
    	String selectString="SELECT id FROM digital.media_digest WHERE type="+_type+" AND is_del=0";
        List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig,selectString);
        List ids = new ArrayList<String>();
        for(int i=0;i<infos.size();i++){
        	ids.add(infos.get(i).get("id").toString());
        }
        return ids;
        
    }
    
    //攻略
    //SELECT * FROM `comment_target_count` WHERE target_source=7000 ORDER BY browse_count DESC
    
    

}
