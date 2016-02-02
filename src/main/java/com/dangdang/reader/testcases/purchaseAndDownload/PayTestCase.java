package com.dangdang.reader.testcases.purchaseAndDownload;

import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.personcenter.ChangeUserProfile;
import com.dangdang.reader.functional.purchaseAndDownload.WxPay;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Created by cailianjie on 2015-5-26.
 */
public class PayTestCase extends TestCaseBase{

    {
        setDataFile("purchaseAndDownload/PayTestCase");
    }

    @Test(dataProvider="dataProvider")
    public void WxPayTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
        WxPay functional =new WxPay(param);
        functional.doWorkAndVerify();

        assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
        assert(functional.getDataVerifyResult());
    }
}
