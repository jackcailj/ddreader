package com.dangdang.readerV5.personal_center.bookfriend;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.LoginManager;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.db.ucenter.UserInfoSql;

/**
 * Created by cailianjie on 2015-7-23.
 */
public class AddBookFriend extends FixtureBase{

    BookFriendRelation beforeRelation;

    public AddBookFriend(){}

    @Override
    protected void genrateVerifyData() throws Exception {

        if(paramMap.get("flag")==null ||!paramMap.get("flag").equals("flag_pubid_error"))
        {
            beforeRelation=UserInfoSql.getRelation(login.getCustId(), LoginManager.getLoginByPubID(paramMap.get("friendPubId")).getCustId());
        }
    }

    @Override
    protected void dataVerify() throws Exception {
        if(login!=null){

            BookFriendRelation afterRelation=null;
            if(paramMap.get("flag")==null || !paramMap.get("flag").equals("flag_pubid_error")) {
                afterRelation = UserInfoSql.getRelation(login.getCustId(), LoginManager.getLoginByPubID(paramMap.get("friendPubId")).getCustId());
            }

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
