package com.dangdang.readerV5.reponse;

import java.util.List;

public class TagDetailData {
	List<BarContent> barList;
	String currentDate;
	String systemDate;
	
	public List<BarContent> getBarList(){
		return barList;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setBarList(List<BarContent> barList){
		this.barList = barList;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }

}
