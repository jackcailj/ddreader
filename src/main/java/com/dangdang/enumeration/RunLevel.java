package com.dangdang.enumeration;

/**
 * Created by cailianjie on 2015-11-11.
 *
 */
public enum RunLevel {
    FAST("fast"),//运行 status code为0的用例，即正常流程
    ALL("all"); //运行所有用例

    String content="";

    RunLevel(String type){
        content=type;
    }
}
