package com.dangdang.reader.functional.param.model;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.common.functional.login.Login;

public class ParseResult {
	ILogin login;

	public ILogin getLogin() {
		return login;
	}

	public void setLogin(ILogin login) {
		this.login = login;
	}
}
