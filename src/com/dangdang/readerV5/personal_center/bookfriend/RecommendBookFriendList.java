package com.dangdang.readerV5.personal_center.bookfriend;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.RecommendBookFriendListReponse;

/**
 * Created by cailianjie on 2015-7-23.
 */
public class RecommendBookFriendList extends FixtureBase{

    ReponseV2<RecommendBookFriendListReponse> reponseResult;

    public RecommendBookFriendList(){}

    public ReponseV2<RecommendBookFriendListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult= JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<RecommendBookFriendListReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getRecommendBookFriends(),null), VerifyResult.FAILED);
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getRecommendBookFriends(),null), VerifyResult.SUCCESS);
        }

        super.dataVerify();
    }
}
