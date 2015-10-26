package com.dangdang.param.parse._enum;

/**
 * Created by cailianjie on 2015-10-12.
 */
public enum  PacketStatus {
    INPROGRESS("0"),
    COMPLETE("1"),
    INVALID("2");

    String content="";

    PacketStatus(String type){
        content=type;
    }

    @Override
    public String toString(){
        return content;
    }

}
