package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-24.
 */
public class CheckSigninStateReponse {

    Boolean isSign;
    Integer continueDays;
    String nextTips;
    List<String> signinCalendar;
    Integer totalNum;


    public Boolean getIsSign() {
        return isSign;
    }

    public void setIsSign(Boolean isSign) {
        this.isSign = isSign;
    }

    public Boolean getSign() {
        return isSign;
    }

    public void setSign(Boolean sign) {
        isSign = sign;
    }

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
}
