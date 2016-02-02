package com.dangdang.reader.testcases.reader;

import java.util.Map;

import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.personcenter.GetPersonalInfo;
import com.dangdang.reader.functional.reader.UpdateBookCloudSyncReadInfo;
import com.mysql.fabric.FabricCommunicationException;

/*
 * author:cailj
 * 阅读器相关测试用例
 */
public class ReaderTestCase extends TestCaseBase{

	
	@Test(dataProvider="dataProvider")
	public void UpdateBookCloudSyncReadInfoTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		UpdateBookCloudSyncReadInfo functional =new UpdateBookCloudSyncReadInfo(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}	
}
