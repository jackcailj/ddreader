package com.dangdang.readerV5.reponse;

public class SearchChannelList {
	String channelId;
	String channelNickName;
	String channelPic;
	String custId;
	String description;
	String score;  //搜索引擎的权重得分, 验证时可忽略
	String status; //认证审核状态0:未审核,1:审核未通过2:审核通过
	Integer subscriptions; //订阅数
	String title;
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getChannelNickName() {
		return channelNickName;
	}
	public void setChannelNickName(String channelNickName) {
		this.channelNickName = channelNickName;
	}
	public String getChannelPic() {
		return channelPic;
	}
	public void setChannelPic(String channelPic) {
		this.channelPic = channelPic;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getSubscriptions() {
		return subscriptions;
	}
	public void setSubscriptions(Integer subscriptions) {
		this.subscriptions = subscriptions;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
