package com.dangdang.reader.testcases.bookshelf;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.bookshelf.RecoverHiddenEbook;

public class RecoverHiddenEbookTests extends TestCaseBase{
	String[] ids;
	{
		testDataFile = "bookshelf/recoverHiddenEbookTest";
	}
	
	@BeforeClass
	public void setupBeforeClass() throws Exception{
		RecoverHiddenEbook hiddenEbook = new RecoverHiddenEbook();
		ids = hiddenEbook.hiddenEbook(2).split(",");
		Thread.sleep(2000);
	}

	@Test(dataProvider="dataProvider")
	public void recoverHiddenEbookTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		if(param.get("productIds").equals("1Id")){
			param.put("productIds", ids[0]);		
		}
		else{
			if(param.get("productIds").equals("2Id")){
				param.put("productIds", ids[0]+","+ids[1]);		
			}			
		}	
		RecoverHiddenEbook hiddenEbook = new RecoverHiddenEbook(param);
		hiddenEbook.doWorkAndVerify();		
		
		assert(excpectd.equals(hiddenEbook.getReponseResult().getStatus().getCode().toString()));
		assert(hiddenEbook.getDataVerifyResult());
	}
}
