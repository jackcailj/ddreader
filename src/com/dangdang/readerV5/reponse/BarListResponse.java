package com.dangdang.readerV5.reponse;

import java.util.List;

import com.dangdang.bookbar.meta.Bar;

public class BarListResponse {
	List<Bar> barList;
	String currentDate;
	String systemDate;
	
	public List<Bar> getBarList(){
		return barList;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }	
	public void setBarList(List<Bar> barList){
		this.barList = barList;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
}
