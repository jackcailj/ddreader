package com.dangdang.param.parse;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.dangdang.db.SqlUtil;
import com.dangdang.db.digital.MediaBooklistDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.enumeration.BookListId;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.digital.meta.MediaBooklist;

/**
 * @author guohaiying
 */
public class GetBookListIdParse implements IParamParse {

	@Override
	public Object parse(Map<String, String> param) throws Exception {
		return null;
	}

	@Override
	public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
		if(StringUtils.isNotBlank(param)){
			String[] params= ParamParse.parseParam(param);
			String bId = params[0].trim();
			if(bId.equals(BookListId.UserBookListId.toString())){ //用户的书单
				String custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
				paramMap.put(key, MediaBooklistDb.getBookListId(custId));
			}else if(bId.equals(BookListId.BookListId.toString())){ //随机返回一个有效的书单
				paramMap.put(key, MediaBooklistDb.getRandChannel());
			}else if(bId.equals(BookListId.MyMonthlyBookListId.toString())){ //我包月的频道书单（在有效期内）
				String custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
				List<String> channelIds = MediaMonthlyAuthorityDb.getUserMonthlyChannelID(custId, "0");				
				MediaBooklist bookList = MediaBooklistDb.getBookListMsg(channelIds.get(SqlUtil.getRandNum(channelIds)));
				paramMap.put(key, bookList.getBooklistId().toString());
			}
		}
	}
}
