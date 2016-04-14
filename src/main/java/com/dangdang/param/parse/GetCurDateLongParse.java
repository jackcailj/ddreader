package com.dangdang.param.parse;

import java.util.Date;
import java.util.Map;

/**
 * Created by cailianjie on 2016-3-15.
 */
public class GetCurDateLongParse implements IParamParse {
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        Date curDate=new Date();
        paramMap.put(key,""+curDate.getTime());
    }
}
