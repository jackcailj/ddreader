package com.dangdang.readerV5.personal_center;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;


/**
 * Created by cailianjie on 2015-7-3.
 */
public class DDReaderStoreUpList extends FixtureBase{

    public  DDReaderStoreUpList(){addAction("dDReaderStoreUpList");}

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }


}
