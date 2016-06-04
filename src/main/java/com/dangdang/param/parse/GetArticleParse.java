package com.dangdang.param.parse;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.dangdang.ddframework.fitnesse.ParamParse;
import org.apache.commons.lang3.StringUtils;

import com.dangdang.bookbar.meta.Article;
import com.dangdang.db.bookbar.ArticleDb;

public class GetArticleParse  implements IParamParse{

	   @Override
	    public Object parse(Map<String, String> param) throws Exception {
	        return null;
	    }

	    @Override
	    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
	    	if(StringUtils.isNotBlank(param)){
				String[] params= ParamParse.parseParam(param);
				List<Article> article = ArticleDb.getArticle(params[0].trim(), params[1].trim(), params[2].trim(),params[3].trim());
				paramMap.put(key, Long.toString(article.get((new Random()).nextInt(article.size())).getMediaDigestId()));
			}
	    }
}
