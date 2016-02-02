package com.dangdang.reader.testcases.bookstore;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;

/**
 * 书城-单品页测试
 * @author guohaiying
 *
 */
public class BookInfoTest extends TestCaseBase{
	
	@BeforeClass
	public void setUp(){
		setDataFile("");
	}

	@Test
	public void getBookInfoTest(){
		
	}
	
	//下载免费电子书
	//下载试读本
	//下载借阅本
	//购买电子书并下载
}
