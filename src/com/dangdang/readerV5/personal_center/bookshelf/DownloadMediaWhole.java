package com.dangdang.readerV5.personal_center.bookshelf;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.param.parse.ParseParamUtil;

/**
 * Created by cailianjie on 2015-7-7.
 */
public class DownloadMediaWhole extends FixtureBase{

    public  DownloadMediaWhole(){addAction("downloadMediaWhole");}

    @Override
    protected void parseParam() throws Exception {
        //获取证书
        GetPublishedCertificate getPublishedCertificate = new GetPublishedCertificate(login,paramMap.get("mediaId"),paramMap.get("isFull"),"");
        getPublishedCertificate.doWork();
    }
}
