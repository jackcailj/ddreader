package com.dangdang.readerV5.reponse;

public class TagContent {
	String barModuleId;
	String barModuleTagId;
	String beginDate;
	String endDate;
	String sort;
	String status;
	String tagName;
	String tagType;

	public String getBarModuleId(){
		return barModuleId;
	}
	public String getBarModuleTagId(){
		return barModuleTagId;
	}
	public String getBeginDate(){
		return beginDate;
	}
	public String getEndDate(){
		return endDate;
	}
	public String getSort(){
		return sort;
	}
	public String getStatus(){
		return status;
	}
	public String getTagName(){
		return tagName;
	}
	public String getTagType(){
		return tagType;
	}
	public void setBarModuleId(String barModuleId){
		this.barModuleId = barModuleId;
	}
	public void setBarModuleTagId(String barModuleTagId){
		this.barModuleTagId = barModuleTagId;
	}
	public void setBeginDate(String beginDate){
		this.beginDate = beginDate;
	}
	public void setEndDate(String endDate){
		this.endDate = endDate;
	}
	public void setSort(String sort){
		this.sort = sort;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public void setTagName(String tagName){
		this.tagName = tagName;
	}
	public void setTagType(String tagType){
		this.tagType = tagType;
	}
}
