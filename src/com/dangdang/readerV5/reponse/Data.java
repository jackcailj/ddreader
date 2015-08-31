package com.dangdang.readerV5.reponse;

public class Data {
	int canDoNum;
	int dayNum;
	int experience;
	int integral;
	String currentDate;
	String systemDate;
	
	public int getCanDoNum(){
		return canDoNum;
	}
	public int getDayNum(){
		return dayNum;
	}
	public int getExperience(){
		return experience;
	}
	public int getIntegral(){
		return integral;
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
	public void setExperience(int experience){
		this.experience = experience;
	}
	public void setIntegral(int integral){
		this.integral = integral;
	}
	public void setCurrentDate(String currentDate) {
		   this.currentDate = currentDate;
	}
	
	public void setSystemDate(String systemDate) {
		   this.systemDate = systemDate;
	}
}
