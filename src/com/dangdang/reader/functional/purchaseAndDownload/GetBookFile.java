package com.dangdang.reader.functional.purchaseAndDownload;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.db.ecms.BookStoreCommSQL;
import com.dangdang.ecms.meta.Ebook;
import com.dangdang.param.parse.ParseParamUtil;

public class GetBookFile extends FunctionalBaseEx{
	
	public GetBookFile(Map<String, String> param){
		super(param);
		addAction("getBookFile");
	}
	
	@Override
	public void parseParam() throws Exception{
		super.parseParam();
		login = ParseParamUtil.parseLogin(paramMap);
	}
	
	@Override
	public void dataVerify(){

	}
	
	public String getResult(){
		
		if(result.toString().contains("code")){
			Assert.fail("error message: " + result.toString());
		}
		return result.toString();
	}
	
	
	public static void main(String[] args) throws Exception{
		Ebook borrowBook = BookStoreCommSQL.getOneBorrowBook();
		Map<String, String> param = new HashMap<String, String>();
		param.put("userName","xujie@123.com");
		param.put("passWord","111111");
		param.put("loginType","email");
		param.put("token","");
		param.put("productId",borrowBook.getProductId().toString());
		param.put("version","4.8.2");
		param.put("token","");
		GetBookFile getBookFile = new GetBookFile(param);
		getBookFile.doWorkAndVerify();
		System.out.println(" result " + getBookFile.result );
	}
	
	
}
