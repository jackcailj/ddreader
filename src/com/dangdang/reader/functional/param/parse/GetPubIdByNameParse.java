package com.dangdang.reader.functional.param.parse;

import com.dangdang.ucenter.UserIdSql;

import java.util.Map;

/**
 * Created by cailianjie on 2015-6-18.
 */
public class GetPubIdByNameParse implements  IParamParse{

    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public Object parse(String param) throws Exception {
        return UserIdSql.GetPubIdByName(param);
    }
}
