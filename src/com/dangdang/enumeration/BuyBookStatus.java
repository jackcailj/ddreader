package com.dangdang.enumeration;

/**
 * Created by cailianjie on 2015-10-12.
 */
public enum  BuyBookStatus {
    BUY("购买书籍"),
    ZENGSONG("赠送书籍"),
    NOTFULL("非全本书籍"),
    FULL("全本书籍"),
    FONT("字体"),
    SENDEDBOOK("赠送的图书");

    String content="";

    BuyBookStatus(String type){
        content=type;
    }

    public String getBookStatusString() throws Exception {
        if(this==BuyBookStatus.BUY//自己购买的书籍
                ||this==NOTFULL
                ||this==FULL
                ||this==FONT){
            return "1001,1002";
        }
        else if(this == BuyBookStatus.ZENGSONG){//接收别人的书籍
            return "1004";
        }
        else if(this == BuyBookStatus.SENDEDBOOK){//送给别人的书籍
            return "9999";
        }

        throw  new Exception("BuyBookStatus不正确");
    }

    public Integer getAuthorifyType() throws Exception {
        if(this==BuyBookStatus.NOTFULL){
            return 2;
        }
        else if(this == BuyBookStatus.FULL
                ||this == BuyBookStatus.SENDEDBOOK
                ||this==ZENGSONG
                ||this==BUY
                ){
            return 1;
        }else if(this == BuyBookStatus.FONT){
            return 3;
        }

        throw  new Exception("BuyBookStatus不正确");
    }

}
