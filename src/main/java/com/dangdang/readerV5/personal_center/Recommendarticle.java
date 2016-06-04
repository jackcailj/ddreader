package com.dangdang.readerV5.personal_center;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.comment.BrowseInfoDb;
import com.dangdang.db.comment.CommentTargetCountDb;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.RecommendArticleResponse;

/**
 * Created by cailianjie on 2015-10-22.
 * Modify by guohaiying 2015-11-6
 */
public class Recommendarticle extends FixtureBase{
	ReponseV2<RecommendArticleResponse> jsonResult;
	String id="";
    
	@Override
	public void set(String name, String value) throws Exception {
		super.set(name, value);
		id = paramMap.get("id");
	}
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<RecommendArticleResponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	String digest_id = paramMap.get("id");
        	int type=0;
        	if(id.contains("Strategy")){//验证攻略
        		type=5;
        	}else if(id.contains("#GetDigestId#DigestId")){//验证频道文章
        		type=3;
        	}
        	//验证频道文章
        	//验证返回的type=3       	
        	//List<CommentTargetCount> list=CommentTargetCountDb.get2(paramMap.get("id"));
        	List<String> list = BrowseInfoDb.get(digest_id,type,50);

        	List<String> jsonList = new ArrayList<String>(); 
        	List<String> dbAllList =  BrowseInfoDb.get(digest_id, type,500);;
        	for(int i=0; i<jsonResult.getData().getArticles().size(); i++){
        		String digestId = jsonResult.getData().getArticles().get(i).getDigestId();
        		System.out.println("jsonList: " + digestId);
        		jsonList.add(digestId);
        		dataVerifyManager.add(new ValueVerify(jsonResult.getData().getArticles().get(i).getDigestType(), String.valueOf(type)).setVerifyContent("验证返回文章的type="+type));
        	}        	
        	for(int i=0; i<list.size(); i++){
        		System.out.println("dbList: "+ list.get(i));
        	}        	
        	dataVerifyManager.add(new ExpressionVerify(list.containsAll(jsonList)).setVerifyContent("验证"));
        	dataVerifyManager.add(new ExpressionVerify(dbAllList.containsAll(jsonList)).setVerifyContent("验证all"));      

        }
        super.dataVerify();
        }

}

