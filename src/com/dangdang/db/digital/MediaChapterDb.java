package com.dangdang.db.digital;

import com.dangdang.autotest.common.PlatForm;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaChapter;
import com.dangdang.enumeration.GetChapterEnum;

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

    public static MediaChapter getBookChapter(String mediaId, GetChapterEnum status) throws Exception {
        String selectString="select * from media_chapter where media_id="+mediaId;
        if(status==GetChapterEnum.FREE_LAST){
            selectString+=" and is_free=1  ORDER BY id desc limit 1";
        }
        else if(status==GetChapterEnum.FU_FEI_LAST){
            selectString += " and is_free=0  ORDER BY id desc limit 1";
        }
        else if(status==GetChapterEnum.FREE){
            selectString += " and is_free=1 ORDER BY  limit 1";
        }
        else if(status==GetChapterEnum.FU_FEI){
            selectString += " and is_free=0   limit 1";
        }
        else{
            throw new Exception(status+"没有处理");
        }

        MediaChapter chapters = DbUtil.selectOne(Config.YCDBConfig,selectString,MediaChapter.class);
        return chapters;
    }

}
