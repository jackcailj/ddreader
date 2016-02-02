package com.dangdang.readerV5.discovery;

import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.DigestListData;

/**
 * 标签列表页    action：getDigestListBySign
 * @author wuhaiyan 
 */
public class GetDigestListBySign  extends FixtureBase {
	
	ReponseV2<DigestListData> reponseResult;
	String signId;
	String sql;
	String digestSql;
	
	public ReponseV2<DigestListData> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<DigestListData>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);
		Date date = new Date();
		String sql = "SELECT sign_id FROM `media_digest_lable` limit 1";
		signId = DbUtil.selectOne(Config.YCDBConfig, sql).get("sign_id").toString();
		if(paramMap.get("signId")!=null&&paramMap.get("signId").equals("FromDB")){
			paramMap.put("signId", signId);
		}
		if(paramMap.get("createTime")!=null&&paramMap.get("createTime").equals("currentTime")){
			paramMap.put("createTime", ((Long)date.getTime()).toString());
		}
        digestSql = "select id from media_digest where sign_ids like '%"+signId+"%' and is_show=1";
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			Integer itemsCount = reponseResult.getData().getDigestList().size();
			Integer  idCount = DbUtil.selectList(Config.YCDBConfig, digestSql).size();
			int pageSize = Integer.parseInt(paramMap.get("pageSize")==null?"10":(paramMap.get("createTime")==null?paramMap.get("pageSize"):"0"));
			if(idCount >= pageSize){
				dataVerifyManager.add(new ValueVerify<Integer>(itemsCount, pageSize));
			}
			else{
				dataVerifyManager.add(new ValueVerify<Integer>(itemsCount, idCount));
			}
		}		
		super.dataVerify();
		verifyResult(expectedCode);
	}

//	/media/api2.go?pageSize=15&action=getDigestListBySign&signId=106&returnType=json&deviceType=FP_Android
//			&channelId=61000&clientVersionNo=1.0.0&serverVersionNo=1.0.0&permanentId=20150521110509038381237169605661659
//			&deviceSerialNo=864587026900416&macAddr=c0%3Aee%3Afb%3A04%3A27%3A3e&resolution=1080*1920&clientOs=4.3&platformSource=FP-P
//			&channelType=all&token=2aa17a1584abb7bcd8b5037a794176f2 HTTP/1.1 
	
	
	//{"data":{"currentDate":"2015-05-21 15:12:33","digestList":[{"authorList":[{"authorId":49545,"name":"王潇"}],"cardRemark":"原本不可知的未来，经过一个个三小时的播种，可以变得明确、清晰、可期待。","cardTitle":"每天专注三小时","cardType":2,"createTime":1432104645000,"id":1019,"pic1Path":"http://img30.ddimg.cn/imgother10/13/26/1900302340_userUpload_1432104614174.jpg"},{"authorList":[{"authorId":57036,"name":"古典"}],"cardRemark":"那些希望通过换地方来寻找公平的人，就像泰坦尼克号上的落海者，他们从一个船舱，逃到另一个船舱，慢慢发现这个地方也在下沉。","cardTitle":"这个世界不公平，出国会让事情变好吗？","cardType":1,"createTime":1432031695000,"id":1007,"pic1Path":"http://img34.ddimg.cn/imgother10/81/31/1900353294_userUpload_1432031484804.jpg"}],"hasNext":true,"systemDate":"1432192353357"},"status":{"code":0},"systemDate":1432192353357}
	
}
