package com.dangdang.readerV5.personal_center;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.comment.CommentTargetCountDb;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
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
	public void set(String name, String value){
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
        	if(id.contains("Strategy")){//验证攻略
        		List<CommentTargetCount> list=CommentTargetCountDb.get("7000","30");
        		List<String> jsonList = new ArrayList<String>(); 
        		List<String> dbList = new ArrayList<String>();  
        		List<String> dbAllList = MediaDigestDb.getDigest(5);
        		for(int i=0; i<jsonResult.getData().getArticles().size();i++){
        			String tmp=jsonResult.getData().getArticles().get(i).getDigestId();
        			System.out.println("jsonList: "+tmp+"\n");
        			jsonList.add(tmp);
        			dataVerifyManager.add(new ValueVerify(jsonResult.getData().getArticles().get(i).getDigestType(), "5").setVerifyContent("验证返回攻略的type=5"));
        		}       	
        		for(int j=0;j<list.size();j++){
        			String tmp=String.valueOf(list.get(j).getTargetId());
        			System.out.println("dbList: "+tmp+"\n");
        			dbList.add(tmp);
        		}        		        	 
        		//dataVerifyManager.add(new ExpressionVerify(dbList.containsAll(jsonList)).setVerifyContent("验证"));
        		dataVerifyManager.add(new ExpressionVerify(dbAllList.containsAll(jsonList)).setVerifyContent("验证all"));
        	}
        	if(id.contains("#GetDigestId#DigestId")){//验证频道文章
        		//验证频道文章
        		//验证返回的type=3       	
        		List<CommentTargetCount> list=CommentTargetCountDb.get2(paramMap.get("id"));
        		List<String> jsonList = new ArrayList<String>(); 
        		List<String> dbList = new ArrayList<String>();  
        		List<String> dbAllList = MediaDigestDb.getDigest(3);
        		for(int i=0; i<jsonResult.getData().getArticles().size(); i++){
        			String digestId = jsonResult.getData().getArticles().get(i).getDigestId();
        			System.out.println("jsonList: " + digestId);
        			jsonList.add(digestId);
        			dataVerifyManager.add(new ValueVerify(jsonResult.getData().getArticles().get(i).getDigestType(), "3").setVerifyContent("验证返回文章的type=3"));
        		}        	
        		for(int i=0; i<list.size(); i++){
        			String tmp = String.valueOf(list.get(i).getTargetId());
        			System.out.println("dbList: "+ tmp);
        			dbList.add(tmp);
        		}        	
        		//dataVerifyManager.add(new ExpressionVerify(dbList.containsAll(jsonList)).setVerifyContent("验证"));
        		dataVerifyManager.add(new ExpressionVerify(dbAllList.containsAll(jsonList)).setVerifyContent("验证all"));      
        	}
        }
        super.dataVerify();
        }

}

