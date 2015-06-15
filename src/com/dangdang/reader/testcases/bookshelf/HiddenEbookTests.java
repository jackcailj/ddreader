package com.dangdang.reader.testcases.bookshelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.bookshelf.HiddenEbook;

public class HiddenEbookTests extends TestCaseBase{
	List<String> ids = new ArrayList<String>();
	{
		testDataFile = "bookshelf/hiddenEbookTest";
	}
	
	@BeforeClass
	public void setupBeforeClass() throws Exception{
		HiddenEbook hiddenEbook = new HiddenEbook();
		ids = hiddenEbook.getBuyBookProductIds();
	}

	@Test(dataProvider="dataProvider")
	public void hiddenEbookTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		if(param.get("productIds").equals("1Id")){
			param.put("productIds", ids.get(0));		
		}
		else{
			if(param.get("productIds").equals("2Id")){
				param.put("productIds", ids.get(0)+","+ids.get(1));		
			}			
		}	
		HiddenEbook hiddenEbook = new HiddenEbook(param);
		hiddenEbook.doWorkAndVerify();		
		
		assert(excpectd.equals(hiddenEbook.getReponseResult().getStatus().getCode().toString()));
		assert(hiddenEbook.getDataVerifyResult());
	}
}
