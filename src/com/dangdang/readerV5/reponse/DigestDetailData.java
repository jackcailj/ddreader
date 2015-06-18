package com.dangdang.readerV5.reponse;

public class DigestDetailData {
    String systemDate;
    String currentDate;
    DigestDetail digestDetail;
    
    public String getSystemDate(){
    	return systemDate;
    }
    public String getCurrentDate(){
    	return currentDate;
    }
    public DigestDetail getDigestDetail(){
    	return digestDetail;
    }
    
    public void setSystemDate(String systemDate){
    	this.systemDate = systemDate;
    }
    public void setCurrentDate(String currentDate){
    	this.currentDate = currentDate;
    }
    public void setDigestDetail(DigestDetail digestDetail){
    	this.digestDetail = digestDetail;
    }
}
