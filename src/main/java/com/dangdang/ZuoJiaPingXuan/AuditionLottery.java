package com.dangdang.ZuoJiaPingXuan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.ZuoJiaPingXuan.reponse.AuditionLotteryReponse;
import com.dangdang.ZuoJiaPingXuan.reponse.PrizeInfo;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.CustDayVoteInfo;
import com.dangdang.db.bookbar.CustDayVoteInfoDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;

import java.text.SimpleDateFormat;

/**
 * Created by cailianjie on 2016-1-4.
 */
public class AuditionLottery extends FixtureBase{

    ReponseV2<AuditionLotteryReponse> reponseResult;

    public AuditionLottery(){}

    public ReponseV2<AuditionLotteryReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult= JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<AuditionLotteryReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){

            //需要联系方式的奖品
        /*    if(reponseResult.getData().getLottery().getLotteryType()==1
                    || reponseResult.getData().getLottery().getLotteryType()==2
                    || reponseResult.getData().getLottery().getLotteryType()==3
                    || reponseResult.getData().getLottery().getLotteryType()==4
                    || reponseResult.getData().getLottery().getLotteryType()==5
                    ){*/
                CustDayVoteInfo voteInfo = CustDayVoteInfoDb.getTodayPrize(login.getCustId());

                PrizeInfo prizeInfo =new PrizeInfo();
                prizeInfo.setVoteInfoId(voteInfo.getVoteInfoId());
                prizeInfo.setLotteryName(voteInfo.getLotteryName());

                SimpleDateFormat sdp = new SimpleDateFormat("yyyy-MM-dd");

                prizeInfo.setLotteryDate(sdp.format(voteInfo.getLotteryDate()));
                prizeInfo.setLotteryType(voteInfo.getLotteryType());


                dataVerifyManager.add(new ValueVerify<PrizeInfo>(reponseResult.getData().getLottery(),prizeInfo,true));
            //}

            if(reponseResult.getData().getLottery().getLotteryType()==6){
                //银铃铛，需验证是否到帐
            }

            if(reponseResult.getData().getLottery().getLotteryType()==7){//流量，虚言症返回了卷号和密码

            }

            if(reponseResult.getData().getLottery().getLotteryType()==87){//流量，虚言症返回了卷号和密码

            }

        }
        super.dataVerify();
    }
}
