package com.dangdang.reader.functional.bookshelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.param.model.ParseResult;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.BookList;
import com.dangdang.reader.functional.reponse.DownLoadBooks;

public class DownloadBookList extends FunctionalBaseEx {
	static String userName = "z11@123.com";
	static String passWord = "111111";
	ReponseV2<DownLoadBooks> reponseResult;
	public DownloadBookList(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("downloadBookList");
	} 
	
	public static void uploadBookListForPrecondition() throws Exception{
		String data = "{\"token\":\"fromLogin\",\"data\":[{\"categoryName\":\"计算机\",\"lastChangedDate\":14567000000,\"books\":[{\"productId\":pId1FromDB,\"lastChangedDate\":14567000000}]},{\"categoryName\":\"dd_no_group\",\"lastChangedDate\":14567000000,\"books\":[{\"productId\":pId2FromDB,\"lastChangedDate\":14567000000}]}]}";
		Map<String, String> param = Util.generateMap("userName="+userName+"&passWord="+passWord+"&loginType=email&token=&data="+data);
		UploadBookList bookList = new UploadBookList(param);
		bookList.doWork();
		if(bookList.getResult().getStatus().getCode()!=0){
			throw new Exception("书架信息上传失败");
		}
	}
	
	public ReponseV2<DownLoadBooks> getResult() {
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<DownLoadBooks>>(){});
		return reponseResult;
	}	
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		login = parseResult.getLogin();
		String custId = login!=null?login.getCustId():"''";
		if(paramMap.get("uId")!=null&&paramMap.get("uId").equals("FromDB")){
			String sql = "SELECT id FROM thirdparty_cust_id WHERE CUST_ID="+custId;
			String id = DbUtil.selectOne(Config.ECMSDBConfig, sql).get("id").toString();
			paramMap.put("uId", id);
		}		
	}
	
	@Override
	protected void dataVerify() throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode()==0){
			List<BookList> ebookList = reponseResult.getData().getBookList();
			String custId = login.getCustId();			
			String sql = "SELECT product_id FROM bookshelf_book  bb left join thirdparty_cust_id  tc "
					   + "on bb.cust_id=tc.id where tc.CUST_ID="+custId+" and tc.id is not null";
			List<Map<String, Object>>  sqlList = DbUtil.selectList(Config.ECMSDBConfig, sql);
			List<String> pIds1 = new ArrayList<String>();
			List<String> pIds2 = new ArrayList<String>();
			for(int i=0; i<ebookList.size(); i++){
				pIds1.add(ebookList.get(i).getBooks().get(0).getProductId());
				pIds2.add(sqlList.get(i).get("product_id").toString());
			}
			
			dataVerifyManager.add(new ValueVerify<List<String>>(pIds1, pIds2));
		}
		
		super.dataVerify();
	}
	
	//http://10.255.223.131/mobile/api2.do?action=downloadBookList&uId=25872535&
		//token=16138359398c4fcc77e32a5cfd6e84e6&referType=shelf&returnType=json&deviceType=Android&deviceSerialNo=860850026397577
		// uId from bookshelf_category cust_id, thirdparty_cust_id

}
