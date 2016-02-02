package com.dangdang.reader.testcases.bookshelf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.bookshelf.GetHiddenEbook;

public class GetHiddenEbookTests extends TestCaseBase{
	List<String> ids = new ArrayList<String>();
	Date date = new Date();
	{
		testDataFile = "bookshelf/getHiddenEbookTest";
	}
	
	@BeforeClass
	public void setupBeforeClass() throws Exception{
		GetHiddenEbook getHiddenEbook = new GetHiddenEbook();
		getHiddenEbook.hiddenEbook(2);
	}

	
	@Test(dataProvider="dataProvider")
	public void getHiddenEbookTest(String caseName,Map<String, String> param,String excpectd) throws Exception {		
		if("currentTime".equals(param.get("lastGetTime"))){
			param.put("lastGetTime", Long.toString(date.getTime()));
		}
		GetHiddenEbook getHiddenEbook = new GetHiddenEbook(param);
		getHiddenEbook.doWorkAndVerify();		
		
		assert(excpectd.equals(getHiddenEbook.getResult().getStatus().getCode().toString()));
		assert(getHiddenEbook.getDataVerifyResult());
		
		//{"data":{"currentDate":"2015-04-10 11:58:57","ebookList":["1900046382","1900046395","1900328769","1900332294"],
		//"ebookNum":4,"ebookTotalNum":4,"systemDate":"1428638337529"},"status":{"code":0},"systemDate":1428638337529}
	}

}
