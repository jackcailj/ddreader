
package com.dangdang.reader.functional.param.model;

import org.apache.commons.lang3.StringUtils;


public class GetExperienceInfoListParam extends ParamBase{
	String pageSize;
	String recordTime;
	
	
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	
	public String parseRecordTimeSql(){
		if(StringUtils.isBlank(recordTime) || recordTime.equals("0"))
		{
			return null;
		}
		return recordTime;
	}
	
	
}

