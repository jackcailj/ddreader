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
    public  static String getMediaId(BookType bookType,BookStatus bookStatus) throws Exception {
        String shelfStatus=bookStatus==BookStatus.VALID?"1":"0";
        String selectString="select m.* from media m left join media_sale ms on m.sale_id=ms.sale_id where ms.shelf_status="+shelfStatus+" and m.shelf_status="+shelfStatus+" and "+(bookType==BookType.EBOOK?"doc_type='ebook'":"doc_type is null")+" limit 1";
        Media media = DbUtil.selectOne(com.dangdang.config.Config.YCDBConfig, selectString, Media.class);
        return media.getMediaId().toString();
    }

    /*
    获取免费书籍Id
     */
    public  static String getFreeMediaId(BookType bookType,BookStatus bookStatus) throws Exception {
        String shelfStatus=bookStatus==BookStatus.VALID?"1":"0";
        //String selectString="select m.* from media m left join media_sale ms on m.sale_id=ms.sale_id where ms.price=0 and ms.shelf_status="+shelfStatus+" and m.shelf_status="+shelfStatus+" and "+(bookType==BookType.EBOOK?"doc_type='ebook'":"doc_type is null")+" limit 1";
        String selectString="select * from media_activity_sale mas left join media m on m.sale_id=mas.sale_id where m.shelf_status="+bookStatus+" and mas.sale_price=0" +" and "+(bookType==BookType.EBOOK?"doc_type='ebook'":"doc_type is null")+" limit 1";
        Media media = DbUtil.selectOne(com.dangdang.config.Config.YCDBConfig, selectString, Media.class);
        return media.getMediaId().toString();
    }
}
