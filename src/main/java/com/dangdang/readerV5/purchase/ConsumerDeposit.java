package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.PlatForm;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaActivityInfo;
import com.dangdang.reader.functional.reponse.Account;
import com.dangdang.readerV5.personal_center.GetAccountInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

public class ConsumerDeposit extends FixtureBase{

	MediaActivityInfo mediaActivityInfo;
	GetAccountInfo beforeMasterAcount;
	GetAccountInfo beforeAttachAcount;
	Account accountInfo;
	ReponseV2<Map<String, Object>> reponseResult;
	
	public String UnCorrect="asdsdsasda";
	public String UnCorrectpaytmentId="12345";
	public String UnCorrectrelationProductId="";

	public ReponseV2<Map<String, Object>> getReponseResult() {
		return reponseResult;
	}

	public ConsumerDeposit() {
	}

	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub

		
		if(login!=null){
			//String token =paramMap.get("token").toString();
			//获取充值方式数据
			
				if(paramMap.get("relationProductId")!=null && paramMap.get("relationProductId").toString().equals("auto")
						&&(paramMap.get("flag")==null || !paramMap.get("flag").equals("paymentId_error"))){
					GetDepositShowView depositShowView =new GetDepositShowView(login,PlatForm.getPlatForm(paramMap.get("fromPaltform")),paramMap.get("paymentId"));
					depositShowView.doWork();
					mediaActivityInfo=depositShowView.getReponseResult().getData().getActivityInfos().get(0);
					String relationProductId=mediaActivityInfo.getRelationProductId().toString();

					//if("auto".equals(paramMap.get("relationProductId").toString())){
					paramMap.put("relationProductId", relationProductId);
					//}
					
					
				}
				
				
				//产生订单
				if(paramMap.get("depositOrderNo")!=null && paramMap.get("depositOrderNo").toString().equals("auto") && StringUtils.isNotBlank(paramMap.get("relationProductId"))
						&&(paramMap.get("flag")==null || !paramMap.get("flag").equals("paymentId_error"))){
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

			beforeMasterAcount = new GetAccountInfo(login,true);
			beforeMasterAcount.doWork();

			beforeAttachAcount = new GetAccountInfo(login,false);
			beforeAttachAcount.doWork();
		    
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

			if(login!=null) {
				GetAccountInfo afterMasterAccountInfo = new GetAccountInfo(login, true);
				afterMasterAccountInfo.doWork();

				dataVerifyManager.add(new ValueVerify<Long>(afterMasterAccountInfo.getReponseMasterResult().getData().getAccountTotal(), beforeMasterAcount.getReponseMasterResult().getData().getAccountTotal()+(mediaActivityInfo==null?0:mediaActivityInfo.getDepositMoney())).setVerifyContent("验证主账户金额是否正确"), VerifyResult.SUCCESS);

				GetAccountInfo afterAttachAccountInfo = new GetAccountInfo(login, false);
				afterAttachAccountInfo.doWork();

				dataVerifyManager.add(new ValueVerify<Long>(afterAttachAccountInfo.getReponseAttachResult().getData().getAccountTotal(), beforeAttachAcount.getReponseAttachResult().getData().getAccountTotal()+(mediaActivityInfo==null?0:mediaActivityInfo.getDepositGiftReadPrice())).setVerifyContent("验证副账户金额是否正确"), VerifyResult.SUCCESS);
			}
		}
		else {
			//expectedOperateResult=false;
		}
		super.dataVerify();
	}
	
	
}
