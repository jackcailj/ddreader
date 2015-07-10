package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * 频道专用
 * @author guohaiying
 *
 */
public class ChannelColumnReponse {

	List<ChannelList> channelList;
	String columnCode;
	Integer count;
	String isShowHorn;
	String name;
	//List<SaleList> saleList;
	String tips; //小喇叭提示内容
	Integer total;

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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
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
