package com.dangdang.digital;

/**
 * Created by cailianjie on 2015-7-7.
 */
public enum BookType {
    EBOOK("出版物"),
    YUANCHUANG("原创"),
    FONT("字体"),
    SHIDU("试读本");

    String content="";


    BookType(String type){
        content=type;
    }

    public String getMediaSqlFilter(){
        if(content.equals("出版物")){
            return " doc_type='ebook' ";
        }

        if(content.equals("原创")){
            return " doc_type is null ";
        }

        if(content.equals("字体")){
            return " uid like 'zt%' ";
        }

        if(content.equals("试读本")){
            return " uid like 'br%' ";
        }

        return "";
    }
}
