package com.dangdang.reader.functional.personcenter;

import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ecms.meta.BookNote;
import com.dangdang.param.parse.ParseParamUtil;

public class GetPersonalBookNoteInfo extends FunctionalBaseEx{

	ReponseV2<BookNote> reponseResult;
	public ReponseV2<BookNote> getReponseResult() {
		return reponseResult;
	}
	public GetPersonalBookNoteInfo(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getPersonalBookNoteInfo");
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		
		reponseResult=JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<BookNote>>(){});
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		setLogin(ParseParamUtil.parseLogin(paramMap));
		ParseParamUtil.removeBlackParam(paramMap);
		
		if(paramMap.get("productId")!=null && login!=null){
			GetPersonalBookNoteInfoList notes=new GetPersonalBookNoteInfoList(login);
			notes.doWorkAndVerify();
			
			paramMap.put("productId", notes.getReponseResult().getData().getBookNoteInfoList().get(new Random().nextInt(notes.getReponseResult().getData().getBookNoteInfoList().size())).getProductId().toString());
		}
	}
}
