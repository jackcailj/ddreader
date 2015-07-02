package com.dangdang.readerV5.reponse;

import java.util.List;

public class ChannelArticleReponse {
	List<ArticlePackageList> articlePackageList;
	Integer articlePackageListNum;
	Integer total;
	public List<ArticlePackageList> getArticlePackageList() {
		return articlePackageList;
	}
	public void setArticlePackageList(List<ArticlePackageList> articlePackageList) {
		this.articlePackageList = articlePackageList;
	}
	public Integer getArticlePackageListNum() {
		return articlePackageListNum;
	}
	public void setArticlePackageListNum(Integer articlePackageListNum) {
		this.articlePackageListNum = articlePackageListNum;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
}
