package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.MediaDigestDb;
import com.dangdang.digital.StoreUpType;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.GetPostListReponse;
import com.dangdang.ucenter.UserInfoSql;
import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cailianjie on 2015-6-17.
 */
public class GetPostList extends FixtureBase{

    ReponseV2<GetPostListReponse> reponseResult;

    public GetPostList(){
        addAction("getPostList");
    }


    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
        ParseParamUtil.removeBlackParam(paramMap);
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();
        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetPostListReponse>>(){});
    }

    public ReponseV2<GetPostListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            if(paramMap.get("selfType").equals("0")) {
                List<MediaDigest> mediaDigests = MediaDigestDb.getMediaDigest(login.getCustId(), StoreUpType.POST);
                if (mediaDigests.size() == 0) {
                    dataVerifyManager.add(new ValueVerify<Integer>(0, reponseResult.getData().getPostList().size()));
                } else {
                    for(MediaDigest mediaDigest:mediaDigests){
                        Matcher matcher = Pattern.compile("\"bodyContent\">(.*?)</p>", Pattern.DOTALL).matcher(mediaDigest.getContent());
                        if(matcher.find()) {
                            mediaDigest.setContent(matcher.group(1));
                        }
                    }
                    dataVerifyManager.add(new ListVerify(reponseResult.getData().getPostList(), mediaDigests, true));
                }
            }
            else {
                List<MediaDigest> mediaDigests = MediaDigestDb.getMediaDigest(UserInfoSql.getCustIdByPubId(paramMap.get("pubId")), StoreUpType.POST);
                if (mediaDigests.size() == 0) {
                    if(reponseResult.getData().getPostList()!=null) {
                        dataVerifyManager.add(new ValueVerify<Integer>(0, reponseResult.getData().getPostList().size()));
                    }

                } else {
                    for(MediaDigest mediaDigest:mediaDigests){
                        Matcher matcher = Pattern.compile("\"bodyContent\">(.*?)</p>", Pattern.DOTALL).matcher(mediaDigest.getContent());
                        if(matcher.find()) {
                            mediaDigest.setContent(matcher.group(1));
                        }
                    }
                    dataVerifyManager.add(new ListVerify(reponseResult.getData().getPostList(), mediaDigests, true));
                }
            }
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getPostList(), null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
