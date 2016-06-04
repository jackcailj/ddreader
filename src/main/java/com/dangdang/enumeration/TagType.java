package com.dangdang.enumeration;

/**
 * Created by cailianjie on 2016-5-18.
 */
public enum TagType {

    PLANTAG("计划tag"),
    OFFICETAG("官方标签"),  //下架的训练
    CUSTOMTAG("自定义标签"); //参加过的训练

    String content="";

    TagType(String id){
        content=id;
    }
}
