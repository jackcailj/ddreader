package com.dangdang.readerV5.personal_center;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import org.spockframework.builder.SetterLikeSlot;

/**
 * Created by cailianjie on 2015-6-24.
 */
public class BindCouponV3 extends FixtureBase{

    public  BindCouponV3(){addAction("bindCouponV3");}

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }
}
