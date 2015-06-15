package com.dangdang.reader.testcases.bookstore;

import java.util.Map;

import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.bookstore.GetBookCategories;
import com.dangdang.reader.functional.purchaseAndDownload.GetEbookOrderFlowV2;

public class GetBookCategoriesTestCase extends TestCaseBase{
	
	{
		testDataFile = "bookstore/GetBookCategoriesTest";
	}
	
	@Test(dataProvider="dataProvider")
	public void GetBookCategoriesTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GetBookCategories block = new GetBookCategories(param);
		block.doWorkAndVerify();

		assert(excpectd.equals(block.getReponseResult().getStatus().getCode().toString()));
		assert(block.getDataVerifyResult());
	}
}
