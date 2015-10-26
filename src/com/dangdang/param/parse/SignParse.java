package com.dangdang.param.parse;

import java.util.Map;
import com.dangdang.ddframework.util.security.SignUtils;
import com.dangdang.db.ecms.BookStoreCommSQL;
import com.dangdang.param.parse._enum.SignEnum;

public class SignParse implements IParamParse{

	@Override
	public Object parse(Map<String, String> param) throws Exception {
		String sign = param.get("sign");
		String token = param.get("token");
		System.out.println("token" + token);
		int index = -1;
		Object returnObject = null;
		
		if(sign!=null){
			index = SignEnum.getIndex(sign);
		}
				
		switch(index){		
			case 1:{//需要先设置好Excel中的token,productIds,timestamp的值
				String productIdString=(param.get("productIds")==null?"":param.get("productIds").toString());
				String key = SignUtils.encryptOrderVirtualPayment(Long.parseLong(BookStoreCommSQL.getCustIdByToken(token)), productIdString);
				String source = productIdString+(param.get("timestamp")==null?"":param.get("timestamp").toString());
				sign =SignUtils.createBindPermissionSign(source, key);
				System.out.println("sign" + sign);
				param.put("sign", sign);
				returnObject = null;
				break;
			}
		}
		
		return returnObject;
	}

	@Override
	public void parse(Map<String, String> paramMap, String key, String param) throws Exception {

	}


}
