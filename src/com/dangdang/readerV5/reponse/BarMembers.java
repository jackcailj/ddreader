package com.dangdang.readerV5.reponse;

public class BarMembers {
	String barMemberId;
	String custId;
	String headPhoto;
	String memberStatus;
	String nickName;
	
	public String getBarMemberId(){
		return barMemberId;
	}
	public String getCustId(){
		return custId;
	}
	public String getHeadPhoto(){
		return headPhoto;
	}
	public String getMemberStatus(){
		return memberStatus;
	}
	public String getNickName(){
		return nickName;
	}
	
	public void setBarMemberId(String barMemberId){
		this.barMemberId = barMemberId;
	}
    public void setCustId(String custId){
		this.custId = custId;
	}
    public void setHeadPhoto(String headPhoto){
		this.headPhoto = headPhoto;
	}
    public void setMemberStatus(String memberStatus){
		this.memberStatus = memberStatus;
	}
    public void setNickName(String nickName){
		this.nickName = nickName;
	}

}
