package com.dangdang.enumeration;

/**
 * Created by cailianjie on 2016-4-6.
 */
public enum ReadPlanFeeStatus {
    ALL("2"),
    FREE("1"),
    NOTFREE("0");


    public String content="";

    ReadPlanFeeStatus(String status){
        content=status;
    }

    @Override
    public String toString() {
        return content;
    }
}
