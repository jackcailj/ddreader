package com.dangdang.param.model;

/**
 * Created by cailianjie on 2015-5-12.
 */
public class ChangePersonalInfoParam extends ParamBase {
    String keyword;
    String nickname;
    String third_id;
    String loginClient;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }



    public String getThird_id() {
        return third_id;
    }

    public void setThird_id(String third_id) {
        this.third_id = third_id;
    }

    public String getLoginClient() {
        return loginClient;
    }

    public void setLoginClient(String loginClient) {
        this.loginClient = loginClient;
    }
}
