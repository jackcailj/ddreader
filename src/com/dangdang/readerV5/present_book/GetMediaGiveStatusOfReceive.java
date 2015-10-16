package com.dangdang.readerV5.present_book;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.RegexVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.MediaGiveDb;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaGiveDetail;
import com.dangdang.readerV5.reponse.GetMediaGiveStatusOfReceiveReponse;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cailianjie on 2015-10-13.
 */
public class GetMediaGiveStatusOfReceive extends FixtureBase{

    ReponseV2<GetMediaGiveStatusOfReceiveReponse> reponseResult;

    public GetMediaGiveStatusOfReceive(){}

    public ReponseV2<GetMediaGiveStatusOfReceiveReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult= JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<GetMediaGiveStatusOfReceiveReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            List<MediaGiveDetail> giveDetail = MediaGiveDb.getMediaPacketDetailInfo(paramMap.get("mediaPacketId"));

            List<String> expectMedia=new ArrayList<String>();
            StringBuilder mediaIds = new StringBuilder();
            for(MediaGiveDetail detail:giveDetail){
                mediaIds.append(Util.getJsonRegexString("mediaId", ""+detail.getMediaId()));
                mediaIds.append(".*?");
            }

            dataVerifyManager.add(new RegexVerify(mediaIds.toString(), result.toString()));
        }
        super.dataVerify();
    }
}
