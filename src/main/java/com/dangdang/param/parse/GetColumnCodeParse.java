package com.dangdang.param.parse;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.dangdang.db.digital.MediaColumnDb;
import com.dangdang.enumeration.ColumnCode;

/**
 * 栏目id
 * @author guohaiying
 */
public class GetColumnCodeParse implements IParamParse{

	@Override
	public Object parse(Map<String, String> param) throws Exception {
		return null;
	}

	@Override
	public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
		if(StringUtils.isNotBlank(param)){
			String[] params = ParamParse.parseParam(param);
			String columnCode = params[0].trim();
			if(columnCode.equals(ColumnCode.Channel.toString())){ //频道
				paramMap.put(key, MediaColumnDb.getChannelColumnCode());
			}if(columnCode.equals(ColumnCode.BookStore.toString())){ //书城
				paramMap.put(key, "");
			}
		}
	}
}
