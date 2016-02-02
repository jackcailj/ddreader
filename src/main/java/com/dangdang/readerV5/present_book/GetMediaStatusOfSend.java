package com.dangdang.readerV5.present_book;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.MediaGiveDb;
import com.dangdang.digital.meta.MediaGive;
import com.dangdang.readerV5.reponse.GetMediaStatusOfSendReponse;
import com.dangdang.readerV5.reponse.MediaPacketInfo;

/**
 * Created by cailianjie on 2015-10-12.
 */
public class GetMediaStatusOfSend extends FixtureBase{

    ReponseV2<GetMediaStatusOfSendReponse> reponseResult;

    public GetMediaStatusOfSend(){}

    public ReponseV2<GetMediaStatusOfSendReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<GetMediaStatusOfSendReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            try {
                MediaGive mediaGive = MediaGiveDb.getMediaPacketInfo(paramMap.get("mediaPacketId"));

                MediaPacketInfo mediaPacketInfo = new MediaPacketInfo();
                mediaPacketInfo.setAdvice(mediaGive.getAdvice());
                mediaPacketInfo.setStatus(mediaGive.getStatus());
                mediaPacketInfo.setPuts(mediaGive.getMediaPutCount());
                mediaPacketInfo.setTotal(mediaGive.getMediaTotalCount());
                //mediaPacketInfo.setNickName(login.getUserInfo().getNickName());

                dataVerifyManager.add(new ValueVerify<MediaPacketInfo>(reponseResult.getData().getMediaPacket(), mediaPacketInfo, true));
            }catch (Exception e){
                dataVerifyManager.add(new ValueVerify<MediaPacketInfo>(reponseResult.getData().getMediaPacket(), null));
            }

        }
        else{
            dataVerifyManager.add(new ValueVerify<MediaPacketInfo>(reponseResult.getData().getMediaPacket(), null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
