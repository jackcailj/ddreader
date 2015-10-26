package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.PlatForm;
import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.core.TestDevice;
import com.dangdang.ddframework.dataverify.RegexVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.db.digital.MediaActivityInfoDb;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.GetPaymentReponse;
import com.dangdang.reader.functional.reponse.PayMent;

import java.util.List;


/*
author:cailianjie
desc:此接口只用于android，ios使用接口 getDepositShowView
 */
public class GetPayment extends FixtureBase{

	ReponseV2<GetPaymentReponse> reponseResult;
	
	public ReponseV2<GetPaymentReponse> getReponseResult() {
		return reponseResult;
	}

	public GetPayment() {
	}

	public GetPayment(ILogin login,PlatForm platForm) {
		setLogin(login);
		paramMap.put("token", login.getToken());
		paramMap.put("fromPaltform",platForm.toString());
	}


	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		
		ParseParamUtil.parseParam(paramMap);
		
		if(paramMap.get("fromPaltform")!=null && EXCEPTSUCCESS){
			paramMap.put("fromPaltform", Config.getDevice()==TestDevice.ANDROID?PlatForm.DDREADER_ANDROID.toString():PlatForm.DDREADER_IOS.toString());
		}

	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<GetPaymentReponse>>(){});
	}
	
	@Override
	protected void dataVerify() throws Exception {
		// TODO Auto-generated method stub
		if(reponseResult.getStatus().getCode()==0){
			//操作成功，验证数据正确
			PlatForm fromPaltform=null;
			if(paramMap.get("fromPaltform")==null){
				fromPaltform=PlatForm.DDREADER_ANDROID;
			}
			else {
				fromPaltform=Config.getDevice()==TestDevice.ANDROID?fromPaltform=PlatForm.DDREADER_ANDROID:PlatForm.DDREADER_IOS;
			}
			List<PayMent> payMents= MediaActivityInfoDb.getPayment(fromPaltform);

			//返回数据与查询数据一致
			dataVerifyManager.add(new ValueVerify<Integer>(payMents.size(), reponseResult.getData().getPayment().size()));

			for(PayMent payMent:payMents){
				StringBuilder regexArticleId = new StringBuilder();
				regexArticleId.append(Util.getJsonRegexString("maxGiving", payMent.getMaxGiving().toString()));
				regexArticleId.append(".*?");
				regexArticleId.append(Util.getJsonRegexString("paymentId", payMent.getPaymentId().toString()));


				dataVerifyManager.add(new RegexVerify(regexArticleId.toString(), result.toString()));
			}
		}
		else {
			dataVerifyManager.add(new ValueVerify<Object>(null, reponseResult.getData().getPayment()), VerifyResult.SUCCESS);
		}
		super.dataVerify();
	}
}
