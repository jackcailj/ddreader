package com.dangdang.reader.functional.personcenter;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.GetPersonalBookNoteInfoListReponse;

public class GetPersonalBookNoteInfoList extends FunctionalBaseEx{
	
	ReponseV2<GetPersonalBookNoteInfoListReponse> reponseResult;

	public ReponseV2<GetPersonalBookNoteInfoListReponse> getReponseResult() {
		return reponseResult;
	}

	public GetPersonalBookNoteInfoList(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getPersonalBookNoteInfoList");
	}
	
	public GetPersonalBookNoteInfoList(ILogin login) {
		// TODO Auto-generated constructor stub
		
		setLogin(login);
		paramMap.put("token", login.getToken());
		paramMap.put("pageNo", "0");
		paramMap.put("pageSize", "10");
		addAction("getPersonalBookNoteInfoList");
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetPersonalBookNoteInfoListReponse>>(){});
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		setLogin(ParseParamUtil.parseLogin(paramMap));
		ParseParamUtil.removeBlackParam(paramMap);
	}
}
