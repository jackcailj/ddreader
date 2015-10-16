package com.dangdang.readerV5.reponse;

/**
 * Created by cailianjie on 2015-10-15.
 */
public class PublistPostInfo {
    Long barId;
    String barName;
    Integer cardType;
    Long createDateLong;
    Long digestId;
    String from;
    Integer isDel;
    Integer isShow;
    String remark;
    String title;
    Integer type;

    public Long getBarId() {
        return barId;
    }

    public void setBarId(Long barId) {
        this.barId = barId;
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Long getCreateDateLong() {
        return createDateLong;
    }

    public void setCreateDateLong(Long createDateLong) {
        this.createDateLong = createDateLong;
    }

    public Long getDigestId() {
        return digestId;
    }

    public void setDigestId(Long digestId) {
        this.digestId = digestId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
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
