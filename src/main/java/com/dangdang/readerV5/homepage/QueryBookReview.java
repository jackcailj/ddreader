package com.dangdang.readerV5.homepage;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.BarModule;
import com.dangdang.bookbar.meta.BarModuleContent;
import com.dangdang.db.bookbar.BarModuleContentDb;
import com.dangdang.db.bookbar.BarModuleDb;
import com.dangdang.db.comment.PraiseInfoDb;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.readerV5.reponse.BarModuleInfo;
import com.dangdang.readerV5.reponse.HomePagePostInfo;
import com.dangdang.readerV5.reponse.QueryBookReviewReponse;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Created by cailianjie on 2016-3-23.
*/
public class QueryBookReview extends FixtureBase{

    ReponseV2<QueryBookReviewReponse> reponseResult;

    public  QueryBookReview(){}


    public ReponseV2<QueryBookReviewReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<QueryBookReviewReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){

            BarModule barModule = BarModuleDb.getHomePageBarModule("homeHotArticle");

            BarModuleInfo expectBarModule=new BarModuleInfo();
            expectBarModule.setType(barModule.getType());
            expectBarModule.setBarModuleId(barModule.getBarModuleId());
            expectBarModule.setModuleName(barModule.getModuleName());
            expectBarModule.setShowNum(barModule.getShowNum());

            //获取配置的首页精彩书评
            List<BarModuleContent> postInfos= BarModuleContentDb.getPostInfoByLocation("homeHotArticle");

            List<String> postIds = Util.getFields(postInfos,"contentId");
            //获取帖子内容
            List<MediaDigest> digests = MediaDigestDb.getMediaDigests(postIds);

            List<HomePagePostInfo> expectHomePageDigestInfoList = new ArrayList<HomePagePostInfo>(postIds.size());
            HomePagePostInfo[] expectHomePageDigestInfos = new HomePagePostInfo[postIds.size()];

            for(MediaDigest digest:digests){

                HomePagePostInfo digestInfo = new HomePagePostInfo();
                digestInfo.setTitle(digest.getTitle());
                digestInfo.setBarId(digest.getBarId());
                digestInfo.setMediaDigestId(digest.getId());
                //digestInfo.setType(digest.getType());
                digestInfo.setPraiseNum(digest.getTopCnt());
                digestInfo.setCommentNum(digest.getReviewCnt());

                //判断是否点赞
                if(login!=null){
                    digestInfo.setIsPraise(PraiseInfoDb.get(login.getCustId(),digest.getId().toString())?1:0);
                }

                int nIndex =postIds.indexOf(digest.getId().toString());


                expectHomePageDigestInfos[nIndex]=digestInfo;
            }

            CollectionUtils.addAll(expectHomePageDigestInfoList,expectHomePageDigestInfos);
            dataVerifyManager.add(new ListVerify(reponseResult.getData().getBookReviewList().get(0).getArticleContent(),expectHomePageDigestInfoList ,true));
            dataVerifyManager.add(new ValueVerify<BarModuleInfo>(reponseResult.getData().getBookReviewList().get(0).getModule(),expectBarModule,true));
        }
        super.dataVerify();
    }
}
