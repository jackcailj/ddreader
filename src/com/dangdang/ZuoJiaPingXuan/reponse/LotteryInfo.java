package com.dangdang.ZuoJiaPingXuan.reponse;

import java.util.Date;

/**
 * Created by cailianjie on 2016-1-4.
 */
public class LotteryInfo {
    private Long voteInfoId;
    private String voteInfoDate;
    private Long custId;
    private Integer haveVoteCount;
    private Integer lastVoteCount;
    private Integer lotteryCount;
    private Byte lotteryType;
    private String lotteryName;
    private Date lotteryDate;
    private Date createDate;

    public Long getVoteInfoId() {
        return voteInfoId;
    }

    public void setVoteInfoId(Long voteInfoId) {
        this.voteInfoId = voteInfoId;
    }

    public String getVoteInfoDate() {
        return voteInfoDate;
    }

    public void setVoteInfoDate(String voteInfoDate) {
        this.voteInfoDate = voteInfoDate;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public Integer getHaveVoteCount() {
        return haveVoteCount;
    }

    public void setHaveVoteCount(Integer haveVoteCount) {
        this.haveVoteCount = haveVoteCount;
    }

    public Integer getLastVoteCount() {
        return lastVoteCount;
    }

    public void setLastVoteCount(Integer lastVoteCount) {
        this.lastVoteCount = lastVoteCount;
    }

    public Integer getLotteryCount() {
        return lotteryCount;
    }

    public void setLotteryCount(Integer lotteryCount) {
        this.lotteryCount = lotteryCount;
    }

    public Byte getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(Byte lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public Date getLotteryDate() {
        return lotteryDate;
    }

    public void setLotteryDate(Date lotteryDate) {
        this.lotteryDate = lotteryDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
