package com.dangdang.reader.functional.reponse;

import com.dangdang.common.functional.login.UserInfo;
import com.dangdang.common.functional.reponse.ddLoginReponseUser;

public class LoginReponse {
	String token;

    //ddLoginReponseUser user;//ddLogin接口可以用此值
	UserInfo user;

	String userPubId;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserPubId() {
		return userPubId;
	}

	public void setUserPubId(String userPubId) {
		this.userPubId = userPubId;
	}

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
