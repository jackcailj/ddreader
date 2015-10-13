package com.dangdang.readerV5.present_book;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.im.IMDB;
import com.dangdang.readerV5.reponse.GetMyGiveListData;
import com.dangdang.readerV5.reponse.GetMyGiveListReponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-9-6.
 */
public class GetMyGiveList extends FixtureBase{

    ReponseV2<GetMyGiveListReponse> reponseResult;

    public GetMyGiveList(){}


    public ReponseV2<GetMyGiveListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();
        reponseResult= JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<GetMyGiveListReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            List<GetMyGiveListData> myGiveListDatas= IMDB.getMyGiveList(login.getCustId());
            if(myGiveListDatas.size()>0) {
                GetMyGiveListReponse getMyGiveListReponse = new GetMyGiveListReponse();
                getMyGiveListReponse.setAmount(myGiveListDatas.size());
                getMyGiveListReponse.setSaleList(myGiveListDatas);

                dataVerifyManager.add(new ValueVerify<GetMyGiveListReponse>(reponseResult.getData(), getMyGiveListReponse, true));
            }
            else{
                dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getSaleList(), null));
            }
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getSaleList(), null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
