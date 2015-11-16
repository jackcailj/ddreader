package com.dangdang.readerV5.personal_center.bookfriend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Bar;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BookFriendMoments;
import com.dangdang.readerV5.reponse.DigestDetailData;

public class GetMyBookFriendMoments extends FixtureBase{
	ReponseV2<BookFriendMoments> reponseResult;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);				
		if(paramMap.get("sortPage")!=null){
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
////			String sql = "SELECT bar_id, bar_name, member_num FROM bar where bar_name like '%"+paramMap.get("barName")+"%'";
////			List<Bar>  list = DbUtil.selectList(Config.BOOKBARDBConfig, sql, Bar.class);	
//			String sql = "SELECT * FROM `book_firend` where active_user_id="+login.getCustId();
//			List<Map<String,Object>>  list = DbUtil.selectList(Config.UCENTERDBConfig, sql);
//			
//			
//			String sql = "SELECT id,card_title,card_remark,review_cnt,top_cnt FROM `media_digest` where is_show=1 and is_del=0 and "
//					+ "type="+paramMap.get("type")+
//					" and sort_page"+(paramMap.get("act").equals("new")?">":"<")+"'"+paramMap.get("sortPage")
//					+"' and show_start_date<'"+df.format(new Date())
//					+"' ORDER BY sort_page DESC limit "+(paramMap.get("pageSize").isEmpty()?"10":paramMap.get("pageSize"));
//			dataVerifyManager.add(new ListVerify(list, reponseResult.getData().getBarList(),true));
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
	}

}
