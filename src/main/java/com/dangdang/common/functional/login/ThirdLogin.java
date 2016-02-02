package com.dangdang.common.functional.login;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dataverify.RecordVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.reponse.ThirdLoginResponse;

public class ThirdLogin extends FunctionalBaseEx {
	{
		Config.getCommonParam().remove("deviceType");
		Config.getCommonParam().remove("channelId");
	}

	ReponseV2<ThirdLoginResponse> reponseResult;
	public ThirdLogin(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("thridLogin");
	} 
	
	public ReponseV2<ThirdLoginResponse> getResult() {
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ThirdLoginResponse>>(){});
		return reponseResult;
	}	
	
	@Override
	protected void dataVerify() throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode()==0){
			String token = reponseResult.getData().getToken();
			String userName = reponseResult.getData().getUser().getUserName();
			String custId = reponseResult.getData().getUser().getId();
			dataVerifyManager.add(new RecordVerify(Config.ECMSDBConfig, "select * from user_device where LOGIN_TOKEN='"+token+
					                           "' and CUST_ID="+custId+" and USERNAME='"+userName+"'"));
		}
		super.dataVerify();
	}
	
}
