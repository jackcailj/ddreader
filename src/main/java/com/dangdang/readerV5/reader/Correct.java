package com.dangdang.readerV5.reader;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dataverify.RecordExVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.digital.meta.MediaCorrectDetail;

/**
 * Created by cailianjie on 2015-11-18.
 */
public class Correct extends FixtureBase{


    public Correct(){bPost=true;}


    @Override
    protected void genrateVerifyData() throws Exception {

        MediaCorrectDetail mediaCorrectDetail = new MediaCorrectDetail();
        mediaCorrectDetail.setChapterId(Long.parseLong(paramMap.get("chapterId")));
        mediaCorrectDetail.setMediaId(Long.parseLong(paramMap.get("mediaId")));
        mediaCorrectDetail.setContent(paramMap.get("content"));
        mediaCorrectDetail.setCorrectContent(paramMap.get("correctContent"));
        try {
            mediaCorrectDetail.setStartIndex(Integer.parseInt(paramMap.get("startIndex")) + 1);
        }catch (Exception e){

        }

        try {
            mediaCorrectDetail.setEndIndex(Integer.parseInt(paramMap.get("endIndex")) + 1);
        }catch (Exception e){

        }

        if(EXCEPTSUCCESS) {


            if (login != null) {

                mediaCorrectDetail.setCustId(Long.parseLong(login.getCustId()));
                dataVerifyManager.add(new RecordExVerify(Config.YCDBConfig, mediaCorrectDetail, "correct_detail_id", " from media_correct_detail where cust_id=" + login.getCustId()));
            }
            else{
                dataVerifyManager.add(new RecordExVerify(Config.YCDBConfig, mediaCorrectDetail, "correct_detail_id", " from media_correct_detail "  ));
            }
        }
        else{
            dataVerifyManager.add(new RecordExVerify(Config.YCDBConfig, mediaCorrectDetail, "correct_detail_id", " from media_correct_detail "  ), VerifyResult.FAILED);
        }
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){

        }
        super.dataVerify();
    }
}

