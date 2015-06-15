package com.dangdang.reader.testcases.bookshelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.bookshelf.UploadBookList;

public class UploadBookListTests extends TestCaseBase{
	{
		testDataFile = "bookshelf/uploadBookListTest";
	}
	
	@Test(dataProvider="dataProvider")
	public void uploadBookListTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		UploadBookList bookList = new UploadBookList(param);
		bookList.doWorkAndVerify();		
		
		assert(excpectd.equals(bookList.getResult().getStatus().getCode().toString()));
		assert(bookList.getDataVerifyResult());
	}
	
	//http://10.255.223.131/mobile/api2.do?action=uploadBookList
	//&data={%22token%22:%2216138359398c4fcc77e32a5cfd6e84e6%22,%22data%22:[{%22categoryName%22:%22computer%22,%22lastChangedDate%22:14567000000,%22books%22:[{%22productId%22:1900089259,%22lastChangedDate%22:14567000000}]},{%22categoryName%22:%22dd_no_group%22,%22lastChangedDate%22:14567000000,%22books%22:[{%22productId%22:1900089259,%22lastChangedDate%22:14567000000}]}]}
    // DB: bookshelf_category     bookshelf_book
}
