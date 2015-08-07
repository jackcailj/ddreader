package com.dangdang.readerV5.reponse;

public class DigestChannel {
	String channelDesc;
    String channelIcon;
    String channelTitle;
    
    public String getChannelDesc(){
    	return channelDesc;
    }
    public String getChannelIcon(){
    	return channelIcon;
    }
    public String getChannelTitle(){
    	return channelTitle;
    }
    public void setChannelDesc(String channelDesc){
    	this.channelDesc = channelDesc;
    }
    public void setChannelIcon(String channelIcon){
    	this.channelIcon = channelIcon;
    }
    public void setChannelTitle(String channelTitle){
    	this.channelTitle = channelTitle;
    }
}
