package com.dangdang.readerV5.reponse;

import java.util.List;

public class MediaRankingListResponse {
	Integer count;
	String listType;
	String name;
	List<SaleList> saleList;
	Integer total;
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
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
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}

}
