package com.dangdang.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.db.digital.SignDb;
import com.dangdang.param.parse._enum.VarKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by cailianjie on 2015-8-21.
 */
public class UpdateSingDateParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        if(StringUtils.isNotBlank(param)) {
            String[] params = param.split(",");
            int firstDay=Integer.parseInt(params[0]);
            Integer lastDay =params.length==2?Integer.parseInt(params[1]):1;

            //更新signin_main数据库，将用户签到日期提前day天
            SignDb.upadteSignDate(((ILogin)VariableStore.get(VarKey.LOGIN)).getCustId(),firstDay,lastDay);
        }
        else {
            throw new Exception("GetCustIdByName异常，用户名为空");
        }
    }
}
