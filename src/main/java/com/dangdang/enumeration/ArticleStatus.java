package com.dangdang.enumeration;

/**
 * Created by cailianjie on 2016-1-22.
 */
public enum  ArticleStatus {
    VALID("有效的文章"),
    DELETED("删除的文章");

    String content="";

    ArticleStatus(String id){
        content=id;
    }

    }
