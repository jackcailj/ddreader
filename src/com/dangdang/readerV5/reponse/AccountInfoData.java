package com.dangdang.readerV5.reponse;


public class AccountInfoData {
	AccountInfo accountInfo;
	String currentDate;
	String systemDate;
	
	public AccountInfo getAccountInfo(){
		return accountInfo;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setAccountInfo(AccountInfo accountInfo){
		this.accountInfo = accountInfo;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
}
