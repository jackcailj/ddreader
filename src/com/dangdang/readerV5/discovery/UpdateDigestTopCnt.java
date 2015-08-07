package com.dangdang.readerV5.discovery;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.RecordVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.reponse.ReponseV2Base;
import com.dangdang.readerV5.reponse.BarListResponse;

public class UpdateDigestTopCnt extends FixtureBase{
	 
	 public ReponseV2Base getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BarListResponse>>(){});
	 }
	 String digestId;
	 int topCnt;
	 
	 @Override
	 public void setParameters(Map<String, String> params) throws Exception {
			super.setParameters(params);	
			String praiseList="(";
			digestId = null;
			topCnt = 0;
			if(paramMap.get("token")!=null&&!(paramMap.get("token").isEmpty())){
				List<Map<String, Object>> list = DbUtil.selectList(Config.BSAECOMMENT,
						"SELECT target_id from praise_info where user_id="+login.getCustId());
				for(int i=0; i<list.size(); i++){
					praiseList = praiseList + list.get(i).get("target_id").toString()+",";
				}
				praiseList = praiseList.substring(0, praiseList.lastIndexOf(","));
				praiseList = praiseList+")";				
			}
			if(paramMap.get("digestId")!=null&&paramMap.get("digestId").equalsIgnoreCase("FromDB")){
				String sql = "select id, top_cnt from media_digest where is_show=1 and (type=1 or type=2) "
					     + "and is_del=0 and last_update_date>'0' and id not in "
						   + praiseList + " ORDER BY RAND() limit 1";
				Map<String, Object> map = DbUtil.selectOne(Config.YCDBConfig, sql);
				digestId = map.get("id").toString();	
				topCnt = Integer.parseInt(map.get("top_cnt")==null?"0":map.get("top_cnt").toString());	
				paramMap.put("digestId",digestId);
			}
			if(paramMap.get("digestId")!=null&&paramMap.get("digestId").equalsIgnoreCase("repeat")){
				String sql = "select id from media_digest where is_show=1 and (type=1 or type=2) "
					     + "and is_del=0 and last_update_date>'0' and id in "
						   + praiseList + " ORDER BY RAND() limit 1";
				digestId = DbUtil.selectOne(Config.YCDBConfig, sql).get("id").toString();	
				paramMap.put("digestId",digestId);
			}			
	  	}
	 
	 @Override
	 public void dataVerify(String expectedCode) throws Exception {
		    ReponseV2Base reponseResult = getResult();
			if(reponseResult.getStatus().getCode() == 0){
				//点赞数量增1
				String sql = "select top_cnt from media_digest where id="+digestId;
				int cnt = Integer.parseInt(DbUtil.selectOne(Config.YCDBConfig, sql).get("top_cnt").toString());
				dataVerifyManager.add(new ValueVerify<Integer>(topCnt+1, cnt,false));
				//用户点赞记录添加到praise_info
				sql = "SELECT * from praise_info where target_id="+digestId+" and user_id="+login.getCustId();
			    dataVerifyManager.add(new RecordVerify(Config.BSAECOMMENT, sql));
			    super.dataVerify();
			}	
			else{
				dataVerifyResult = false;
				//TODO will add verify
			}
			verifyResult(expectedCode);
		}
}
