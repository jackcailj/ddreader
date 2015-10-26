package com.dangdang.db.ecms;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ecms.meta.ExperienceInfo;

public class ExperienceInfoEx {
	private Long id;
	private Long custId;
	private Long productId;
	private Long recordTime;
	private Short type;
	private Map<String, Object> remarks;
	private String deviceType;
	
	public ExperienceInfoEx() {
		// TODO Auto-generated constructor stub
	}

	public ExperienceInfoEx(ExperienceInfo info) {
		// TODO Auto-generated constructor stub
		id=info.getExperienceId();
		custId=info.getCustId();
		productId=info.getProductId();
		recordTime=info.getRecordTime();
		type=info.getType();
		remarks=JSONObject.parseObject(info.getRemarks());
		deviceType=info.getDeviceType();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Long recordTime) {
		this.recordTime = recordTime;
	}

	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}



	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	public Map<String, Object> getRemarks() {
		return remarks;
	}

	public void setRemarks(Map<String, Object> remarks) {
		this.remarks = remarks;
	}
}
