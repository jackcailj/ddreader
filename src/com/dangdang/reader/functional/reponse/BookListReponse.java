package com.dangdang.reader.functional.reponse;

import java.util.List;

public class BookListReponse {
	List<MobileEbookInfo> ebookList;

	
	Integer ebookNum;
	Integer ebookNumNoFilter;
	Integer ebookTotalNum;
	String name;

	public List<MobileEbookInfo> getEbookList() {
		return ebookList;
	}
	public void setEbookList(List<MobileEbookInfo> ebookList) {
		this.ebookList = ebookList;
	}

	public Integer getEbookNum() {
		return ebookNum;
	}
	public void setEbookNum(Integer ebookNum) {
		this.ebookNum = ebookNum;
	}
	public Integer getEbookNumNoFilter() {
		return ebookNumNoFilter;
	}
	public void setEbookNumNoFilter(Integer ebookNumNoFilter) {
		this.ebookNumNoFilter = ebookNumNoFilter;
	}
	public Integer getEbookTotalNum() {
		return ebookTotalNum;
	}
	public void setEbookTotalNum(Integer ebookTotalNum) {
		this.ebookTotalNum = ebookTotalNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
