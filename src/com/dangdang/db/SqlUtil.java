package com.dangdang.db;

import java.util.ArrayList;
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
	
	//返回list中的随机一个值
	public static String getRandValue(List<String> list) throws Exception{
		int size = list.size();
		int n = (int) (Math.random()*(size-1));
		System.out.println(n);
		return list.get(n);
	}
	
	public static void main(String[] args){
		List<String> list = new ArrayList<String>();
		list.add("aa");
		list.add("bb");
		list.add("cc");
		list.add("dd");
		list.add("ee");
		list.add("ff");
		try {
			String s=SqlUtil.getRandValue(list);
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
