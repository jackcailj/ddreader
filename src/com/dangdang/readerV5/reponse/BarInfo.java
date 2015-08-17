package com.dangdang.readerV5.reponse;

public class BarInfo {
	String barDesc;
	String barId;
	String barImgUrl;
	String barName;
	String barType;
	String hasBook;
	String memberNum;
	String memberStatus;
	
	public String getBarDesc(){
		return barDesc;
	}
	public String getBarId(){
		return barId;
	}
	public String getBarImgUrl(){
		return barImgUrl;
	}
	public String getBarName(){
		return barName;
	}
	public String getBarType(){
		return barType;
	}
	public String getHasBook(){
		return hasBook;
	}
	public String getMemberNum(){
		return memberNum;
	}
	public String getMemberStatus(){
		return memberStatus;
	}
	public void setBarDesc(String barDesc){
		this.barDesc = barDesc;
	}
	public void setBarId(String barId){
		this.barId = barId;
	}
	public void setBarImgUrl(String barImgUrl){
		this.barImgUrl = barImgUrl;
	}
	public void setBarName(String barName){
		this.barName = barName;
	}
	public void setBarType(String barType){
		this.barType = barType;
	}
	public void setHasBook(String hasBook){
		this.hasBook = hasBook;
	}
	public void setMemberNum(String memberNum){
		this.memberNum = memberNum;
	}
	public void setMemberStatus(String memberStatus){
		this.memberStatus = memberStatus;
	}
}
