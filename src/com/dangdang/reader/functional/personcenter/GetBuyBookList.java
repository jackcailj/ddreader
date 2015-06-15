package com.dangdang.reader.functional.personcenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.common.functional.login.Login;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.BuyBookListResponse;

public class GetBuyBookList  extends FunctionalBaseEx{
	ReponseV2<BuyBookListResponse> reponseResult;

	public GetBuyBookList(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getBuyBookList");
	}
	
	public GetBuyBookList(Login login) {
		// TODO Auto-generated constructor stub
		paramMap.put("token", login.getToken());
		paramMap.put("pageNum","1");
		paramMap.put("pageSize", "10");
		
		addAction("getBuyBookList");
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<BuyBookListResponse>>(){});
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		ParseParamUtil.parseParam(paramMap);
		
		if(paramMap.get("lastGetTime")!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Date lastGetTime = sdf.parse(paramMap.get("lastGetTime").toString());
			paramMap.put("lastGetTime", ""+lastGetTime.getTime());
		}
	}
	
	public ReponseV2<BuyBookListResponse> getReponseResult() {
		return reponseResult;
	}
}
