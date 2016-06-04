package com.dangdang.db.comment;

import com.dangdang.BaseComment.meta.Comment;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * Created by cailianjie on 2016-4-28.
 */
public class CommentDb {


    /*
    根据CommentId获取Comment信息
     */
    public static Comment getCommentById(String commentId) throws Exception {
        String selectString ="SELECT * from `comment` where comment_id="+commentId;

        Comment comment = DbUtil.selectOne(Config.BSAECOMMENT,selectString,Comment.class);

        return comment;
    }

}
