package com.dangdang.reader.functional.param.parse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;



/**
 * 
 * @author guohaiying
 *
 */
public class ParseFactory {
	Logger log = Logger.getLogger(ParseFactory.class);	
	List<String> keyList = new ArrayList<String>();
	
	/**
	 * 根据Excel中的字段值，调用相应解析类
	 * @param keyList  Excel中的字段值
	 * @param paramMap Excel传入的Map值
	 */
	public void createParse(Map<String, String> paramMap){
		getKeyList(paramMap);
//		if(keyList.contains("".precondition)){
//			PreconditionParse pre = new PreconditionParse();
//			pre.parse(paramMap);
//		}
	}
	
	public Object createParse(Map<String, String> paramMap, String key){
		getKeyList(paramMap);
		if(keyList.contains(key)){
			String className = "com.dangdang.reader.functional.param.parse."+key.replaceFirst(key.substring(0,1),key.substring(0,1).toUpperCase())+"Parse";
			Class c = null;
			try {
				c = Class.forName(className);
				log.info("创建解析类： " + c);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				return ((IParamParse)c.newInstance()).parse(paramMap);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	//获取Excel的字段值
	private void getKeyList(Map<String, String> paramMap){
		Iterator<Map.Entry<String, String>> iterator = paramMap.entrySet().iterator(); 
		while(iterator.hasNext()){
			Map.Entry<String, String> entry = iterator.next();
			String key = (String) entry.getKey(); 
			log.info(key);
			keyList.add(key);
		}
	}

	
}
