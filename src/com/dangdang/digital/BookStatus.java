package com.dangdang.digital;

import com.dangdang.config.Config;

/**
 * Created by cailianjie on 2015-7-7.
 */
public enum BookStatus {
    VALID("有效书籍"),
    XIAJIA("下架书籍"),
    VALID_FULL("完结的有效书籍"),
    VALID_NOTFULL("未完结的有效书籍");

    String content="";

    BookStatus(String type){
        content=type;
    }

    public String getShelfStatus(){
        if(content.equals("有效书籍")|| content.equals("完结的有效书籍")){
            return "1";
        }
        else if(content.equals("下架书籍") || content.equals("未完结的有效书籍")){
            return "2";
        }

        return "0";
    }

    public String getIsFull(){
        if(content.equals("完结的有效书籍")){
            return "1";
        }
        else if(content.equals("未完结的有效书籍")){
            return "0";
        }

        return " 1 ";
    }
}
