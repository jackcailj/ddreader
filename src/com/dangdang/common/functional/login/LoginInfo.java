package com.dangdang.common.functional.login;

public class LoginInfo {
	String action ="login";
	String userName;
	String passWord;
	String loginType;
    String loginClient="0";
	String key="testkey";

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

    public String getLoginClient() {
        return loginClient;
    }

    public void setLoginClient(String loginClient) {
        this.loginClient = loginClient;
    }
	
	public String getAction() {
		return action;
	}
//	public void setAction(String action) {
//		this.action = action;
//	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
}
