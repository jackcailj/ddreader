package com.dangdang.param.parse;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.dangdang.db.bookbar.BarDb;
import com.dangdang.db.bookbar.BarRelationDb;
import com.dangdang.enumeration.BarStatus;

public class GetBarIdOrObjectIdParse  implements IParamParse {
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        BarDb bar = null;
        BarRelationDb baR = null;
        if(StringUtils.isNotBlank(param)){
        	String[] params = ParamParse.parseParam(param);
        	BarStatus status = BarStatus.valueOf(params[0]);
        	if(params[1].equalsIgnoreCase("objectId")){
        		paramMap.put(key,baR.getObjectId(status).get("object_id").toString());
        	}
        	else{
        		paramMap.put(key,bar.getBarInfo(status).getBarId().toString());
        	}
        	
        }
        else{
            throw new Exception("GetBarIdOrObjectIdParse参数为空");
        }
    }
}
