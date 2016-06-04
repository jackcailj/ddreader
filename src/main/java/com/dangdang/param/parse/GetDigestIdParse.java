package com.dangdang.param.parse;

import java.util.List;
import java.util.Map;

import com.dangdang.ddframework.fitnesse.ParamParse;
import org.apache.commons.lang3.StringUtils;

import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.db.SqlUtil;
import com.dangdang.db.comment.CommentTargetCountDb;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.enumeration.DigestId;

/**
 * 
 * @author guohaiying
 *
 */
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
			String[] params= ParamParse.parseParam(param);
			String id = params[0].trim();
			String param2 = params[1].trim();
			if(id.equals(DigestId.StrategyBefore30.toString())){ //阅读数〉30的攻略id
				List<CommentTargetCount> list = CommentTargetCountDb.get("7000","30");
				paramMap.put(key,String.valueOf(list.get((int) (Math.random()*28)).getTargetId()));
			}else if(id.equals(DigestId.UserDigestId.toString())){ //3：频道  5：攻略
				String custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
				paramMap.put(key,MediaDigestDb.getUserDigestId(custId, param2));
			}else if(id.equals(DigestId.DigestId.toString())){
				paramMap.put(key,SqlUtil.getRandValue(MediaDigestDb.getDigest(Integer.valueOf(param2))));
			}
		}
	}

}
