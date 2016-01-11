package com.dangdang.ZuoJiaPingXuan.reponse;

import com.dangdang.bookbar.meta.CustDayVoteInfo;

import java.util.List;

/**
 * Created by cailianjie on 2016-1-4.
 */
public class WinLotteryUsersReponse {
    List<LotteryInfo> custDayVoteInfoList;

    public List<LotteryInfo> getCustDayVoteInfoList() {
        return custDayVoteInfoList;
    }

    public void setCustDayVoteInfoList(List<LotteryInfo> custDayVoteInfoList) {
        this.custDayVoteInfoList = custDayVoteInfoList;
    }
}
