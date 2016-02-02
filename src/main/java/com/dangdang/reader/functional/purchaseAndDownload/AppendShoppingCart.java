package com.dangdang.reader.functional.purchaseAndDownload;

import java.util.Map;

import com.dangdang.autotest.common.FunctionalBaseEx;

/*
 * author:cailj
 */
public class AppendShoppingCart extends FunctionalBaseEx {
	
	public AppendShoppingCart(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("appendShoppingCart");
	}
	
	public AppendShoppingCart(String token,String cardId,String productId) {
		// TODO Auto-generated constructor stub
		paramMap.put("token", token);
		paramMap.put("cardId", cardId);
		paramMap.put("productIds", productId);
		addAction("appendShoppingCart");
	}
}
