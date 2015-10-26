package com.dangdang.reader.functional.purchaseAndDownload;

import java.util.Map;
import java.util.HashMap;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.core.TestDevice;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ecms.meta.UserEbook;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.BorrowRuleReponse;
import com.dangdang.reader.functional.reponse.PurchaseEbookVirtualPaymentReponse;

/**
 * 续借借阅本
 * @author guohaiying
 *
 */
public class purchaseEbookVirtualPayment2 extends FunctionalBaseEx{
	
	ReponseV2<PurchaseEbookVirtualPaymentReponse> reponseResult;	
	public ReponseV2<PurchaseEbookVirtualPaymentReponse> getReponseResult() {
		return reponseResult;
	}


	ReponseV2<BorrowRuleReponse> ruleReponseResult;
	GetEbookAppendBorrowRule borrowRule;
	
	//解析参数
	public purchaseEbookVirtualPayment2(Map<String, String> param){
		super(param);	
		addAction("purchaseEbookVirtualPayment");
	}
	
	@Override
	public void beforeParseParam() throws Exception {
		super.beforeParseParam();
	}
	
	@Override
	public void parseParam() throws Exception{
		super.parseParam();
		login = ParseParamUtil.parseLogin(paramMap);
		
		//设置时间戳
		long timestamp = System.currentTimeMillis();
		paramMap.put("timestamp", String.valueOf(timestamp));
		
		//获取用户借阅的已过期的书
		UserEbook userEbook = (UserEbook)ParseParamUtil.parseExcelField(paramMap, "productIds");		
		
		//设置加密串
		ParseParamUtil.parseExcelField(paramMap, "sign");
		
		//设置借阅天数
		if(paramMap.get("dayNum").equals("auto")){
			Map<String, String> ruleParam = new HashMap<String, String>();	
			ruleParam.put("token", paramMap.get("token"));
			ruleParam.put("productId", paramMap.get("productIds"));
			borrowRule = new GetEbookAppendBorrowRule(ruleParam);
			borrowRule.setLogin(login);
			borrowRule.doWorkAndVerify();
			ruleReponseResult = borrowRule.getReponseResult();
			Integer day = ruleReponseResult.getData().getRules().get(0).getDayNum();
			paramMap.put("dayNum", String.valueOf(day));
		}	
		
		//设置from_url
		String fromURL = Config.getDevice().toString();
		if(fromURL.equals(TestDevice.ANDROID.toString())){
			paramMap.put("from_url","103");
		}else if(fromURL.equals(TestDevice.IPHONE.toString())){
			paramMap.put("from_url","101");
		}
		
		afterParam();
	}
	
	public void afterParam() throws NumberFormatException, Exception{
		String after = paramMap.get("afterParam");
		if(after.equals(null)){
			paramMap.remove("afterParam");
			return;
		}
		/*if(after.equals("iosPrice")){
			BookStoreTestEvnSQL.setIOSPrice(Long.valueOf(paramMap.get("productIds")));
		}*/
		
		/*if(after.equals("donnotSupportPurchase")){
			BookStoreTestEvnSQL.setBorrowDuration(Long.valueOf(paramMap.get("productIds")));
		}*/
		
		paramMap.remove("afterParam");
	}

	
	@Override
	public void doWork() throws Exception{
		super.doWork();
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<PurchaseEbookVirtualPaymentReponse>>(){});
	}
	
	@Override
	protected void genrateVerifyData() throws Exception {
		
	}
	
	@Override
	public void dataVerify(){
		//验证扣款是否正确
		//验证已购列表中是否有此书
		//验证权限是否下放
		
	}
	
	
	public static void main(String[] args) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", "d2@126.com");
		map.put("passWord", "ddtest");
		map.put("loginType", "email");
		map.put("token", "");
		map.put("productIds", "1900089316");//用户已过期的借阅本
		map.put("sign", "purchase");		
		map.put("dayNum", "auto");
		map.put("isAppendBorrow", "true");
		map.put("afterParam", "iosPrice");
		purchaseEbookVirtualPayment2 p2 = new purchaseEbookVirtualPayment2(map);
		p2.doWork();
		long p = p2.reponseResult.getData().getBorrowDuration();
		System.out.println("aaaa" + p);
	}
}

