package com.dangdang.readerV5.reponse;

public class BookList {	
	//json中返回的字段名称
	Integer bookNum;
	Integer booklistId;
	Integer changeNum;
	Integer channelId;
	Integer creator;
	String description;
	String imageUrl;
	Integer isShow;
	String name;
	Integer owner;
	Integer status;
	Integer storeNum;
	
	//数据库表中的字段名称
	Integer booklist_id;
	Integer channel_id;
	Integer is_show;
	String image_url;
	Integer book_num;
	Integer store_num;
	Integer change_num;
	
	
	public Integer getBooklist_id() {
		return booklist_id;
	}
	public void setBooklist_id(Integer booklist_id) {
		this.booklist_id = booklist_id;
	}
	public Integer getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(Integer channel_id) {
		this.channel_id = channel_id;
	}
	public Integer getIs_show() {
		return is_show;
	}
	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public Integer getBook_num() {
		return book_num;
	}
	public void setBook_num(Integer book_num) {
		this.book_num = book_num;
	}
	public Integer getStore_num() {
		return store_num;
	}
	public void setStore_num(Integer store_num) {
		this.store_num = store_num;
	}
	public Integer getChange_num() {
		return change_num;
	}
	public void setChange_num(Integer change_num) {
		this.change_num = change_num;
	}
	public Integer getBookNum() {
		return bookNum;
	}
	public void setBookNum(Integer bookNum) {
		this.bookNum = bookNum;
	}
	public Integer getBooklistId() {
		return booklistId;
	}
	public void setBooklistId(Integer booklistId) {
		this.booklistId = booklistId;
	}
	public Integer getChangeNum() {
		return changeNum;
	}
	public void setChangeNum(Integer changeNum) {
		this.changeNum = changeNum;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOwner() {
		return owner;
	}
	public void setOwner(Integer owner) {
		this.owner = owner;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStoreNum() {
		return storeNum;
	}
	public void setStoreNum(Integer storeNum) {
		this.storeNum = storeNum;
	}

}
