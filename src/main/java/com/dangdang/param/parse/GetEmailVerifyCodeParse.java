package com.dangdang.param.parse;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

import java.util.Map;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class GetEmailVerifyCodeParse implements IParamParse{


    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }


    /*
    获取产品productid
    参数：
        param：用，分割多个参数，格式BookType,ProductIdsEnum,数量
     */
    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        Map<String,Object> result=DbUtil.selectOne(Config.SQLSERVER187Config,"select top 1 validatecode,Verify_send_datetime from email_verify  where email='"+paramMap.get("email")+"' order by Verify_send_datetime desc");
        paramMap.put(key,result.get("validatecode").toString());

    }
}
