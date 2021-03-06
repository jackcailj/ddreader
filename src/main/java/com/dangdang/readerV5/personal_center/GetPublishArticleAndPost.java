package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.LoginManager;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.readerV5.reponse.*;
import com.dangdang.db.ucenter.UserInfoSql;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cailianjie on 2015-8-21.
 */
public class GetPublishArticleAndPost extends FixtureBase{

    ReponseV2<GetPublishArticleAndPostReponse> reponseResult;

    public GetPublishArticleAndPost(){}


    public ReponseV2<GetPublishArticleAndPostReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetPublishArticleAndPostReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){

            List<MediaDigest> mediaDigests=null;
            if(paramMap.get("selfType").equals("0")){
                mediaDigests = MediaDigestDb.getMyCreateMediaDigest(login.getCustId(), 10);
            }
            else if(paramMap.get("selfType").equals("1") && (paramMap.get("flag")==null || paramMap.get("flag")!=null && !paramMap.get("flag").equals("flag_pubid_error"))) {
                    mediaDigests = MediaDigestDb.getMyCreateMediaDigest(LoginManager.getLoginByPubID(paramMap.get("pubId")).getCustId(), 10);

            }

            if(mediaDigests==null){
                dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getPublishList(), null), VerifyResult.SUCCESS);
            }
            else {
                List<PublistPostInfo> postListDigestInfoList = new ArrayList<PublistPostInfo>();
                for (MediaDigest mediaDigest : mediaDigests) {
                    Matcher matcher = Pattern.compile("\"bodyContent\">(.*?)(</br>|<img|</p>)", Pattern.DOTALL).matcher(mediaDigest.getContent());
                    if (matcher.find()) {
                        mediaDigest.setContent(matcher.group(1));
                    }
                    PublistPostInfo postListDigestInfo = new PublistPostInfo();

                    postListDigestInfo.setTitle(mediaDigest.getTitle());
                    postListDigestInfo.setBarId(mediaDigest.getBarId());
                    postListDigestInfo.setCardType(mediaDigest.getCardType());
                    //postListDigestInfo.setType(mediaDigest.getType());
                    //postListDigestInfo.setContent(mediaDigest.getContent());
                    postListDigestInfo.setRemark(mediaDigest.getCardRemark());
                    postListDigestInfo.setIsDel(mediaDigest.getIsDel() ? 1 : 0);
                    postListDigestInfo.setIsShow(mediaDigest.getIsShow() ? 1 : 0);

                    //postListDigestInfo.setCreateDateLong(mediaDigest.getCreateDate().getTime());
                    postListDigestInfo.setDigestId(mediaDigest.getId());
                    //postListDigestInfo.setCustId(login.getCustId());
                    postListDigestInfoList.add(postListDigestInfo);


                }

                dataVerifyManager.add(new ListVerify(reponseResult.getData().getPublishList(), postListDigestInfoList, true));
            }
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(null, reponseResult.getData().getPublishList()), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
