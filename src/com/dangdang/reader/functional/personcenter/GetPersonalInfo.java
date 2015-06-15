package com.dangdang.reader.functional.personcenter;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ecms.EcmsUtil;
import com.dangdang.ecms.meta.BookNote;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.GetPersonalInfoReponse;

public class GetPersonalInfo extends FunctionalBaseEx{
	
	ReponseV2<GetPersonalInfoReponse> reponseResult;

	public GetPersonalInfo(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getPersonalInfo");
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		
		ParseResult parseResult= ParseParamUtil.parseParam(paramMap);
		setLogin(parseResult.getLogin());
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetPersonalInfoReponse>>(){});
	}
	
	public ReponseV2<GetPersonalInfoReponse> getReponseResult() {
		return reponseResult;
	}
	
	@Override
	protected void dataVerify() throws Exception {
		// TODO Auto-generated method stub
		if(reponseResult.getStatus().getCode()==0){
			List<BookNote> bookNotes= EcmsUtil.getBookNote(login.getCustId());
			dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getBookNoteCount(), bookNotes.size()));
		}
		super.dataVerify();
	}
	
}
