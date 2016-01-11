package com.dangdang.ZuoJiaPingXuan.reponse;

/**
 * Created by cailianjie on 2016-1-5.
 */
public class PrizeInfo {
    String lotteryDate;
    Long lotteryId;
    String lotteryName;
    Byte lotteryType;
    Long voteInfoId;


    public String getLotteryDate() {
        return lotteryDate;
    }

    public void setLotteryDate(String lotteryDate) {
        this.lotteryDate = lotteryDate;
    }

    public Long getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Long lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public Byte getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(Byte lotteryType) {
        this.lotteryType = lotteryType;
    }

    public Long getVoteInfoId() {
        return voteInfoId;
    }

    public void setVoteInfoId(Long voteInfoId) {
        this.voteInfoId = voteInfoId;
    }
}
