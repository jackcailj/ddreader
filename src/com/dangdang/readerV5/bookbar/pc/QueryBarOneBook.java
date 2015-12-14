package com.dangdang.readerV5.bookbar.pc;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BarBooks;
//import com.dangdang.base.bookbar.client.api.IBarApi;
//import com.dangdang.base.bookbar.client.vo.*;

public class QueryBarOneBook extends FixtureBase {
	//ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");
	//IBarApi iBarApi = (IBarApi)factory.getBean("iBarApi");
	

	String barId;
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {		
			super.setParameters(params);
			String sql = "select bar_id from bar_product_info where is_has_bar=1 and bar_id in "
					+ "(select bar_id from bar where bar_status=2) order by rand() limit 1";
			Map<String,Object> map = DbUtil.selectOne(Config.BOOKBARDBConfig, sql);	
			if(paramMap.get("barId")!=null&&paramMap.get("barId").equals("FromDB")){
				barId = map.get("bar_id").toString();
				paramMap.put("barId", barId);
			}
	 }
	
	@Override
	 public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<BarBooks> reponseResult = 
					JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BarBooks>>(){});
			if(reponseResult.getStatus().getCode() == 0){
//				String sql = "SELECT * FROM bar_product_info where bar_id="+barId;
//				List<BarProductInfo>  list = DbUtil.selectList(Config.BOOKBARDBConfig, sql, BarProductInfo.class);	

//				List<Map<String,Object>> books = iBarApi.queryBarOneBook(Long.parseLong(barId));
//				for(int i=0; i<books.size(); i++){
//					logger.info(books.get(i).toString());					
//				}
				dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getBooks().size(),1,false));

				super.dataVerify();
			}	
			else{
				dataVerifyResult = false;
				//TODO will add verify
			}
			verifyResult(expectedCode);
		}

}
