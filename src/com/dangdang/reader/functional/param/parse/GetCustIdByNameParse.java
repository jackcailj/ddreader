package com.dangdang.reader.functional.param.parse;

import com.dangdang.common.functional.login.Login;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
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
    public void parse(Map<String, String> paramMap,String key,String param) throws Exception {
        if(StringUtils.isNotBlank(param)) {
            String custId= Login.getCusId(param);
            paramMap.put(key,custId);
        }
        else {
            throw new Exception("GetCustIdByName异常，用户名为空");
        }
    }
}
