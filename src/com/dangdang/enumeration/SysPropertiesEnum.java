package com.dangdang.enumeration;

/**
 * Created by cailianjie on 2015-12-8.
 */
public enum SysPropertiesEnum {

    BATCH_BUY_CHAPTER_COUNT("batch.buy.chapter.count");


    String content="";
    SysPropertiesEnum(String type){
        content= type;
    }

    @Override
    public String toString() {
        return content;
    }
}
