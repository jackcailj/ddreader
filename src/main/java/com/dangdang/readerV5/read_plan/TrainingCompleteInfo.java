package com.dangdang.readerV5.read_plan;

import com.dangdang.readerV5.reponse.UserBaseInfo;

/**
 * Created by cailianjie on 2016-5-3.
 */
public class TrainingCompleteInfo {
    Integer days;
    Double finishReadRate;
    Integer myRankIndex;

    UserBaseInfo myUserInfo;
    UserBaseInfo userBaseInfo;

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Double getFinishReadRate() {
        return finishReadRate;
    }

    public void setFinishReadRate(Double finishReadRate) {
        this.finishReadRate = finishReadRate;
    }

    public Integer getMyRankIndex() {
        return myRankIndex;
    }

    public void setMyRankIndex(Integer myRankIndex) {
        this.myRankIndex = myRankIndex;
    }

    public UserBaseInfo getMyUserInfo() {
        return myUserInfo;
    }

    public void setMyUserInfo(UserBaseInfo myUserInfo) {
        this.myUserInfo = myUserInfo;
    }

    public UserBaseInfo getUserBaseInfo() {
        return userBaseInfo;
    }

    public void setUserBaseInfo(UserBaseInfo userBaseInfo) {
        this.userBaseInfo = userBaseInfo;
    }
}
