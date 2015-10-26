package com.dangdang.readerV5.reponse;

/**
 * Created by cailianjie on 2015-8-21.
 */
public class SigninReponse {
    Integer continueDays;
    String nextTips;
    String tips;

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
}
