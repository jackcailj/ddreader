package com.dangdang.reader.functional.bookshelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ecms.meta.BookshelfBook;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.NearbyList;
import com.dangdang.reader.functional.reponse.NearbySearchResponse;
import com.dangdang.digital.meta.MediaActivityInfo;

public class NearbySearch extends FunctionalBaseEx {
	ReponseV2<NearbySearchResponse> reponseResult;
	
	public NearbySearch(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("nearbySearch");
	} 	
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		login = parseResult.getLogin();
		paramMap.remove("token");
		if(login!=null){
			paramMap.put("uToken", login.getToken());
		}
	}
	
	public ReponseV2<NearbySearchResponse> getResult() {
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<NearbySearchResponse>>(){});
		return reponseResult;
	}
	
	@Override
	protected void dataVerify() throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode()==0){
			List<NearbyList> list = reponseResult.getData().getNearbyList();
			if(list.size()==0){
				throw new Exception("网络质量差，没找到附近的人");
			}
			for(int i=0; i<list.size(); i++){
				String userId = list.get(i).getUserId();
				String selectString="select productId from bookshelf_book where cust_id="+userId;
				List<BookshelfBook> infos = DbUtil.selectList(Config.ECMSDBConfig, selectString, BookshelfBook.class);
				List<String> list1 = new ArrayList<String>();
				List<String> list2 = new ArrayList<String>();
				for(int j=0; j<infos.size(); j++){
					list1.add(Long.toString(infos.get(j).getProductId()));
					list2.add(Integer.toString(list.get(i).getBooks().get(j).getProductId()));
				}
				dataVerifyManager.add(new ListVerify(list1,list2, true));				
			}
		}
		super.dataVerify();
	}
	

}
