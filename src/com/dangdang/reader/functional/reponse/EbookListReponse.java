package com.dangdang.reader.functional.reponse;


import java.util.List;
import com.dangdang.ecms.meta.Ebook;

public class EbookListReponse {
	
	List<Ebook> ebookListInfo;

	public List<Ebook> getEbookListInfo() {
		return ebookListInfo;
	}

	public void setEbookListInfo(List<Ebook> ebookListInfo) {
		this.ebookListInfo = ebookListInfo;
	}
}
