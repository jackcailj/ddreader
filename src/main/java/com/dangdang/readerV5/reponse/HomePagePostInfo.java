package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2016-3-29.
 */
public class HomePagePostInfo {


    Long barId;
    Integer commentNum;
    String content;
    List<String> imgList;
    Integer isPraise;
    Integer isResolve;
    Integer isTop;
    Integer isWonderful;
    Long lastModifiedDateMsec;
    Long mediaDigestId;
    Integer praiseNum;
    String title;
    Integer type;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getBarId() {
        return barId;
    }

    public void setBarId(Long barId) {
        this.barId = barId;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public Integer getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(Integer isPraise) {
        this.isPraise = isPraise;
    }

    public Integer getIsResolve() {
        return isResolve;
    }

    public void setIsResolve(Integer isResolve) {
        this.isResolve = isResolve;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Integer getIsWonderful() {
        return isWonderful;
    }

    public void setIsWonderful(Integer isWonderful) {
        this.isWonderful = isWonderful;
    }

    public Long getLastModifiedDateMsec() {
        return lastModifiedDateMsec;
    }

    public void setLastModifiedDateMsec(Long lastModifiedDateMsec) {
        this.lastModifiedDateMsec = lastModifiedDateMsec;
    }

    public Long getMediaDigestId() {
        return mediaDigestId;
    }

    public void setMediaDigestId(Long mediaDigestId) {
        this.mediaDigestId = mediaDigestId;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
