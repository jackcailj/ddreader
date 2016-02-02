package com.dangdang.reader.functional.reponse;

import java.util.List;

public class HiddenBookList {
	String currentDate;
	List<String> ebookList;
	String ebookNum;
	String ebookTotalNum;
	String systemDate;	
	
	public String getCurrentDate(){
		return currentDate;
	}	
	public List<String> getEbookList(){
		return ebookList;
	}
	public String getEbookNum(){
		return ebookNum;
	}	
	public String getEbookTotalNum(){
		return ebookTotalNum;
	}	
	public String getSystemDate(){
		return systemDate;
	}	 
	
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}	
	public void setEbookList(List<String> ebookList){
		this.ebookList = ebookList;
	}
	public void setEbookNum(String ebookNum){
		this.ebookNum = ebookNum;
	}	
	public void setEbookTotalNum(String ebookTotalNum){
		this.ebookTotalNum = ebookTotalNum;
	}	
	public void setSystemDate(String systemDate){
		this.systemDate = systemDate;
	}	
}
