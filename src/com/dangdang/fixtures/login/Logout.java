package com.dangdang.fixtures.login;

import java.util.Map;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.util.Util;

public class Logout extends FixtureBase{

	public Logout(){
		URL=Config.getLoginUrl();
	}

}
