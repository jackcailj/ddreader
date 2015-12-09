package com.dangdang.db.digital;

import com.dangdang.autotest.common.PlatForm;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaChapter;

import java.util.List;

/**
 * Created by cailianjie on 2015-12-8.
 */
public class MediaChapterDb {


    /*
    获取章节id>chapterId的剩余付费章节数
     */
    public static List<MediaChapter> GetBookChapterLast(String chapterId) throws Exception {

        String selectString="select * from media_chapter where media_id=(select media_id from media_chapter where id ="+chapterId+" ) and id>"+chapterId+" and is_free=0";
        List<MediaChapter> chapters = DbUtil.selectList(Config.YCDBConfig,selectString,MediaChapter.class);
        return chapters;
    }

}
