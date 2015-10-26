package com.dangdang.reader.functional.gd_tongji;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.param.parse.ParseParamUtil;

import java.util.Map;

/**
 * Created by cailianjie on 2015-7-13.
 */
public class GdtActive extends FixtureBase{

    @Override
    public void parseParameters(Map<String, String> params) throws Exception {
        paramMap = params;
    }

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }
}
