package com.dangdang.readerV5.reponse;

import com.dangdang.db.account.IntegralItem;

/**
 * Created by cailianjie on 2015-6-17.
 */
public class UserInfo {
    Integer barOwnerLevel;
    Integer channelOwner;
    Integer bookFriend;
    Long custId;
    String custImg;
    Long displayId;
    Integer gender;
    Integer goldNum;
    Integer goldNumIos;
    Integer level;
    String nickName;
    Integer silverNum;
    Integer silverNumIos;

    public Integer getBookFriend() {
        return bookFriend;
    }

    public void setBookFriend(Integer bookFriend) {
        this.bookFriend = bookFriend;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustImg() {
        return custImg;
    }

    public void setCustImg(String custImg) {
        this.custImg = custImg;
    }

    public Long getDisplayId() {
        return displayId;
    }

    public void setDisplayId(Long displayId) {
        this.displayId = displayId;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getGoldNum() {
        return goldNum;
    }

    public void setGoldNum(Integer goldNum) {
        this.goldNum = goldNum;
    }

    public Integer getGoldNumIos() {
        return goldNumIos;
    }

    public void setGoldNumIos(Integer goldNumIos) {
        this.goldNumIos = goldNumIos;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSilverNum() {
        return silverNum;
    }

    public void setSilverNum(Integer silverNum) {
        this.silverNum = silverNum;
    }

    public Integer getSilverNumIos() {
        return silverNumIos;
    }

    public void setSilverNumIos(Integer silverNumIos) {
        this.silverNumIos = silverNumIos;
    }


    public Integer getBarOwnerLevel() {
        return barOwnerLevel;
    }

    public void setBarOwnerLevel(Integer barOwnerLevel) {
        this.barOwnerLevel = barOwnerLevel;
    }

    public Integer getChannelOwner() {
        return channelOwner;
    }

    public void setChannelOwner(Integer channelOwner) {
        this.channelOwner = channelOwner;
    }
}
