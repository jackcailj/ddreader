package com.dangdang.reader.functional.reponse;

import java.util.List;

import com.dangdang.ecms.meta.Ebook;

public class EbookReponse {
	List<Ebook>  ebookList;
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ebook> getEbookList() {
		return ebookList;
	}

	public void setEbookList(List<Ebook> ebookList) {
		this.ebookList = ebookList;
	}
}
