package com.dangdang.param.parse;

import com.dangdang.db.ucenter.UserInfoSql;
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
    public void parse(Map<String, String> paramMap,String key,String param) throws Exception {
        if(StringUtils.isNotBlank(param)) {
            String custId= UserInfoSql.getCustIdByName(param);
            paramMap.put(key,custId);
        }
        else {
            throw new Exception("GetCustIdByName异常，用户名为空");
        }
    }
}
