package com.dangdang.param.parse;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.dangdang.config.Config;
import com.dangdang.db.authority.AuthorityDb;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.enumeration.TokenType;

/**
 * 
 * @author guohaiying
 */
public class GetTokenParse implements IParamParse{
	String tokenFlag="";
	@Override
	public Object parse(Map<String, String> param) throws Exception {
		return null;
	}

	@Override
	public void parse(Map<String, String> paramMap, String key, String param)
			throws Exception {
		if(StringUtils.isNotBlank(param)){
			String[] params= ParamParse.parseParam(param);
			tokenFlag = params[0].trim();
			String deviceType = Config.getCommonParam().get("deviceType");
			
			if(tokenFlag.equals(TokenType.UserNoMonthly.toString())){ //获取没有频道包月的Token
				paramMap.put(key,UserDeviceDb.getNoMonthlyToken(deviceType));
			
			}else if(tokenFlag.equals(TokenType.UserMonthly.toString())){ //获取有频道包月(未过期)的Token
				paramMap.put(key,UserDeviceDb.getTokenByCustId());
			
			}else if(tokenFlag.equals(TokenType.UserMonthlyOverdue.toString())){ //获取有频道包月(已过期)的Token
				paramMap.put(key,UserDeviceDb.getMonthlyOverdueToken(deviceType));
			
			}else if(tokenFlag.equals(TokenType.MonthlyChannelOffline.toString())){ //获取有频道(已下架)包月(未过期)的Token						
				paramMap.put(key,UserDeviceDb.getMonthlyChannelOfflineToken(deviceType));
			
			}else if(tokenFlag.equals(TokenType.UserNoBuyBook.toString())){ //获取没有买过书的Token
				paramMap.put(key,UserDeviceDb.getNoBuyBookToken(deviceType));
				
			}else if(tokenFlag.equals(TokenType.UserAlreadyBuyBook.toString())){ //获取买过书的Token
				String token = UserDeviceDb.getAlreadyBuyBookToken(deviceType);
				paramMap.put(key, token);				
				paramMap.put("mediaId", AuthorityDb.getUserAlreadyBuyBook(UserDeviceDb.getCustIdByToken(token)));
			}
		
		}
		
		if(paramMap.get("cId")!=null){
			if(paramMap.get("cId").equals("auto")&&tokenFlag.equals(TokenType.UserMonthly.toString())){
				String custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
				List<String> channels = MediaMonthlyAuthorityDb.getUserMonthlyChannelID(custId);
				int n=(int)Math.random()*(channels.size()-1);
				paramMap.put("cId",channels.get(n));
			}else if(paramMap.get("cId").equals("auto")&&tokenFlag.equals(TokenType.UserNoMonthly.toString())){
				paramMap.put("cId",ChannelDb.getChannelWithOwnerType("1"));
			}
		}
	}

}
