package com.dangdang.reader.functional.reponse;

public class User {	
	String displayId;
	String email;
	String id;
	String nickName;
	String phone;
	String registerDate;
	String userName;
	String vipType;
	
	public String getDisplayId(){
		return displayId;
	}
	public String getEmail(){
		return email;
	}
	public String getId(){
		return id;
	}
	public String getNickName(){
		return nickName;
	}
	public String getPhone(){
		return phone;
	}
	public String getRegisterDate(){
		return registerDate;
	}
	public String getUserName(){
		return userName;
	}
	public String getVipType(){
		return vipType;
	}
	
	public void setDisplayId(String displayId){
		this.displayId = displayId;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public void setId(String id){
		this.id = id;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	public void setRegisterDate(String registerDate){
		this.registerDate = registerDate;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
	public void setVipType(String vipType){
		this.vipType = vipType;
	}

}
