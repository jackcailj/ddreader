package com.dangdang.common.tests.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.dangdang.common.functional.login.ThirdLogin;
import com.dangdang.ddframework.core.TestCaseBase;

public class ThirdLoginTests extends TestCaseBase{
	List<String> ids = new ArrayList<String>();
	{
		testDataFile = "thirdparty/thridLoginTest";
	}

	@Test(dataProvider="dataProvider")
	public void thirdLoginTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		
		ThirdLogin thirdLogin = new ThirdLogin(param);
		thirdLogin.doWorkAndVerify();		
		
		assert(excpectd.equals(thirdLogin.getResult().getStatus().getCode().toString()));
		assert(thirdLogin.getDataVerifyResult());
	}
}
