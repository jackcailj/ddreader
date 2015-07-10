package com.dangdang.readerV5.reponse;

import java.util.List;

public class SquareInfo {
	Module module;
	List<TagContent> tagContent;
	List<BarContent> barContent;
	List<ArticleContent> articleContent;
	
	public Module getModule(){
		return module;
	}
	public List<TagContent> getTagContent(){
		return tagContent;
	}
	public List<BarContent> getBarContent(){
		return barContent;
	}
	public List<ArticleContent> getArticleContent(){
		return articleContent;
	}
	
	public void setModule(Module module){
		this.module = module;
	}
	public void setTagContent(List<TagContent> tagContent){
		this.tagContent = tagContent;
	}
	public void setBarContent(List<BarContent> barContent){
		this.barContent = barContent;
	}
	public void setArticleContent(List<ArticleContent> articleContent){
		this.articleContent = articleContent;
	}

}
