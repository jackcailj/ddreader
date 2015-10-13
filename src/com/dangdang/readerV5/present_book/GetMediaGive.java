package com.dangdang.readerV5.present_book;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.authority.AuthorityDb;
import com.dangdang.authority.MediaAuthority;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.MediaGiveDb;
import com.dangdang.digital.meta.MediaGive;
import com.dangdang.digital.meta.MediaGiveDetail;
import com.dangdang.reader.functional.param.parse._enum.BuyBookStatus;
import com.dangdang.reader.functional.param.parse._enum.PacketStatus;
import com.dangdang.readerV5.reponse.GetMediaGiveReponse;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cailianjie on 2015-10-13.
 */
public class GetMediaGive extends FixtureBase{

    ReponseV2<GetMediaGiveReponse> reponseResult;

    public GetMediaGive(){}

    public ReponseV2<GetMediaGiveReponse> getReponseResult() {
        return reponseResult;
    }


    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult= JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetMediaGiveReponse>>(){});
    }


    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            MediaGive mediaGive = MediaGiveDb.getMediaPacketInfo(paramMap.get("mediaPacketId"));
            List<MediaGiveDetail> mediaGiveDetails=MediaGiveDb.getMediaPacketDetailInfo(paramMap.get("mediaPacketId"));

            String[] mediaIds=paramMap.get("mediaIds").split(",");
            List<String> mediaIdsArray = new ArrayList<String>();
            CollectionUtils.addAll(mediaIdsArray,mediaIds);


            boolean bAllGet=true;
            for(MediaGiveDetail detail:mediaGiveDetails){
                if(mediaIdsArray.contains(detail.getMediaId().toString())) {
                    //验证MediaGiveDetail状态是否正确
                    dataVerifyManager.add(new ValueVerify<String>(detail.getStatus().toString(), PacketStatus.COMPLETE.toString()).setVerifyContent("验证MediaGiveDetail 的status为1"));
                    dataVerifyManager.add(new ValueVerify<String>(detail.getReceiveCustId().toString(),login.getCustId()).setVerifyContent("验证接收人custId正确"));

                    //验证书籍权限转移是否正确

                    //验证领取人获取书籍权限
                    MediaAuthority mediaAuthority = AuthorityDb.getUserEbook(login.getCustId(),detail.getMediaId().toString());
                    dataVerifyManager.add(new ValueVerify<String>(mediaAuthority.getRelationType(), BuyBookStatus.ZENGSONG.getBookStatusString()).setVerifyContent("验证收书人获取书籍权限"));

                    //验证赠送人失去书籍权限
                    mediaAuthority = AuthorityDb.getUserEbook(detail.getGiveCustId().toString(),detail.getMediaId().toString());
                    dataVerifyManager.add(new ValueVerify<String>(mediaAuthority.getRelationType(), BuyBookStatus.SENDEDBOOK.getBookStatusString()).setVerifyContent("验证赠送人失去书籍权限"));
                }
                else{
                    //验证MediaGiveDetail状态是否正确
                    dataVerifyManager.add(new ValueVerify<String>(detail.getStatus().toString(), PacketStatus.INPROGRESS.toString()).setVerifyContent("验证验证MediaGiveDetail的status为0"));
                    dataVerifyManager.add(new ValueVerify<String>(detail.getReceiveCustId().toString(),null).setVerifyContent("验证接收人custId为null"));
                    bAllGet=false;
                }
            }

            //验证是否为全部领取
            if(bAllGet){
                dataVerifyManager.add(new ValueVerify<String>(mediaGive.getStatus().toString(), PacketStatus.COMPLETE.toString()).setVerifyContent("验证MediaGive的status为1"));
            }
            else{
                dataVerifyManager.add(new ValueVerify<String>(mediaGive.getStatus().toString(), PacketStatus.INPROGRESS.toString()).setVerifyContent("验证MediaGive的status为0"));
            }

        }
        super.dataVerify();
    }
}
