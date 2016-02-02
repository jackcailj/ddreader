package com.dangdang.common.functional.login;

/**
 * Created by cailianjie on 2015-6-16.
 */
public enum DDLoginType {
    EMAIL("email"),
    PHONE("phone");

    String content="";
    DDLoginType(String name){
        content=name;
    }
}
