package com.dangdang.readerV5.personal_center;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.param.parse.ParseParamUtil;

/**
 * Created by cailianjie on 2015-6-24.
 */
public class LoginOutV2 extends FixtureBase {

    public LoginOutV2(){addAction("loginOutV2");}


    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }

}
