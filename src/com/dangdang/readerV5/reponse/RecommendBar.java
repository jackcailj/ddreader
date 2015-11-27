package com.dangdang.readerV5.reponse;

import java.util.List;

public class RecommendBar {
	int barCnt;
	List<BarInfo> barList;
	String currentDate;
	String systemDate;
	
	public int getBarCnt(){
		return barCnt;
	}
	public List<BarInfo> getBarList(){
		return barList;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }	
	public void setBarCnt(int barCnt){
		this.barCnt = barCnt;
	}
	public void setBarList(List<BarInfo> barList){
		this.barList = barList;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }

}
