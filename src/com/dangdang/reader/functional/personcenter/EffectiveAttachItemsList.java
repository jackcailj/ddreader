package com.dangdang.reader.functional.personcenter;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.Register;
import com.dangdang.ddframework.core.ConfigCore;
import com.dangdang.ddframework.core.TestDevice;
import com.dangdang.ddframework.dataverify.RecordVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.AttachAccountItemsVolistResponse;
import com.dangdang.reader.functional.reponse.Data;

/**
 * 附账户铃铛期限查询接口   action=effectiveAttachItemsList
 * @author wuhaiyan
 * 
 * */
public class EffectiveAttachItemsList extends FunctionalBaseEx {
	
	ReponseV2<AttachAccountItemsVolistResponse> reponseResult;

	public EffectiveAttachItemsList(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("effectiveAttachItemsList");
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<AttachAccountItemsVolistResponse>>(){});
	}
	
	public ReponseV2<AttachAccountItemsVolistResponse> getResult(){
		return reponseResult;
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		login = parseResult.getLogin();
	}
	
	@Override
	protected void dataVerify() throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode()==0){
			Integer itemsCount = reponseResult.getData().getAttachAccountItemsVolist().size();
			String custId = login.getCustId();
			String sql = "select a.attach_account_items_id from attach_account_items a left join attach_account_activity b on a.activity_code = b.activity_code"
				    + "	where 1 = 1 and a.cust_id="+custId+" and a.consume_source="+(ConfigCore.getDevice().equals(TestDevice.ANDROID)?"'Android'":"'IOS'")
					+" and a.is_show =1 order by a.effective_date_msec ASC";
			Integer  idCount = DbUtil.selectList(Config.ACCOUNTDBConfig, sql).size();
			dataVerifyManager.add(new ValueVerify<Integer>(itemsCount, idCount));
		}
		
		super.dataVerify();
	}
	
//	select a.*,b.activity_name from attach_account_items a left join attach_account_activity b on a.activity_code = b.activity_code 
//	where 1 = 1 and a.cust_id="+custId+" and a.consume_source="+(ConfigCore.getDevice().equals(TestDevice.ANDROID)?"Android":"IOS")+" and a.is_show =1 order by a.effective_date_msec ASC limit 0, 50 
}
