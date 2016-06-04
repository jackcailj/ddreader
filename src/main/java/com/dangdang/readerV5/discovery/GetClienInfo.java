package com.dangdang.readerV5.discovery;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.LotteryConfigDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.LotteryConfig;
import com.dangdang.readerV5.reponse.Data;

public class GetClienInfo extends FixtureBase{
	int canDoNum;
	
	 public ReponseV2<Data> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<Data>>(){});
	 }	 
	 
	 @Override
	 public void dataVerify(String expectedCode) throws Exception {
		    ReponseV2<Data> reponseResult = getResult();
		    if(reponseResult.getStatus().getCode()==0){
		    	LotteryConfig db = LotteryConfigDb.getLotteryConfig();
		    	//验证DayNum
		    	dataVerifyManager.add(new ValueVerify<Integer>(db.getDayNum(), reponseResult.getData().getDayNum()));
		    	super.dataVerify();	
		    	//验证CanDoNum
			}
		    else{
		    	dataVerifyResult = false;
		    }			
			verifyResult(expectedCode);
	}
}
