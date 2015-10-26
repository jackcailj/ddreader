package com.dangdang.reader.functional.personcenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.Register;
import com.dangdang.ddframework.dataverify.RecordVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.param.model.ParseResult;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.Data;

/**
 * 用户签到接口  action=custSigninWithBell
 * 
 * @author wuhaiyan
 * 
 * */
public class CustSigninWithBell  extends FunctionalBaseEx{
	ReponseV2<Data> reponseResult;
	String userName = Util.getRandomString(8)+"@dd.com";
	String pwd = "111111";
    SimpleDateFormat dateFromat = new SimpleDateFormat("yyyy-MM-dd");
   
	public CustSigninWithBell(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("custSigninWithBell");
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
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		Date date = new Date();
		if(paramMap.get("userName").equals("random")){
			paramMap.put("userName", userName);
		}
		if(paramMap.get("signinDate").equals("currenDate")){
			paramMap.put("signinDate", dateFromat.format(date));
		}
		if(paramMap.get("signinDate").equals("before")){
			paramMap.put("signinDate", dateFromat.format(new Date(System.currentTimeMillis()-1000*60*60*24)));
		}
		if(paramMap.get("signinDate").equals("after")){
			paramMap.put("signinDate", dateFromat.format(new Date(System.currentTimeMillis()+1000*60*60*24)));
		}
		if(paramMap.get("userName")!=null&& StringUtils.isNotBlank(paramMap.get("userName").toString())){
			Register register = new Register(paramMap);
			register.doWork();
		}
		
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		login = parseResult.getLogin();
	}
	
//	protected void genrateVerifyData(int status) throws Exception {      
//		String custId = login.getCustId();
//		String sql = "select record_id from `lottery_prize_record` where cust_id="+custId;
//		LotteryPrizeRecord record = new LotteryPrizeRecord();
//		record.setCustId(Long.parseLong(custId));
//		dataVerifyManager.add(new RecordExVerify(Config.ECMSDBConfig, record,"record_id",sql));
//	}
	
	@Override
	protected void dataVerify() throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode()==0){
			String custId = login.getCustId();
			String sql = "select signin_id from signin where cust_id="+custId;
			String id = DbUtil.selectOne(Config.ECMSDBConfig, sql).get("signin_id").toString();
			sql = "select * from signin_detail where signin_id="+id;
			dataVerifyManager.add(new RecordVerify(Config.ECMSDBConfig, sql));
		} 
		super.dataVerify();
	}
}
