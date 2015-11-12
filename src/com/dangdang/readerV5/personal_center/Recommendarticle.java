package com.dangdang.readerV5.personal_center;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.comment.CommentTargetCountDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.RecommendArticleResponse;

/**
 * Created by cailianjie on 2015-10-22.
 * Modify by guohaiying 2015-11-6
 */
public class Recommendarticle extends FixtureBase{

	ReponseV2<RecommendArticleResponse> jsonResult;
    
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<RecommendArticleResponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	List<CommentTargetCount> list=CommentTargetCountDb.get("7000","30");
        	List<String> jsonList = new ArrayList<String>(); 
        	List<String> dbList = new ArrayList<String>(); ; 
        	for(int i=0; i<jsonResult.getData().getArticles().size();i++){
        		String tmp=jsonResult.getData().getArticles().get(i).getDigestId();
        		System.out.println("jsonList: "+tmp+"\n");
        		jsonList.add(tmp);
        	}
        	
        	for(int j=0;j<list.size();j++){
        		String tmp=String.valueOf(list.get(j).getTargetId());
        		System.out.println("dbList: "+tmp+"\n");
        		dbList.add(tmp);
        	}        		
        	 
            dataVerifyManager.add(new ExpressionVerify(dbList.contains(jsonList)).setVerifyContent("验证"));
            
        }
        super.dataVerify();
    }

}

