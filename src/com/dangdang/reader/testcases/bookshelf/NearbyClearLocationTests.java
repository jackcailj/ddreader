package com.dangdang.reader.testcases.bookshelf;

import java.util.Map;

import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.bookshelf.NearbyClearLocation;

public class NearbyClearLocationTests extends TestCaseBase{
	{
		testDataFile = "bookshelf/nearbyClearLocationTest";
	}
	
	@Test(dataProvider="dataProvider")
	public void nearbyClearLocationTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		NearbyClearLocation location = new NearbyClearLocation(param);
		location.doWorkAndVerify();		
		
		assert(excpectd.equals(location.getReponseResult().getStatus().getCode().toString()));
		assert(location.getDataVerifyResult());
	}
}
