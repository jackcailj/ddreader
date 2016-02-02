package com.dangdang.reader.functional.account;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.reponse.GetNeedMergeAccountReponse;

public class GetNeedMergeAccount extends FunctionalBaseEx{
	ReponseV2<GetNeedMergeAccountReponse> reponseResult;
	
	public GetNeedMergeAccount(Map<String, String> parMap) {
		// TODO Auto-generated constructor stub
		super(parMap);
	}
	
	public ReponseV2<GetNeedMergeAccountReponse> getReponseResult() {
		return reponseResult;
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<GetNeedMergeAccountReponse>>(){});
	}
}
