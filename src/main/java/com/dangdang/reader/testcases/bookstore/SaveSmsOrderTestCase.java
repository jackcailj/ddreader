package com.dangdang.reader.testcases.bookstore;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.purchaseAndDownload.GetSmsOrderStatus;
import com.dangdang.reader.functional.purchaseAndDownload.SaveSmsOrder;
import com.dangdang.reader.functional.purchaseAndDownload.SmsCallback;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Created by cailianjie on 2015-5-19.
 */
public class SaveSmsOrderTestCase extends TestCaseBase{
    {
        setDataFile("bookstore/SaveSmsOrderTest");
    }


    @Test(dataProvider="dataProvider")
    public void SaveSmsOrderTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
        SaveSmsOrder functional = new SaveSmsOrder(param);
        functional.doWorkAndVerify();

        assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
        assert(functional.getDataVerifyResult());
    }


    @Test(dataProvider="dataProvider")
    public void SmsCallbackOrderTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
        SmsCallback functional = new SmsCallback(param);
        functional.doWorkAndVerify();

        //assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
        assert(functional.getDataVerifyResult());
    }

    @Test(dataProvider="dataProvider")
    public void GetSmsOrderStatusTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
        GetSmsOrderStatus functional = new GetSmsOrderStatus(param);
        functional.doWorkAndVerify();

        //assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
        assert(functional.getDataVerifyResult());
    }
}
