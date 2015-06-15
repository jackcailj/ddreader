package com.dangdang.reader.functional.param.parse;

import java.util.HashMap;
import java.util.Map;

import com.dangdang.common.functional.login.Login;
import com.dangdang.reader.functional.param.model.ParseResult;

/**
 * 参数解析工具类， 其它类在解析参数时都统一调用此接口方法。
 * @author guohaiying
 *
 */
public class ParseParamUtil {

	private static LoginParamParse loginParamParse = new LoginParamParse();;
	private static RemoveBlankParamParse removeBlankParamParse = new RemoveBlankParamParse();
	private static ParseFactory parseFactory = new ParseFactory();
	/*
	 * 解析管理类，如果有多个公共解析，放到此处统一处理
	 * 参数：
	 * 		param：读取用户数据生成的测试用例数据
	 * 返回：
	 * 		ParseResult，返回解析结果，增加此结果用来扩展，可以返回多个对象，需要返回多个对象时，在ParseResult类中增加
	 * 					登录失败Login为null;
	 */
	public static ParseResult parseParam(Map<String, String> param) throws Exception{
		ParseResult parseResult = new ParseResult();
		
		Login login = loginParamParse.parse(param);
		removeBlankParamParse.parse(param);
		
		parseResult.setLogin(login);
		
		return parseResult;
	}
	
	public static Login parseLogin(Map<String, String> param) throws Exception{
		return loginParamParse.parse(param);
	}
	
	/**
	 * 解析Excel中的key字段
	 * @param param 
	 * @param key Excel中的字段值
	 * @return
	 */
	public static Object parseExcelField(Map<String, String> param, String key){		
		return parseFactory.createParse(param, key);	
	}
	
	public static void parseExcelField(Map<String, String> param){
		parseFactory.createParse(param);
	}
	
	public static void removeBlackParam(Map<String, String> param) throws Exception{

	}
	
	public static void main(String[] args) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		map.put("userName", "z16@123.com");
		map.put("passWord", "111111");
		map.put("loginType", "email");
		map.put("token", "");
		map.put("sign","购买");
		Login login = ParseParamUtil.parseLogin(map);
		ParseParamUtil.parseExcelField(map, "sign");
		
	}
	
	
}
