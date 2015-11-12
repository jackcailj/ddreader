package com.dangdang.param.parse;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.dangdang.bookbar.meta.Article;
import com.dangdang.bookbar.meta.BarMember;
import com.dangdang.db.bookbar.ArticleDb;
import com.dangdang.db.bookbar.BarMemberDb;

public class GetBarMemeberParse implements IParamParse{

	   @Override
	    public Object parse(Map<String, String> param) throws Exception {
	        return null;
	    }

	    @Override
	    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
	    	if(StringUtils.isNotBlank(param)){
				List<BarMember> barMember = BarMemberDb.getJoniedBarEmember(param);
				paramMap.put(key, Long.toString(barMember.get((new Random()).nextInt(20)).getBarId()));
			}
	    }
}
