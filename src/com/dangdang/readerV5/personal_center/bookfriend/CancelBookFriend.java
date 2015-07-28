package com.dangdang.readerV5.personal_center.bookfriend;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ucenter.UserInfoSql;

/**
 * Created by cailianjie on 2015-7-24.
 */
public class CancelBookFriend extends FixtureBase{

    BookFriendRelation beforeRelation;


    public CancelBookFriend(){}

    @Override
    protected void genrateVerifyData() throws Exception {

        if(login!=null && paramMap.get("friendPubId")!=null) {
            beforeRelation = UserInfoSql.getRelation(login.getCustId(), UserInfoSql.getCustIdByPubId(paramMap.get("friendPubId")));
        }
    }

    @Override
    protected void dataVerify() throws Exception {

        BookFriendRelation afterRelation=null;
        if(login!=null && paramMap.get("friendPubId")!=null) {
            afterRelation = UserInfoSql.getRelation(login.getCustId(), UserInfoSql.getCustIdByPubId(paramMap.get("friendPubId")));
        }

        if(reponseV2Base.getStatus().getCode()==0){
            if(beforeRelation==BookFriendRelation.NO_RELATION){
                //原来没有关注，应不能走到此分支
                throw new Exception("没有关注过，不能取消关注");
                //dataVerifyManager.add(new ValueVerify<BookFriendRelation>(BookFriendRelation.ACTIVE,afterRelation));
            }
            else if(beforeRelation==BookFriendRelation.ACTIVE){
                //原来为主动关注，现在取消关注，状态变为不关注
                dataVerifyManager.add(new ValueVerify<BookFriendRelation>(BookFriendRelation.NO_RELATION,afterRelation));
            }
            else if(beforeRelation==BookFriendRelation.PASSIVE){
                //原来为被动关注，现在取消关注，状态变为不关注
                dataVerifyManager.add(new ValueVerify<BookFriendRelation>(BookFriendRelation.NO_RELATION,afterRelation));
            }
            else if(beforeRelation==BookFriendRelation.EACHOTHER){
                //原来为互相关注，一方取消关注后，状态变为0或1
                if(beforeRelation.isActive()){
                    dataVerifyManager.add(new ValueVerify<BookFriendRelation>(BookFriendRelation.PASSIVE,afterRelation));
                }
                else {
                    dataVerifyManager.add(new ValueVerify<BookFriendRelation>(BookFriendRelation.ACTIVE,afterRelation));
                }
            }
        }
        else{
            if(beforeRelation!=null){
                dataVerifyManager.add(new ValueVerify<BookFriendRelation>(beforeRelation,afterRelation), VerifyResult.SUCCESS);
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
