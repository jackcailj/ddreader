package com.dangdang.readerV5.homepage;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.ChannelArticlesDigestDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyType;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.StringUtil;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.ChannelArticlesDigest;
import com.dangdang.readerV5.reponse.DigestInfo;
import com.dangdang.readerV5.reponse.HomePageDetailReponse;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cailianjie on 2016-1-25.
 */
public class HomePageDetail extends FixtureBase{

    ReponseV2<HomePageDetailReponse> reponseResult;

    public HomePageDetail(){}


    public ReponseV2<HomePageDetailReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<HomePageDetailReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){

            Date date = StringUtils.isBlank(paramMap.get("time"))?null:new Date(Long.parseLong(paramMap.get("time")));

            Boolean isFilter = StringUtils.isBlank(paramMap.get("isFilterSub"))? false: Boolean.parseBoolean(paramMap.get("isFilterSub"));

            int pageSize = StringUtils.isBlank(paramMap.get("pageSize"))?10:Integer.parseInt(paramMap.get("pageSize"));

            List<ChannelArticlesDigest> recommentArticle= ChannelArticlesDigestDb.getHomeArticle(date,login==null?null:login.getCustId(),isFilter,pageSize);

            List<String> expectDigestIds = Util.getFields(recommentArticle, "digestId");
            List<String> returnDigestIds = new ArrayList();

            for (DigestInfo digestInfo : reponseResult.getData().getDigestList()) {
                List ids = Util.getFields(JSONObject.toJSONString(digestInfo.getDigest()), "id");
                returnDigestIds.addAll(ids);
            }

            if(isFilter) {
                dataVerifyManager.add(new ListVerify(returnDigestIds, expectDigestIds, false));
            }else{
                //由于数据用了斐波那契数排序，这里不验证排序，之验证数量和id是否正确。
                dataVerifyManager.add(new ValueVerify<Integer>(returnDigestIds.size(), expectDigestIds.size(), false).setVerifyContent("验证列表大小一致"));
                dataVerifyManager.add(new ListVerify(returnDigestIds, expectDigestIds, false, VerifyType.CONTAINS).setVerifyContent("验证数据一致"));
            }

        }
        else{

        }
        super.dataVerify();
    }
}
