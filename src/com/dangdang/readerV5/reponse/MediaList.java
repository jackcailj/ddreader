package com.dangdang.readerV5.reponse;

public class MediaList {
	Integer authorId;
	String authorPenname;
	String categoryIds;
	String categorys;
	Integer chapterCnt;
	String coverPic;
	String descs;
	Integer isFull;
	Integer mediaId;
	String recommandWords;
	Integer saleId;
	String speaker;
	String title;
	
	//Add by guohaiying  频道返回的字段
	Integer channelId;
	Integer isStore;
	Integer mediaType;
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getIsStore() {
		return isStore;
	}
	public void setIsStore(Integer isStore) {
		this.isStore = isStore;
	}
	public Integer getMediaType() {
		return mediaType;
	}
	public void setMediaType(Integer mediaType) {
		this.mediaType = mediaType;
	}
	
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public String getAuthorPenname() {
		return authorPenname;
	}
	public void setAuthorPenname(String authorPenname) {
		this.authorPenname = authorPenname;
	}
	public String getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}
	public String getCategorys() {
		return categorys;
	}
	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}
	public Integer getChapterCnt() {
		return chapterCnt;
	}
	public void setChapterCnt(Integer chapterCnt) {
		this.chapterCnt = chapterCnt;
	}
	public String getCoverPic() {
		return coverPic;
	}
	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	public Integer getIsFull() {
		return isFull;
	}
	public void setIsFull(Integer isFull) {
		this.isFull = isFull;
	}
	public Integer getMediaId() {
		return mediaId;
	}
	public void setMediaId(Integer mediaId) {
		this.mediaId = mediaId;
	}
	public String getRecommandWords() {
		return recommandWords;
	}
	public void setRecommandWords(String recommandWords) {
		this.recommandWords = recommandWords;
	}
	public Integer getSaleId() {
		return saleId;
	}
	public void setSaleId(Integer saleId) {
		this.saleId = saleId;
	}
	public String getSpeaker() {
		return speaker;
	}
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
