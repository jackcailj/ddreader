package com.dangdang.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaDigest;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-3.
 */
public class MediaDigestDb {

    /*
    获取正常我的正常帖子列表
     */
    public static List<MediaDigest> getMediaDigest(String custId, StoreUpType type) throws Exception {
        String selectString="select id,author,media_id,media_chapter_id,media_name,bar_id,first_catetory_id,first_catetory_name,content,type*1 as type,column_id,column_name,stars,review_cnt,collect_cnt,share_cnt,\t\n" +
                "click_cnt,top_cnt,card_title,card_remark,card_type,pic1_path,small_pic1_path,small_pic2_path,small_pic3_path,show_start_date,create_date,title,\n" +
                "is_show,is_del,sign_ids,day_or_night,mood,weight,operator,sort_page,is_paper_book from media_digest where type in ("+type.getDigestType()+") and author like \"%"+custId+"%\" and is_del=0 ORDER BY create_date desc";
        List<MediaDigest> medias = DbUtil.selectList(Config.YCDBConfig, selectString, MediaDigest.class);
        return medias;
    }
}
