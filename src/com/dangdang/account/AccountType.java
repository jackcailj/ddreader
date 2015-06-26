package com.dangdang.account;

/**
 * Created by cailianjie on 2015-6-26.
 */
public enum AccountType {
    MASTER("master"),
    ATTACH("attach");

    String content;
    AccountType(String type){
        content=type;
    }
}
