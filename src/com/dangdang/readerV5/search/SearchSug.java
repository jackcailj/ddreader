package com.dangdang.readerV5.search;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.SearchSugResponse;
import com.dangdang.readerV5.reponse.Sug;

/**
 * 联想提示
 * @author guohaiying
 */
public class SearchSug extends FixtureBase{
	ReponseV2<SearchSugResponse> jsonResult;

    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<SearchSugResponse>>(){});
    }
    
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	List<Sug> sugs = jsonResult.getData().getSugs();
        	dataVerifyManager.add(new ExpressionVerify(sugs.size()>0).setVerifyContent("验证联想提示列表不为空！"));
        }
        super.dataVerify();
    }
}
