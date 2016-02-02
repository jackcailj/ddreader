package com.dangdang.reader.functional.bookshelf;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dataverify.RecordVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.param.model.ParseResult;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.Data;

public class UploadBookList extends FunctionalBaseEx {
	ReponseV2<Data> reponseResult;
	List<Map<String, Object>> resultList;
	public UploadBookList(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("uploadBookList");
	} 
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		login = parseResult.getLogin();
		
		resultList= DbUtil.selectList(Config.ECMSDBConfig, "select productId from ebook where shelfStatus=1 limit 2");
		String data = paramMap.get("data");
		if(paramMap.get("token")!=null){
			data = data.replace("fromLogin", paramMap.get("token"));
		}
		if(data!=null){
			if(data.contains("pId1FromDB")){
				System.out.println(resultList.get(0).get("productId").toString());
				data = data.replace("pId1FromDB", resultList.get(0).get("productId").toString());
			}
			if(data.contains("pId2FromDB")){
				data = data.replace("pId2FromDB", resultList.get(1).get("productId").toString());		
			}
			paramMap.put("data", data);
		}		
	}
		
	
	public ReponseV2<Data> getResult() {
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<Data>>(){});
		return reponseResult;
	}	
	
	@Override
	protected void dataVerify() throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode()==0){
			String custId = login.getCustId();
			for(int i=0; i<resultList.size(); i++){
				String sql = "SELECT * FROM bookshelf_book  bb left join thirdparty_cust_id  tc "
						   + "on bb.cust_id=tc.id and tc.CUST_ID="+custId+" WHERE product_id="
						   +resultList.get(i).get("productId").toString()+" and tc.id is not null";
				dataVerifyManager.add(new RecordVerify(Config.ECMSDBConfig, sql));
				String id = DbUtil.selectOne(Config.ECMSDBConfig, sql).get("category_id").toString();
				sql = "SELECT * FROM bookshelf_category WHERE c_id="+id+" and cust_id in (SELECT id FROM thirdparty_cust_id WHERE CUST_ID="+custId+")";
				dataVerifyManager.add(new RecordVerify(Config.ECMSDBConfig, sql));
			}			
		} 
		super.dataVerify();
	}
}
