package com.dangdang.reader.functional.reponse;

import java.util.List;

public class BuyBookListResponse {	
	String currentDate;
	List<MobileEbookInfo> ebookList;
	
	public String getCurrentDate(){
		return currentDate;
	}
	
	public List<MobileEbookInfo> getEbookList(){
		return ebookList;
	}
	
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	
	public void setEbookList(List<MobileEbookInfo> ebookList){
		this.ebookList = ebookList;
	}
}
