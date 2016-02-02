package com.dangdang.readerV5.reponse;

import java.util.List;

public class SearchMediaResponse {
	List<SearchMediaPaperList> searchMediaPaperList;

	public List<SearchMediaPaperList> getSearchMediaPaperList() {
		return searchMediaPaperList;
	}

	public void setSearchMediaPaperList(
			List<SearchMediaPaperList> searchMediaPaperList) {
		this.searchMediaPaperList = searchMediaPaperList;
	}

}
