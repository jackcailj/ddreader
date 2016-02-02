package com.dangdang.readerV5.bookbar;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Bar;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.readerV5.reponse.BarListResponse;


/**
 * 书吧搜索接口
 * @author wuhaiyan
 */
public class QueryBarList  extends FixtureBase {
	 ReponseV2<BarListResponse>   reponseResult;
	 
	 public ReponseV2<BarListResponse> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BarListResponse>>(){});
	}
	 
	 @Override
	 public void setParameters(Map<String, String> params) throws Exception {
		    String rBarName="";
			super.setParameters(params);
			if(paramMap.get("barName")!=null&&paramMap.get("barName").equals("Random")){
				rBarName = Util.getRandomString(8)+"careatebar"+((new Random()).nextInt());
				paramMap.put("barName", rBarName);
			}
	  	}
	 
	 @Override
	 public void dataVerify(String expectedCode) throws Exception {
			reponseResult = getResult();
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
