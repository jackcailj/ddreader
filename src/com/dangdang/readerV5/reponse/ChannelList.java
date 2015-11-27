package com.dangdang.readerV5.reponse;

public class ChannelList {
	String channelId;
	String description;
	Integer hasBoughtMonthly;
	String icon;
	String isAllowMonthly;
	String owner;
	String ownerCustId;
	String ownerType;
	Integer subNumber;//订阅数
	String title;
	UserBaseInfo userBaseInfo;
		
	public String getIsAllowMonthly() {
		return isAllowMonthly;
	}
	public void setIsAllowMonthly(String isAllowMonthly) {
		this.isAllowMonthly = isAllowMonthly;
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
	public Integer getHasBoughtMonthly() {
		return hasBoughtMonthly;
	}
	public void setHasBoughtMonthly(Integer hasBoughtMonthly) {
		this.hasBoughtMonthly = hasBoughtMonthly;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
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
	public UserBaseInfo getUserBaseInfo() {
		return userBaseInfo;
	}
	public void setUserBaseInfo(UserBaseInfo userBaseInfo) {
		this.userBaseInfo = userBaseInfo;
	}
}
