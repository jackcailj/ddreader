package com.dangdang.reader.functional.param.parse;

import java.util.Map;

/*
 * 
 * 笔记参数解析
 * 有效笔记：
 * 1、1条笔记
 * 2、多条笔记
 * 3、0条笔记
 * 无效笔记：
 * 1、noteInfo超过1024个汉字
 * 2、callOutInfo超过2048个汉字
 * 3、characterStartIndex大于characterEndIndex
 * author：cailj
 */
public class NoteInfoParse implements IParamParse{

	@Override
	public Object parse(Map<String, String> param) throws Exception {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Object parse(String param) throws Exception {
		return null;
	}

}
