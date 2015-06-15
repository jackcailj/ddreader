package com.dangdang.reader.functional.param.model;

/**
 * Created by cailianjie on 2015-5-12.
 */
public class ChangeUserProfileParam extends ParamBase {
    String keyword;
    String content;
    String third_id;
    String loginClient;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
