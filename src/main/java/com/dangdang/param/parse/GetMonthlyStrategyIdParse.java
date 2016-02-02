package com.dangdang.param.parse;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import com.dangdang.db.digital.ChannelMonthlyStrategyDb;
import com.dangdang.digital.meta.ChannelMonthlyStrategy;

/**
 * 
 * @author guohaiying
 *
 */
public class GetMonthlyStrategyIdParse implements IParamParse{
	
	@Override
	public Object parse(Map<String, String> param) throws Exception {
		return null;
	}
	
	@Override
	public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
		if(StringUtils.isNotBlank(param)){
			String channelId = "";
			String[] params= ParamParse.parseParam(param);
			channelId = params[0].trim();
		
			if(paramMap.get(key).equals("auto")){ //企业频道
				List<ChannelMonthlyStrategy> list = ChannelMonthlyStrategyDb.getChannelMonthlyStrategy(channelId);
				paramMap.put(key, String.valueOf(list.get(0).getId()));	
			}
		}
	}
}
