package com.dangdang.enumeration;

/**
 * Created by cailianjie on 2016-4-6.
 */
public enum  ReadPlanStatus {
    VALID("有效状态"),
    XIAJIA("下架状态");

    public String content="";

    ReadPlanStatus(String status){
        content=status;
    }

}
