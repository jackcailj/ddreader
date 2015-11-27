package com.dangdang.param.parse;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

import com.dangdang.authority.meta.MediaMonthlyAuthority;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.db.digital.ChannelArticlesDigestDb;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.db.digital.ChannelMonthlyStrategyDb;
import com.dangdang.db.digital.ChannelSubUserDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.digital.meta.ChannelMonthlyStrategy;
import com.dangdang.enumeration.ChannelId;

/**
 * 
 * @author guohaiying
 *
 */
public class GetChannelIdParse implements IParamParse{

	@Override
	public Object parse(Map<String, String> param) throws Exception {
		return null;
	}

	@Override
	public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
		if(StringUtils.isNotBlank(param)){
			String cId = "";
			String cIdValue = "";
			String flag = "-1";
			String[] params= ParamParse.parseParam(param);
			cId = params[0].trim();
			flag = params[1].trim();
			String custId = "";
			if(cId.equals(ChannelId.TYPE.toString())){ //企业频道
				cIdValue = ChannelDb.getChannelWithOwnerType(flag);
				paramMap.put(key, cIdValue);
			}else if(cId.equals(ChannelId.IfAllowMonthly.toString())){ 
				if(flag.equals("0")) //不支持包月的频道
					cIdValue = ChannelMonthlyStrategyDb.getNoMonthlyStrategyChannel();
				else if(flag.equals("1")) //支持包月的频道
					cIdValue = ChannelMonthlyStrategyDb.getMonthlyStrategyChannel();
				paramMap.put(key, cIdValue);
			}else if(cId.equals(ChannelId.IfAlreadyMonthly.toString())&&flag.equals("0")){ //支持包月，0未包月的频道(注意验证到期时间)
				custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
				cIdValue = ChannelDb.getMonthlyChannel(custId);
				paramMap.put(key, cIdValue);
			}else if(cId.equals(ChannelId.NoOverdueIfAutoRenew.toString())){ //已购买的频道未过期， 1续费(注意验证到期时间) 0不续费
				custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
				List<String> list =  MediaMonthlyAuthorityDb.getUserMonthlyChannelID(custId, flag);
				cIdValue = list.get(0);
				paramMap.put(key, cIdValue);
			}else if(cId.equals(ChannelId.OverdueIfAutoRenew.toString())){ //已购买的频道已过期，1(自动续订状态)（需要跑后台任务） 0(不自动续费状态)，测续费
				custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
				cIdValue = MediaMonthlyAuthorityDb.getAutoRenewChannel(custId, flag);
				paramMap.put(key, cIdValue);
			}else if(cId.equals(ChannelId.Offline.toString())){//0频道支持包月且下线  1//未过期且已下线
				if(flag.equals("0"))
					cIdValue = ChannelMonthlyStrategyDb.getMonthlyAndOfflineChannel();
				else if(flag.equals("1")){
					List<MediaMonthlyAuthority> list = MediaMonthlyAuthorityDb.getMonthlyChannelOffline();
					cIdValue = list.get(0).getRelationId().toString();
				}
				paramMap.put(key, cIdValue);

			}else if(cId.equals(ChannelId.Mine.toString())){ //获取我自己的频道
				custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
				cIdValue = ChannelDb.getChannelIdByCustId(custId);
				paramMap.put(key, cIdValue);
			}else if(cId.equals(ChannelId.IfSub.toString())){
				custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
				if(flag.equals("0"))
					cIdValue = ChannelSubUserDb.getNotUserChannel(custId);
				else
					cIdValue = ChannelSubUserDb.getUserSubChannel(custId);
				paramMap.put(key, cIdValue);
			}else if(cId.equals(ChannelId.IfHaveTag.toString())){
				if(flag.equals("0"))
					cIdValue = ChannelDb.getHaveNotTagChannel();
				else 
					cIdValue = ChannelDb.getHaveTagChannel();
				paramMap.put(key, cIdValue);
			}else{
				throw new Exception("[custId:]"+custId+" 获取ChannelId失败！");
			}
			
			if(paramMap.get("channelMonthlyStrategyId")!=null){
				if(paramMap.get("channelMonthlyStrategyId").equals("auto")){
					List<ChannelMonthlyStrategy> list = ChannelMonthlyStrategyDb.getChannelMonthlyStrategy(cIdValue);
					paramMap.put("channelMonthlyStrategyId", String.valueOf(list.get(0).getId()));
				}
			}
			
			if(paramMap.get("digestId")!=null){
				if(paramMap.get("digestId").equals("auto")){
					//获取频道下的文章					
					paramMap.put("digestId", ChannelArticlesDigestDb.getDigestIdByChannelId(cIdValue));
				}
			}

		}

	}
}
