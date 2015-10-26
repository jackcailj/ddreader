package com.dangdang.readerV5.reponse;

import com.dangdang.ddframework.dataverify.verify_annotation.NotNull;

public class Channel {
	ChannelBookList bookList;
	@NotNull
	String channelId;
	//List<ChannelMonthlyStrategy> channelMonthlyStrategy;
	String description;
	@NotNull
	String hasArtical;
	@NotNull
	String hasBoughtMonthly;
	@NotNull
	String icon;
	@NotNull
	String isAllowMonthly;
	@NotNull
	String isSub;
	//String monthlyType;
	//String ownder;
	//String ownerCustId;
	@NotNull
	String ownerType;
	//String shelfStatus;
	@NotNull
	String subNumber;
	@NotNull
	String title;

	public ChannelBookList getBookList() {
		return bookList;
	}

	public void setBookList(ChannelBookList bookList) {
		this.bookList = bookList;
	}
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHasArtical() {
		return hasArtical;
	}
	public void setHasArtical(String hasArtical) {
		this.hasArtical = hasArtical;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIsSub() {
		return isSub;
	}
	public void setIsSub(String isSub) {
		this.isSub = isSub;
	}
//	public String getOwnder() {
//		return ownder;
//	}
//	public void setOwnder(String ownder) {
//		this.ownder = ownder;
//	}
	public String getSubNumber() {
		return subNumber;
	}
	public void setSubNumber(String subNumber) {
		this.subNumber = subNumber;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getHasBoughtMonthly() {
		return hasBoughtMonthly;
	}

	public void setHasBoughtMonthly(String hasBoughtMonthly) {
		this.hasBoughtMonthly = hasBoughtMonthly;
	}

	public String getIsAllowMonthly() {
		return isAllowMonthly;
	}

	public void setIsAllowMonthly(String isAllowMonthly) {
		this.isAllowMonthly = isAllowMonthly;
	}

//	public String getMonthlyType() {
//		return monthlyType;
//	}
//	
//	public void setMonthlyType(String monthlyType) {
//		this.monthlyType = monthlyType;
//	}

	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
//	public List<ChannelMonthlyStrategy> getChannelMonthlyStrategy() {
//		return channelMonthlyStrategy;
//	}
//
//	public void setChannelMonthlyStrategy(
//			List<ChannelMonthlyStrategy> channelMonthlyStrategy) {
//		this.channelMonthlyStrategy = channelMonthlyStrategy;
//	}
}
