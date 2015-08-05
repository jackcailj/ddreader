package com.dangdang.reader.functional.param.parse;

import com.dangdang.ddframework.util.Util;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by cailianjie on 2015-8-3.
 */
public class RandomUserNameParse implements IParamParse{

    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {

        int nRandomNumber=10;
        if(StringUtils.isNotBlank(param)){
            nRandomNumber = Integer.parseInt(param);
        }
        String randomString = Util.getRandomString(nRandomNumber);
        paramMap.put(key,randomString.toLowerCase());
    }
}
