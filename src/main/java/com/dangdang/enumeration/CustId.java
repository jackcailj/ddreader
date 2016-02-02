package com.dangdang.enumeration;

/**
 * 
 * @author guohaiying
 *
 */
public enum CustId {
	Pass("审核通过"), 
	UnPass("审核不通过"), 
	UnApply("未申请过");  


	String content="";
	
	CustId(String id){
		content=id;
	}

}
