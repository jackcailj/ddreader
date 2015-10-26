package com.dangdang.reader.functional.bookshelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.Login;
import com.dangdang.ddframework.dataverify.RecordExVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.ecms.meta.UserEbookStatus;
import com.dangdang.param.model.ParseResult;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.personcenter.GetBuyBookList;
import com.dangdang.reader.functional.reponse.Data;
import com.dangdang.reader.functional.reponse.MobileEbookInfo;

public class HiddenEbook extends FunctionalBaseEx {
	//ParseResult login;
	ReponseV2<Data> reponseResult;
	static List<String> ids = new ArrayList<String>();
	static String userName = "z11@123.com";
	static String passWord = "111111";
		
	public List<String> getBuyBookProductIds() throws Exception{
		List<String> ids = new ArrayList<String>();
		Map<String, String> param = Util.generateMap("userName="+userName+"&passWord="+passWord+"&loginType=email&lastGetTime=&referType=shelf&relationType=buy&pageNum=1"
				+ "&pageSize=50&textFieldType=txt&gzip=yes&ebookReturnFields=relationType,imgUrl,createDate,desc&token=");
		GetBuyBookList list = new GetBuyBookList(param);
		list.doWork();
		List<MobileEbookInfo>  books = list.getReponseResult().getData().getEbookList();
		if(0==books.size()){
			throw new Exception("已购图书为空");
		}
		else{
			for(int i=0; i<books.size(); i++){
				ids.add(books.get(i).getProductId());
			}
		}
		return ids;	
	}
	
	public String hiddenEbook(int count) throws Exception{		
		String productId = "";
		ids = getBuyBookProductIds();
		for(int i=0; i<count&&i<ids.size(); i++){
			productId += ids.get(i)+",";
		}
		productId = productId.substring(0, productId.length()-1);
		Map<String, String> param = Util.generateMap("userName="+userName+"&passWord="+passWord+"&loginType=email&token=&productIds="+productId);
		HiddenEbook hiddenEbook = new HiddenEbook(param);
		hiddenEbook.doWork();
		if(!(hiddenEbook.getReponseResult().getStatus().getCode().equals(0))){
			throw new Exception("隐藏电子书未成功");
		}
		return productId;
	}
	
	public HiddenEbook(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("hiddenEbook");
	} 
	
	public HiddenEbook() {
		
	} 
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		//http://10.255.223.131/mobile/api2.do?action=hiddenEbook&token=e5370ac1f288266d4ef1e02c9feef29b&productIds=1900101626&deviceType=Android
		//http://10.255.223.131/mobile/api2.do?action=recoverhiddenEbook&token=efd47830d2ced841c0a86d36dc9cc8af&productIds=1900101346&deviceType=Android
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<Data>>(){});
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		login = parseResult.getLogin();
	}
	
	public ReponseV2<Data> getReponseResult() {
		return reponseResult;
	}
	
	@Override
	protected void genrateVerifyData() throws Exception {    
		genrateVerifyData(1);
	}

	protected void genrateVerifyData(int status) throws Exception {      
		String custId = Login.getCusId(userName);
        custId = (custId==null) ? "\'\'" : custId;
    	String ids = paramMap.get("productIds");
    	String[] pIds = (ids==null) ? new String[] {"\'\'"} : ids.split(",");
		for(int i=0; i<pIds.length; i++){
			UserEbookStatus ebookStatus = new UserEbookStatus();
			ebookStatus.setCustId(Long.parseLong(custId));
			ebookStatus.setProductId(pIds[i].equals("\'\'")? 0: Long.parseLong(pIds[i]));
			ebookStatus.setStatus(status);
			dataVerifyManager.add(new RecordExVerify(Config.ECMSDBConfig, ebookStatus,"ebook_status_id",
					" from user_ebook_status where cust_id="+custId+ " and product_id="+pIds[i]+" and status="+status));
		}
	}
	
	@Override
	protected void dataVerify() throws Exception {
		if(reponseResult.getStatus().getCode()!=0){
			expectedOperateResult=false;
		}
		super.dataVerify();
	}
	

}