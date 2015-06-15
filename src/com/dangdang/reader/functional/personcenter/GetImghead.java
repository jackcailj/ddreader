package com.dangdang.reader.functional.personcenter;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.GetImgheadReponse;

public class GetImghead extends FunctionalBaseEx{

	ReponseV2<GetImgheadReponse> reponseResult;
	
	
	public GetImghead(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getImghead");
	}
	
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		
		ParseResult parseResult = ParseParamUtil.parseParam(paramMap);
		setLogin(parseResult.getLogin());
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetImgheadReponse>>(){});
	}
	
	public ReponseV2<GetImgheadReponse> getReponseResult() {
		return reponseResult;
	}


}
