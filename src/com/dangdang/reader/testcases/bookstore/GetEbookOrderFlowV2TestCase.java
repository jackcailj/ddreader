package com.dangdang.reader.testcases.bookstore;

import java.util.Map;

import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.bookstore.Block;
import com.dangdang.reader.functional.purchaseAndDownload.GetEbookOrderFlowV2;

public class GetEbookOrderFlowV2TestCase extends TestCaseBase{

	@Test(dataProvider="dataProvider")
	public void GetEbookOrderFlowV2Test(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GetEbookOrderFlowV2 block = new GetEbookOrderFlowV2(param);
		block.doWorkAndVerify();

		assert(excpectd.equals(block.getReponseResult().getStatus().getCode().toString()));
		assert(block.getDataVerifyResult());
	}
}
