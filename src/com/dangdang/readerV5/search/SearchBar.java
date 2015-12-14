package com.dangdang.readerV5.search;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.BarMember;
import com.dangdang.db.bookbar.BarMemberDb;
import com.dangdang.db.ucenter.LoginRecordDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.SearchBarList;
import com.dangdang.readerV5.reponse.SearchBarResponse;
import com.dangdang.ucenter.meta.LoginRecord;

/**
 * Created by cailianjie on 2015-8-4.
 */
public class SearchBar extends FixtureBase {
	ReponseV2<SearchBarResponse> jsonResult;
	
	@Override
	public void doWork() throws Exception{
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<SearchBarResponse>>(){});
	}

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	List<SearchBarList> actualList = jsonResult.getData().getSearchBarList();
        	for(int i=0; i<actualList.size(); i++){
        		List<BarMember> expectedList = BarMemberDb.getBarRemember(actualList.get(i).getBarId());
        		int memberNum = expectedList.size();
        		dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getBarMembers(), String.valueOf(memberNum)).setVerifyContent("验证BarMembers"));
        		if(memberNum==0)
        			dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getBarOwner(), "吧主宝座空缺").setVerifyContent("验证BarOwner"));
        		else{
        			long custID = expectedList.get(i).getCustId();
        			LoginRecord loginRecord = LoginRecordDb.getLoginRecord(String.valueOf(custID));
        			dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getBarOwner(), loginRecord.getCustNickname()).setVerifyContent("验证BarOwner"));
        		}
        	}
        }
        super.dataVerify();
    }
}
