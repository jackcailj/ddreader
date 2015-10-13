package com.dangdang.reader.functional.param.parse._enum;

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

    public String toString(){
        return content;
    }

}
