package com.dangdang.readerV5.reponse;

public class CreateBarResponse {
	String barId;
	String currentDate;
	String systemDate;
	
	public String getBarId(){
		return barId;
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
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String systemDate){
		this.systemDate = systemDate;
    }
	//{"data":{"barId":"5","currentDate":"2015-06-29 20:03:08","systemDate":"1435579388836"},"status":{"code":0},"systemDate":1435579388836}

}
