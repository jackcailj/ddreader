package com.dangdang.readerV5.reponse;

public class PublishArticleResponse {
	int mediaDigestId;
	String currentDate;
	String systemDate;
	
	public int getMediaDigestId(){
		return mediaDigestId;
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
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
}
