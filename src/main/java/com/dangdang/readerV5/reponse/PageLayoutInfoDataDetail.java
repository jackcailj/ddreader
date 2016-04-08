package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.MediaDigest;

import java.util.Date;
import java.util.List;

/**
 * Created by cailianjie on 2016-3-25.
 */
public class PageLayoutInfoDataDetail {
    String block;

    HomePageBannerList blockObject;

    List<ChannelList> channelList;

    List<SquareInfo> squareInfo;

    List<TagLinkInfo>  taglinkList;

    List<HomePageDigest>  digestList;

    List<HomePagePostInfos>  BookReviewList;

    String columnCode;
    Long columnEndTime;
    Integer columnType;
    Long count;
    Date currentDate;
    String icon;
    Boolean isShowHorn;
    String name;
    Long systemDate;
    String tips;
    Integer total;

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public HomePageBannerList getBlockObject() {
        return blockObject;
    }

    public void setBlockObject(HomePageBannerList blockObject) {
        this.blockObject = blockObject;
    }

    public String getColumnCode() {
        return columnCode;
    }

    public void setColumnCode(String columnCode) {
        this.columnCode = columnCode;
    }

    public Long getColumnEndTime() {
        return columnEndTime;
    }

    public void setColumnEndTime(Long columnEndTime) {
        this.columnEndTime = columnEndTime;
    }

    public Integer getColumnType() {
        return columnType;
    }

    public void setColumnType(Integer columnType) {
        this.columnType = columnType;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getShowHorn() {
        return isShowHorn;
    }

    public void setShowHorn(Boolean showHorn) {
        isShowHorn = showHorn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSystemDate() {
        return systemDate;
    }

    public void setSystemDate(Long systemDate) {
        this.systemDate = systemDate;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


    public List<ChannelList> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<ChannelList> channelList) {
        this.channelList = channelList;
    }

    public List<SquareInfo> getSquareInfo() {
        return squareInfo;
    }

    public void setSquareInfo(List<SquareInfo> squareInfo) {
        this.squareInfo = squareInfo;
    }

    public List<TagLinkInfo> getTaglinkList() {
        return taglinkList;
    }

    public void setTaglinkList(List<TagLinkInfo> taglinkList) {
        this.taglinkList = taglinkList;
    }


    public List<HomePageDigest> getDigestList() {
        return digestList;
    }

    public void setDigestList(List<HomePageDigest> digestList) {
        this.digestList = digestList;
    }

    public List<HomePagePostInfos> getBookReviewList() {
        return BookReviewList;
    }

    public void setBookReviewList(List<HomePagePostInfos> bookReviewList) {
        BookReviewList = bookReviewList;
    }
}
