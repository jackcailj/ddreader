package com.dangdang.ZuoJiaPingXuan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.ZuoJiaPingXuan.reponse.LotteryInfo;
import com.dangdang.ZuoJiaPingXuan.reponse.WinLotteryUsersReponse;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.CustDayVoteInfo;
import com.dangdang.db.bookbar.CustDayVoteInfoDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cailianjie on 2016-1-4.
 */
public class WinLotteryUsers extends FixtureBase{

    ReponseV2<WinLotteryUsersReponse> reponseResult;

    public WinLotteryUsers(){}

    public ReponseV2<WinLotteryUsersReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<WinLotteryUsersReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            List<CustDayVoteInfo> custDayVoteInfos= CustDayVoteInfoDb.getCustDayVoteList();

            List<LotteryInfo> lotteryInfos = new ArrayList<LotteryInfo>();

            for(CustDayVoteInfo info: custDayVoteInfos){
                LotteryInfo lotteryInfo = new LotteryInfo();
                lotteryInfo.setCreateDate(info.getCreateDate());
                lotteryInfo.setCustId(info.getCustId());
                lotteryInfo.setHaveVoteCount(info.getHaveVoteCount());
                lotteryInfo.setLotteryCount(info.getLotteryCount());
                lotteryInfo.setLotteryDate(info.getLotteryDate());
                //lotteryInfo.setLotteryName(info.getLotteryName());
                lotteryInfo.setLotteryType(info.getLotteryType());
                lotteryInfo.setVoteInfoId(info.getVoteInfoId());
                lotteryInfo.setLastVoteCount(info.getLastVoteCount());
                lotteryInfo.setVoteInfoDate(info.getVoteInfoDate());

                lotteryInfos.add(lotteryInfo);

            }

            dataVerifyManager.add(new ListVerify(reponseResult.getData().getCustDayVoteInfoList(),lotteryInfos,true).setVerifyContent("验证中奖名单正确"));
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getCustDayVoteInfoList(),null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
