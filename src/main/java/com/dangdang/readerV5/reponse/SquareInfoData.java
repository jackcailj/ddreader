package com.dangdang.readerV5.reponse;

import java.util.List;

public class SquareInfoData {
	List<SquareInfo> squareInfo;
	String currentDate;
	String systemDate;
	
	public List<SquareInfo> getSquareInfo(){
		return squareInfo;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setSquareInfo(List<SquareInfo> squareInfo){
		this.squareInfo = squareInfo;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
}
