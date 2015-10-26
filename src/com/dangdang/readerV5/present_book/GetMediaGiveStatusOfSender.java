package com.dangdang.readerV5.present_book;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.db.digital.MediaGiveDb;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaGiveDetail;
import com.dangdang.readerV5.reponse.GetMediaGiveStatusOfSenderReponse;
import com.dangdang.readerV5.reponse.MediaByCustIdInfo;
import com.dangdang.readerV5.reponse.MediaInfoPacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-10-12.
 */
public class GetMediaGiveStatusOfSender extends FixtureBase{

    ReponseV2<GetMediaGiveStatusOfSenderReponse> reponseResult;

    public GetMediaGiveStatusOfSender(){}

    public ReponseV2<GetMediaGiveStatusOfSenderReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult= JSONObject.parseObject(result.toString( ),new TypeReference<ReponseV2<GetMediaGiveStatusOfSenderReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            List<MediaGiveDetail> giveDetails = MediaGiveDb.getMediaPacketDetailInfo(paramMap.get("mediaPacketId"));
            List<MediaByCustIdInfo> packetDetails=new ArrayList<MediaByCustIdInfo>();
            Map<Long,MediaByCustIdInfo> map =new HashMap<Long, MediaByCustIdInfo>();
            for(MediaGiveDetail detail:giveDetails){
                Long custId=detail.getReceiveCustId()==null?0:detail.getReceiveCustId();
                MediaByCustIdInfo mediaByCustIdInfo=null;
                if(!map.containsKey(custId)) {
                    mediaByCustIdInfo = new MediaByCustIdInfo();
                    mediaByCustIdInfo.setCustId(custId);
                    mediaByCustIdInfo.setMediaList(new ArrayList<MediaInfoPacket>());
                    map.put(custId, mediaByCustIdInfo);
                }else{
                    mediaByCustIdInfo=map.get(custId);
                }

                mediaByCustIdInfo.setGetDate(detail.getGetDate());

                Media media= MediaDb.getMedia(detail.getMediaId().toString());
                MediaInfoPacket mediaInfoPacket=new MediaInfoPacket();
                mediaInfoPacket.setAuthorPenname(media.getAuthorPenname());
                mediaInfoPacket.setDescs(media.getDescs());
                mediaInfoPacket.setMediaId(media.getMediaId());
                mediaInfoPacket.setSaleId(media.getSaleId());
                mediaInfoPacket.setTitle(media.getTitle());
                mediaByCustIdInfo.getMediaList().add(mediaInfoPacket);
            }

            for(Map.Entry<Long,MediaByCustIdInfo> entry:map.entrySet()){
                if(entry.getKey().equals(0)){
                    entry.getValue().setCustId(null);
                }
                packetDetails.add(entry.getValue());
            }

            dataVerifyManager.add(new ListVerify(reponseResult.getData().getMediaList(),packetDetails,true));
        }
        super.dataVerify();
    }
}
