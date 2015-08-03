package com.dangdang.readerV5.discovery;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.DigestChannel;
import com.dangdang.readerV5.reponse.DigestDetail;
import com.dangdang.readerV5.reponse.DigestDetailData;
import com.dangdang.readerV5.reponse.EbookInfo;

/**
 * 精品阅读详情页      action：getDigestDetail
 * @author wuhaiyan 
 */
public class GetDigestDetail  extends FixtureBase {
	String digestId;
	String channelId;
	
	public ReponseV2<DigestDetailData> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<DigestDetailData>>(){});
	}
	
	@Override
	public void  setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);;
		digestId = "";
		channelId = "";
		if(paramMap.get("digestId")!=null&&paramMap.get("digestId").equals("DigestDB")){
			String sql = "SELECT id FROM `media_digest` where media_id is not null and "
					     + "is_show=1 and is_del=0 ORDER BY RAND() limit 1";
			digestId = DbUtil.selectOne(Config.YCDBConfig, sql).get("id").toString();
			paramMap.put("digestId", digestId);
		}
		if(paramMap.get("digestId")!=null&&paramMap.get("digestId").equals("ChannelDB")){
			String sql = "SELECT cd.digest_id, cd.channel_id FROM channel_articles_digest as cd left join "
					     + "`media_digest` as md on md.id=cd.digest_id where md.media_id is not null and "
					     + "md.is_show=1 and md.is_del=0 and cd.status=1 and (md.type=1 or md.type=2) ORDER BY RAND() limit 1";
			Map<String,Object> map = DbUtil.selectOne(Config.YCDBConfig, sql);
			digestId = map.get("digest_id").toString();
			paramMap.put("digestId", digestId);
			if(paramMap.get("ddChannelId")!=null&&paramMap.get("ddChannelId").equals("ChannelDB")){
				channelId = map.get("channel_id").toString();
				paramMap.put("ddChannelId", channelId);
			}
		}		
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<DigestDetailData> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			DigestDetail detail = reponseResult.getData().getDigestDetail();
			String sql = "select is_paper_book, media_id,card_title from media_digest where id="+digestId+" and is_show=1";
			Map<String,Object>  map1 = DbUtil.selectOne(Config.YCDBConfig, sql);
			dataVerifyManager.add(new ValueVerify<String>(detail.getMediaId(), map1.get("media_id").toString()));
			dataVerifyManager.add(new ValueVerify<String>(detail.getCardTitle(), map1.get("card_title").toString()));
			
			//验证ebookInfo
			sql = "select author_penname,cover_pic, title, editor_recommend, product_id "
					+ "from media where media_id="+map1.get("media_id").toString();
			Map<String,Object>  map2 = DbUtil.selectOne(Config.YCDBConfig, sql);
			EbookInfo info = new EbookInfo();
			info.setBookAuthor(map2.get("author_penname").toString());
			info.setBookImgUrl(detail.getEbookInfo().getBookImgUrl());
			info.setBookName(map2.get("title").toString());
			info.setEditorRecommend(map2.get("editor_recommend")!=null?map2.get("editor_recommend").toString():"");
			info.setIsPaperBook(Boolean.valueOf(map1.get("is_paper_book").toString())?1:0);
			info.setProductId(map1.get("media_id").toString());			
			dataVerifyManager.add(new ValueVerify(info, detail.getEbookInfo(), true));
			
			//频道信息
			if(paramMap.get("ddChannelId")!=null&&!(paramMap.get("ddChannelId").isEmpty())){
				sql = "select icon,title,description from channel where "
						+ "channel_id="+map1.get("ddChannelId").toString();
				Map<String,Object>  map3 = DbUtil.selectOne(Config.YCDBConfig, sql);
				DigestChannel channel = new DigestChannel();
				channel.setChannelDesc(map3.get("description").toString());
				channel.setChannelIcon(map3.get("icon").toString());
				channel.setChannelTitle(map3.get("title").toString());
				dataVerifyManager.add(new ValueVerify(channel, reponseResult.getData().getChannel(), true));
			}			
			//验证isPraise
			try{
				sql = "SELECT * from praise_info where target_id="+digestId+" and user_id="+login.getCustId();
				DbUtil.selectOne(Config.BSAECOMMENT, sql);
				dataVerifyManager.add(new ValueVerify<Integer>(1, reponseResult.getData().getDigestDetail().getIsPraise(), false));
			}
			catch(NullPointerException e){
				dataVerifyManager.add(new ValueVerify<Integer>(0, reponseResult.getData().getDigestDetail().getIsPraise(), false));
			}
			
			//验证订阅信息
			try{
				sql = "SELECT * from channel_sub_user where channel_id=18 and cust_id="+login.getCustId();
				Map<String,Object> map4 = DbUtil.selectOne(Config.YCDBConfig, sql);
				dataVerifyManager.add(new ValueVerify<Integer>(Integer.parseInt(map4.get("type").toString()), 
						                                       reponseResult.getData().getSubscribe(), false));
			}
			catch(NullPointerException e){
				dataVerifyManager.add(new ValueVerify<Integer>(0, reponseResult.getData().getSubscribe(), false));
			}
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}

	///media/api2.go?action=getDigestDetail&digestId=1209&token=cc9c1943d250f787a0ff542b42deef5d&returnType=json&deviceType=FP_Android&channelId=61000&clientVersionNo=1.0.0&serverVersionNo=1.0.0&permanentId=20150521110509038381237169605661659&deviceSerialNo=864587026900416&macAddr=c0%3Aee%3Afb%3A04%3A27%3A3e&resolution=1080*1920&clientOs=4.3&platformSource=FP-P&channelType=all&token=cc9c1943d250f787a0ff542b42deef5d 
	//	{"data":{"currentDate":"2015-05-28 19:45:42","digestDetail":{"alreayMark":false,"authorList":[{"authorId":49545,"name":"王潇"}],"cardRemark":"困在一个节点，眼看时间滴答滴答，不能进，不能退，无法施展，无可奈何。","cardTitle":"当我们困在原地","cardType":2,"cardUrl":"/media/api2.go?action=getDigestContentForH5&digestId=1196","digestId":1196,"ebookInfo":{"bookAuthor":"王潇","bookImgUrl":"http://img30.ddimg.cn/imgother10/13/26/1900302340_cover_k_epub.jpg","bookName":"女人明白要趁早之三观易碎","editorRecommend":"是猛药，不是鸡汤！励志榜样王潇全新力作,献给女性的三观重塑读本!","productId":1900302340},"mediaId":1900302340,"mood":5,"pic1Path":"http://img30.ddimg.cn/imgother10/13/26/1900302340_userUpload_1432725701684.jpg","reviewCnt":4,"signList":[{"id":106,"name":"反鸡汤"}]},"systemDate":"1432813542009"},"status":{"code":0},"systemDate":1432813542009}

}

