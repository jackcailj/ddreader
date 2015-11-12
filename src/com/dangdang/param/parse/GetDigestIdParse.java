package com.dangdang.param.parse;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.db.comment.CommentTargetCountDb;
import com.dangdang.enumeration.DigestId;

public class GetDigestIdParse implements IParamParse{

	@Override
	public Object parse(Map<String, String> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parse(Map<String, String> paramMap, String key, String param)
			throws Exception {
		if(StringUtils.isNotBlank(param)){
			String id = "";
			String[] params= ParamParse.parseParam(param);
			id = params[0].trim();
			if(id.equals(DigestId.StrategyBefore30.toString())){ //阅读数〉30的攻略id
				List<CommentTargetCount> list = CommentTargetCountDb.get("7000","30");
				paramMap.put(key,String.valueOf(list.get((int) (Math.random()*28)).getTargetId()));
			}
		
		}
	}

}
