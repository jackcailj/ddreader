package com.dangdang.readerV5.discovery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
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
					     + "is_show=1 and is_del=0 and (type=1 or type=2) ORDER BY RAND() limit 1";
			digestId = DbUtil.selectOne(Config.YCDBConfig, sql).get("id").toString();
			paramMap.put("digestId", digestId);
		}
		if(paramMap.get("digestId")!=null&&paramMap.get("digestId").equals("ChannelDB")){
			String sql = "SELECT cd.digest_id, cd.channel_id FROM channel_articles_digest as cd left join "
					     + "`media_digest` as md on md.id=cd.digest_id where md.media_id is not null and "
					     + "md.is_show=1 and md.is_del=0 and cd.status=1 ORDER BY RAND() limit 1";
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
			
			try{
				Map<String,Object>  map2 = new HashMap<String,Object>();
				List<String> list1 = new ArrayList<String>();
				List<String> list2 = new ArrayList<String>();
				if(map1.get("is_paper_book").toString().equals("false")){
					sql = "select author_penname,cover_pic, title, editor_recommend, product_id "
							+ "from media where media_id="+map1.get("media_id").toString();
					map2 = DbUtil.selectOne(Config.YCDBConfig, sql);
					list1.add(map2.get("author_penname").toString());
					list1.add(map2.get("title").toString());
					list1.add("0");
					list1.add(map1.get("media_id").toString());
				//	list1.add(map2.get("cover_pic").toString());
				//	list1.add(map2.get("editor_recommend")!=null?map2.get("editor_recommend").toString():"");
				}
				else{
					sql = "select book_author, product_name, product_id "
							+ "from bar_product_info where product_id="+map1.get("media_id").toString();
					map2 = DbUtil.selectOne(Config.BOOKBARDBConfig, sql);
					list1.add(map2.get("book_author").toString());
					list1.add(map2.get("product_name").toString());
					list1.add("1");
					list1.add(map2.get("product_id").toString());
				//	list1.add(map2.get("cover_pic").toString());
				//	list1.add(map2.get("editor_recommend")!=null?map2.get("editor_recommend").toString():"");
				}
				list2.add(detail.getEbookInfo().getBookAuthor());
				list2.add(detail.getEbookInfo().getBookName());
				list2.add(Integer.toString(detail.getEbookInfo().getIsPaperBook()));
				list2.add(detail.getEbookInfo().getProductId());
				//list2.add(detail.getEbookInfo().getBookImgUrl());
				//list2.add(detail.getEbookInfo().getEditorRecommend());
				dataVerifyManager.add(new ListVerify(list1, list2, false));				
			}
			catch(Exception e){
				if(e.getMessage().contains("没有返回结果")){
					dataVerifyManager.add(new ValueVerify(null, detail.getEbookInfo().getProductId(), false));
				}
			}			
			
			//频道信息
			if(paramMap.get("ddChannelId")!=null&&!(paramMap.get("ddChannelId").isEmpty())){
				sql = "select icon,title,description from channel where "
						+ "channel_id="+paramMap.get("ddChannelId");
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
			catch(Exception e){
				dataVerifyManager.add(new ValueVerify<Integer>(0, reponseResult.getData().getDigestDetail().getIsPraise(), false));
			}
			
			//验证订阅信息
			try{
				sql = "SELECT * from channel_sub_user where channel_id=18 and cust_id="+login.getCustId();
				Map<String,Object> map4 = DbUtil.selectOne(Config.YCDBConfig, sql);
				dataVerifyManager.add(new ValueVerify<Integer>(Integer.parseInt(map4.get("type").toString()), 
						                                       reponseResult.getData().getSubscribe(), false));
			}
			catch(Exception e){
				dataVerifyManager.add(new ValueVerify<Integer>(0, reponseResult.getData().getSubscribe(), false));
			}
			
			//点赞数量，评论数量
			int topCnt = 0;
			int reviewCnt = 0;
			try{
				sql = "SELECT * from comment_target_count where target_id="+digestId;
				CommentTargetCount count = DbUtil.selectOne(Config.BSAECOMMENT, sql, CommentTargetCount.class);
				topCnt = count.getPraiseCount();
				reviewCnt = count.getCommentCount();				
			}
			catch(Exception e){
				//没有点赞和评论时，得到null，所以catch 空指针异常
				e.printStackTrace();
			}	
			dataVerifyManager.add(new ValueVerify<Integer>(topCnt,
		              reponseResult.getData().getDigestDetail().getTopCnt(), false));	
            dataVerifyManager.add(new ValueVerify<Integer>(reviewCnt,
                    reponseResult.getData().getDigestDetail().getReviewCnt(), false));	

			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}

}

