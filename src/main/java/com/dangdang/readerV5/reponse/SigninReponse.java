package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-21.
 */
public class SigninReponse {
    Integer continueDays;
    String nextTips;
    String tips;
    List<String> signinCalendar;
    Integer totalNum;
    String signinInfo;



    public Integer getContinueDays() {
        return continueDays;
    }

    public void setContinueDays(Integer continueDays) {
        this.continueDays = continueDays;
    }

    public String getNextTips() {
        return nextTips;
    }

    public void setNextTips(String nextTips) {
        this.nextTips = nextTips;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public List<String> getSigninCalendar() {
        return signinCalendar;
    }

    public void setSigninCalendar(List<String> signinCalendar) {
        this.signinCalendar = signinCalendar;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getSigninInfo() {
        return signinInfo;
    }

    public void setSigninInfo(String signinInfo) {
        this.signinInfo = signinInfo;
    }
}
