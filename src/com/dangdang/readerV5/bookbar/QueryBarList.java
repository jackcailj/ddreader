package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Bar;
import com.dangdang.bookbar.meta.DefaultImage;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.readerV5.reponse.BarDefImgResponse;
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
		    String barName="";
		    String rBarName="";
			super.setParameters(params);
			String sql = "SELECT bar_name FROM `bar` where length(bar_name)>4 ORDER BY rand() limit 1";
			Map<String,Object> map = DbUtil.selectOne(Config.BOOKBARDBConfig, sql);	
			if(paramMap.get("barName")!=null&&paramMap.get("barName").equals("Random")){
				rBarName = Util.getRandomString(8)+"careatebar"+((new Random()).nextInt());
				paramMap.put("barName", rBarName);
			}
			if(paramMap.get("barName")!=null&&paramMap.get("barName").equals("FromDB")){
				barName = map.get("bar_name").toString();
				paramMap.put("barName", barName);
			}
			if(paramMap.get("barName")!=null&&paramMap.get("barName").equals("FromDB-sub")){
				barName = map.get("bar_name").toString().substring(0, 1);
				paramMap.put("barName", barName);
			}
	  	}
	 
	 @Override
	 public void dataVerify(String expectedCode) throws Exception {
			reponseResult = getResult();
			if(reponseResult.getStatus().getCode() == 0){
				String sql = "SELECT bar_id, bar_name, member_num FROM bar where bar_name like '%"+paramMap.get("barName")+"%'";
				List<Bar>  list = DbUtil.selectList(Config.BOOKBARDBConfig, sql, Bar.class);				
				dataVerifyManager.add(new ListVerify(list, reponseResult.getData().getBarList(),true));
				super.dataVerify();
			}	
			else{
				dataVerifyResult = false;
				//TODO will add verify
			}
			verifyResult(expectedCode);
		}
}
