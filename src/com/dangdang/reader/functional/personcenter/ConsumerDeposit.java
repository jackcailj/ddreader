package com.dangdang.reader.functional.personcenter;

import java.util.Date;
import java.util.Map;

import com.dangdang.ddframework.core.TestDevice;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.common.PlatForm;
import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.Login;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.account.GetAccount;

import com.dangdang.reader.functional.param.parse.ParseParamUtil;

import com.dangdang.reader.functional.reponse.Account;
import com.dangdang.account.meta.AttachAccount;
import com.dangdang.account.meta.MasterAccount;
import com.dangdang.digital.meta.MediaActivityInfo;

public class ConsumerDeposit extends FunctionalBaseEx{
	
	MediaActivityInfo mediaActivityInfo; 
	//MasterAccount masterAccount;
	//AttachAccount attachAccount;
	Account accountInfo;
	ReponseV2<Map<String, Object>> reponseResult;
	
	public String UnCorrect="asdsdsasda";
	public String UnCorrectpaytmentId="12345";
	public String UnCorrectrelationProductId="";

	public ReponseV2<Map<String, Object>> getReponseResult() {
		return reponseResult;
	}

	public ConsumerDeposit(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("consumerDeposit");
		URL=Config.getTempUrl();
	}
	
	/*
	 * 参数：
	 * 		token:
	 * 		money：充值的金额，必须与客户端充值金额一致，目前为5、10，50，100
	 * 		fromPaltform：平台来源
	 */
	public ConsumerDeposit(Login login,Integer money,PlatForm fromPaltform) throws Exception {
		// TODO Auto-generated constructor stub
		paramMap.put("token", login.getToken());
		originalParamMap.put("userName", "test");
		//paramMap.put("relationProductCount", relationProductCount);
		paramMap.put("fromPaltform", fromPaltform.toString());
		DepositShowView depositShowView =new DepositShowView(login.getToken(),paramMap.get("fromPaltform").toString());
		depositShowView.doWork();
		mediaActivityInfo = depositShowView.getMediaActivityInfo(money);
		paramMap.put("relationProductId", mediaActivityInfo.getRelationProductId());
		paramMap.put("payTime", "auto");
		paramMap.put("md5Sign", "auto");
		paramMap.put("paymentId","1016");
		
		paramMap.put("depositOrderNo", "auto");
		
		addAction("consumerDeposit");
		addCommonParam();
		URL=Config.getTempUrl();
		setLogin(login);
	}
	/*
	 * 参数：
	 * 		token:
	 * 		money：充值的金额，必须与客户端充值金额一致，目前为5、10，50，100
	 * 		fromPaltform：平台来源
	 */
	public ConsumerDeposit(Login login,String relationProductId,Integer money,PlatForm fromPaltform) throws Exception {
		// TODO Auto-generated constructor stub
		paramMap.put("token", login.getToken());
		//paramMap.put("userName", login.getLoginInfo().getUserName());
		originalParamMap.put("userName", login.getLoginInfo().getUserName());
		//paramMap.put("relationProductCount", relationProductCount);
		paramMap.put("fromPaltform", fromPaltform.toString());
		//DepositShowView depositShowView =new DepositShowView(token,paramMap.get("fromPaltform").toString());
		//depositShowView.doWork();
		mediaActivityInfo = new MediaActivityInfo();
		mediaActivityInfo.setDepositMoney(money);
		paramMap.put("relationProductId", relationProductId);
		paramMap.put("payTime", "auto");
		paramMap.put("md5Sign", "auto");
		paramMap.put("paymentId","1016");
		
		paramMap.put("depositOrderNo", "auto");
		
		addAction("consumerDeposit");
		addCommonParam();
		URL=Config.getTempUrl();
		this.login =login;
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		//super.parseParam();
		
		setLogin(ParseParamUtil.parseLogin(paramMap));
		ParseParamUtil.removeBlackParam(paramMap);
		
		if(login!=null){
			//String token =paramMap.get("token").toString();
			//获取充值方式数据
			
				if(paramMap.get("relationProductId")!=null && paramMap.get("relationProductId").toString().equals("auto") ){
					DepositShowView depositShowView =new DepositShowView(login.getToken(),paramMap.get("fromPaltform").toString());
					
					depositShowView.doWork();
					mediaActivityInfo=depositShowView.getReponseResult().getData().getdSDepositPayInfoVo().get(3);
					String relationProductId=mediaActivityInfo.getRelationProductId().toString();
					
					//if("auto".equals(paramMap.get("relationProductId").toString())){
					paramMap.put("relationProductId", relationProductId);
					//}
					
					
				}
				
				
				//产生订单
				if(paramMap.get("depositOrderNo")!=null && paramMap.get("depositOrderNo").toString().equals("auto") && paramMap.get("relationProductId")!=null){
					String relationProductId =paramMap.get("relationProductId").toString();
					if(relationProductId.contains("invaild")){
						relationProductId.replace("invaild,", "");
						paramMap.put("relationProductId", relationProductId);
						paramMap.put("depositOrderNo","12334455");
					}
					else {
						GenerateOrder generateOrder =new GenerateOrder(paramMap.get("token").toString(),paramMap.get("relationProductId").toString());
						generateOrder.doWork();
						paramMap.put("depositOrderNo", generateOrder.getOrderId());
						
					}
				}
		}
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(paramMap.get("payTime")!=null && paramMap.get("payTime").toString().equals("auto"))
			paramMap.put("payTime",""+new Date().getTime());
		
	
		if(paramMap.get("md5Sign")!=null){
			//计算md5值
			StringBuilder md5=new StringBuilder();
			md5.append(paramMap.get("token") ==null?"":paramMap.get("token").toString());
			md5.append(paramMap.get("depositOrderNo") ==null?"":paramMap.get("depositOrderNo").toString());
			md5.append(paramMap.get("payTime") ==null?"":paramMap.get("payTime").toString());
			md5.append(paramMap.get("deviceType") ==null?"":paramMap.get("deviceType").toString());
			md5.append(paramMap.get("relationProductId") ==null?"":paramMap.get("relationProductId").toString());
			md5.append(paramMap.get("paymentId") ==null?"":paramMap.get("paymentId").toString());
			md5.append(paramMap.get("fromPaltform") ==null?"":paramMap.get("fromPaltform").toString());
			md5.append(paramMap.get("receiptData") ==null?"":paramMap.get("receiptData").toString());
			//md5.append(paramMap.get("relationProductCount") ==null?"":paramMap.get("relationProductCount").toString());
			if("auto".equals(paramMap.get("md5Sign").toString())){
				md5.append("c914277ba785496d80cbab1428ccc7bbbc557b0a59fc4ae38e2df7bafe3ae98f6c1480fe53f947ccae7b557db46601d5cf250e876b284851b71366724f988b2b");
			}
			else {
				//验证md5key不正确情况
				md5.append("c914277ba785496d80cbab1428ccc7bbbc557b0a59fc4ae38e2df7bafe3ae98f6c1480fe53f947ccae7b557db46601d5cf250e876b284851b71366724f123rte");
			}
			
			paramMap.put("md5Sign", DigestUtils.md5Hex(md5.toString()));
		}
		
	}
	
	@Override
	protected void genrateVerifyData() throws Exception {
		// TODO Auto-generated method stub
		//String token =paramMap.get("token").toString();
		if(login!=null){
			
			GetAccount getAccount =new GetAccount(login,paramMap.get("deviceType").toString());
			getAccount.doWork();
		    accountInfo =getAccount.getReponseResult().getData().getAccount();
			
			String paytmentId=paramMap.get("paymentId");
			//paytmentId=12345表示paymentid不正确
			if(EXCEPTSUCCESS && paytmentId !=null && !paytmentId.toString().equals(UnCorrectpaytmentId)
					&& paramMap.get("depositOrderNo")!=null && !paramMap.get("depositOrderNo").toString().equals(UnCorrect)
					&& paramMap.get("relationProductId")!=null && !paramMap.get("relationProductId").toString().equals(UnCorrect)&& !paramMap.get("relationProductId").toString().equals("1900705483")
					&& paramMap.get("md5Sign")!=null 
					)
			{
				logger.info("setMasterAccountMoney");
				if(Config.getDevice()== TestDevice.ANDROID) {
					accountInfo.setMasterAccountMoney(accountInfo.getMasterAccountMoney() + mediaActivityInfo.getDepositMoney() + (mediaActivityInfo.getDepositGiftGoldPrice() == null ? 0 : mediaActivityInfo.getDepositGiftGoldPrice()));
				}
				else{
					accountInfo.setMasterAccountMoneyIOS(accountInfo.getMasterAccountMoneyIOS() + mediaActivityInfo.getDepositMoney() + (mediaActivityInfo.getDepositGiftGoldPrice() == null ? 0 : mediaActivityInfo.getDepositGiftGoldPrice()));
				}

				//accountInfo.setAttachAccountMoney(accountInfo.getAttachAccountMoney()+(mediaActivityInfo.getDepositGiftReadPrice()==null?0:mediaActivityInfo.getDepositGiftReadPrice()));
			}
			
		/*	
			if(EXCEPTSUCCESS){
				String custId=Login.getCusId(originalParamMap.get("userName").toString());
				OrderForm orderForm =new OrderForm();
				orderForm.setCustId(Long.parseLong(custId));
				//orderForm.setPrice(new BigDecimal(totalCost));
				orderForm.setOrderType("98");
				orderForm.setPayTypeSub(1);
				
				List<OrderItem> orderItems=new ArrayList<OrderItem>();
				//for(Integer productId: productIds){
					OrderItem orderItem =new OrderItem();
					orderItem.setItemId(Long.parseLong(paramMap.get("relationProductId").toString()));
					
					orderItems.add(orderItem);
				//}
				dataVerifyManager.add(new RecordExVerify(Config.ECMSDBConfig, orderForm, "ID", " from order_form where CUST_ID="+custId,orderItems,"ORDER_NO"));
			}*/
		
			//masterAccount.setLastModifiedDate(null);
			
			
			// 没有送银铃铛情况下，副账号不变，送了，则有变化
		   // attachAccount = DbUtil.selectOne(Config.ACCOUNTDBConfig, "select * from attach_account where cust_id="+custId,AttachAccount.class);
		    if(EXCEPTSUCCESS && mediaActivityInfo!=null && mediaActivityInfo.getDepositGiftReadPrice()!=null && mediaActivityInfo.getDepositGiftReadPrice()>0 
		    		&& paytmentId !=null && !paytmentId.toString().equals(UnCorrectpaytmentId)
		    		&& paramMap.get("depositOrderNo")!=null && !paramMap.get("depositOrderNo").toString().equals(UnCorrect)
		    		&& paramMap.get("relationProductId")!=null && !paramMap.get("relationProductId").toString().equals(UnCorrect)&& !paramMap.get("relationProductId").toString().equals("1900705483")
		    		&& paramMap.get("md5Sign")!=null ){
		    	
		    	logger.info("赠送金额:" + mediaActivityInfo.getDepositGiftReadPrice());
				if(Config.getDevice()==TestDevice.ANDROID) {
					accountInfo.setAttachAccountMoney(accountInfo.getAttachAccountMoney() + mediaActivityInfo.getDepositGiftReadPrice());
				}
				else{
					accountInfo.setAttachAccountMoneyIOS(accountInfo.getAttachAccountMoneyIOS() + mediaActivityInfo.getDepositGiftReadPrice());
				}

		    }
		    
		}
		
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult=JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<Map<String, Object>>>(){});
	}
	
	@Override
	protected void dataVerify() throws Exception {
		// TODO Auto-generated method stub
		
		
		//验证返回数据格式是否正确
		if(reponseResult.getStatus().getCode()==0){
			Map<String, Object> dataMap=reponseResult.getData();

			dataVerifyManager.add(new ValueVerify<String>("" + accountInfo.getMasterAccountMoney(), dataMap.get("mainBalance").toString()).setVerifyContent("验证android主账户金额是否正确"));
			dataVerifyManager.add(new ValueVerify<String>("" + accountInfo.getAttachAccountMoney(), dataMap.get("subBalance").toString()).setVerifyContent("验证android副账户金额是否正确"));
            dataVerifyManager.add(new ValueVerify<String>("" + accountInfo.getMasterAccountMoneyIOS(), dataMap.get("mainBalanceIOS").toString()).setVerifyContent("验证ios主账户金额是否正确"));
            dataVerifyManager.add(new ValueVerify<String>("" + accountInfo.getAttachAccountMoneyIOS(), dataMap.get("subBalanceIOS").toString()).setVerifyContent("验证ios副账户金额是否正确"));
		}
		else {
			//expectedOperateResult=false;
		}
		super.dataVerify();
	}
	
	
}
