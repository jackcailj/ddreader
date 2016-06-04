package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-14.
 */
public class TrainingRank {
    Integer myRankIndex;
    Double myTodayFinishRate;
    UserBaseInfo myUserInfo;

    List<TrainingNewsInfo> rankList;



    public Integer getMyRankIndex() {
        return myRankIndex;
    }

    public void setMyRankIndex(Integer myRankIndex) {
        this.myRankIndex = myRankIndex;
    }

    public Double getMyTodayFinishRate() {
        return myTodayFinishRate;
    }

    public void setMyTodayFinishRate(Double myTodayFinishRate) {
        this.myTodayFinishRate = myTodayFinishRate;
    }

    public UserBaseInfo getMyUserInfo() {
        return myUserInfo;
    }

    public void setMyUserInfo(UserBaseInfo myUserInfo) {
        this.myUserInfo = myUserInfo;
    }

    public List<TrainingNewsInfo> getRankList() {
        return rankList;
    }

    public void setRankList(List<TrainingNewsInfo> rankList) {
        this.rankList = rankList;
    }
}
