package com.dangdang.param.parse;

import java.util.Date;
import java.util.Map;

/**
 * Created by cailianjie on 2016-1-4.
 */
public class GetTimeStampParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        Date now =new Date();
        paramMap.put(key,""+now.getTime());
    }
}
