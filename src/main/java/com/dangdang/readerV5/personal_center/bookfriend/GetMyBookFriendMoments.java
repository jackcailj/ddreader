package com.dangdang.readerV5.personal_center.bookfriend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.readerV5.reponse.BookFriendMoment;
import com.dangdang.readerV5.reponse.BookFriendMoments;

public class GetMyBookFriendMoments extends FixtureBase{
	ReponseV2<BookFriendMoments> reponseResult;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);				
		if(paramMap.get("crateDateLong")!=null){
			String time = paramMap.get("crateDateLong");
			if(time.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")){
			    time = time.replace("T", " ");
		        paramMap.put("crateDateLong",Long.toString(df.parse(time).getTime()));
	        }	
		}
		String value = paramMap.get("acter");
		paramMap.remove("acter");
		paramMap.put("act", value);
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BookFriendMoments>>(){});
		if(reponseResult.getStatus().getCode() == 0){
			String limit = paramMap.get("pageSize")==null?"10":paramMap.get("pageSize");
			String date = (paramMap.get("act").equals("new")?">":"<")+"'"+(paramMap.get("createDateLong")!=null?paramMap.get("createDateLong"):"0")+"'";
			List<Map<String, Object>> digest = MediaDigestDb.getDigestOfBookFriend(login.getCustId(), limit, date);
			List<BookFriendMoment> response = reponseResult.getData().getBookFriendMoments();
			List<String> list1 = new ArrayList<String>();
			List<String> list2 = new ArrayList<String>();
			for(int i=0; i<digest.size(); i++){
				list2.add(digest.get(i).get("id").toString());
				list2.add(digest.get(i).get("type").toString());
				list1.add(response.get(i).getDigestId());
				list1.add(response.get(i).getType());
			}
			dataVerifyManager.add(new ListVerify(list1, list2,false));
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}	

}
