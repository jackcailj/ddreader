package com.dangdang.reader.functional.bookstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ecms.meta.Ebook;
import com.dangdang.reader.functional.reponse.EbookReponse;

public class BookListByChannel extends FunctionalBaseEx{
	Logger log = Logger.getLogger(BookListByChannel.class);	
	
	ReponseV2<EbookReponse> reponseResult;
	
	public ReponseV2<EbookReponse> getReponseResult() {
		return reponseResult;
	}
	public BookListByChannel(Map<String, String> param) {
		super(param);	
	}
		
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<EbookReponse>>(){});
	}

	@Override
	public void dataVerify() throws Exception {
		//String code = null;
		int productId;
		//json中返回的ebookName
		List<Ebook> list = reponseResult.getData().getEbookList();
		List<Integer> bookIDList = new ArrayList<Integer>();
		for(int i=0; i<list.size(); i++){
			Ebook ebook = list.get(i);
			productId = ebook.getProductId();
			log.info("json: " + productId);
			bookIDList.add(productId);
		}	
		
		//在数据库中get的bookName
		String name = reponseResult.getData().getName();	
		log.info("json name: " + name);
//		Map<String, String> map = bsComm.getCCByModelName(name);
//		log.info(map.size());
//		Set set = map.entrySet();
//		Iterator iterator = set.iterator();
//		while(iterator.hasNext()){
//			Map.Entry mapentry = (Map.Entry) iterator.next();
//			code = (String) mapentry.getValue();
//		}
//		long columnID = bsComm.selectDocID(code);
		List<Integer> bookIDList2 = new ArrayList<Integer>();
//		List<Ebook> ebookList = bsComm.selectColumnDoc(961);	
//		for(int i=0; i<ebookList.size(); i++){
//			bookIDList2.add(ebookList.get(i).getProductId());
//		}	
//		Assert.assertTrue(Compare.Contains(bookIDList, bookIDList2));

	}			
		 

}
