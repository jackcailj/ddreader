package com.dangdang.readerV5.reponse;

import java.util.List;

public class FreeForLimitedReponse {
	String columnCode;
	String count;
	String icon;
	String isShowHorn;
	String name;
	String overtime;
	List<SaleList> saleList;
	String tips;
	String total;
	public String getColumnCode() {
		return columnCode;
	}
	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
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
	public String getOvertime() {
		return overtime;
	}
	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}
	public List<SaleList> getSaleList() {
		return saleList;
	}
	public void setSaleList(List<SaleList> saleList) {
		this.saleList = saleList;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}

}
