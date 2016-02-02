package com.dangdang.reader.testcases.bookstore;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.bookstore.BookListByChannel;

/**
 * 书城-推荐页
 * @author guohaiying
 *
 */
public class BookListByChannelTest extends TestCaseBase{

	@BeforeClass
	public void setUp() throws Exception{
		setDataFile("bookstore/BookListByChannelTest");	
	}	
	
	/**
	 * 书城轮播图，banner图
	 * @param caseName
	 * @param param
	 * @param excpectd
	 * @throws Exception
	 */
	@Test(dataProvider="dataProvider")
	public void test(String caseName,Map<String, String> param,String excpectd) throws Exception {
		BookListByChannel channel = new BookListByChannel(param);
		channel.doWorkAndVerify();

		assert("0".equals(channel.getReponseResult().getStatus().getCode().toString()));
		assert(channel.getDataVerifyResult());
	}
		
}
