package com.dangdang.readerV5.present_book;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.RecordExVerify;
import com.dangdang.ddframework.dataverify.RecordVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaGive;
import com.dangdang.digital.meta.MediaGiveDetail;
import com.dangdang.readerV5.reponse.GiveMediaGiveReponse;
import com.jidesoft.validation.ValidationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cailianjie on 2015-10-10.
 */
public class GiveMediaGive extends FixtureBase{

    ReponseV2<GiveMediaGiveReponse> reponseResult;

    public GiveMediaGive(){}

    public ReponseV2<GiveMediaGiveReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<GiveMediaGiveReponse>>(){});
    }

    /*
    获取红包Id
     */
    public String getMediaPacketId(){
        return reponseResult.getData().getMediaPacketId();
    }


    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            MediaGive expectedMediaGive=new MediaGive();
            expectedMediaGive.setAdvice(paramMap.get("advice"));
            expectedMediaGive.setGiveCustId(Long.parseLong(login.getCustId()));
            expectedMediaGive.setGiveId(reponseResult.getData().getMediaPacketId());
            expectedMediaGive.setMediaPutCount(0);//被领取书的数量

            String[] mediaIds = paramMap.get("mediaIds").split(",");
            expectedMediaGive.setMediaTotalCount(mediaIds.length);
            expectedMediaGive.setStatus(0);

            dataVerifyManager.add(new RecordVerify(Config.YCDBConfig, expectedMediaGive));

            //List<MediaGiveDetail> details =new ArrayList<MediaGiveDetail>();
            for(String mediaId:mediaIds){
                MediaGiveDetail mediaGiveDetail=new MediaGiveDetail();
                mediaGiveDetail.setGiveCustId(Long.parseLong(login.getCustId()));
                mediaGiveDetail.setMediaId(Long.parseLong(mediaId));
                mediaGiveDetail.setPacketId(reponseResult.getData().getMediaPacketId());
                mediaGiveDetail.setStatus(0);

                dataVerifyManager.add(new RecordVerify(Config.YCDBConfig, mediaGiveDetail));
                //details.add(mediaGiveDetail);
            }
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getMediaPacketId(),null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
