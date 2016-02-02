package com.dangdang.readerV5.bookbar;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dataverify.RegexVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.readerV5.reponse.ChannelResponse;
import com.dangdang.readerV5.reponse.ShareToBarReponse;

/**
 * Created by cailianjie on 2015-10-20.
 */
public class ShareToBar extends FixtureBase{


    ReponseV2<ShareToBarReponse> reponseResult;

    public  ShareToBar(){}


    public ReponseV2<ShareToBarReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ShareToBarReponse>>(){});
    }


    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            MediaDigest mediaDigest = MediaDigestDb.getMediaDigest(reponseResult.getData().getMediaDigestId().intValue());

            dataVerifyManager.add(new ValueVerify<String>(mediaDigest.getBarId().toString(),paramMap.get("barId"))
                    .setVerifyContent("验证分享的barId正确"));

            dataVerifyManager.add(new ValueVerify<String>(mediaDigest.getTitle(),paramMap.get("title"))
                    .setVerifyContent("验证分享的title正确"));

          /*  String desc="";
            int articleType = Integer.parseInt(paramMap.get("articleType"));
            switch (articleType){
                case 43: {
                    ChannelResponse channel = ChannelSQL.getChannel(Integer.parseInt(paramMap.get("shareSourceId")));
                    desc = channel.getChannel().getDescription();
                }
            }

            dataVerifyManager.add(new ValueVerify<String>(mediaDigest.getCardRemark(), paramMap.get("content")+" "+desc)
                    .setVerifyContent("验证分享的留言正确"));*/

            dataVerifyManager.add(new RegexVerify(paramMap.get("content"), mediaDigest.getCardRemark())
                    .setVerifyContent("验证分享的留言正确"));

            dataVerifyManager.add(new ValueVerify<String>(mediaDigest.getCreatorCustId().toString(), login.getCustId())
                    .setVerifyContent("验证创建人正确"));

        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getMediaDigestId(), null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
