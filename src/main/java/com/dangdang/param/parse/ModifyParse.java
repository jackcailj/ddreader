package com.dangdang.param.parse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.dangdang.db.digital.ChannelBgImgDb;
import com.dangdang.enumeration.ModifyContent;
import com.dangdang.readerV5.reponse.UrlList;

/**
 * @author guohaiying
 */
public class ModifyParse implements IParamParse {
	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
	String date = df.format(new Date());// new Date()为获取当前系统时间

	@Override
	public Object parse(Map<String, String> param) throws Exception {
		return null;
	}

	@Override
	public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
		if(StringUtils.isNotBlank(param)){
			String[] params= ParamParse.parseParam(param);
			String param1 = params[0].trim();
			if(param1.equals(ModifyContent.ChannelName.toString())){ //个人频道名称
				paramMap.put(key, date);
			}else if(param1.equals(ModifyContent.ChannelDescription.toString())){ //个人频道描述
				paramMap.put(key, date + "修改个人频道描述");
			}else if(param1.equals(ModifyContent.ChannelIcon.toString())){ //个人频道背景图
				List<UrlList> list = ChannelBgImgDb.getBackImg("1");
				int n=(int)Math.random()*(list.size()-1);
				paramMap.put(key, list.get(n).toString());
			}else if(param1.equals(ModifyContent.BookListName.toString())){ //个人书单名称
				paramMap.put(key, date);
			}else if(param1.equals(ModifyContent.BookListDescription.toString())){ //个人书单描述
				paramMap.put(key, date + "修改个人书单描述");
			}else if(param1.equals(ModifyContent.BookListIcon.toString())){ //个人书单背景图
				List<UrlList> list = ChannelBgImgDb.getBackImg("0");
				int n=(int)Math.random()*(list.size()-1);
				paramMap.put(key, list.get(n).toString());
			}
		}
	}
}
