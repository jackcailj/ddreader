package com.dangdang.ZuoJiaPingXuan;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.util.security.SignUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by cailianjie on 2016-1-4.
 */
public class GetEBook extends FixtureBase{

    public GetEBook(){}


    @Override
    protected void parseParam() throws Exception {

        if(paramMap.get("sign")!=null && StringUtils.isBlank(paramMap.get("sign"))){
            String productIdString=paramMap.get("productId");
            //String key = SignUtils.encryptOrderVirtualPayment(Long.parseLong(login.getCustId()),productIdString );
            String source = paramMap.get("deviceSerialNo")+(paramMap.get("timestamp")==null?"":paramMap.get("timestamp"))+productIdString;
            String key =SignUtils.encryptPrizeByMd5(paramMap.get("deviceSerialNo"),paramMap.get("timestamp"),productIdString);

            String sign =SignUtils.createBindPermissionSign(source, key);
            paramMap.put("sign", sign);
        }
    }
}
