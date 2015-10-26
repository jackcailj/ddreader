package com.dangdang.reader.functional.bookshelf;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.reponse.HiddenBookList;

public class GetHiddenEbook extends HiddenEbook {
	ReponseV2<HiddenBookList> reponseResult;
	
	public GetHiddenEbook(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getHiddenEbook");
	}
	
	public GetHiddenEbook() {
		
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		//http://10.255.223.131/mobile/api2.do?action=getHiddenEbook&lastGetTime=&token=bc062bf6d6eb88f706693b56258f5ae0&pageNum=1&pageSize=1000&textFieldType=txt&gzip=yes&ebookReturnFields=mainCategory,relationType,fullFileSize,imgUrl,createDate,borrowDuration,desc
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<HiddenBookList>>(){});
	}
	
	public ReponseV2<HiddenBookList> getResult() {
		return reponseResult;
	}
	
	@Override
	protected void genrateVerifyData() throws Exception {       
		//TODO
	}	

}
