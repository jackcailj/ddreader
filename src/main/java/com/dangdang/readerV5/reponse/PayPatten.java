package com.dangdang.readerV5.reponse;

import java.util.List;

import com.dangdang.ddframework.dataverify.verify_annotation.NotNull;

public class PayPatten {
	@NotNull
	List<KeyValue> payPatten;
	String currentDate;
	String systemDate;
	@NotNull
	String tips;
	
	public List<KeyValue> getPayPatten(){
		return payPatten;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	public String getTips(){
		return tips;
	}
	
	public void setPayPatten(List<KeyValue> payPatten){
		this.payPatten = payPatten;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
	public void setTips(String tips){
		this.tips = tips;
	}

}
