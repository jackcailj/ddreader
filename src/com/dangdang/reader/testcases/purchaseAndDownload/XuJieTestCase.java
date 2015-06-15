package com.dangdang.reader.testcases.purchaseAndDownload;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.purchaseAndDownload.purchaseEbookVirtualPayment2;

public class XuJieTestCase extends TestCaseBase{

	@BeforeClass
	public void setUp() throws Exception{
		setDataFile("purchaseAndDownload/purchaseEbookVirtualPayment2");	
	}
	
	@Test(dataProvider="dataProvider")
	public void XuJieTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		purchaseEbookVirtualPayment2 purchase2 =new purchaseEbookVirtualPayment2(param);
		purchase2.doWorkAndVerify();

		assert(excpectd.equals(purchase2.getReponseResult().getStatus().getCode().toString()));
		//assert(purchase2.getDataVerifyResult());
	}
}
