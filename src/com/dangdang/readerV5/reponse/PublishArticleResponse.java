package com.dangdang.readerV5.reponse;

public class PublishArticleResponse {
	int mediaDigestId;
	int experience;
	int integral;
	String currentDate;
	String systemDate;
	
	public int getMediaDigestId(){
		return mediaDigestId;
	}
	public int getExperience(){
		return experience;
	}
	public int getIntegral(){
		return integral;
	}	
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }	
	
	public void setMediaDigestId(int mediaDigestId){
		this.mediaDigestId = mediaDigestId;
	}
	public void setExperience(int experience){
		this.experience = experience;
	}
	public void setIntegral(int integral){
		this.integral = integral;
	}	
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
}
