package com.dangdang.readerV5.personal_center.bookshelf;

import com.dangdang.authority.AuthorityDb;
import com.dangdang.authority.MediaAuthority;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;

/**
 * Created by cailianjie on 2015-7-16.
 */
public class HideUserBookList extends FixtureBase{

    public HideUserBookList(){}


    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            try {
                String[] splitMediaId=paramMap.get("mediaId").split(",");
                for(String mediaId:splitMediaId) {
                    MediaAuthority mediaAuthority = AuthorityDb.getUserEbook(login.getCustId(),mediaId );
                    dataVerifyManager.add(new ValueVerify<String>(paramMap.get("isHide"), "" + mediaAuthority.getIsHide()));
                }
            }
            catch (Exception e){

            }

        }

        super.dataVerify();
    }
}
