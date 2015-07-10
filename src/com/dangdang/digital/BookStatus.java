package com.dangdang.digital;

/**
 * Created by cailianjie on 2015-7-7.
 */
public enum BookStatus {
    VALID("有效书籍"),
    XIAJIA("下架书籍");

    String content="";

    BookStatus(String type){
        content=type;
    }
}
