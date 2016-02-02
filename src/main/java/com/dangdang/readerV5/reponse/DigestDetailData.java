package com.dangdang.readerV5.reponse;

public class DigestDetailData {
    String systemDate;
    String currentDate;
    DigestDetail digestDetail;
    DigestChannel channel;
    int subscribe;
    
    public String getSystemDate(){
    	return systemDate;
    }
    public String getCurrentDate(){
    	return currentDate;
    }
    public DigestDetail getDigestDetail(){
    	return digestDetail;
    }
    public DigestChannel getChannel(){
    	return channel;
    }
    public int getSubscribe(){
    	return subscribe;
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
    public void setChannel(DigestChannel channel){
    	this.channel = channel;
    }
    public void setSubscribe(int subscribe){
    	this.subscribe = subscribe;
    }
}
