package com.dangdang.readerV5.discovery;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.RecordExVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.ecms.meta.LotteryPrizeRecord;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.LotteryResponse;

public class GetLotteryResultWithBell extends FixtureBase {
//	{
//		Config.getCommonParam().remove("deviceSerialNo");
//		Config.getCommonParam().remove("deviceType");
//	}

	static String deviceNo;
	static{
		deviceNo = Util.getRandomString(5);
	}
	
	 public ReponseV2<LotteryResponse> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<LotteryResponse>>(){});
	 }
	 
	 @Override
	 public void setParameters(Map<String, String> params) throws Exception {
		 ParseResult parseResult=ParseParamUtil.parseParameter(params);
		 paramMap =  params;
		 Config.getCommonParam().remove("deviceSerialNo");
		 Config.getCommonParam().remove("deviceType");
		 paramMap.putAll(Config.getCommonParam());
		 login = parseResult.getLogin();
		 if(paramMap.get("deviceSerialNo")!=null&&paramMap.get("deviceSerialNo").equals("Random")){
			 paramMap.put("deviceSerialNo", deviceNo);
		 }
		 handleParam();		 
	 }
	 
	 @Override
	 public String doRequest() throws Exception{
		 super.doWork();
		 return result.toString();		 
	 }
	
	protected void genrateVerifyData(int status) throws Exception {      
		String custId = login.getCustId();
		String sql = "select record_id from `lottery_prize_record` where cust_id="+custId;
		LotteryPrizeRecord record = new LotteryPrizeRecord();
		record.setCustId(Long.parseLong(custId));
		dataVerifyManager.add(new RecordExVerify(Config.YCDBConfig, record,"record_id",sql));
	}
	
	 @Override
	 public void dataVerify(String expectedCode) throws Exception {
		    ReponseV2<LotteryResponse> reponseResult = getResult();
		    if(reponseResult.getStatus().getCode()==0){
		    	
		    	super.dataVerify();		
			}
		    else{
		    	dataVerifyResult = false;
		    }			
			verifyResult(expectedCode);
		}

}
