package com.dangdang.readerV5.reponse;

import java.util.List;

public class BorrowReponse {
	String columnCode;
	String count;
	//String icon;
	String isShowHorn;
	String name;
	List<BorrowSaleList> saleList;
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
//	public String getIcon() {
//		return icon;
//	}
//	public void setIcon(String icon) {
//		this.icon = icon;
//	}
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
	public List<BorrowSaleList> getSaleList() {
		return saleList;
	}
	public void setSaleList(List<BorrowSaleList> saleList) {
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
