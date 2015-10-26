package com.dangdang.readerV5.purchase;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.Data;

public class AppendCart  extends FixtureBase{
	String cartId;
	
	public ReponseV2<Data> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<Data>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);
		if(login!=null&&cartId==null){
			cartId = login.getCustId();
		}
		if(!(paramMap.get("cartId").isEmpty())){
			paramMap.put("cartId",cartId);
		}
		if(paramMap.get("productIds").equalsIgnoreCase("fromDB")){
			Map<String, Object> map = DbUtil.selectOne(Config.BOOKBARDBConfig, 
					"SELECT * FROM `bar_product_info` where paper_book_status=0 and type=2 limit 1");
			paramMap.put("productIds",map.get("product_id").toString()+".1");
		}
		
	}
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<Data> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}


}
