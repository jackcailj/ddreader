package com.dangdang.readerV5.discovery;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.core.ConfigCore;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.dataverify.RecordVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.ddframework.util.security.SignUtils;
import com.dangdang.reader.functional.reponse.Data;
import fitnesse.slim.SystemUnderTest;

public class RecivedOrCancelPrize extends FixtureBase {
	{
		Config.getCommonParam().remove("deviceSerialNo");
	}
	String deviceNo;
	ReponseV2<Data> reponseResult;
	String userName = "whytest@dd.con";
	String pwd = "111111";
	String timestamp;
	String productId;
    String recordId;
	static String sign= "";
	
	@SystemUnderTest
	public GetLotteryResultWithBell result = new GetLotteryResultWithBell();

	
	public void precondition(int repeat) throws Exception{
		deviceNo = Util.getRandomString(15);
		String response = "";
		String param = "action=getLotteryResultWithBell&userName="+userName+"&passWord="+pwd
				       + "&loginType=email&token=&deviceSerialNo="+deviceNo+"&deviceType="
				       + Config.getCommonParam().get("deviceType");	
		result.setParameters(Util.generateMap(param));
		int i = 0;
		// Testing 环境摇一摇次数默认是99次， Staing 和 online环境默认次数是3
		int j =  (ConfigCore.getEnvironment().equals(TestEnvironment.TESTING))?3:2;
		while(!(response.equals("ebook"))&&i<=j){	
			result.doRequest();
			response = (result.getResult().getData().getLotteryStatus()!=null&&result.getResult().getData().getLotteryStatus().equals("1"))
					   ?result.getResult().getData().getPrizeType():"";
			i++;
		}
		if(i>j){
			if(repeat>1){
			  precondition(repeat-1);
			}
		}
		else{
			String deviceSerialNo = deviceNo;
			timestamp = result.getResult().getData().getTimestamp();
			recordId = result.getResult().getData().getRecordId();		
			productId = result.getResult().getData().getEbook().getProductId();
			String source = deviceSerialNo+timestamp+productId;
			String key = SignUtils.encryptPrizeByMd5(deviceSerialNo, timestamp, productId);
			sign = SignUtils.createBindPermissionSign(source, key);
			System.out.println("Sign 参数值是： "+sign);
		}
		if(sign.isEmpty()){
			throw new Exception("没有摇到电子书，重新摇奖来继续测领奖接口");
		}
	}
	
	 @Override
	 public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode()==0){
			String custId = login.getCustId();
			String sql = "SELECT record_id FROM `lottery_prize_record` where `value`="+productId+
					     " and type_id=3 and cust_id="+custId+" and received_date='"+reponseResult.getData().getCurrentDate()+"'";
			dataVerifyManager.add(new RecordVerify(Config.YCDBConfig, sql));
			super.dataVerify();
		} 
		 else{
		    	dataVerifyResult = false;
		 }			
		 verifyResult(expectedCode);		
	}
	 
	@Override
	 public void setParameters(Map<String, String> params) throws Exception {
		 	if(sign.isEmpty() || paramMap.get("recordId").equals("0")){
				precondition(3);
			}
			super.setParameters(params);
			if(paramMap.get("recordId").equals("fromResponse")){
				paramMap.put("recordId", recordId);
			}
			if(paramMap.get("sign").equals("fromResponse")){
				paramMap.put("sign", sign);
			}
			if(paramMap.get("timestamp").equals("fromResponse")){
				paramMap.put("timestamp", timestamp);
			}
			if(paramMap.get("productId").equals("fromResponse")){
				paramMap.put("productId", productId);
			}		
			paramMap.put("deviceSerialNo", deviceNo);
	}
	
	public ReponseV2<Data> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<Data>>(){});
	}
	
	public static void main(String[] args){
		String deviceSerialNo="860671020032683";
		String timestamp="1456919114699";
		String strategyId="97";
		//String key="";
		String source = deviceSerialNo+timestamp+strategyId;
		String key = SignUtils.encryptPrizeByMd5(deviceSerialNo, timestamp, strategyId);
		//sign = SignUtils.createBindPermissionSign(source, key);
		System.out.println(key);
		
	}

}
