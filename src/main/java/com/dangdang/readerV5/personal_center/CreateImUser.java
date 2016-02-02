package com.dangdang.readerV5.personal_center;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.param.parse.ParseParamUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by cailianjie on 2015-7-9.
 */
public class CreateImUser extends FixtureBase{

    public CreateImUser(){addAction("createImUser");}

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));

        if(login !=null && paramMap.get("custId")!=null && StringUtils.isEmpty(paramMap.get("custId"))){
            paramMap.put("custId",login.getPubId());
        }
    }
}
