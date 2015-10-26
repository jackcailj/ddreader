package com.dangdang.reader.functional.purchaseAndDownload;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ecms.meta.Ebook;
import com.dangdang.reader.functional.account.GetAccount;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.AccountReponse;
import com.dangdang.reader.functional.reponse.BorrowRuleReponse;
import com.dangdang.reader.functional.reponse.Rules;

/**
 * 获取续借规则
 * @author guohaiying
 *
 */
public class GetEbookAppendBorrowRule extends FunctionalBaseEx{
	Logger log = Logger.getLogger(GetEbookAppendBorrowRule.class);
	ReponseV2<BorrowRuleReponse> reponseResult;	
	GetAccount accout;
	ReponseV2<AccountReponse> accountReponseResult;
	

	public ReponseV2<AccountReponse> getAccountReponseResult() {
		return accountReponseResult;
	}

	public GetEbookAppendBorrowRule(Map<String, String> param){
		super(param);
		addAction("getEbookAppendBorrowRule");
	}
	
	@Override
	public void parseParam() throws Exception{
		super.parseParam();
		if(login==null){
			login = ParseParamUtil.parseLogin(paramMap);
		}
		//解析productId参数
		Ebook ebook = (Ebook)ParseParamUtil.parseExcelField(paramMap, "productId");
			
	}
	
	@Override
	public void doWork() throws Exception{		
		super.doWork();		
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<BorrowRuleReponse>>(){});
	}
	
	
	long exceptedAttachAccountMoney;
	long exceptedAttachAccountMoneyIOS;
	long exceptedMasterAccountMoney;
	long exceptedMasterAccountMoneyIOS;
	@Override
	public void genrateVerifyData() throws Exception{
		accout = new GetAccount(login, paramMap.get("deviceType"));
		accout.doWork();
		accountReponseResult = accout.getReponseResult();
		exceptedAttachAccountMoney = accountReponseResult.getData().getAccount().getAttachAccountMoney();
		exceptedAttachAccountMoneyIOS = accountReponseResult.getData().getAccount().getAttachAccountMoneyIOS();
		exceptedMasterAccountMoney = accountReponseResult.getData().getAccount().getMasterAccountMoney();
		exceptedMasterAccountMoneyIOS = accountReponseResult.getData().getAccount().getMasterAccountMoneyIOS();
	}
	
	@Override
	public void dataVerify() throws Exception{
		log.info("【续费价格是否计算正确: 】");
		int code = reponseResult.getStatus().getCode();
		if(code==0){
			int price = reponseResult.getData().getPrice();
			List<Rules> list = reponseResult.getData().getRules();
			Rules rules;
			int dayNum;
			int borrowPrice;
			int priceFactor;
			float priceRatio;
			for(int i=0; i<list.size(); i++){
				rules = list.get(0);
				dayNum = rules.getDayNum();
				priceFactor = rules.getPriceFactor();
				priceRatio = rules.getPriceRatio();
				borrowPrice = rules.getPrice();
				String result = String.valueOf(price * dayNum * priceRatio / priceFactor);
				log.info("result");
				log.info("price * dayNum * priceRatio / priceFactor");
				log.info(price + "*" + dayNum + "*" + priceRatio + "/" + priceFactor);
				int exceptedPrice = Integer.valueOf(result.substring(0,result.indexOf(".")));
				log.info("exceptedPrice: " + exceptedPrice);
				log.info("actualPrice: " + borrowPrice);
				if(!(borrowPrice==exceptedPrice)){
					Assert.fail("续借价格错误");
				}				
				
			}	
			log.info("【续费价格验证通过！ 】");
			
		}
		
		//验证点2：账户金额是否获取正确
		log.info("【验证账号金额：】");
		long actualAttachAccountMoney = reponseResult.getData().getAccount().getAttachAccountMoney();
		long actualAttachAccountMoneyIOS = reponseResult.getData().getAccount().getAttachAccountMoneyIOS();
		long actualMasterAccountMoney = reponseResult.getData().getAccount().getMasterAccountMoney();
		long actualMasterAccountMoneyIOS = reponseResult.getData().getAccount().getMasterAccountMoneyIOS();
		Assert.assertEquals(exceptedAttachAccountMoney, actualAttachAccountMoney,"android副账户金额验证不一致");
		Assert.assertEquals(exceptedMasterAccountMoney, actualMasterAccountMoney,"android主账户金额验证不一致");
		Assert.assertEquals(exceptedAttachAccountMoneyIOS, actualAttachAccountMoneyIOS,"ios副账户金额验证不一致");
		Assert.assertEquals(exceptedMasterAccountMoneyIOS, actualMasterAccountMoneyIOS,"ios主账户金额验证不一致");
		log.info("【账号金额验证通过！】");
	}
	
	public ReponseV2<BorrowRuleReponse> getReponseResult() {
		return reponseResult;
	}

	public void setReponseResult(ReponseV2<BorrowRuleReponse> reponseResult) {
		this.reponseResult = reponseResult;
	}
	
	
	public static void main(String[] args) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", "z16@123.com");
		map.put("passWord", "111111");
		map.put("loginType", "email");
		map.put("token", "");
		map.put("productId", "1900101582");//可借阅的书
		GetEbookAppendBorrowRule rule = new GetEbookAppendBorrowRule(map);
		rule.doWorkAndVerify();
		
	}
}
