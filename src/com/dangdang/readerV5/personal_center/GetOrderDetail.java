package com.dangdang.readerV5.personal_center;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;

/**
 * 获取纸书订单详情
 * Created by cailianjie on 2015-7-9.
 */
public class GetOrderDetail extends FixtureBase{

    public GetOrderDetail(){addAction("getOrderDetail");}


    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }
}
