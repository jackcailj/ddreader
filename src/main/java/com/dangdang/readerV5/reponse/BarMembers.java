package com.dangdang.readerV5.reponse;

public class BarMembers {
	String barMemberId;
	String memberStatus;
	UserBaseInfo userBaseInfo;
		
	public String getBarMemberId(){
		return barMemberId;
	}
	public String getMemberStatus(){
		return memberStatus;
	}
	public UserBaseInfo getUserBaseInfo(){
		return userBaseInfo;
	}	
	public void setBarMemberId(String barMemberId){
		this.barMemberId = barMemberId;
	}
    public void setMemberStatus(String memberStatus){
		this.memberStatus = memberStatus;
	}
    public void setUserBaseInfo(UserBaseInfo userBaseInfo){
		this.userBaseInfo = userBaseInfo;
	}

}
