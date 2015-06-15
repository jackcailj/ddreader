package com.dangdang.reader.functional.reponse;

import java.util.List;

public class Categories extends ChildCategory{
	List<ChildCategory> childCategory;

	public List<ChildCategory> getChildCategory() {
		return childCategory;
	}

	public void setChildCategory(List<ChildCategory> childCategory) {
		this.childCategory = childCategory;
	}
}