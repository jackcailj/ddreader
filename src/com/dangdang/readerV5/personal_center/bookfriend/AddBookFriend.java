package com.dangdang.readerV5.personal_center.bookfriend;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ucenter.UserInfoSql;
import com.dangdang.ucenter.meta.BookFirend;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-23.
 */
public class AddBookFriend extends FixtureBase{

    BookFriendRelation beforeRelation;

    public AddBookFriend(){}

    @Override
    protected void genrateVerifyData() throws Exception {

        beforeRelation=UserInfoSql.getRelation(login.getCustId(), UserInfoSql.getCustIdByPubId(paramMap.get("friendPubId")));
    }

    @Override
    protected void dataVerify() throws Exception {
        if(login!=null){
            BookFriendRelation afterRelation =UserInfoSql.getRelation(login.getCustId(), UserInfoSql.getCustIdByPubId(paramMap.get("friendPubId")));

            if(reponseV2Base.getStatus().getCode()==0){
                if(beforeRelation==BookFriendRelation.NO_RELATION){
                    dataVerifyManager.add(new ValueVerify<BookFriendRelation>(BookFriendRelation.ACTIVE,afterRelation));
                }
                else if(beforeRelation==BookFriendRelation.ACTIVE){
                    dataVerifyManager.add(new ValueVerify<BookFriendRelation>(BookFriendRelation.EACHOTHER,afterRelation));
                }
                else if(beforeRelation==BookFriendRelation.PASSIVE){
                    dataVerifyManager.add(new ValueVerify<BookFriendRelation>(BookFriendRelation.EACHOTHER,afterRelation));
                }
            }
            else{
                if(beforeRelation!=null){
                    dataVerifyManager.add(new ValueVerify<BookFriendRelation>(beforeRelation,afterRelation), VerifyResult.SUCCESS);
                }
            }
        }
        super.dataVerify();
    }

    @Override
    public void reset() {
        super.reset();
        beforeRelation=null;
    }
}
