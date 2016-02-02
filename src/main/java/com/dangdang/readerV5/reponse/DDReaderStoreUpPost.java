package com.dangdang.readerV5.reponse;

import java.util.Date;

/**
 * Created by cailianjie on 2015-8-1.
 */
public class DDReaderStoreUpPost {
    Integer cardType;
    Integer postId;
    String remark;
    Date storeDateLong;
    String title;
    Integer type;

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getStoreDateLong() {
        return storeDateLong;
    }

    public void setStoreDateLong(Date storeDateLong) {
        this.storeDateLong = storeDateLong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
