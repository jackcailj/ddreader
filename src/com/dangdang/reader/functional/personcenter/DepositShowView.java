package com.dangdang.reader.functional.personcenter;

import java.util.List;
import java.util.Map;

import com.dangdang.ecms.EcmsUtil;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.common.PlatForm;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.core.TestDevice;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.ActivityInfoListReponse;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaActivityInfo;


/*
author:cailianjie
desc:此接口只用于android，ios使用接口 getDepositShowView
 */
public class DepositShowView extends FunctionalBaseEx{

	ReponseV2<ActivityInfoListReponse> reponseResult;
	
	public ReponseV2<ActivityInfoListReponse> getReponseResult() {
		return reponseResult;
	}

	public DepositShowView(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		
		URL=Config.getTempUrl();
		addAction("getDSDepositShowView");
	}
	
	public DepositShowView(String token,String fromPaltform) {
		// TODO Auto-generated constructor stub
		paramMap.put("token", token);
		//paramMap.put("paymentId", playmentId);
		paramMap.put("fromPaltform", fromPaltform);
		
		URL=Config.getTempUrl();
		addAction("getDSDepositShowView");
	}
	
	public MediaActivityInfo getMediaActivityInfo(Integer Money){
		List<MediaActivityInfo> infos= reponseResult.getData().getdSDepositPayInfoVo();
		for(MediaActivityInfo info : infos){
			if(info.getDepositMoney().equals(Money*100)){
				return info;
			}
		}
		
		return null;
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
		reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<ActivityInfoListReponse>>(){});
	}
	
	@Override
	protected void dataVerify() throws Exception {
		// TODO Auto-generated method stub
		if(reponseResult.getStatus().getCode()==0){
			//操作成功，验证数据正确
			String fromPaltform="";
			if(paramMap.get("fromPaltform")==null){
				fromPaltform=PlatForm.DDREADER_ANDROID.toString();
			}
			else {
				fromPaltform=Config.getDevice()==TestDevice.ANDROID?paramMap.get("fromPaltform").toString():PlatForm.DDREADER_IOS.toString();
			}
			/*String selectString="select  deposit_gift_read_price,deposit_money,deposit_read_price,end_time,relation_product_id,start_time"
							+" from media_activity_info where from_paltform='"+fromPaltform+"' "+(fromPaltform.equals("ds_ios")?" and relation_product_id like '"+Config.getDevice().toString()+"%'":" and activity_name like "+(Config.getEnvironment()==TestEnvironment.ONLINE?"'%支付宝%'":"'%微信%'"))+ " and status=1"
						    +" ORDER BY  deposit_read_price";*/
			List<MediaActivityInfo> infos = EcmsUtil.getDepositShowView(fromPaltform);
			//返回数量不能为0
			dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getdSDepositPayInfoVo().size(),0),VerifyResult.FAILED);
			//返回数据与查询数据一致
			dataVerifyManager.add(new ListVerify(infos, reponseResult.getData().getdSDepositPayInfoVo(), true));	
		}
		else {
			//dataVerifyManager.add(new ValueVerify<>(infos, reponseResult.getData(), true));
		}
		super.dataVerify();
	}
}
