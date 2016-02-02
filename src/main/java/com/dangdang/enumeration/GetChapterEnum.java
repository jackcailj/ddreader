package com.dangdang.enumeration;

/**
 * Created by cailianjie on 2015-12-10.
 */
public enum GetChapterEnum {
    FREE_LAST("免费最后一个章节"),
    FU_FEI_LAST("频道背"),
    FREE("免费"),
    FU_FEI("频道背");

    String content="";

    GetChapterEnum(String id){
        content=id;
    }

}
