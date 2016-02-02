package com.dangdang.readerV5.reponse;

import java.util.List;

public class ColumnReponse {
	String columnCode;
	String count;
	String icon;
	Boolean isShowHorn;
	String name;
	List<SaleList> saleList;
	String tips;
	Integer total;
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
	public Boolean getIsShowHorn() {
		return isShowHorn;
	}
	public void setIsShowHorn(Boolean isShowHorn) {
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
