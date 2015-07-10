package com.dangdang.common.functional.login;

/**
 * Created by cailianjie on 2015-6-16.
 */
public interface ILogin {

    public String getCustId() throws Exception;
    public String getPubId();
    public String getToken();

    public LoginInfo getLoginInfo();
}
