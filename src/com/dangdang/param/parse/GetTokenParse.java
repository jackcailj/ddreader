package com.dangdang.param.parse;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.dangdang.config.Config;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.db.ucenter.UserDeviceDb;

/**
 * 
 * @author guohaiying
 *
 */
public class GetTokenParse implements IParamParse{

	@Override
	public Object parse(Map<String, String> param) throws Exception {
		return null;
	}

	@Override
	public void parse(Map<String, String> paramMap, String key, String param)
			throws Exception {
		if(StringUtils.isNotBlank(param)){
			String[] params= ParamParse.parseParam(param);
			String tokenFlag = params[0].trim();
			String deviceType = Config.getCommonParam().get("deviceType");
			if(tokenFlag.equals("UserNoMonthly")){//获取没有频道包月的Token
				paramMap.put(key,UserDeviceDb.getNoMonthlyToken(deviceType));
			}else if(tokenFlag.equals("UserMonthly")){//获取有频道包月(未过期)的Token
				paramMap.put(key,UserDeviceDb.getTokenByCustId());
			}else if(tokenFlag.equals("UserMonthlyOverdue")){//获取有频道包月(已过期)的Token
				paramMap.put(key,UserDeviceDb.getMonthlyOverdueToken(deviceType));
			}else if(tokenFlag.equals("MonthlyChannelOffline")){//获取有频道(已下架)包月(未过期)的Token						
				paramMap.put(key,UserDeviceDb.getMonthlyChannelOfflineToken(deviceType));
			}
		
		}
	}

}
