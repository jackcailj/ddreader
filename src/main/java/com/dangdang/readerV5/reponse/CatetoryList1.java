package com.dangdang.readerV5.reponse;

import java.util.List;

public class CatetoryList1 {
	List<Catetory> catetoryList;
	String code;
	Integer id;
	String image;
	String image2;
	Boolean leaf;
	String name;
	Integer parentId;
	Boolean parsed;
	public List<Catetory> getCatetoryList() {
		return catetoryList;
	}
	public void setCatetoryList(List<Catetory> catetoryList) {
		this.catetoryList = catetoryList;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Boolean getParsed() {
		return parsed;
	}
	public void setParsed(Boolean parsed) {
		this.parsed = parsed;
	}

}
