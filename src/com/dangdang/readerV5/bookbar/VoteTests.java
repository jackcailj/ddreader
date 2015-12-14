package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.ArticleItems;
import com.dangdang.db.bookbar.ArticleItemsDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.Data;

public class VoteTests extends FixtureBase {
	static String digestId = null;
	List<Integer> orgCount;
	String ids = "";
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);
		if (paramMap.get("itemsIds")!=null&&!(paramMap.get("itemsIds").isEmpty())){
			String[] itemsIds = paramMap.get("itemsIds").split(",");
			int count = Integer.parseInt(itemsIds[1]);
			List<ArticleItems> items;
			if(login!=null){
				String custId = login.getCustId();
				if(itemsIds[0].equalsIgnoreCase("voted")){
					items = ArticleItemsDb.getVotedItemsIdByUser(custId);
					paramMap.put("mediaDigestId", Long.toString(items.get(0).getMediaDigestId()));		
				}
				else{
					items = ArticleItemsDb.getNoVotedItemsIdByUser(custId);
				}				
			}
			else{
				items = ArticleItemsDb.getArticleItemsId(count);
			}
			ids = "";	
			orgCount =  new ArrayList<Integer>();
        	for(int i=0; i<count; i++){        		
        		ids = ids+items.get(i).getArticleItemsId()+","; 
        		orgCount.add(items.get(i).getVoteCount());	
        	}
        	ids = ids+";";
        	ids = ids.replace(",;","");
        	paramMap.put("itemsIds",ids);	
        	paramMap.put("mediaDigestId", Long.toString(items.get(0).getMediaDigestId()));
		}
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<Data> reponse=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<Data>>(){});
		if(reponse.getStatus().getCode() == 0){
			List<Integer> newCount =  new ArrayList<Integer>();
			String[] items = ids.split(",");
			for(String id:items){
				newCount.add(ArticleItemsDb.getOneArticleItemsId(id).getVoteCount()-1);
			}	
			dataVerifyManager.add(new ListVerify(orgCount, newCount, false));
		    super.dataVerify();			
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}

}
