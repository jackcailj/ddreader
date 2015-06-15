package com.dangdang.reader.functional.personcenter;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dataverify.RecordExVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.ecms.meta.LotteryPrizeRecord;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.LotteryResponse;

/**
 * 获取摇奖结果  action=getLotteryResultWithBell
 * @author wuhaiyan
 * 
 * */
public class GetLotteryResultWithBell extends FunctionalBaseEx{
	ReponseV2<LotteryResponse> reponseResult;
	{
		Config.getCommonParam().remove("deviceSerialNo");
	}

	static String deviceNo;
	static{
		deviceNo = Util.getRandomString(5);
	}

	public GetLotteryResultWithBell(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getLotteryResultWithBell");
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<LotteryResponse>>(){});
	}
	
	public ReponseV2<LotteryResponse> getResult(){
		return reponseResult;
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		paramMap.put("deviceSerialNo", deviceNo);
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		login = parseResult.getLogin();
	}
	
	protected void genrateVerifyData(int status) throws Exception {      
		String custId = login.getCustId();
		String sql = "select record_id from `lottery_prize_record` where cust_id="+custId;
		LotteryPrizeRecord record = new LotteryPrizeRecord();
		record.setCustId(Long.parseLong(custId));
		dataVerifyManager.add(new RecordExVerify(Config.ECMSDBConfig, record,"record_id",sql));
	}
	
	@Override
	protected void dataVerify() throws Exception {
		if(reponseResult.getStatus().getCode()!=0){
			expectedOperateResult=false;
		}
		super.dataVerify();		
	}

}
