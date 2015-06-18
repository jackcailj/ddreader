package com.dangdang.reader.functional.personcenter;

import java.util.ArrayList;
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
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.PayMentReponse;
import com.dangdang.digital.meta.MediaActivityInfo;

public class LastTimePayment extends FunctionalBaseEx{

	ReponseV2<PayMentReponse> reponseResult;
		
	public LastTimePayment(Map<String,String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("findLastTimePayment");
		URL=Config.getTempUrl();
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		ParseParamUtil.parseParam(paramMap);
	}
	
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<PayMentReponse>>(){});
	}
	
	@Override
	protected void dataVerify() throws Exception {
		// TODO Auto-generated method stub
		if(reponseResult.getStatus().getCode()==0){
			//操作成功，验证数据正确
			String selectString="select activity_id,activity_name,activity_type_code,activity_type_id,creation_date,creator,deposit_gift_read_price,"
					+" deposit_money,deposit_read_price,end_time,last_changed_date,modifier,monthly_payment_relation,monthly_payment_type,new_level,"
			        +" original_level,relation_product_id,start_time,`status` "
					+ " from (select payment from media_consumer_deposit where cust_id="+Login.getCusId(originalParamMap.get("userName"))+" ORDER BY pay_time desc LIMIT 1) as a"
					+" LEFT JOIN  media_activity_info mai on mai.activity_type_id=a.payment where mai.from_paltform='ds_android' "
;
			List<MediaActivityInfo> infos = DbUtil.selectList(Config.YCDBConfig, selectString, MediaActivityInfo.class);
			List<MediaActivityInfo> returnValues=null;
			if(reponseResult.getData() == null){
				returnValues=new ArrayList<MediaActivityInfo>();
			}
			else {
				returnValues=  reponseResult.getData().getPayment();
			}
			dataVerifyManager.add(new ListVerify(infos,returnValues, true));
		}
		else {
			//dataVerifyManager.add(new ValueVerify<>(infos, reponseResult.getData(), true));
		}
		super.dataVerify();
	}

	public ReponseV2<PayMentReponse> getReponseResult() {
		return reponseResult;
	}
}
