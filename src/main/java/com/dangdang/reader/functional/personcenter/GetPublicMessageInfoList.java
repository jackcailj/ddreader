package com.dangdang.reader.functional.personcenter;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.reponse.ReponseV2;


/*
 * author:cailj
 */
public class GetPublicMessageInfoList extends FunctionalBaseEx{

	ReponseV2<Map<String, Object>> reponseResult;
	
	
	public GetPublicMessageInfoList(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getPublicMessageInfoList");
	}

	public ReponseV2<Map<String, Object>> getReponseResult() {
		return reponseResult;
	}

	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<Map<String, Object>>>(){});
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
	}
}
