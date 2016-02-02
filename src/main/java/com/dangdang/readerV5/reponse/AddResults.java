package com.dangdang.readerV5.reponse;

import java.util.List;

public class AddResults {
	List<AdResult> result;
	String currentDate;
	String systemDate;
	
	public List<AdResult> getResult(){
		return result;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setResult(List<AdResult> result){
		this.result = result;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }

}
