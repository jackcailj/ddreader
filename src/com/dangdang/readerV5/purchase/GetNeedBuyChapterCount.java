package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.MediaChapterDb;
import com.dangdang.db.digital.MediaSysPropertiesDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaChapter;
import com.dangdang.digital.meta.MediaSysProperties;
import com.dangdang.enumeration.SysPropertiesEnum;
import com.dangdang.readerV5.reponse.GetNeedBuyChapterCountReponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cailianjie on 2015-12-8.
 */
public class GetNeedBuyChapterCount extends FixtureBase{

    ReponseV2<GetNeedBuyChapterCountReponse> reponseResult;

    public GetNeedBuyChapterCount(){}

    public ReponseV2<GetNeedBuyChapterCountReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetNeedBuyChapterCountReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            String batchBuyConfig= MediaSysPropertiesDb.getSysProperties(SysPropertiesEnum.BATCH_BUY_CHAPTER_COUNT);
            String[] batchBuyConfigs=batchBuyConfig.split(",");

            List<MediaChapter> chapters = MediaChapterDb.GetBookChapterLast(paramMap.get("chapterId"));

            List<Integer> batchBuyCount=new ArrayList<Integer>();
            for (int i=0;i<batchBuyConfigs.length;++i){
                Integer nConfig = Integer.parseInt(batchBuyConfigs[i]);
                if(nConfig<=chapters.size()){
                    batchBuyCount.add(nConfig);
                }
                else {
                    if(i==0){
                        batchBuyCount.add(chapters.size());
                        break;
                    }
                }
            }

            dataVerifyManager.add(new ListVerify(reponseResult.getData().getChapterCountList(),batchBuyCount,false)
                    .setVerifyContent("验证返回的购买范围正确"));
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getChapterCountList(),null)
                    .setVerifyContent("验证不返回购买范围"), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
