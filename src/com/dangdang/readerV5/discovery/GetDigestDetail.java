package com.dangdang.readerV5.discovery;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.DigestDetail;
import com.dangdang.readerV5.reponse.DigestDetailData;
import com.dangdang.readerV5.reponse.DigestListData;

/**
 * 精品阅读详情页      action：getDigestDetail
 * @author wuhaiyan 
 */
public class GetDigestDetail  extends FunctionalBaseEx {
	
	ReponseV2<DigestDetailData> reponseResult;
	String digestId;
	String sql;
	String digestSql;

	public GetDigestDetail(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getDigestDetail");
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<DigestDetailData>>(){});
	}
	
	public ReponseV2<DigestDetailData> getResult(){
		return reponseResult;
	}
	
	@Override
	protected void parseParam() throws Exception {
		super.parseParam();
		String sql = "SELECT id FROM `media_digest` ORDER BY rand() limit 1";
		digestId = DbUtil.selectOne(Config.YCDBConfig, sql).get("id").toString();
		if(paramMap.get("digestId").equals("FromDB")){
			paramMap.put("digestId", digestId);
		}
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		login = parseResult.getLogin();
		
	}
	
	@Override
	protected void dataVerify() throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			DigestDetail detail = reponseResult.getData().getDigestDetail();
			digestSql = "select author, media_id from media_digest where id="+digestId+" and is_show=1";
			Map<String,Object>  list = DbUtil.selectList(Config.YCDBConfig, digestSql).get(0);;
			dataVerifyManager.add(new ValueVerify<String>(detail.getAuthorList().get(0).getAuthorId()+":"
			                                              +detail.getAuthorList().get(0).getName(), list.get("author").toString()));
			dataVerifyManager.add(new ValueVerify<String>(detail.getMediaId(), list.get("media_id").toString()));
		}		
		super.dataVerify();
	}

	///media/api2.go?action=getDigestDetail&digestId=1209&token=cc9c1943d250f787a0ff542b42deef5d&returnType=json&deviceType=FP_Android&channelId=61000&clientVersionNo=1.0.0&serverVersionNo=1.0.0&permanentId=20150521110509038381237169605661659&deviceSerialNo=864587026900416&macAddr=c0%3Aee%3Afb%3A04%3A27%3A3e&resolution=1080*1920&clientOs=4.3&platformSource=FP-P&channelType=all&token=cc9c1943d250f787a0ff542b42deef5d 
	//	{"data":{"currentDate":"2015-05-28 19:45:42","digestDetail":{"alreayMark":false,"authorList":[{"authorId":49545,"name":"王潇"}],"cardRemark":"困在一个节点，眼看时间滴答滴答，不能进，不能退，无法施展，无可奈何。","cardTitle":"当我们困在原地","cardType":2,"cardUrl":"/media/api2.go?action=getDigestContentForH5&digestId=1196","digestId":1196,"ebookInfo":{"bookAuthor":"王潇","bookImgUrl":"http://img30.ddimg.cn/imgother10/13/26/1900302340_cover_k_epub.jpg","bookName":"女人明白要趁早之三观易碎","editorRecommend":"是猛药，不是鸡汤！励志榜样王潇全新力作,献给女性的三观重塑读本!","productId":1900302340},"mediaId":1900302340,"mood":5,"pic1Path":"http://img30.ddimg.cn/imgother10/13/26/1900302340_userUpload_1432725701684.jpg","reviewCnt":4,"signList":[{"id":106,"name":"反鸡汤"}]},"systemDate":"1432813542009"},"status":{"code":0},"systemDate":1432813542009}

}

