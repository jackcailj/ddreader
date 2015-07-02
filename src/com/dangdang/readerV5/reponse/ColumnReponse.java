package com.dangdang.readerV5.reponse;

import java.util.List;

public class ColumnReponse {

	String columnCode;
	String columnEndTime;
	Integer count;
	String currentDate;
	String icon;
	String isShowHorn;
	String name;
	List<SaleList> saleList;
	String systemDate;
	String tips; //小喇叭提示内容
	Integer total;
	
	//频道json返回结果字段
	List<ChannelList> channelList;
	
	//频道数据库中表字段
	String is_show_horn; //是否显示小喇叭 
	
	public String getIs_show_horn() {
		return is_show_horn;
	}
	public void setIs_show_horn(String is_show_horn) {
		this.is_show_horn = is_show_horn;
	}
	public List<ChannelList> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<ChannelList> channelList) {
		this.channelList = channelList;
	}
	
	public String getColumnCode() {
		return columnCode;
	}
	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}
	public String getColumnEndTime() {
		return columnEndTime;
	}
	public void setColumnEndTime(String columnEndTime) {
		this.columnEndTime = columnEndTime;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIsShowHorn() {
		return isShowHorn;
	}
	public void setIsShowHorn(String isShowHorn) {
		this.isShowHorn = isShowHorn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SaleList> getSaleList() {
		return saleList;
	}
	public void setSaleList(List<SaleList> saleList) {
		this.saleList = saleList;
	}
	public String getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(String systemDate) {
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
}
