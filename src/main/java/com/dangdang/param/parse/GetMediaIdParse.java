package com.dangdang.param.parse;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaBooklistDetail;
import com.dangdang.db.digital.MediaBookListDetailDb;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.enumeration.BookStatus;
import com.dangdang.enumeration.BookType;


public class GetMediaIdParse implements IParamParse{
	@Override
	public Object parse(Map<String, String> param) throws Exception {
		return null;
	}

	@Override
	public void parse(Map<String, String> paramMap, String key, String param)
			throws Exception {
		if(StringUtils.isNotBlank(param)){
			String[] params= ParamParse.parseParam(param);
			String mediaId = params[0].trim();

			if(mediaId.equals("1")){ 
				Media media = MediaDb.getMedia(BookType.EBOOK, BookStatus.VALID);
				paramMap.put(key, String.valueOf(media.getMediaId()));
			}
		}
	}
}
