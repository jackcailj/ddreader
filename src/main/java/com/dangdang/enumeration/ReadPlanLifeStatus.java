package com.dangdang.enumeration;

/**
 * Created by cailianjie on 2016-4-6.
 */
public enum ReadPlanLifeStatus {
    NOTSTART("0"),
    FINISHED("2"),
    INPROGRESS("1");


    public String content="";

    ReadPlanLifeStatus(String status){
        content=status;
    }

    @Override
    public String toString() {
        return content;
    }
}
