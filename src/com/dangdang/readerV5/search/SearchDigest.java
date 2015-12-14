package com.dangdang.readerV5.search;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.comment.CommentTargetCountDb;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.SearchDigestList;
import com.dangdang.readerV5.reponse.SearchDigestResponse;
import com.dangdang.digital.meta.MediaDigest;

/**
 * Created by cailianjie on 2015-8-4.
 */
public class SearchDigest extends FixtureBase{
	ReponseV2<SearchDigestResponse> jsonResult;
	
	@Override
	public void doWork() throws Exception{
		super.doWork();
		jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<SearchDigestResponse>>(){});
	}
	
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	
        	List<SearchDigestList> actualList = jsonResult.getData().getSearchDigestList();
        	List<String> actualTitleList = new ArrayList<String>();
        	for(int i=0; i<actualList.size(); i++){
        		actualTitleList.add(actualList.get(i).getDigestTitle());
        		String actualDigestId = actualList.get(i).getDigestId();
        		int actualType = actualList.get(i).getDigestType();
        		MediaDigest expectedDigest = MediaDigestDb.getMediaDigest(actualDigestId);
        		//验证card_remark,pic1_path,title,type
        		//dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getDescription(),expectedDigest.getCardRemark()).setVerifyContent("验证Description"));
        		//dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getDigestPic(),expectedDigest.getPic1Path()).setVerifyContent("验证DigestPic"));
        		//dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getDigestTitle(),expectedDigest.getTitle()).setVerifyContent("验证Title"));
        		//dataVerifyManager.add(new ValueVerify<Integer>(actualType,expectedDigest.getType()).setVerifyContent("验证DigestType"));
        		
        		//验证comments praises
        		//表media_digest 类型 1:翻篇儿; 2:抢先读; 3:频道; 4:贴子;5:攻略
        		//表comment_target_count  评论来源（1000,书吧;2000,翻篇,3000:抢先读,4000,频道,7000 攻略）
        		String targetSource="";
        		if(actualType==1) targetSource="2000";
        		else if(actualType==2) targetSource="3000";
        		else if(actualType==3) targetSource="4000";
        		else if(actualType==4) targetSource="1000";
        		else if(actualType==5) targetSource="7000";
        		CommentTargetCount count = CommentTargetCountDb.getCommentTargetCount(actualDigestId, targetSource);
        		if(count==null){
        			dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getComments(),"0").setVerifyContent("验证Comments"));
            		dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getPraises(),"0").setVerifyContent("验证Praises"));
        		}else{
        			dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getComments(),String.valueOf(count.getCommentCount())).setVerifyContent("验证Comments"));
            		dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getPraises(),String.valueOf(count.getPraiseCount())).setVerifyContent("验证Praises"));
        		}

        	}
    	}
        super.dataVerify();
	}
}
