package com.dangdang.readerV5.reponse;

public class MediaList {
	String authorId;
	String authorPenname;
	//String categoryIds;
	//String categorys;
	String chapterCnt;
	//String coverPic;
	String descs;
	String isFull;
	String isStore;
	String mediaId;
	String mediaType;
	String recommandWords;
	String saleId;
	String title;

	public String getIsStore() {
		return isStore;
	}
	public void setIsStore(String isStore) {
		this.isStore = isStore;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorPenname() {
		return authorPenname;
	}
	public void setAuthorPenname(String authorPenname) {
		this.authorPenname = authorPenname;
	}
//	public String getCategoryIds() {
//		return categoryIds;
//	}
//	public void setCategoryIds(String categoryIds) {
//		this.categoryIds = categoryIds;
//	}
//	public String getCategorys() {
//		return categorys;
//	}
//	public void setCategorys(String categorys) {
//		this.categorys = categorys;
//	}
	public String getChapterCnt() {
		return chapterCnt;
	}
	public void setChapterCnt(String chapterCnt) {
		this.chapterCnt = chapterCnt;
	}
//	public String getCoverPic() {
//		return coverPic;
//	}
//	public void setCoverPic(String coverPic) {
//		this.coverPic = coverPic;
//	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	public String getIsFull() {
		return isFull;
	}
	public void setIsFull(String isFull) {
		this.isFull = isFull;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getRecommandWords() {
		return recommandWords;
	}
	public void setRecommandWords(String recommandWords) {
		this.recommandWords = recommandWords;
	}
	public String getSaleId() {
		return saleId;
	}
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
