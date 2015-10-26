package com.dangdang.reader.functional.purchaseAndDownload;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.common.functional.login.Login;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.GetRechargeVirtualPidReponse;

public class GetRechargeVirtualPid extends FunctionalBaseEx{

	ReponseV2<GetRechargeVirtualPidReponse> reponseResult;
	
	public ReponseV2<GetRechargeVirtualPidReponse> getReponseResult() {
		return reponseResult;
	}

	public GetRechargeVirtualPid(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getRechargeVirtualPid");
	}
	
	public GetRechargeVirtualPid(Login login,Integer price ) {
		// TODO Auto-generated constructor stub
		paramMap.put("token", login.getToken());
		paramMap.put("payable", price.toString());
		addCommonParam();
		addAction("getRechargeVirtualPid");
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		ParseParamUtil.parseParam(paramMap);
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetRechargeVirtualPidReponse>>(){});
	}
}
