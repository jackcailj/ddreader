package com.dangdang.reader.testcases;

import java.util.Map;

import org.testng.annotations.Test;

import test.JsonObject;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.purchaseAndDownload.ListShoppingCart;

public class ShopCartTestCase extends TestCaseBase{
	
	@Test(dataProvider="dataProvider")
	public void listShoppingCartTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		ListShoppingCart listShoppingCart =new ListShoppingCart(param);
		listShoppingCart.doWorkAndVerify();
		
		
		assert(excpectd.equals(listShoppingCart.getResult().getStatus().getCode().toString()));
		assert(listShoppingCart.getDataVerifyResult()==true);
	}
}
