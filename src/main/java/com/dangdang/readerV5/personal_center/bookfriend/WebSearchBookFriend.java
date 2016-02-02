package com.dangdang.readerV5.personal_center.bookfriend;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.RegexVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.readerV5.reponse.BookFriendInfo;
import com.dangdang.readerV5.reponse.WebSearchBookFriendReponse;

import javax.jws.WebService;

/**
 * Created by cailianjie on 2015-7-24.
 */
public class WebSearchBookFriend extends FixtureBase{

    ReponseV2<WebSearchBookFriendReponse> reponseResult;
    public WebSearchBookFriend(){}

    public ReponseV2<WebSearchBookFriendReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<WebSearchBookFriendReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getBookFriends(),null), VerifyResult.FAILED);
            if(reponseResult.getData().getBookFriends().size()>0){
                dataVerifyManager.add(new RegexVerify(Util.getJsonRegexString("nickName",paramMap.get("searchInput")),result.toString()));
            }
        }
        else
        {
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getBookFriends(), null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
