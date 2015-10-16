package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.RegexVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.MediaDb;
import com.dangdang.digital.StoreUpSQL;
import com.dangdang.digital.StoreUpType;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaStoreup;
import com.dangdang.readerV5.reponse.DDReaderStoreUpArticle;
import com.dangdang.readerV5.reponse.DDReaderStoreUpListReponse;
import com.dangdang.readerV5.reponse.DDReaderStoreUpMedia;
import com.dangdang.readerV5.reponse.DDReaderStoreUpPost;
import com.dangdang.ucenter.UserInfoSql;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by cailianjie on 2015-7-3.
 */
public class DDReaderStoreUpList extends FixtureBase{


    ReponseV2<DDReaderStoreUpListReponse<DDReaderStoreUpMedia>> reponseResultMedia;
    ReponseV2<DDReaderStoreUpListReponse<DDReaderStoreUpArticle>> reponseResultArticle;
    ReponseV2<DDReaderStoreUpListReponse<DDReaderStoreUpPost>> reponseResultPost;


    public  DDReaderStoreUpList(){addAction("dDReaderStoreUpList");}


    /*
        shelfType:0代表自己，1代表他人
    */
    public  DDReaderStoreUpList(ILogin login,StoreUpType storeUpType){
        setLogin(login);
        paramMap.put("token", login.getToken());
        paramMap.put("selfType","0");
        paramMap.put("type", storeUpType.toString().toLowerCase());
        paramMap.put("pageSize", "10000");
    }


    public ReponseV2<DDReaderStoreUpListReponse<DDReaderStoreUpMedia>> getReponseResultMedia() {
        return reponseResultMedia;
    }

    public ReponseV2<DDReaderStoreUpListReponse<DDReaderStoreUpArticle>> getReponseResultArticle() {
        return reponseResultArticle;
    }

    public ReponseV2<DDReaderStoreUpListReponse<DDReaderStoreUpPost>> getReponseResultPost() {
        return reponseResultPost;
    }


    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResultMedia = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<DDReaderStoreUpListReponse<DDReaderStoreUpMedia>>>(){});
        reponseResultArticle = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<DDReaderStoreUpListReponse<DDReaderStoreUpArticle>>>(){});
        reponseResultPost = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<DDReaderStoreUpListReponse<DDReaderStoreUpPost>>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            StoreUpType storeUpType =StoreUpType.valueOf(paramMap.get("type").toUpperCase());

            if(paramMap.get("selfType").equals("0")) {
                if (login != null) {//获取自己的
                    List storeList = new ArrayList();
                    List<MediaStoreup> mediaStoreups = StoreUpSQL.getStoreUpList(login.getCustId(), storeUpType);
                    if (storeUpType == StoreUpType.MEDIA) {
                        List<Media> medias = MediaDb.getMedias(Util.getFields(mediaStoreups, "targetId"));
                        for (MediaStoreup mediaStoreup : mediaStoreups) {
                            DDReaderStoreUpMedia storeUpMedia = new DDReaderStoreUpMedia();
                            storeUpMedia.setStoreDateLong(mediaStoreup.getStoreDate());
                            storeUpMedia.setProductId(mediaStoreup.getTargetId());
                            for (Media media : medias) {
                                if (mediaStoreup.getTargetId().equals(media.getProductId())) {
                                    //只返回第一个作者
                                    //storeUpMedia.setAuthorName(media.getAuthorPenname().split(";")[0]);
                                    storeUpMedia.setBookName(media.getTitle());
                                    storeUpMedia.setEditorRecommend(media.getDescs());

                                }
                            }
                            storeList.add(storeUpMedia);
                        }

                        dataVerifyManager.add(new ListVerify(reponseResultMedia.getData().getStoreUpList(), storeList, true).setVerifyContent("验证收藏列表Meida数据是否正确"));
                    } else if (storeUpType == StoreUpType.ARTICLE) {
                        if (mediaStoreups.size() > 0) {
                            //不必对详细信息，之比对id是否存在
                            StringBuilder regexArticleId = new StringBuilder();
                            for (MediaStoreup mediaStoreup : mediaStoreups) {
                                regexArticleId.append(Util.getJsonRegexString("articleId", mediaStoreup.getTargetId().toString()));
                                regexArticleId.append(".*?");
                            }

                            //regexArticleId.(regexArticleId.length()-".*?".length(),".*?".length());

                            dataVerifyManager.add(new RegexVerify(regexArticleId.toString(), result.toString()));
                        } else {
                            dataVerifyManager.add(new RegexVerify(Util.getJsonRegexString("storeUpList", "\\[\\]"), result.toString()));
                        }

                    } else if (storeUpType == StoreUpType.POST) {
                        if (mediaStoreups.size() > 0) {
                            //不必对详细信息，之比对id是否存在
                            StringBuilder regexArticleId = new StringBuilder();
                            for (MediaStoreup mediaStoreup : mediaStoreups) {
                                regexArticleId.append(Util.getJsonRegexString("postId", mediaStoreup.getTargetId().toString()));
                                regexArticleId.append(".*?");
                            }

                            //regexArticleId.(regexArticleId.length()-".*?".length(),".*?".length());

                            dataVerifyManager.add(new RegexVerify(regexArticleId.toString(), result.toString()));
                        } else {
                            dataVerifyManager.add(new RegexVerify(Util.getJsonRegexString("storeUpList", "\\[\\]"), result.toString()));
                        }
                    }
                }
            }
            else{
                //获取别人的
                List<MediaStoreup> storeups = StoreUpSQL.getStoreUpList(UserInfoSql.getCustIdByPubId(paramMap.get("pubId")), storeUpType);
                StringBuilder regexArticleId = new StringBuilder();
                for (MediaStoreup mediaStoreup : storeups) {
                    if(storeUpType==StoreUpType.MEDIA) {
                        regexArticleId.append(Util.getJsonRegexString("productId", mediaStoreup.getTargetId().toString()));
                    }
                    else if(storeUpType==StoreUpType.ARTICLE){
                        regexArticleId.append(Util.getJsonRegexString("articleId", mediaStoreup.getTargetId().toString()));
                    }
                    else if(storeUpType==StoreUpType.POST){
                        regexArticleId.append(Util.getJsonRegexString("postId", mediaStoreup.getTargetId().toString()));
                    }
                    regexArticleId.append(".*?");
                }
                if(storeups.size()==0){
                    dataVerifyManager.add(new RegexVerify(Util.getJsonRegexString("storeUpList","\\[\\]"),result.toString()));
                }
                else {
                    dataVerifyManager.add(new RegexVerify(regexArticleId.toString(), result.toString()));
                }
            }


        }
        super.dataVerify();
    }
}
