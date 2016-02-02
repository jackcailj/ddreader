package com.dangdang.enumeration;

import java.util.EnumMap;

/**
 * Created by cailianjie on 2016-1-19.
 */
public enum  ExperienceEnum {

    READ_BOOK("1"),
    READ_COMPLETE_BOOK("2"),
    SHARE_BOOK("3"),
    SHARE_NOTE("4"),
    HUAXIAN("5"),
    NOTE("6"),
    FATIE("7"),
    BUY("8"),
    JIEYUE("9"),
    SHARE_ZHUANTI("10"),
    SHARE_PINDAO("11"),
    SHARE_GONGLUE("12"),
    SHARE_ARTICLE("13"),
    SHARE_SHUDAN("14"),
    SHARE_BAR("15"),
    SHARE_TIEZI("16"),
    PUBLISH_GONGLUE("17"),
    PUBLISH_ARTICLE("18"),
    CREATE_CHANNEL("19"),
    CREATE_BAR("20"),
    SHENQING_BAR_OWNER("21"),
    SUB_CHANNEL("22"),
    JOIN_BAR("23"),
    VOTE("24"),
    ZENGSONG("25");


    String content="";

    ExperienceEnum(String id){
        content=id;
    }

    /*
    获取分享行为enum组
    return：格式为   3,4

     */
    public static  String getShare(){
        return "3,4,10,11,12,13,14,15,16";
    }

    @Override
    public String toString() {
        return content;
    }
}
