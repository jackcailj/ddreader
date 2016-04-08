package com.dangdang.readerV5.reponse;

import com.dangdang.ddframework.dataverify.verify_annotation.NotNull;

public class BarContent {
	String articleNum;
	String barDesc;
	String barId;
	String barImgUrl;
	String barName;
	String memberNum;
	String recommendReason;
	String isActivity;
	
	public String getArticleNum(){
		return articleNum;
	}
	public String getBarDesc(){
		return barDesc;
	}
	public String getBarId(){
		return barId;
	}
	public String getBarImgUrl(){
		return barImgUrl;
	}
	public String getBarName(){
		return barName;
	}
	public String getMemberNum(){
		return memberNum;
	}
	public String getRecommendReason(){
		return recommendReason;
	}
	
	public void setArticleNum(String articleNum){
		this.articleNum = articleNum;
	}
	public void setBarDesc(String barDesc){
		this.barDesc = barDesc;
	}
	public void setBarId(String barId){
		this.barId = barId;
	}
	public void setBarImgUrl(String barImgUrl){
		this.barImgUrl = barImgUrl;
	}
	public void setBarName(String barName){
		this.barName = barName;
	}
	public void setMemberNum(String memberNum){
		this.memberNum = memberNum;
	}
	public void setRecommendReason(String recommendReason){
		this.recommendReason = recommendReason;
	}

    public String getIsActivity() {
        return isActivity;
    }

    public void setIsActivity(String isActivity) {
        this.isActivity = isActivity;
    }
}
