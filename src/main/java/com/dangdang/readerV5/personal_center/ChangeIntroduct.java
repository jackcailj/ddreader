package com.dangdang.readerV5.personal_center;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.param.parse.ParseParamUtil;

/**
 * Created by cailianjie on 2015-6-19.
 */
public class ChangeIntroduct extends FixtureBase{

    public ChangeIntroduct(){addAction("changeIntroduct");}

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
        ParseParamUtil.removeBlackParam(paramMap);
    }

}
