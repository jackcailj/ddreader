package com.dangdang.account;

/**
 * Created by cailianjie on 2015-6-18.
 */
public class AccountInfo {
    String userName;
    Integer custId;
    Integer masterAccountMoney;
    Integer masterAccountMoneyIos;
    Integer attachAccountMoney;
    Integer attachAccountMoneyIos;
    Integer accountGrade;
    Integer accountIntegral;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Integer getMasterAccountMoney() {
        return masterAccountMoney;
    }

    public void setMasterAccountMoney(Integer masterAccountMoney) {
        this.masterAccountMoney = masterAccountMoney;
    }

    public Integer getMasterAccountMoneyIos() {
        return masterAccountMoneyIos;
    }

    public void setMasterAccountMoneyIos(Integer masterAccountMoneyIos) {
        this.masterAccountMoneyIos = masterAccountMoneyIos;
    }

    public Integer getAttachAccountMoney() {
        return attachAccountMoney;
    }

    public void setAttachAccountMoney(Integer attachAccountMoney) {
        this.attachAccountMoney = attachAccountMoney;
    }

    public Integer getAttachAccountMoneyIos() {
        return attachAccountMoneyIos;
    }

    public void setAttachAccountMoneyIos(Integer attachAccountMoneyIos) {
        this.attachAccountMoneyIos = attachAccountMoneyIos;
    }

    public Integer getAccountGrade() {
        return accountGrade;
    }

    public void setAccountGrade(Integer accountGrade) {
        this.accountGrade = accountGrade;
    }

    public Integer getAccountIntegral() {
        return accountIntegral;
    }

    public void setAccountIntegral(Integer accountIntegral) {
        this.accountIntegral = accountIntegral;
    }
}
