package com.dangdang.readerV5.reponse;

public class DiscoveryHomePage {
	int fpHasNew;
	int qxdHasNew;
	String currentDate;
	String systemDate;
	
	public int getFpHasNew(){
		return fpHasNew;
	}
	public int getQxdHasNew(){
		return qxdHasNew;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setFpHasNew(int fpHasNew){
		this.fpHasNew = fpHasNew;
	}
	public void setQxdHasNew(int qxdHasNew){
		this.qxdHasNew = qxdHasNew;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }

}
