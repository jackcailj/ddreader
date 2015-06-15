package com.dangdang.reader.functional.bookstore;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.common.functional.login.Login;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.BookListDataReponse;
import com.dangdang.reader.functional.reponse.BookListReponse;

/*
 * 返回字体列表
 */
public class BookList extends FunctionalBaseEx{
	
	ReponseV2<BookListReponse> reponseResult;
	
	public BookList(Map<String, String> param) {
		super(param);
		addAction("bookList");
	}
	
	public BookList(Login login, String columnCode) {
		this.login =login;
		paramMap.put("columnCode", columnCode);
		paramMap.put("ebookReturnFields", "imgUrl,salePrice,saleQutity,desc,freeBook,isBorrow,editorRecommend,paperBookPrice,translator");
		
		addCommonParam();
		addAction("bookList");
	}

	public ReponseV2<BookListReponse> getReponseResult() {
		return reponseResult;
	}

	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		login=parseResult.getLogin();
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		
		reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BookListReponse>>(){});
	}
}
