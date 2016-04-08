package com.dangdang.readerV5.homepage;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.ChannelArticlesDigestDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.ChannelArticlesDigest;
import com.dangdang.digital.meta.ChannelOwner;
import com.dangdang.readerV5.reponse.HomePageRemindReponse;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by cailianjie on 2016-2-26.
 */
public class HomePageRemind extends FixtureBase {


    ReponseV2<HomePageRemindReponse> reponseResult;

    public HomePageRemind(){}


    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<HomePageRemindReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            if(StringUtils.isBlank(paramMap.get("time"))){
                dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getNewArticleCount(),0));
            }
            else{

                dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getNewArticleCount(),null), VerifyResult.FAILED);
               /* try{

                    Date date = StringUtils.isBlank(paramMap.get("time"))?null:new Date(Long.parseLong(paramMap.get("time")));
                    //获取首页文章列表
                    List<ChannelArticlesDigest> recommentArticle= ChannelArticlesDigestDb.getHomeArticle(date,login==null?null:login.getCustId(),false,1000000);




                    dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getNewArticleCount(),recommentArticle.size()));

                }catch (Exception e){
                    dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getNewArticleCount(),0));
                }*/

            }
        }
        super.dataVerify();
    }
}
