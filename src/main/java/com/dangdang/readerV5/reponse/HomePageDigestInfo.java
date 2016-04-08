package com.dangdang.readerV5.reponse;

import com.dangdang.ddframework.dataverify.verify_annotation.NotNull;
import com.dangdang.digital.meta.MediaDigest;

/**
 * Created by cailianjie on 2016-3-25.
 */
public class HomePageDigestInfo {

    String author;
    String cardRemark;
    String cardTitle;
    Integer clickCnt;
    Long createDate;
    Long createTime;
    Long creatorCustId;
    Long id;
    Integer isDel;
    Integer isHomepageToAll;
    Integer isPaperBook;
    Integer isShow;
    Integer isSupportReward;
    Long lastUpdateDate;
    Byte makeType;
    String pic1Path;
    Integer replyCnt;
    Long showStartDate;
    Long showStartDateYmd;
    Long showStartDateYmdLong;
    String showStartDateYmdString;
    Long sortPage;
    String title;
    Integer topCnt;
    Integer type;
    Integer weight;


    public HomePageDigestInfo(){}

    public HomePageDigestInfo(MediaDigest digest){
        author=digest.getAuthor();
        cardRemark=digest.getCardRemark();
        cardTitle=digest.getCardTitle();
        clickCnt=digest.getClickCnt();
        createDate=digest.getCreateDate()==null?null:digest.getCreateDate().getTime();
        creatorCustId=digest.getCreatorCustId();
        id=digest.getId();
        isDel=digest.getIsDel()?1:0;
        isShow=digest.getIsShow()?1:0;
        isSupportReward=digest.getIsSupportReward();
        lastUpdateDate=digest.getLastUpdateDate()==null?null:digest.getLastUpdateDate().getTime();
        makeType=digest.getMakeType();
        replyCnt=digest.getReviewCnt();
        topCnt=digest.getTopCnt();
        type=digest.getType();
        weight=digest.getWeight();


    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCardRemark() {
        return cardRemark;
    }

    public void setCardRemark(String cardRemark) {
        this.cardRemark = cardRemark;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public Integer getClickCnt() {
        return clickCnt;
    }

    public void setClickCnt(Integer clickCnt) {
        this.clickCnt = clickCnt;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getCreatorCustId() {
        return creatorCustId;
    }

    public void setCreatorCustId(Long creatorCustId) {
        this.creatorCustId = creatorCustId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getIsHomepageToAll() {
        return isHomepageToAll;
    }

    public void setIsHomepageToAll(Integer isHomepageToAll) {
        this.isHomepageToAll = isHomepageToAll;
    }

    public Integer getIsPaperBook() {
        return isPaperBook;
    }

    public void setIsPaperBook(Integer isPaperBook) {
        this.isPaperBook = isPaperBook;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getIsSupportReward() {
        return isSupportReward;
    }

    public void setIsSupportReward(Integer isSupportReward) {
        this.isSupportReward = isSupportReward;
    }

    public Long getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Byte getMakeType() {
        return makeType;
    }

    public void setMakeType(Byte makeType) {
        this.makeType = makeType;
    }

    public String getPic1Path() {
        return pic1Path;
    }

    public void setPic1Path(String pic1Path) {
        this.pic1Path = pic1Path;
    }

    public Integer getReplyCnt() {
        return replyCnt;
    }

    public void setReplyCnt(Integer replyCnt) {
        this.replyCnt = replyCnt;
    }

    public Long getShowStartDate() {
        return showStartDate;
    }

    public void setShowStartDate(Long showStartDate) {
        this.showStartDate = showStartDate;
    }

    public Long getShowStartDateYmd() {
        return showStartDateYmd;
    }

    public void setShowStartDateYmd(Long showStartDateYmd) {
        this.showStartDateYmd = showStartDateYmd;
    }

    public Long getShowStartDateYmdLong() {
        return showStartDateYmdLong;
    }

    public void setShowStartDateYmdLong(Long showStartDateYmdLong) {
        this.showStartDateYmdLong = showStartDateYmdLong;
    }

    public String getShowStartDateYmdString() {
        return showStartDateYmdString;
    }

    public void setShowStartDateYmdString(String showStartDateYmdString) {
        this.showStartDateYmdString = showStartDateYmdString;
    }

    public Long getSortPage() {
        return sortPage;
    }

    public void setSortPage(Long sortPage) {
        this.sortPage = sortPage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTopCnt() {
        return topCnt;
    }

    public void setTopCnt(Integer topCnt) {
        this.topCnt = topCnt;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
