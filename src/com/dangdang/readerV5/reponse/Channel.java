package com.dangdang.readerV5.reponse;

import java.util.List;

import com.dangdang.digital.model.ChannelMonthlyStrategy;

public class Channel {
	ChannelBookList bookList;
	String channelId;
	//List<ChannelMonthlyStrategy> channelMonthlyStrategy;
	String description;
	String hasArtical;
	String hasBoughtMonthly;
	String icon;
	String isAllowMonthly;
	String isSub;
	//String monthlyType;
	//String ownder;
	String ownerType;
	String subNumber;
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
