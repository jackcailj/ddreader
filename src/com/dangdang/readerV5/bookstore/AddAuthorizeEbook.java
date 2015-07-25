package com.dangdang.readerV5.bookstore;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.readerV5.personal_center.ActivateDdMoneyV2;

/**
 * Created by cailianjie on 2015-7-23.
 */
public class AddAuthorizeEbook extends FixtureBase{

    public AddAuthorizeEbook(){}

    public AddAuthorizeEbook(ILogin login,String mediaId){
        paramMap.put("token",login.getToken());
        paramMap.put("mediaId",mediaId);
    }
}
