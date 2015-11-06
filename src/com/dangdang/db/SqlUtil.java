package com.dangdang.db;

import java.util.List;

/**
 * 
 * @author guohaiying
 *
 */
public class SqlUtil {
	//把list转换为(?,?,?,?)的形式
	public static String getListToString(List<String> list) throws Exception{
		String s = "(";
		for(int i=0; i<list.size(); i++){
			s+=list.get(i)+",";
		}
		s=s.substring(0, s.lastIndexOf(","))+")";
		return s;
	}
}
