package com.dangdang.readerV5.bookbar;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.BarProductInfo;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BarBooks;

public class QueryBarBook extends FixtureBase{
	ReponseV2<BarBooks>   reponseResult;
	public ReponseV2<BarBooks> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BarBooks>>(){});
	}
	
	String barId;
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {		
			super.setParameters(params);
			String sql = "select bar_id from bar_product_info where is_has_bar=1 and bar_id in "
					+ "(select bar_id from bar where bar_status=2) limit 1";
			Map<String,Object> map = DbUtil.selectOne(Config.BOOKBARDBConfig, sql);	
			if(paramMap.get("barId")!=null&&paramMap.get("barId").equals("FromDB")){
				barId = map.get("bar_id").toString();
				paramMap.put("barId", barId);
			}
	 }
	
	@Override
	 public void dataVerify(String expectedCode) throws Exception {
			reponseResult = getResult();
			if(reponseResult.getStatus().getCode() == 0){
				String sql = "SELECT * FROM bar_product_info where bar_id="+barId;
				List<BarProductInfo>  list = DbUtil.selectList(Config.BOOKBARDBConfig, sql, BarProductInfo.class);	
				dataVerifyManager.add(new ValueVerify<Integer>(list.size(), reponseResult.getData().getBooks().size(),false));
				//暂时无法验证    生成书吧的书和单品页那边的书来源不一致
//				List<Book> books = new ArrayList<Book>();
//				for(int i=0; i<list.size(); i++){
//					Book book = new Book();
//					book.setAuthorName(list.get(i).getBookAuthor());
//					book.setBookType(Integer.toString(list.get(i).getType()));
//					//封面信息，简介应该是楼上接口返回的，在现有数据表里找不到
//					book.setCoverPic(null);
//					book.setDescs(null);
//					book.setProductId(Long.toString(list.get(i).getProductId()));
//					book.setPublisher(list.get(i).getPublisher());
//					book.setTitle(list.get(i).getProductName());
//					books.add(book);
//					reponseResult.getData().getBooks().get(i).setCoverPic(null);
//					reponseResult.getData().getBooks().get(i).setDescs(null);
//				}
//				
//				dataVerifyManager.add(new ListVerify(books, reponseResult.getData().getBooks(),true));
				super.dataVerify();
			}	
			else{
				dataVerifyResult = false;
				//TODO will add verify
			}
			verifyResult(expectedCode);
		}
	
	
	

}
