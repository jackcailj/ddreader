package com.dangdang.reader.testcases;

import java.util.Map;

import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.SubmitDDClickLog;

public class SubmitDDClickLogTests extends TestCaseBase{

	@Test(dataProvider="dataProvider")
	public void submitDDClickLogTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		param.put("operationLog", SubmitDDClickLog.parseParam(param));		
		SubmitDDClickLog ddClickLog = new SubmitDDClickLog(param);
		ddClickLog.doWorkAndVerify();		
		
		assert(excpectd.equals(ddClickLog.getResult().getStatus().getCode().toString()));
	}
}
