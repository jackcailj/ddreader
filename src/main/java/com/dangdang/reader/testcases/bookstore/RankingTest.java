package com.dangdang.reader.testcases.bookstore;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;

/**
 * 书城-榜单页
 * @author guohaiying
 *
 */
public class RankingTest extends TestCaseBase{
	
	@BeforeClass
	public void setUp(){
		setDataFile("");
	}
	
	@Test(dataProvider="dataProvider")
	public void rankingListTest(String caseName,Map<String, String> param,String excpectd){
		
	}

}
