package com.dangdang.readerV5.reponse;

import java.util.List;

public class BarDefImgResponse {

	List<String> barDefImg;
	String currentDate;
	String systemDate;
	
	public List<String> getBarDefImg(){
		return barDefImg;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setBarDefImg(List<String> barDefImg){
		this.barDefImg = barDefImg;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
}
