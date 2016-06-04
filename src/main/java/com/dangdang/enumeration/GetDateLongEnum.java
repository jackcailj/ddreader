package com.dangdang.enumeration;

/**
 * Created by cailianjie on 2016-4-18.
 */
public enum GetDateLongEnum {

    CUR("此刻时间"),
    ADD("增加"),
    SUB("减小"),
    DATE("日期");

    String content="";

    GetDateLongEnum(String id){
        content=id;
    }
}
