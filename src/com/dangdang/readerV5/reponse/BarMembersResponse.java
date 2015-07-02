package com.dangdang.readerV5.reponse;

import java.util.List;

public class BarMembersResponse {
	List<BarMembers> barMembers;
	String currentDate;
	String systemDate;
	
	public List<BarMembers> getBarMembers(){
		return barMembers;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setBarMembers(List<BarMembers> barMembers){
		this.barMembers = barMembers;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }

}
