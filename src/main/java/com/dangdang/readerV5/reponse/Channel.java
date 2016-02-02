package com.dangdang.readerV5.reponse;

import java.util.List;

public class Channel {
	ChannelBookList bookList;
	Long channelId;
	String description;
	Integer digestNum;
	String hasArtical;	
	String hasBoughtMonthly;
	String icon;
	Integer isAllowMonthly;
	String isMine;
	String isSub;
	Integer monthlyType;
	String ownder;
	String ownerCustId;
	String ownerType;
	Integer shelfStatus;
	String tagIds;
	String tagNames;
	Integer subNumber;
	String title;
	List<MonthlyStrategy> channelMonthlyStrategy;
	UserBaseInfo userBaseInfo;
	public List<MonthlyStrategy> getChannelMonthlyStrategy() {
		return channelMonthlyStrategy;
	}
	public void setChannelMonthlyStrategy(List<MonthlyStrategy> channelMonthlyStrategy) {
		this.channelMonthlyStrategy = channelMonthlyStrategy;
	}
	public UserBaseInfo getUserBaseInfo() {
		return userBaseInfo;
	}
	public void setUserBaseInfo(UserBaseInfo userBaseInfo) {
		this.userBaseInfo = userBaseInfo;
	}
	public ChannelBookList getBookList() {
		return bookList;
	}
	public void setBookList(ChannelBookList bookList) {
		this.bookList = bookList;
	}
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDigestNum() {
		return digestNum;
	}
	public void setDigestNum(Integer digestNum) {
		this.digestNum = digestNum;
	}
	public String getHasArtical() {
		return hasArtical;
	}
	public void setHasArtical(String hasArtical) {
		this.hasArtical = hasArtical;
	}
	public String getHasBoughtMonthly() {
		return hasBoughtMonthly;
	}
	public void setHasBoughtMonthly(String hasBoughtMonthly) {
		this.hasBoughtMonthly = hasBoughtMonthly;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getIsAllowMonthly() {
		return isAllowMonthly;
	}
	public void setIsAllowMonthly(Integer isAllowMonthly) {
		this.isAllowMonthly = isAllowMonthly;
	}
	public String getIsSub() {
		return isSub;
	}
	public void setIsSub(String isSub) {
		this.isSub = isSub;
	}
	public Integer getMonthlyType() {
		return monthlyType;
	}
	public void setMonthlyType(Integer monthlyType) {
		this.monthlyType = monthlyType;
	}
	public String getOwnder() {
		return ownder;
	}
	public void setOwnder(String ownder) {
		this.ownder = ownder;
	}
	public String getOwnerCustId() {
		return ownerCustId;
	}
	public void setOwnerCustId(String ownerCustId) {
		this.ownerCustId = ownerCustId;
	}
	public String getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	public Integer getShelfStatus() {
		return shelfStatus;
	}
	public void setShelfStatus(Integer shelfStatus) {
		this.shelfStatus = shelfStatus;
	}
	public Integer getSubNumber() {
		return subNumber;
	}
	public void setSubNumber(Integer subNumber) {
		this.subNumber = subNumber;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsMine() {
		return isMine;
	}
	public void setIsMine(String isMine) {
		this.isMine = isMine;
	}
	public String getTagIds() {
		return tagIds;
	}
	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}
	public String getTagNames() {
		return tagNames;
	}
	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}
}
