package com.dangdang.reader.testcases.bookstore;

import java.util.Map;

import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.purchaseAndDownload.GetEbookOrderFlowV2;
import com.dangdang.reader.functional.purchaseAndDownload.GetRechargeVirtualPid;

public class GetRechargeVirtualPidTestCase extends TestCaseBase{

	@Test(dataProvider="dataProvider")
	public void GetRechargeVirtualPidTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GetRechargeVirtualPid block = new GetRechargeVirtualPid(param);
		block.doWorkAndVerify();

		assert(excpectd.equals(block.getReponseResult().getStatus().getCode().toString()));
		assert(block.getDataVerifyResult());
	}
	
}
