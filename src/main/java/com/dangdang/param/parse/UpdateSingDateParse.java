package com.dangdang.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.config.Config;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.db.digital.SignDb;
import com.dangdang.ddframework.util.TelnetUtil;
import com.dangdang.enumeration.VarKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by cailianjie on 2015-8-21.
 * 更新签到日期
 * #UpdateSingDate#1
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

            //清楚redis
            TelnetUtil.clearRedis(Config.getRedis(),Config.REDIS_PORT,"6");
        }
        else {
            throw new Exception("GetCustIdByName异常，用户名为空");
        }
    }
}
