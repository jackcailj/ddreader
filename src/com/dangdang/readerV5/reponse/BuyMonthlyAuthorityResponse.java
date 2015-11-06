package com.dangdang.readerV5.reponse;

public class BuyMonthlyAuthorityResponse {	
	String currentDate;
	String monthlyEndTime;
	String systemDate;
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getMonthlyEndTime() {
		return monthlyEndTime;
	}
	public void setMonthlyEndTime(String monthlyEndTime) {
		this.monthlyEndTime = monthlyEndTime;
	}
	public String getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
	}

}
