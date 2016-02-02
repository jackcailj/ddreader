package com.dangdang.readerV5.personal_center.bookfriend;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BookFriendInfo;
import com.dangdang.readerV5.reponse.GetMyBookFriendListReponse;
import com.dangdang.db.ucenter.UserInfoSql;
import com.dangdang.ucenter.meta.BookFirend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cailianjie on 2015-7-23.
 */
public class GetMyBookFriendList extends FixtureBase{

    ReponseV2<GetMyBookFriendListReponse> reponseResult;

    public GetMyBookFriendList(){}

    public ReponseV2<GetMyBookFriendListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetMyBookFriendListReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            List<BookFirend> myfriends = UserInfoSql.getMyBookFriends(login.getCustId());
            List<BookFriendInfo> bookFriendInfos = new ArrayList<BookFriendInfo>();
            for(BookFirend firend: myfriends){
                //主动关注
                if(!login.getCustId().equals(""+firend.getActiveUserId())) {
                    //LoginRecord loginRecord = UserInfoSql.getUserInfoByCustId(""+firend.getActiveUserId());
                    BookFriendInfo bookFriendInfo = new BookFriendInfo();

                   // bookFriendInfo.setNickName(loginRecord.getCustNickname());
                   // bookFriendInfo.setGender(loginRecord.getGender());
                   // bookFriendInfo.setCustImg(loginRecord.getCustImg());
                   // bookFriendInfo.setIntroduct(loginRecord.getIntroduct());

                    bookFriendInfo.setCreateDate(firend.getCreateDate().getTime());
                    bookFriendInfo.setCreateDateLong(firend.getCreateDate().getTime());
                    bookFriendInfo.setRelationShip(firend.getRelationShip());
                    if(firend.getRelationShip()==0) {
                        bookFriendInfo.setRelationShip(1);
                    }

                    bookFriendInfos.add(bookFriendInfo);
                }

                //被动关注
                if(!login.getCustId().equals(""+firend.getPassiveUserId())) {
                    //LoginRecord loginRecord = UserInfoSql.getUserInfoByCustId(""+firend.getPassiveUserId());
                    BookFriendInfo bookFriendInfo = new BookFriendInfo();

                    //bookFriendInfo.setNickName(loginRecord.getCustNickname());
                    //bookFriendInfo.setGender(loginRecord.getGender());
                    //bookFriendInfo.setCustImg(loginRecord.getCustImg());
                   // bookFriendInfo.setIntroduct(loginRecord.getIntroduct());

                    bookFriendInfo.setCreateDate(firend.getCreateDate().getTime());
                    bookFriendInfo.setCreateDateLong(firend.getCreateDate().getTime());
                    bookFriendInfo.setRelationShip(firend.getRelationShip());

                    bookFriendInfos.add(bookFriendInfo);
                }
            }
            dataVerifyManager.add(new ListVerify(reponseResult.getData().getBookFriendList(),bookFriendInfos,true));
        } else {
            dataVerifyManager.add(new ValueVerify<Object>(null, reponseResult.getData().getBookFriendList()), VerifyResult.SUCCESS);
        }

        super.dataVerify();
    }
}
