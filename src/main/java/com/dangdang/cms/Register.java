package com.dangdang.cms;

import com.dangdang.autotest.common.FixtureBase;

/**
 * cms后台注册
 * @author guohaiying
 * @date 2016年5月23日 下午5:34:31
 */
public class Register extends FixtureBase{
	
	public void setUserName(String uname){
		paramMap.put("userName", uname);
	}
	
	

}
