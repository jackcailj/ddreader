package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.GetPostListReponse;

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
}
