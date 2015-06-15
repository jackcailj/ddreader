package com.dangdang.reader.functional.param.parse;

import java.util.Map;

import com.dangdang.common.functional.login.Login;

/**
 * Excel字段解析接口，对于Excel中单个字段进行解析时都需要实现此接口
 * @author guohaiying
 *
 */
public interface IParamParse {
	public Object parse(Map<String, String> param) throws Exception;
}
