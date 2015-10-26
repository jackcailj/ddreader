package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.Media;
import com.dangdang.param.parse.GetProductIdsParse;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.GetEbookOrderFlowV2Reponse;

import java.util.List;


/**
 * Created by cailianjie on 2015-7-13.
 */
public class GetEbookOrderFlowV2 extends FixtureBase{

    ReponseV2<GetEbookOrderFlowV2Reponse> reponseResult;



    public GetEbookOrderFlowV2(){addAction("getEbookOrderFlowV2");}

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }

    public ReponseV2<GetEbookOrderFlowV2Reponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetEbookOrderFlowV2Reponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {

        if(reponseResult.getStatus().getCode()==0){
            List<Media> medias = (List<Media>) VariableStore.get(GetProductIdsParse.MEDIA_VAR);
            Integer payable=0;
            for (Media media : medias){
                if(!media.getUid().startsWith("br")) {
                    payable += media.getPrice();
                }
            }

            dataVerifyManager.add(new ValueVerify<Integer>(payable,reponseResult.getData().getPayable()).setVerifyContent("验证payable是否正确"));
            dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getKey(),null).setVerifyContent("验证key不为空"), VerifyResult.FAILED);

        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData(), null), VerifyResult.FAILED);
        }

        super.dataVerify();
    }
}
