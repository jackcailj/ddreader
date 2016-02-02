package com.dangdang.reader.functional.personcenter;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.core.ConfigCore;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.dataverify.RecordVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.ddframework.util.security.SignUtils;
import com.dangdang.param.model.ParseResult;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.Data;


/**
 * 领取/放弃奖品服务接口  action=recivedOrCancelPrize
 * @author wuhaiyan
 * 
 * */
public class RecivedOrCancelPrize extends FunctionalBaseEx{
	{
		Config.getCommonParam().remove("deviceSerialNo");
	}
	String deviceNo;
	ReponseV2<Data> reponseResult;
	String userName = "z11@123.com";
	String pwd = "111111";
	String timestamp;
	String productId;
    String recordId;
	static String sign= "";
	
	public RecivedOrCancelPrize(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("recivedOrCancelPrize");
	}
	
	public void precondition(int repeat) throws Exception{
		GetLotteryResultWithBell result = null;
		deviceNo = Util.getRandomString(15);
		String response = "";
		String param = "action=getLotteryResultWithBell&userName="+userName+"&passWord="+pwd
				       + "&loginType=email&token=&deviceSerialNo="+deviceNo;	
		int i = 0;
		// Testing 环境摇一摇次数默认是99次， Staing 和 online环境默认次数是3
		int j =  (ConfigCore.getEnvironment().equals(TestEnvironment.TESTING))?3:2;
		while(!(response.equals("ebook"))&&i<=j){			
			result = new GetLotteryResultWithBell(Util.generateMap(param));
			result.doWork();
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
	protected void dataVerify() throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode()==0){
			String custId = login.getCustId();
			String sql = "SELECT record_id FROM `lottery_prize_record` where `value`="+productId+
					     " and type_id=3 and cust_id="+custId+" and received_date='"+reponseResult.getData().getCurrentDate()+"'";
			dataVerifyManager.add(new RecordVerify(Config.ECMSDBConfig, sql));
		} 
		else{
			expectedOperateResult =false;
		}
		super.dataVerify();
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		if(sign.isEmpty()){
			precondition(3);
		}
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
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		login = parseResult.getLogin();
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<Data>>(){});
	}
	
	public ReponseV2<Data> getResult(){
		return reponseResult;
	}
	
	
	//SELECT * FROM `lottery_prize_record`
}
