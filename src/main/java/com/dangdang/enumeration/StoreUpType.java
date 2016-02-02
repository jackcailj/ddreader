package com.dangdang.enumeration;

/**
 * Created by cailianjie on 2015-7-31.
 */
public enum StoreUpType {
    MEDIA("media"),
    DISCOVER("discover"),
    DIGEST("digest"),
    ARTICLE("article"),
    POST("post"),
    GONGLV("gonglv");

    String content="";
    StoreUpType(String type){
        content= type;
    }

    public String getDigestType(){
        if(content.equals("article")){
            return "2,3";
        }
        else if(content.equals("digest")){
            return "1";
        }
        else if(content.equals("post")){
            return "4";
        }
        else if(content.equals("gonglv")){
            return "5";
        }

        return "";
    }
}
