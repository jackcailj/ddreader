package com.dangdang.digital;

/**
 * Created by cailianjie on 2015-7-7.
 */
public enum BookType {
    EBOOK("出版物"),
    YUANCHUANG("原创");

    String content="";

    BookType(String type){
        content=type;
    }
}
