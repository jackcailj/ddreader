package com.dangdang.reader.functional.param.parse;

import com.dangdang.common.functional.login.Login;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by cailianjie on 2015-6-17.
 */
public class GetCustIdByNameParse implements IParamParse{

    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public Object parse(String param) throws Exception {
        if(StringUtils.isNotBlank(param)) {
            return Login.getCusId(param);
        }

        throw new Exception("GetCustIdByName异常，用户名为空");
    }
}
