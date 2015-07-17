package com.dangdang.readerV5.reponse;

import java.util.List;

public class CatetoryList3 {
	List<CatetoryList2> catetoryList;
	String code;
	String id;
	String leaf;
	String name;
	String parentId;
	String parsed;
	public List<CatetoryList2> getCatetoryList() {
		return catetoryList;
	}
	public void setCatetoryList(List<CatetoryList2> catetoryList) {
		this.catetoryList = catetoryList;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLeaf() {
		return leaf;
	}
	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParsed() {
		return parsed;
	}
	public void setParsed(String parsed) {
		this.parsed = parsed;
	}
}
