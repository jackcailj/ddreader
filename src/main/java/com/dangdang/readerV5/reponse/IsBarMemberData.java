package com.dangdang.readerV5.reponse;

public class IsBarMemberData {
	boolean isBarMember;
	String currentDate;
	String systemDate;
	
	public boolean getIsBarMember(){
		return isBarMember;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setIsBarMember(boolean isBarMember){
		this.isBarMember = isBarMember;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }

}
