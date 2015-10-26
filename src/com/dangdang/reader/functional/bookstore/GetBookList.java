package com.dangdang.reader.functional.bookstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.reader.functional.reponse.BookListReponse;
import com.dangdang.reader.functional.reponse.MobileEbookInfo;
import com.dangdang.ddframework.reponse.ReponseV2;

/*
 * 返回书籍列表
 */
public class GetBookList extends FunctionalBaseEx{

	public List<MobileEbookInfo> books=new ArrayList<MobileEbookInfo>();
	
	public GetBookList(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("bookList");
	}
	
	public GetBookList(String columnCode) {
		// TODO Auto-generated constructor stub
		paramMap.put("columnCode", columnCode);
		addAction("bookList");
		addCommonParam();
	}
	
	public List<MobileEbookInfo> getResult() {
		ReponseV2<BookListReponse> reponse=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BookListReponse>>(){});
		return reponse.getData().getEbookList();
	}
}
