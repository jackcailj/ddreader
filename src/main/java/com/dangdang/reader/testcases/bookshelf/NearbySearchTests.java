package com.dangdang.reader.testcases.bookshelf;

import java.util.Map;

import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.bookshelf.NearbySearch;

public class NearbySearchTests extends TestCaseBase{
	
	{
		testDataFile = "bookshelf/nearbySearchTest";
	}
	
	@Test(dataProvider="dataProvider")
	public void nearbySearchTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		NearbySearch nearby = new NearbySearch(param);
		nearby.doWorkAndVerify();		
		
		assert(excpectd.equals(nearby.getResult().getStatus().getCode().toString()));
		assert(nearby.getDataVerifyResult());
	}
	

}
