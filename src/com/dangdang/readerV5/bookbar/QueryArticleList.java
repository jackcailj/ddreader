package com.dangdang.readerV5.bookbar;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.ArticleListData;

public class QueryArticleList extends FixtureBase{
	ReponseV2<ArticleListData>   reponseResult;
	 
	public ReponseV2<ArticleListData> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<ArticleListData>>(){});
	}
}
