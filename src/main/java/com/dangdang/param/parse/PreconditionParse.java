package com.dangdang.param.parse;

import java.util.Map;

import org.apache.log4j.Logger;


/**
 * Excel中precondition（前提条件）字段解析
 * @author guohaiying
 *
 */
public class PreconditionParse implements IParamParse{

	Logger log = Logger.getLogger(PreconditionParse.class);
	
	@Override
	public Object parse(Map<String, String> param) {
		log.info(" in PreconditionParse.class ");
		return null;
		
	}

	@Override
	public void parse(Map<String, String> paramMap, String key, String param) throws Exception {

	}

}
