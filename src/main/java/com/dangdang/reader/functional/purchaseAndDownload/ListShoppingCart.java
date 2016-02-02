package com.dangdang.reader.functional.purchaseAndDownload;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.reader.functional.bookstore.GetBookList;
import com.dangdang.param.model.ParseResult;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.BookListDataReponse;
import com.dangdang.reader.functional.reponse.MobileEbookInfo;
import com.dangdang.ddframework.reponse.ReponseV2;

public class ListShoppingCart extends FunctionalBaseEx{
	
	protected ReponseV2<BookListDataReponse> reponseResult;
	
	//protected String currentCustId;

	public ListShoppingCart(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("listShoppingCart");
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		login=parseResult.getLogin();
		
		//currentCustId = Login.getCusId(originalParamMap.get("userName").toString());
		
		//获取用的的购物车ID
		GetShoppingCartId getShoppingCartId =new GetShoppingCartId(paramMap.get("token").toString());
		getShoppingCartId.doWork();
		paramMap.put("cartId", getShoppingCartId.getCardId());
		
		//查询数据库，看购物车中的数据是否为空，如果为空，随机添加一条记录
		//以后使用其他接口代替
		String selectSql ="select product_id from shopping_cart_detail sd left join shopping_cart s on sd.cart_id=s.cart_id where s.cust_id="+login.getCustId();
		
		String productId="";
		List<Map<String, Object>> shopCartList=DbUtil.selectList(Config.ECMSDBConfig, selectSql);
		if(shopCartList.size()==0){
			//从推荐类表中随机获取一本书，加入购物车
			GetBookList getBookList =new GetBookList("ad40_newBooks");
			getBookList.doWork();
			List<MobileEbookInfo> results=getBookList.getResult();
			AppendShoppingCart appendShoppingCart =new AppendShoppingCart(paramMap.get("token").toString(),paramMap.get("cartId").toString(),results.get(0).getProductId().toString());
			appendShoppingCart.doWork();
		}
		
	}

	@Override
	public void doWork() throws Exception {
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BookListDataReponse>>(){});
	}

	/*
         * (non-Javadoc)
         * @see com.dangdang.ddframework.core.FunctionalBase#dataVerify()
         * 查询数据库，验证数据是否正确
            1、购物车数量正确
            2、购物车返回产品数据正确
         */
	@Override
	protected void dataVerify() throws Exception {
		// TODO Auto-generated method stub

		getResult();
		if(reponseResult.getStatus().getCode()==0){
			//成功了，进行数据验证
			//验证返回的列表数据为0
			
			String expectDataSql="select s.cart_id,book.eBookPrice*100 as bell, book.author,REPLACE(book.title,'(电子书)','') as bookName,book.publisher,book.publishDate,book.productId,book.eBookPrice as price from shopping_cart_detail sd" 
								+" left join shopping_cart s on sd.cart_id=s.cart_id"
								+" left join ebook book on sd.product_id=book.productId"
								+" where s.cust_id="+login.getCustId();
			
			List<MobileEbookInfo> mobileEbookInfos = DbUtil.selectList(Config.ECMSDBConfig, expectDataSql,MobileEbookInfo.class);
			if(reponseResult.getData().getProducts()== null){
				dataVerifyManager.add(new ValueVerify<Integer>(mobileEbookInfos.size(), 0));
			}
			else{	
				dataVerifyManager.add(new ListVerify(mobileEbookInfos, reponseResult.getData().getProducts(),true));
			}
		}
		else {
			//验证返回的列表数据为0
			ValueVerify<Integer> verify =new ValueVerify(reponseResult.getData().getProducts(),null);
			dataVerifyManager.add(verify);
		}
		
		super.dataVerify();
	}
	
	public ReponseV2<BookListDataReponse> getResult(){

		return reponseResult;
	}
	
	
}
