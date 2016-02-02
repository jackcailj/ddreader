package com.dangdang.reader.testcases.bookstore;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.bookstore.Block;
import com.dangdang.reader.functional.purchaseAndDownload.GetEbookAppendBorrowRule;

public class BorrowBookTest extends TestCaseBase{

	GetEbookAppendBorrowRule borrowRule;
	
	@BeforeClass
	public void setUp() throws Exception{
		setDataFile("bookstore/BorrowBookTest");	
	}
	
	@Test(dataProvider="dataProvider")
	public void GetEbookAppendBorrowRuleTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
//		borrowRule = new GetEbookAppendBorrowRule();
//		borrowRule.doWorkAndVerify();

		//assert(excpectd.equals(block.getReponseResult().getStatus().getCode().toString()));
//		assert(borrowRule.getDataVerifyResult());
	}
}
