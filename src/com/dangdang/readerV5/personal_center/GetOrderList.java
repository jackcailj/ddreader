package com.dangdang.readerV5.personal_center;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;

/**
 * Created by cailianjie on 2015-7-9.
 */
public class GetOrderList extends FixtureBase{

    public GetOrderList(){addAction("getOrderList");}

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }

}