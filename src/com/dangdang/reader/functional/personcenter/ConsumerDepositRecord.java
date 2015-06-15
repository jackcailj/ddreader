package com.dangdang.reader.functional.personcenter;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.Login;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.ConsumerDepositsReponse;
import com.dangdang.digital.meta.MediaConsumerDeposit;

public class ConsumerDepositRecord extends FunctionalBaseEx{



	ReponseV2<ConsumerDepositsReponse> reponseResult;
	public ConsumerDepositRecord(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getConsumerDeposit");
		URL=Config.getTempUrl();
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		login = parseResult.getLogin();
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		
		reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<ConsumerDepositsReponse>>() {
		});
	}

	public ReponseV2<ConsumerDepositsReponse> getReponseResult() {
		return reponseResult;
	}
	
	@Override
	protected void dataVerify() throws Exception {
		// TODO Auto-generated method stub
		if(reponseResult.getStatus().getCode()==0){
			
			//读取返回
			//操作成功，验证数据正确
			String selectString ="select consumer_deposit_id,creation_date,cust_id,deposit_no,deposit_order_no,main_gold,sub_gold,money,pay_time,"
							+" case  when (payment=1010 or payment=1018) then '支付宝充值' when (payment=1012 or payment=1017) then '微信充值' when (payment=1011 or payment=1016) THEN '财付通充值'  END as payment"
							+" from media_consumer_deposit where cust_id="+login.getCustId()+" and from_paltform='"+paramMap.get("fromPaltform").toString()+"' ORDER BY pay_time desc"
							+" limit "+(paramMap.get("start")==null? 0:paramMap.get("start").toString())+","+(Integer.parseInt(paramMap.get("end").toString())+1);

			List<MediaConsumerDeposit> infos = DbUtil.selectList(Config.YCDBConfig, selectString, MediaConsumerDeposit.class);
			dataVerifyManager.add(new ListVerify(infos, reponseResult.getData().getConsumerDeposits(), true));
		}
		else {
			//dataVerifyManager.add(new ValueVerify<>(infos, reponseResult.getData(), true));
		}
		
		super.dataVerify();
	}
}
