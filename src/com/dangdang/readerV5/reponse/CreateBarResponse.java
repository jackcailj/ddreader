package com.dangdang.readerV5.reponse;

public class CreateBarResponse {
	String barId;
	int experience;
	int integral;
	String currentDate;
	String systemDate;
	
	public String getBarId(){
		return barId;
	}
	public int getExperience(){
		return experience;
	}
	public int getIntegral(){
		return integral;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setBarId(String barId){
		this.barId = barId;
	}
	public void setExperience(int experience){
		this.experience = experience;
	}
	public void setIntegral(int integral){
		this.integral = integral;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String systemDate){
		this.systemDate = systemDate;
    }
}
