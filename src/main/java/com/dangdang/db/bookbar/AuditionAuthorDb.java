package com.dangdang.db.bookbar;

import com.dangdang.bookbar.meta.AuditionAuthor;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

import java.util.List;

/**
 * Created by cailianjie on 2016-1-4.
 */
public class AuditionAuthorDb {

    /*
    获取评选作家详细信息
     */
    public static AuditionAuthor getAuditionAuthorInfo(String authorId) throws Exception {
        String selectString ="select * from audition_author where author_id="+authorId;
        AuditionAuthor auditionAuthor = DbUtil.selectOne(Config.BOOKBARDBConfig,selectString,AuditionAuthor.class);

        return auditionAuthor;
    }

    /*
       获取评选作家列表，返回100个
        */
    public static List<AuditionAuthor> getAuditionAuthorList() throws Exception {
        String selectString ="select * from audition_author  limit 100";
        List<AuditionAuthor> auditionAuthors = DbUtil.selectList(Config.BOOKBARDBConfig,selectString,AuditionAuthor.class);

        return auditionAuthors;
    }

}
