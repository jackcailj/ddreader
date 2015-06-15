package com.dangdang.reader.functional.reponse;

import java.util.List;

import com.dangdang.ecms.meta.Ebook;

public class BookListDataReponse {




    List<MobileEbookInfo> products;

	Integer ebookNum;
	Integer ebookNumNoFilter;
	Integer ebookTotalNum;
	String name;



    public List<MobileEbookInfo> getProducts() {
        return products;
    }

    public void setProducts(List<MobileEbookInfo> products) {
        this.products = products;
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
