package com.dangdang.reader.functional.param.parse;

import java.util.Iterator;
import java.util.Map;

import org.testng.Assert;

import com.dangdang.ecms.BookStoreCommSQL;
import com.dangdang.ecms.BookStoreTestEvnSQL;
import com.dangdang.ecms.meta.Ebook;
import com.dangdang.ecms.meta.UserEbook;
import com.dangdang.reader.functional.param.parse._enum.ProductIdsEnum;


public class ProductIdsParse implements IParamParse{

	@Override
	public Object parse(Map<String, String> param) throws Exception {
		String token = param.get("token");
		String productIds = param.get("productIds");
		String timestamp = param.get("timestamp");
		System.out.println("token" + token);
		int index = -1;
		Object returnObject = null;
		
		if(productIds!=null){
			index = ProductIdsEnum.getIndex(productIds);
		}
				
		switch(index){		
			case 1:{//获取用户已过期的借阅本
				String custId = BookStoreCommSQL.getCustIdByToken(token);
				UserEbook userEbook = BookStoreTestEvnSQL.getUserOverdueBorrowBook(custId, Long.valueOf(timestamp));
				if(userEbook.getProductId() == -1){
					Assert.fail("productId参数解析出错：未获取到custId "+custId+"用户下已过期的借阅本");
				}
				param.put("productIds", String.valueOf(userEbook.getProductId()));
				return userEbook;
			}
			case 2:{
				Ebook ebook = BookStoreCommSQL.getOneBorrowBook();	
				param.put("productIds", String.valueOf(ebook.getProductId()));
				return ebook;
			}
		}
		
		return returnObject;
	}

	@Override
	public void parse(Map<String, String> paramMap, String key, String param) throws Exception {

	}

}
