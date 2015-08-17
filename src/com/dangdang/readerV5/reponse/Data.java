package com.dangdang.readerV5.reponse;

public class Data {
	int canDoNum;
	int dayNum;
	String currentDate;
	String systemDate;
	
	public int getCanDoNum(){
		return canDoNum;
	}
	public int getDayNum(){
		return dayNum;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	
	public String getSystemDate() {
		return systemDate;
	}
	
	public void setCanDoNum(int canDoNum){
		this.canDoNum = canDoNum;
	}
	public void setDayNum(int dayNum){
		this.dayNum = dayNum;
	}
	public void setCurrentDate(String currentDate) {
		   this.currentDate = currentDate;
	}
	
	public void setSystemDate(String systemDate) {
		   this.systemDate = systemDate;
	}
}
