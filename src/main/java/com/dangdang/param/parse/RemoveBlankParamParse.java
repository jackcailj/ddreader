package com.dangdang.param.parse;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


/*
 * 移除为空的字段
 * 规则：
 * 如果字段为空，代表不传此字段
 * 如果字段值为REMOVE，代表不传此字段
 */
public class RemoveBlankParamParse implements IParamParse{

	public Object parse(Map<String, String> param) {
		// TODO Auto-generated method stub
		//移除空字段
		 Iterator<Map.Entry<String, String>> it = param.entrySet().iterator();  
	     while(it.hasNext()){  
	    	 Map.Entry<String, String> entry=it.next();  
	    	 if(StringUtils.isEmpty(entry.getValue())){
	    		 it.remove();
				}
	     }
	     return null;
	}
	
	/**
	 * 值为REMOVE，代表不传此字段 
	 */
	public Object parseNotPassParam(Map<String, String> param) {
		// TODO Auto-generated method stub
		//移除空字段
		 Iterator<Map.Entry<String, String>> it = param.entrySet().iterator();  
	     while(it.hasNext()){  
	    	 Map.Entry<String, String> entry=it.next();  
	    	 if(entry.getValue().equalsIgnoreCase("REMOVE")){
	    		 it.remove();
				}
	     }
	     return null;
	}

	@Override
	public void parse(Map<String, String> paramMap, String key, String param) throws Exception {

	}



}
