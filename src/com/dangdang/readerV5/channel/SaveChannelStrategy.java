package com.dangdang.readerV5.channel;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.meta.AccountActionTypeInfo;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.account.AccountActionTypeInfoDb;
import com.dangdang.db.account.AccountExperienceItemsDb;
import com.dangdang.db.account.AccountIntegralItemsDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.SaveChannelStrategyResponse;

public class SaveChannelStrategy extends FixtureBase{	
	ReponseV2<SaveChannelStrategyResponse> jsonResult;
	String _userExperience; //用户经验值
	String _userIntegral; //用户积分
	String custId;
	
	@Override
	public void set(String name, String value){
		super.set(name, value);
		String title = "AutoTest"+ Math.random();
		String content = "<body class=\"bodyContent\"><link rel=\"stylesheet\" href=\"http://e.dangdang.com/media/h5/fenxiang/raiders/radiersmodel.css\"><div class=\"raiderwrap\" data-channelid=\"\">" +
				"<div class=\"raiderdes\"></div><div class=\"raidercell\"><h2><p><span class=\"block\"><i></i></span>唐璜——石宴：汉法对照(电子书)</p></h2><div class=\"raidercelldes\"></div>" +
				"<div class=\"raidercellbook\" data-saleid=\"1900089256\" data-mediaid=\"1900089256\" data-productid=\"0\" data-mediatype=\"2\"><div class=\"book\">" +
				"<div class=\"bookcover\" data-imgsrc=\"http://img60.ddimg.cn/digital/product/92/56/1900089256_ii_cover.jpg?version=51ad18ab-a218-4da4-9727-61cb117ec600\"></div><div class=\"bookmes\">" +
				"<p class=\"bookname\">唐璜——石宴：汉法对照(电子书)</p><p class=\"bookauthor\">（美）库克</p><p class=\"bookdes\">史前文明的曙光曾经照彻整个地球，但随着时与事的轮转与更替，这些文明的遗迹已经寥若星辰。神秘的传说，宏传的建筑，种种不可一世的伟业都已成为记忆。但是，人类无止境的探索精神正在使这些往事经由科学的方法重新展观于人们的眼前。本书以大量的田野考古证据揭开了千百年来笼罩其上的重重迷雾。</p></div></div>" +
				"<div class=\"bookprice\"><p class=\"more\">查看详情 &gt;</p><p class=\"price\">电子书：</p></div></div></div></div><script type=\"text/javascript\" src=\"http://e.dangdang.com/media/h5/fenxiang/raiders/radiersmodel.js\">" +
						"</script></body>";
		String author = "hello咪咪";
		String productArray = "[{\"mediaId\":\"1900089256\",\"saleId\":\"1900089256\",\"productId\":\"1900089256\",\"type\":\"2\"}]";
		paramMap.put("title",title);
		paramMap.put("content",content);
		paramMap.put("author",author);
		paramMap.put("productArray",productArray);
		
		System.out.println("title: "+ paramMap.get("title"));
	}
	
	/*
	 * 生成需要验证的数据
	 * //个人发表文章/攻略 30  后台删除文章/攻略31  道长删除文章/攻略32
	 */
	@Override
	protected void genrateVerifyData() throws Exception {
		custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
		_userExperience = AccountExperienceItemsDb.getUserExperience(custId);
		_userIntegral = AccountIntegralItemsDb.getUserIntegral(custId);
	}
	
	
	
	@Override
	public void doWork() throws Exception {
		super.doWork();
		jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<SaveChannelStrategyResponse>>(){});
	}

	@Override
	protected void dataVerify() throws Exception {
		if(reponseV2Base.getStatus().getCode()==0){	
			String custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
			//个人发表文章/攻略 30  后台删除文章/攻略31  道长删除文章/攻略32
			AccountActionTypeInfo info = AccountActionTypeInfoDb.getAccountActionType(30);			
	        //验证json与数据库中数据
			String userExperience = AccountExperienceItemsDb.getUserExperience(custId);
			String userIntegral = AccountIntegralItemsDb.getUserIntegral(custId);
			dataVerifyManager.add(new ValueVerify<String>(jsonResult.getData().getExperience(), String.valueOf(info.getExperienceAward())).setVerifyContent("验证经验值"));
			dataVerifyManager.add(new ValueVerify<String>(jsonResult.getData().getIntegral(), String.valueOf(info.getIntegralAward())).setVerifyContent("验证积分值"));
			
			//验证经验/积分总值
			dataVerifyManager.add(new ValueVerify<Integer>(Integer.valueOf(_userExperience)+info.getExperienceAward(), Integer.valueOf(userExperience)).setVerifyContent("验证经验总值"));
			dataVerifyManager.add(new ValueVerify<Integer>(Integer.valueOf(_userIntegral)+info.getIntegralAward(), Integer.valueOf(userIntegral)).setVerifyContent("验证积分总值"));
		}
		super.dataVerify();
	}
}
