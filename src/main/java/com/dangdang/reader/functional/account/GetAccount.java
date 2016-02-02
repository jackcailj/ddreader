package com.dangdang.reader.functional.account;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.param.model.ParseResult;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.AccountReponse;
import com.dangdang.account.meta.AttachAccount;
import com.dangdang.account.meta.MasterAccount;

public class GetAccount extends FunctionalBaseEx{

	ReponseV2<AccountReponse> reponseResult;
	
	public ReponseV2<AccountReponse> getReponseResult() {
		return reponseResult;
	}


	public GetAccount(Map<String,String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getAccount");
	}
	
	
	public GetAccount(ILogin login,String deviceType) {
		// TODO Auto-generated constructor stub
		paramMap.put("token", login.getToken());
		paramMap.put("deviceType", deviceType);
		addAction("getAccount");
		setLogin(login);
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		ParseResult parseResult =ParseParamUtil.parseParam(paramMap);
		setLogin(parseResult.getLogin());
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<AccountReponse>>(){});
	}
	
	
	@Override
	protected void dataVerify() throws Exception {
		// TODO Auto-generated method stub
		
		if(reponseResult.getStatus().getCode()==0){
			//获取当前账户信息
			MasterAccount masterAccount = DbUtil.selectOne(Config.ACCOUNTDBConfig, "select * from master_account where cust_id="+login.getCustId(), MasterAccount.class);
			
			
			// 没有送银铃铛情况下，副账号不变，送了，则有变化
		    AttachAccount attachAccount = DbUtil.selectOne(Config.ACCOUNTDBConfig, "select * from attach_account where cust_id="+login.getCustId(),AttachAccount.class);
		    if(paramMap.get("deviceType").toLowerCase().contains("android")){
		    	dataVerifyManager.add(new ValueVerify<Long>((long) masterAccount.getMasterAccountMoney(), reponseResult.getData().getAccount().getMasterAccountMoney()));
		    	dataVerifyManager.add(new ValueVerify<Long>((long) attachAccount.getAttachAccountMoney(), reponseResult.getData().getAccount().getAttachAccountMoney()));
		    }
		    else {
		    	dataVerifyManager.add(new ValueVerify<Long>((long) masterAccount.getMasterAccountMoneyIos(), reponseResult.getData().getAccount().getMasterAccountMoneyIOS()));
		    	dataVerifyManager.add(new ValueVerify<Long>((long) attachAccount.getAttachAccountMoneyIos(), reponseResult.getData().getAccount().getAttachAccountMoneyIOS()));
			}
		   }
		super.dataVerify();
	}
}
