package com.dangdang.readerV5.reponse;

import com.dangdang.account.IntegralItem;

/**
 * Created by cailianjie on 2015-8-21.
 */
public class SigninReponse {
    Integer continueDays;
    String nextPrizeValue;
    Integer prizeValue;

    public Integer getContinueDays() {
        return continueDays;
    }

    public void setContinueDays(Integer continueDays) {
        this.continueDays = continueDays;
    }

    public String getNextPrizeValue() {
        return nextPrizeValue;
    }

    public void setNextPrizeValue(String nextPrizeValue) {
        this.nextPrizeValue = nextPrizeValue;
    }

    public Integer getPrizeValue() {
        return prizeValue;
    }

    public void setPrizeValue(Integer prizeValue) {
        this.prizeValue = prizeValue;
    }
}
