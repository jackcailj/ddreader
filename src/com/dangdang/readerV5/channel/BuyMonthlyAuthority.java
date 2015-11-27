package com.dangdang.readerV5.channel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.meta.MasterAccount;
import com.dangdang.authority.meta.MediaMonthlyAuthority;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.account.MasterAccountDB;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.db.digital.ChannelMonthlyStrategyDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.ChannelMonthlyStrategy;
import com.dangdang.readerV5.reponse.BuyMonthlyAuthorityResponse;

/**
 * 购买频道包月权限
 * @author guohaiying
 */
public class BuyMonthlyAuthority extends FixtureBase{

	ReponseV2<BuyMonthlyAuthorityResponse> jsonResult;
	String custId;
	String channelId;
	String strategyId;
	MediaMonthlyAuthority dbResult1;
	MasterAccount masterAccount1;
	
    @Override
	protected void genrateVerifyData() throws Exception {
    	custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
    	channelId = paramMap.get("cId");
    	dbResult1 = MediaMonthlyAuthorityDb.get(custId, channelId);   	
    	masterAccount1 = MasterAccountDB.getUserMasterAccount(custId);
    	if(masterAccount1.getMasterAccountMoney()<500){//如果账户余额不足，则先设置主账户余额
    		MasterAccountDB.SetUserAccount(custId, "master_account_money");
    		masterAccount1 = MasterAccountDB.getUserMasterAccount(custId);
    	}
	}
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<BuyMonthlyAuthorityResponse >>(){});
    }
    
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	MediaMonthlyAuthority dbResult2 = MediaMonthlyAuthorityDb.get(custId, channelId);
        	
        	//1.验证json返回的包月结束时间是否与数据库数据一致
        	String jsonMonthlyEndTime = jsonResult.getData().getMonthlyEndTime();       	
        	String tmp = df.format(dbResult2.getMonthlyEndTime());
        	String dbMonthlyEndTime = Long.toString(df.parse(tmp).getTime());
        	dataVerifyManager.add(new ValueVerify<String>(jsonMonthlyEndTime, dbMonthlyEndTime).setVerifyContent("验证包月到期时间"));
        	
        	//2.验证请求接口后的时间是否=请求接口前的时间+30天
        	ChannelMonthlyStrategy strategy = null;
        	if(dbResult1!=null){
        		//获取包月策略
        		strategyId = paramMap.get("channelMonthlyStrategyId");
        		strategy = ChannelMonthlyStrategyDb.getChannelMonthlyStrategy(channelId, strategyId);
        		String name = strategy.getName();
        		int n = 0; //包月的月数
        		if(name.contains("一")) n=1;
        		else if(name.contains("三")) n=3;
        		else if(name.contains("六")) n=6;
        		else if(name.contains("十二")) n=12;
        		
        		tmp = df.format(dbResult1.getMonthlyEndTime());
        		String dbMonthlyEndTime1 = Long.toString(df.parse(tmp).getTime());
        		dataVerifyManager.add(new ExpressionVerify((Long.valueOf(dbMonthlyEndTime)-Long.valueOf(dbMonthlyEndTime1)==2592000000l*n)?true:false).setVerifyContent("验证续费后包月到期时间"));
        	}
        	
        	//3.验证isAutomaticallyRenew字段参数值与数据库值一致
        	dataVerifyManager.add(new ValueVerify<String>(paramMap.get("isAutomaticallyRenew"),String.valueOf(dbResult2.getIsAutomaticallyRenew())).setVerifyContent("验证是否自动续费字段"));
            
        	//4.验证扣费是否正确
        	String device = paramMap.get("deviceType");
        	MasterAccount masterAccount2 = MasterAccountDB.getUserMasterAccount(custId);
        	if(device.equals("Android")||device.equals("android")){
        		dataVerifyManager.add(new ExpressionVerify((masterAccount1.getMasterAccountMoney()-masterAccount2.getMasterAccountMoney())==strategy.getAndroid()?true:false).setVerifyContent("验证android包月后账户金额"));
        		dataVerifyManager.add(new ExpressionVerify(masterAccount1.getMasterAccountMoneyIos()==masterAccount2.getMasterAccountMoneyIos()?true:false).setVerifyContent("验证ios包月后账户金额"));
        	}else{
        		dataVerifyManager.add(new ExpressionVerify((masterAccount1.getMasterAccountMoneyIos()-masterAccount2.getMasterAccountMoneyIos())==strategy.getIos()?true:false).setVerifyContent("验证ios包月后账户金额"));
        		dataVerifyManager.add(new ExpressionVerify(masterAccount1.getMasterAccountMoney()==masterAccount2.getMasterAccountMoney()?true:false).setVerifyContent("验证android包月后账户金额"));
        	}
        }
        super.dataVerify();
    }
    
    public static void main(String[] args){
    	//Date dbMonthlyEndTime = dbList2.getMonthlyEndTime();
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	boolean flag = Long.valueOf("1501036890000")-Long.valueOf("1495852890000")==2592000000l*2;
    	System.out.println(Long.valueOf("1501036890000")-Long.valueOf("1495852890000"));
    	System.out.println(flag);
    	try {
			String l = Long.toString(df.parse("2017-07-26 10:41:30").getTime());
			System.out.println("aaa"+l);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
