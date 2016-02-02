package com.dangdang.readerV5.reponse;

import java.util.List;

import com.dangdang.BaseComment.meta.TagInfo;

public class TagInfoVoList {
	List<TagInfo> tagInfoVoList;
	String currentDate;
	String systemDate;
	
	public List<TagInfo> getTagInfoVoList(){
		return tagInfoVoList;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }	
	public void setTagInfoVoList(List<TagInfo> tagInfoVoList){
		this.tagInfoVoList = tagInfoVoList;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
}
