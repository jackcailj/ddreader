package com.dangdang.readerV5.personal_center;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;

/**
 * Created by cailianjie on 2015-7-7.
 */
public class GetBorrowAuthorityList extends FixtureBase{

    public GetBorrowAuthorityList(){addAction("getBorrowAuthorityList");}


    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }
}
