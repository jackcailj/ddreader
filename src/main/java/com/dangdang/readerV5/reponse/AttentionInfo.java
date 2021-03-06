package com.dangdang.readerV5.reponse;


import com.dangdang.ddframework.dataverify.verify_annotation.NotNull;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by cailianjie on 2016-3-24.
 */
public class AttentionInfo {
    @NotNull
    Integer barOwnerLevel;

    @NotNull
    Integer channelId;
    @NotNull
    Integer channelOwner;
    @NotNull
    String channelTitle;
    @NotNull
    Integer  commentCount;
    @NotNull
    Long createDateLong;
    @NotNull
    String custImg;
    @NotNull
    String custNickName;
    @NotNull
    Integer digestId;
    @NotNull
    String from;
    @NotNull
    Integer indexNum;
    @NotNull
    Integer isPraise;
    @NotNull
    Integer makeType;
    @NotNull
    String pic1Path;
    @NotNull
    Integer praiseCount;
    @NotNull
    String pubCustId;
    @NotNull
    String remark;
    @NotNull
    String title;
    @NotNull
    Integer type;

    public Integer getBarOwnerLevel() {
        return barOwnerLevel;
    }

    public void setBarOwnerLevel(Integer barOwnerLevel) {
        this.barOwnerLevel = barOwnerLevel;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getChannelOwner() {
        return channelOwner;
    }

    public void setChannelOwner(Integer channelOwner) {
        this.channelOwner = channelOwner;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Long getCreateDateLong() {
        return createDateLong;
    }

    public void setCreateDateLong(Long createDateLong) {
        this.createDateLong = createDateLong;
    }

    public String getCustImg() {
        return custImg;
    }

    public void setCustImg(String custImg) {
        this.custImg = custImg;
    }

    public String getCustNickName() {
        return custNickName;
    }

    public void setCustNickName(String custNickName) {
        this.custNickName = custNickName;
    }

    public Integer getDigestId() {
        return digestId;
    }

    public void setDigestId(Integer digestId) {
        this.digestId = digestId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Integer indexNum) {
        this.indexNum = indexNum;
    }

    public Integer getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(Integer isPraise) {
        this.isPraise = isPraise;
    }

    public Integer getMakeType() {
        return makeType;
    }

    public void setMakeType(Integer makeType) {
        this.makeType = makeType;
    }

    public String getPic1Path() {
        return pic1Path;
    }

    public void setPic1Path(String pic1Path) {
        this.pic1Path = pic1Path;
    }

    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getPubCustId() {
        return pubCustId;
    }

    public void setPubCustId(String pubCustId) {
        this.pubCustId = pubCustId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
