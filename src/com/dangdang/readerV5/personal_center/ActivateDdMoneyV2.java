package com.dangdang.readerV5.personal_center;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
//import groovyx.gpars.csp.PAR;

/**
 * Created by cailianjie on 2015-6-24.
 */
public class ActivateDdMoneyV2 extends FixtureBase{

    public ActivateDdMoneyV2(){addAction("activateDdMoneyV2");}


    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }
}
