package com.dangdang.param.parse;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import com.dangdang.enumeration.CustId;

/**
 * custId
 * @author guohaiying
 */
public class GetCustIdParse implements IParamParse{

	@Override
	public Object parse(Map<String, String> param) throws Exception {
		return null;
	}

	@Override
	public void parse(Map<String, String> paramMap, String key, String param)
			throws Exception {
		if(StringUtils.isNotBlank(param)){
			String[] params= ParamParse.parseParam(param);
			String custId = params[0].trim();
			String type = params[1].trim();
			
			if(custId.equals(CustId.Pass.toString())){ //获取没有频道包月的Token
				paramMap.put(key,"");
			
			}else if(custId.equals(CustId.UnPass.toString())){ //获取有频道包月(未过期)的Token
				paramMap.put(key,"");
			
			}else if(custId.equals(CustId.UnApply.toString())){
				paramMap.put(key,"");
			}
		}
	}

}
