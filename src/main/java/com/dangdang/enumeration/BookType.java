package com.dangdang.enumeration;

import com.dangdang.config.Config;

/**
 * Created by cailianjie on 2015-7-7.
 */
public enum BookType {
    EBOOK("出版物"),
    YUANCHUANG("原创"),
    FONT("字体"),
    SHIDU("试读本"),
    EBOOK_FREE("出版物免费"),
    YUANCHUANG_FREE("原创免费");

    String content="";


    BookType(String type){
        content=type;
    }

    public String getMediaSqlFilter(){
        if(content.equals("出版物")){
            return " (doc_type='ebook' or doc_type='drebook') and (m.promotion_id is null or m.promotion_id=0)  and mr.ID is not null  and mr.DEVICE_TYPE_CODE like '%"+ Config.getDevice().toString()+"%'";
        }

        if(content.equals("原创")){
            return " doc_type ='yc' and (m.promotion_id is null or m.promotion_id=0)  ";
        }

        if(content.equals("字体")){
            return " uid like 'zt%' ";
        }

        if(content.equals("试读本")){
            return " uid like 'br%'  and mr.ID is not null  and mr.DEVICE_TYPE_CODE like '%"+ Config.getDevice().toString()+"%'";
        }

        if(content.equals("出版物免费")){
            return " doc_type='drebook' and m.promotion_id =3  and mr.ID is not null  and mr.DEVICE_TYPE_CODE like '%"+ Config.getDevice().toString()+"%' ";
        }

        if(content.equals("原创免费")){
            return " doc_type is ='yc' and (m.promotion_id =3 )";
        }

        return "";
    }


    /*
    检测是否为试读本
     */
    public boolean isShiDu(String uid){
        if(uid.startsWith("br")){
            return true;
        }

        return false;
    }
}
