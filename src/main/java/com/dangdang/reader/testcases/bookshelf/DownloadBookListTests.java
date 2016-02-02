package com.dangdang.reader.testcases.bookshelf;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.ddframework.util.Util;
import com.dangdang.reader.functional.bookshelf.DownloadBookList;
import com.dangdang.reader.functional.bookshelf.RecoverHiddenEbook;
import com.dangdang.reader.functional.bookshelf.UploadBookList;

public class DownloadBookListTests extends TestCaseBase{
	{
		testDataFile = "bookshelf/downloadBookListTest";
	}
	
	@BeforeClass
	public void setupBeforeClass() throws Exception{
		DownloadBookList.uploadBookListForPrecondition();
	}
	
	@Test(dataProvider="dataProvider")
	public void downloadBookListTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		DownloadBookList bookList = new DownloadBookList(param);
		bookList.doWorkAndVerify();		
		
		assert(excpectd.equals(bookList.getResult().getStatus().getCode().toString()));
		assert(bookList.getDataVerifyResult());
	}

}
