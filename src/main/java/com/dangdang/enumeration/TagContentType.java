package com.dangdang.enumeration;

/**
 * Created by cailianjie on 2016-2-3.
 */
public enum  TagContentType {
    CHANNEL("4000"),
    ARTICLE("5000"),
    BAR("1000"),
    GONGLUE("7000"),
    PLAN("8000"),
    TRAINING("9000"),
    MEDIA("6000");

    String content="";

    TagContentType(String id){
        content=id;
    }


    @Override
    public String toString() {
        return content;
    }
}
