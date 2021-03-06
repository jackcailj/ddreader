package com.dangdang.readerV5.discovery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.readerV5.reponse.DiscoveryHomePage;

/**
 * 发现频道首页接口
 * @author wuhaiyan
 * */
public class GetDiscoveryHomePageInfo extends FixtureBase{
	public ReponseV2<DiscoveryHomePage> getResult(){
		return JSONObject.parseObject(result.toString(), 
				new TypeReference<ReponseV2<DiscoveryHomePage>>(){});
	}
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String fpTime = null;
	String qxdTime = null;
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);				
		if(paramMap.get("lastFPRequestTime")!=null){
			fpTime = paramMap.get("lastFPRequestTime");
			if(fpTime.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")){
		    fpTime = fpTime.replace("T", " ");
		    paramMap.put("lastFPRequestTime",Long.toString(df.parse(fpTime).getTime()));
	}		
			
		}
		if(paramMap.get("lastQXDRequestTime")!=null){
			 qxdTime = paramMap.get("lastQXDRequestTime");
			 if(qxdTime.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")){
				qxdTime = qxdTime.replace("T", " ");
				paramMap.put("lastQXDRequestTime",Long.toString(df.parse(qxdTime).getTime()));
			 }
		}
			
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<DiscoveryHomePage> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){	
			//当前时间到上次更新时间之间的翻篇儿新增记录条数
			List<MediaDigest> digest1 = MediaDigestDb.getNewDigest("1", fpTime);
			
			//当前时间到上次更新时间之间的抢先读新增记录条数
		    List<MediaDigest> digest2 = MediaDigestDb.getNewDigest("2", qxdTime);
			
			dataVerifyManager.add(new ValueVerify<Integer>(digest1.size(), reponseResult.getData().getFpHasNew(), false));
			dataVerifyManager.add(new ValueVerify<Integer>(digest2.size(), reponseResult.getData().getQxdHasNew(), false));			
			super.dataVerify();			
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
