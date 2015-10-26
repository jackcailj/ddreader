package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.enumeration.StoreUpType;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.GetPostListReponse;
import com.dangdang.readerV5.reponse.PostListDigestInfo;
import com.dangdang.db.ucenter.UserInfoSql;

import java.util.ArrayList;
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
            Integer lastMediaid=null;
            try {
                lastMediaid=Integer.parseInt(paramMap.get("digestId"));
            }catch (Exception e){

            }

            //组织验证数据，验证。
            if(paramMap.get("selfType").equals("0")) {
                List<MediaDigest> mediaDigests = MediaDigestDb.getMediaDigest(login.getCustId(), StoreUpType.POST,lastMediaid);
                if (mediaDigests.size() == 0) {
                    dataVerifyManager.add(new ValueVerify<Integer>(0, reponseResult.getData().getPostList().size()));
                } else {
                    List<PostListDigestInfo> postListDigestInfoList=new ArrayList<PostListDigestInfo>();
                    for(MediaDigest mediaDigest:mediaDigests){
                        Matcher matcher = Pattern.compile("\"bodyContent\">(.*?)(</br>|<img|</p>)", Pattern.DOTALL).matcher(mediaDigest.getContent());
                        if(matcher.find()) {
                            mediaDigest.setContent(matcher.group(1));
                        }
                        PostListDigestInfo postListDigestInfo=new PostListDigestInfo();
                        postListDigestInfo.setTitle(mediaDigest.getTitle());
                        postListDigestInfo.setBarId(mediaDigest.getBarId());
                        postListDigestInfo.setCardType(mediaDigest.getCardType());
                        //postListDigestInfo.setType(mediaDigest.getType());
                        postListDigestInfo.setContent(mediaDigest.getContent());
                        postListDigestInfo.setIsDel(mediaDigest.getIsDel()?1:0);
                        postListDigestInfo.setIsShow(mediaDigest.getIsShow()?1:0);
                        //postListDigestInfo.setCreateDateLong(mediaDigest.getCreateDate().getTime());
                        postListDigestInfo.setMediaDigestId(mediaDigest.getId());
                        postListDigestInfo.setCustId(login.getCustId());
                        postListDigestInfoList.add(postListDigestInfo);
                    }
                    dataVerifyManager.add(new ListVerify(reponseResult.getData().getPostList(), postListDigestInfoList, true));
                }
            }
            else {
                List<MediaDigest> mediaDigests = MediaDigestDb.getMediaDigest(UserInfoSql.getCustIdByPubId(paramMap.get("pubId")), StoreUpType.POST,lastMediaid);
                if (mediaDigests.size() == 0) {
                    if(reponseResult.getData().getPostList()!=null) {
                        dataVerifyManager.add(new ValueVerify<Integer>(0, reponseResult.getData().getPostList().size()));
                    }

                } else {
                    List<PostListDigestInfo> postListDigestInfoList=new ArrayList<PostListDigestInfo>();
                    for(MediaDigest mediaDigest:mediaDigests){
                        Matcher matcher = Pattern.compile("\"bodyContent\">(.*?)(</br>|<img|</p>)", Pattern.DOTALL).matcher(mediaDigest.getContent());
                        if(matcher.find()) {
                            mediaDigest.setContent(matcher.group(1));
                        }

                        PostListDigestInfo postListDigestInfo=new PostListDigestInfo();
                        postListDigestInfo.setTitle(mediaDigest.getTitle());
                        postListDigestInfo.setBarId(mediaDigest.getBarId());
                        postListDigestInfo.setCardType(mediaDigest.getCardType());
                        //postListDigestInfo.setType(mediaDigest.getType());
                        postListDigestInfo.setContent(mediaDigest.getContent());
                        postListDigestInfo.setIsDel(mediaDigest.getIsDel()?1:0);
                        postListDigestInfo.setIsShow(mediaDigest.getIsShow()?1:0);
                        //postListDigestInfo.setCreateDateLong(mediaDigest.getCreateDate().getTime());
                        postListDigestInfo.setMediaDigestId(mediaDigest.getId());
                        postListDigestInfo.setCustId(UserInfoSql.getCustIdByPubId(paramMap.get("pubId")));
                        postListDigestInfoList.add(postListDigestInfo);
                    }
                    dataVerifyManager.add(new ListVerify(reponseResult.getData().getPostList(), postListDigestInfoList, true));
                }
            }
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getPostList(), null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
