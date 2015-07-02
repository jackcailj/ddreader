package com.dangdang.autotest.common;

import java.lang.reflect.*;

import com.dangdang.ddframework.dataverify.VerifyBase;
import com.dangdang.readerV5.reponse.ChannelResponse;

public class ResponseVerify<T> extends VerifyBase{
	Object _jsonValue;
	T _dbValue;
	String _classPath;
	boolean _listSort=false;
	
	public ResponseVerify(String classPath, Object c){
		_classPath = classPath;
		_jsonValue = c;

	}
//	public ResponseVerify(String classPath, T jsonValue, T dbValue){
//		_classPath = classPath;
//		_jsonValue = jsonValue;
//		_dbValue = dbValue;
//	}
//	
//	public ResponseVerify(String classPath, T jsonValue, T dbValue, boolean equal){
//		_classPath = classPath;
//		_jsonValue = jsonValue;
//		_dbValue = dbValue;
//		_listSort = equal;
//	}
	
	@Override
	public boolean dataVerify() throws Exception {
		Class<?> c = null;
		c = Class.forName(_classPath);
		
		Object obj = c.newInstance();
		obj = _jsonValue;
		//obj.getClass().get
//		Field[] fields = obj.getClass().getDeclaredFields();
//		for(int i=0; i<fields.length; i++){
//			//属性的类型
//			Class<?> type = fields[i].getType();
//			System.out.println(fields[i].getName() + " " + type);			
//			Field field= obj.getClass().getDeclaredField(fields[1].getName());
//			field.setAccessible(true);
	
//			obj.getClass().getDeclaredFields();
//			//System.out.println("field"+field);
//		}
		
		
		
		return false;
	}
	
	public static void main(String[] args) throws Exception{
		ChannelResponse c = new ChannelResponse();
		c.setChannelId(6);
		ResponseVerify r = new ResponseVerify<ChannelResponse>("com.dangdang.readerV5.reponse.ChannelResponse", c);
		r.dataVerify();
	}

}
