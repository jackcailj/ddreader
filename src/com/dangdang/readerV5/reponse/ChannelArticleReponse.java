package com.dangdang.readerV5.reponse;

import java.util.List;

public class ChannelArticleReponse {
	List<ArticlePackageList> articlePackageList;
	String articlePackageListNum;
	String total;
	public List<ArticlePackageList> getArticlePackageList() {
		return articlePackageList;
	}
	public void setArticlePackageList(List<ArticlePackageList> articlePackageList) {
		this.articlePackageList = articlePackageList;
	}
	public String getArticlePackageListNum() {
		return articlePackageListNum;
	}
	public void setArticlePackageListNum(String articlePackageListNum) {
		this.articlePackageListNum = articlePackageListNum;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
}
