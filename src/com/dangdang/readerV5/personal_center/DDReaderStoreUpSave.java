package com.dangdang.readerV5.personal_center;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;

/**
 * Created by cailianjie on 2015-7-1.
 */
public class DDReaderStoreUpSave extends FixtureBase{

    public DDReaderStoreUpSave(){addAction("dDReaderStoreUpSave");}


    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }


    @Override
    protected void dataVerify() throws Exception {
        super.dataVerify();
    }
}
