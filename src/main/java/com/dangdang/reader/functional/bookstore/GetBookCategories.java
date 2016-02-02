package com.dangdang.reader.functional.bookstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.reponse.Categories;
import com.dangdang.reader.functional.reponse.CategoriesReponse;



/*
 * 刷新缓存
 * http://10.255.223.131/mobile/api2.do?action=memcache&key=ebook.category.first&act=del
 * http://10.255.223.131//mobile/api2.do?action=memcache&key=android_98.01&act=del
 */
public class GetBookCategories extends FunctionalBaseEx{

	ReponseV2<CategoriesReponse> reponseResult;
	
	public GetBookCategories(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getBookCategories");
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<CategoriesReponse>>(){});
	}
	
	@Override
	protected void dataVerify() throws Exception {
		// TODO Auto-generated method stub
		if(reponseResult.getStatus().getCode()==0){
			List<String> dbCategories= databaseSeq();
			List<Categories> result= reponseResult.getData().getCategories();
			if(result.size()>0)
			{
				List<String> resultList= new ArrayList<String>();
			
				for(Categories categories2:result){
					resultList.add(categories2.getName());
				}
				dataVerifyManager.add(new ListVerify(dbCategories, resultList, false),VerifyResult.SUCCESS);
			}
		}
		super.dataVerify();
		
	}
	
	public ReponseV2<CategoriesReponse> getReponseResult() {
		return reponseResult;
	}

	public List<String> databaseSeq() throws Exception{
		String selectString = "select value from sys_property WHERE `key`='ebook.category.first'";
		List<Map<String, Object>> result=DbUtil.selectList(Config.ECMSDBConfig,selectString);
		
		List<String> seqList = new ArrayList<String>();
		if(result.size()==0){
			return seqList;
		}
		String[] values = result.get(0).get("value").toString().split(",");
		
		
		for(String value:values){
			String[] pairs=value.split(":");
			seqList.add(pairs[1]);
		}
		return seqList;
	}
}
