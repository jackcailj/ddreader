package com.dangdang.readerV5.discovery;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.Data;

public class GetClienInfo extends FixtureBase{
	
	 public ReponseV2<Data> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<Data>>(){});
	 }
	 
	 @Override
	 public void dataVerify(String expectedCode) throws Exception {
		    ReponseV2<Data> reponseResult = getResult();
		    if(reponseResult.getStatus().getCode()==0){
		    	String sql = "select * from lottery_config where config_id=1";
		    	Map<String,Object> config = DbUtil.selectOne(Config.YCDBConfig, sql);
		    	dataVerifyManager.add(new ValueVerify<Integer>(Integer.parseInt(config.get("day_num").toString()), 
		    		                	reponseResult.getData().getDayNum(),false));
		    	super.dataVerify();		
			}
		    else{
		    	dataVerifyResult = false;
		    }			
			verifyResult(expectedCode);
	}
}
