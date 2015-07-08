package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.MediaBought;
import com.dangdang.digital.meta.MediaBoughtDetail;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.GetMyBoughtDetailListReponse;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by cailianjie on 2015-7-8.
 */
public class GetMyBoughtDetailList extends FixtureBase{

    ReponseV2<GetMyBoughtDetailListReponse> reponseResult;

    public GetMyBoughtDetailList(){addAction("getMyBoughtDetailList");}

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }

    public ReponseV2<GetMyBoughtDetailListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<GetMyBoughtDetailListReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if( reponseResult.getStatus().getCode()==0){
            List<MediaBoughtDetail> boughtDetailList= MediaBought.getBoughtDetailList(paramMap.get("boughtId"),
                    paramMap.get("start")==null?0:Integer.parseInt(paramMap.get("start"))
                    ,paramMap.get("end")==null?100000:Integer.parseInt(paramMap.get("end")));

            dataVerifyManager.add(new ListVerify(boughtDetailList,reponseResult.getData().getBoughtDetailList(),true).setVerifyContent("验证返回的购买详情是否正确"));
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData(),null), VerifyResult.FAILED);
        }
        super.dataVerify();
    }
}
