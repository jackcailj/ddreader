package com.dangdang.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	public static int getRandNum(List arg){
		return (int)(Math.random()*(arg.size()-1));
	}
		
//	public static String getMillisecond(Date time) throws ParseException{
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		long millionSecond = sdf.parse(time).getTime();
//		return String.valueOf(millionSecond);
//	}
	
	public static void main(String[] args) throws ParseException{

//		List<String> list = new ArrayList<String>();
//		list.add("aa");
//		list.add("bb");
//		list.add("cc");
//		list.add("dd");
//		list.add("ee");
//		list.add("ff");
//		try {
//			String s=SqlUtil.getRandValue(list);
//			System.out.println(s);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
