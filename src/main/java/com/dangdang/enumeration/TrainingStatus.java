package com.dangdang.enumeration;

/**
 * Created by cailianjie on 2016-5-18.
 */
public enum TrainingStatus {
    VALID("有效的"),
    XIAJIA("下架"),  //下架的训练
    UNJOIN("未参加"),//未参加的训练
    JOINED("参加"); //参加过的训练

    String content="";

    TrainingStatus(String id){
        content=id;
    }
}
