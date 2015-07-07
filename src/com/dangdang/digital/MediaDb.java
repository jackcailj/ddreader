package com.dangdang.digital;

import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Media;

/**
 * Created by cailianjie on 2015-7-7.
 */
public class MediaDb {

    /*
       获取一个有效的mediaId
    */
    public  static String getMediaId(BookType bookType) throws Exception {
        String selectString="select * from media where "+(bookType==BookType.EBOOK?"doc_type='ebook'":"doc_type is null")+" and shelf_status =1 limit 1";

        Media media = DbUtil.selectOne(com.dangdang.config.Config.YCDBConfig, selectString, Media.class);
        return media.getMediaId().toString();
    }
}
