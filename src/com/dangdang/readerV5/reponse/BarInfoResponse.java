package com.dangdang.readerV5.reponse;

public class BarInfoResponse {
	BarInfo bar;
	String currentDate;
	String systemDate;
	
	public BarInfo getBar(){
		return bar;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getSystemDate(){
		return systemDate;
    }	
	public void setBar(BarInfo bar){
		this.bar = bar;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setSystemDate(String currentDate){
		this.systemDate = currentDate;
    }
}
