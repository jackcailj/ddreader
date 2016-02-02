package com.dangdang.reader.functional.reponse;

public class ThirdLoginResponse {
	String currentDate;
	String custImg;
	String custNickName;
	String systemDate;
	String token;
	String unbindNum;
	String uniqueKey;
	String userPubId;
	User user;
	
	public String getCurrentDate(){
		return currentDate;
	}
	public String getCustImg(){
		return custImg;
	}
	public String getCustNickName(){
		return custNickName;
	}
	public String getSystemDate(){
		return systemDate;
	}
	public String getToken(){
		return token;
	}
	public String getUnbindNum(){
		return unbindNum;
	}
	public String getUniqueKey(){
		return uniqueKey;
	}
	public String getUserPubId(){
		return userPubId;
	}
	
	public User getUser(){
		return user;
	}
	
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setCustImg(String custImg){
		this.custImg = custImg;
	}
	public void setCustNickName(String custNickName){
		this.custNickName = custNickName;
	}
	public void setSystemDate(String systemDate){
		this.systemDate = systemDate;
	}
	public void setToken(String token){
		this.token = token;
	}
	public void setUnbindNum(String unbindNum){
		this.unbindNum = unbindNum;
	}
	public void setUniqueKey(String uniqueKey){
		this.uniqueKey = uniqueKey;
	}
	public void setUserPubId(String userPubId){
		this.userPubId = userPubId;
	}
	
	public void setUser(User user){
		this.user = user;
	}
}
