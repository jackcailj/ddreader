package com.dangdang.readerV5.reponse;

public class ContentListData {
   	ContentList contentList;
	String currentDate;
	String systemDate;
	
	public ContentList getContentList(){
		return contentList;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }	
	public void setContentList(ContentList contentList){
		this.contentList = contentList;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
}
