package com.dangdang.autotest.common;

import com.dangdang.ddframework.core.TestDevice;

/*
 * 定义平台来源
 */
public enum PlatForm {
	DDREADER_ANDROID("ds_android"),//当当读书安卓
	DDREADER_IOS("ds_ios"),//当当读书ios
	YC_ANDROID("yc_android"),//原创安卓
	YC_IOS("yc_ios"),//原创ios
	DS("ds"),//当当读书平台
	YC("yc");//当读小说平台

	
	 private String context;
		
	   private PlatForm(String value) {
			// TODO Auto-generated constructor stub
		   context=value;
		}
	   
	  @Override
	public String toString() {
		// TODO Auto-generated method stub
		return context;
	}
	  
	public static PlatForm getPlatForm(TestDevice device) {
		if(device==TestDevice.ANDROID){
			return PlatForm.DDREADER_ANDROID;
		}
		else {
			return PlatForm.DDREADER_IOS;
		}
	}
}
