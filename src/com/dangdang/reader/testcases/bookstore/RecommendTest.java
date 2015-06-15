package com.dangdang.reader.testcases.bookstore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import com.dangdang.ddframework.core.ConfigCore;
import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.bookstore.Block;
import com.dangdang.reader.functional.bookstore.BookList;
import com.dangdang.reader.functional.bookstore.BookReviewStar;

import com.dangdang.reader.functional.bookstore.MultiAction;

/**
 * 书城-推荐页
 * 书城-特价页
 * @author guohaiying
 *
 */
public class RecommendTest extends TestCaseBase{
	Block block;
	//BookList bookList;
//	MaleFemale maleFemale;
//	Guessulike guessulike;
	MultiAction multiAction;
	BookReviewStar bookReviewStar;
//	BookStoreComm bsComm;
	
	@BeforeClass
	public void setUp() throws Exception{
		setDataFile("bookstore/RecommendTest");	
		
		if("testing".equals(ConfigCore.getEnvironment().toString())){
			System.out.println(ConfigCore.getEnvironment().toString());
			//bsComm = new BookStoreComm();
			//bsComm.initAll();
		}
	}	
	
	/**
	 * 书城轮播图，banner图
	 * @param caseName
	 * @param param
	 * @param excpectd
	 * @throws Exception
	 */
	@Test(dataProvider="dataProvider")
	public void BlockTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		Block block = new Block(param);
		block.doWorkAndVerify();

		//assert(excpectd.equals(block.getReponseResult().getStatus().getCode().toString()));
		assert(block.getDataVerifyResult());
	}
	
	/**
	 * 书城-推荐页
	 * @param caw/vcxzz  seName
	 * @param param
	 * @param excpectd
	 * @throws Exception
	 */
//	@Test(dataProvider="dataProvider")
//	public void multiActionTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
//		MultiAction multiAction = new MultiAction(param);
//		multiAction.doWorkAndVerify();
//
//		//assert(excpectd.equals(multiAction.getReponseResult().getStatus().getCode().toString()));
//		assert(multiAction.getDataVerifyResult());
//	}
	
	/**
	 * 书城-推荐页-主打推荐、新书首发
	 * 书城-特价页-免费借阅、今日限免、免费抢、限时特价、巨便宜
	 * @param caseName
	 * @param param
	 * @param excpectd
	 * @throws Exception
	 */
	
//	@DataProvider(name="dbProvider")
//	public Object[][] dbProvider() throws Exception{
//		Map<String, String> tmp = bsComm.getCCByModelName("推荐页");
//		Map<String, String> tmp2 = bsComm.getCCByModelName("特价页");
//		tmp.putAll(tmp2);
//		Object[][] array = new Object[tmp.size()][2];
//		
//		Set set = tmp.entrySet();
//		Iterator iterator = set.iterator();
//		int i=0;
//		while(iterator.hasNext()){
//			Map.Entry mapentry = (Map.Entry) iterator.next();
//			String name = (String) mapentry.getKey();
//			String code = (String) mapentry.getValue();
//			array[i][0] = name;
//			array[i][1] = code;
//			i++;
//		}
//		return array;	
//	}
	
//	@Test(dataProvider="dbProvider")
//	public void bookListTest(String name, String code) throws Exception {
//		Map<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("action", "bookList");
//		paramMap.put("columnCode", code);	
//		paramMap.put("pageNum", "1");
//		paramMap.put("pageSize", "3");
//		paramMap.put("textFieldType", "txt");	
//		paramMap.put("ebookReturnFields", "editorRecommend,paperBookPrice,imgUrl,freeBook");
//		paramMap.put("token", "");
//			
//		bookList = new BookList(paramMap);
//		bookList.doWorkAndVerify();				
//
//		assert("0".equals(bookList.getReponseResult().getStatus().getCode().toString()));
//		assert(bookList.getDataVerifyResult());
//	}
	
	
	@Test(dataProvider="dataProvider")
	public void bookListTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		BookList bookList = new BookList(param);
		bookList.doWorkAndVerify();

		//assert(excpectd.equals(bookList.getReponseResult().getStatus().getCode().toString()));
		assert(bookList.getDataVerifyResult());
	}
	
	/**
	 * 书城-推荐-名人推荐
	 * @param caseName
	 * @param param
	 * @param excpectd
	 * @throws Exception
	 */
	@Test(dataProvider="dataProvider")
	public void bookReviewStarTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		BookReviewStar bookReviewStar = new BookReviewStar(param);
		bookReviewStar.doWorkAndVerify();

		//assert(excpectd.equals(bookReviewStar.getReponseResult().getStatus().getCode().toString()));
		assert(bookReviewStar.getDataVerifyResult());
	}
	
	/**
	 * 书城-推荐页-男生
	 * 书城-推荐页-女生
	 * @param caseName
	 * @param param
	 * @param excpectd
	 * @throws Exception
	 */
//	@Test(dataProvider="dataProvider")
//	public void maleFemaleTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
//		MaleFemale maleFemale = new MaleFemale(param);
//		maleFemale.doWorkAndVerify();
//
//		//assert(excpectd.equals(maleFemale.getReponseResult().getStatus().getCode().toString()));
//		assert(maleFemale.getDataVerifyResult());
//	}
	
	/**
	 * 书城-推荐页-猜你喜欢
	 * @param caseName
	 * @param param
	 * @param excpectd
	 * @throws Exception
	 */
//	@Test(dataProvider="dataProvider")
//	public void guessulikeTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
//		Guessulike guessulike = new Guessulike(param);
//		guessulike.doWorkAndVerify();
//
//		//assert(excpectd.equals(guessulike.getReponseResult().getStatus().getCode().toString()));
//		assert(guessulike.getDataVerifyResult());
//	}
	

}
