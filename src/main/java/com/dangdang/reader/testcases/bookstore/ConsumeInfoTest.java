package com.dangdang.reader.testcases.bookstore;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.account.AccountConsumeInfo;
import com.dangdang.reader.functional.account.AccountConsumeItemsList;


public class ConsumeInfoTest extends TestCaseBase{

	AccountConsumeInfo consumeInfo;
	AccountConsumeItemsList consumeItemsList;
	
	@BeforeClass
	public void setUp(){
		setDataFile("bookstore/ConsumeInfoTest");		
	}
	
	@Test(dataProvider="dataProvider")
	public void AccountConsumeInfoTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		consumeInfo = new AccountConsumeInfo(param);
		consumeInfo.doWorkAndVerify();

		//assert(excpectd.equals(consumeInfo.getReponseResult().getStatus().getCode().toString()));
		assert(consumeInfo.getDataVerifyResult());
	}
	
	@Test(dataProvider="dataProvider")
	public void AccountConsumeItemsListTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		consumeItemsList = new AccountConsumeItemsList(param);
		consumeItemsList.doWorkAndVerify();

		//assert(excpectd.equals(consumeItemsList.getReponseResult().getStatus().getCode().toString()));
		assert(consumeItemsList.getDataVerifyResult());
	}
	
}
