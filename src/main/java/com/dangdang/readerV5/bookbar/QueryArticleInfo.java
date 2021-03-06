package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.ArticleItems;
import com.dangdang.config.Config;
import com.dangdang.db.bookbar.ArticleItemsDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.Article;
import com.dangdang.readerV5.reponse.ArticleInfo;
import com.dangdang.readerV5.reponse.UserBaseInfo;

/**
 * 帖子详情接口
 * @author wuhaiyan
 */
public class QueryArticleInfo  extends FixtureBase{
	ReponseV2<ArticleInfo>   reponseResult;
	Map<String,Object> map = new HashMap<String,Object>();
	String mediaDigestId = null;
	 
	public ReponseV2<ArticleInfo> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<ArticleInfo>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		try{
			super.setParameters(params);
			mediaDigestId = paramMap.get("mediaDigestId");
	    }
	       catch(Exception e){
		   throw new Exception("数据表中不存在符合条件的帖子id，此用例不执行");
	    }
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			Article article = reponseResult.getData().getArticle();
			String sql ="SELECT * FROM `media_digest` where id="+mediaDigestId+" and is_show=1";
			Map<String,Object> digest = DbUtil.selectOne(Config.YCDBConfig, sql);
			Map<String, Object> userInfo = new HashMap<String, Object>();
			try{
				userInfo = DbUtil.selectOne(Config.UCENTERDBConfig, 
						"select cust_nickname, cust_img from login_record where cust_id="+map.get("cust_id").toString());
			}
			catch(Exception e){
				e.printStackTrace();
				userInfo = null;
			}
			
			List<String> list1 = new ArrayList<String>();	
			List<String> list2 = new ArrayList<String>();	
			list1.add(digest.get("bar_id").toString());
			list1.add(userInfo!=null?userInfo.get("cust_nickname").toString().split("@")[0]:null);
			list1.add(mediaDigestId);
			list1.add(!(digest.get("title")==null)?digest.get("title").toString():null);
			
			try{
				CommentTargetCount count = DbUtil.selectOne(Config.BSAECOMMENT, 
						"SELECT * FROM `comment_target_count` where target_id="+mediaDigestId, CommentTargetCount.class);
				list1.add(Integer.toString(count.getCommentCount()));
				list1.add(Integer.toString(count.getPraiseCount()));
			}
			catch(Exception e){
				//没有点赞和评论时，得到null，所以catch 空指针异常
				e.printStackTrace();
				list1.add("0");
				list1.add("0");
			}			
			list2.add(article.getBarId());
			String nickName = null;
			if(userInfo!=null){
				if(paramMap.get("action").equals("queryArticleInfo")){
					nickName = article.getNickName();
				}
				else{
					//return the UserBaseInfo if action is queryArticleInfoV2 in 5.2 version
					nickName = article.getUserBaseInfo().getNickName();
				}
			}			
			list2.add(nickName);
			list2.add(article.getMediaDigestId());
			list2.add(article.getTitle()!=null?article.getTitle().toString():null);		
			list2.add(article.getCommentNum());
			list2.add(article.getPraiseNum());
						
			dataVerifyManager.add(new ValueVerify(list2, list1,false));
			//5.3 验证吧主头衔
			//在发帖，删帖，加入吧，退出吧，用户升级，降级的时候，这些动作会触发代码，更新吧主的头衔
			//更新的数据存在login_record表里的bar_owner_level字段。
			//下边验证level有时候失败，可能是因为，没有上述动作触发数据更新。
			//在这儿用getBarOwnerLevel方法验证，是为了验证规则的正确性，其他接口是从数据表取bar_owner_level值来验证的。
			BarCommon common = new BarCommon();
			String custId = article.getUserBaseInfo().getPubCustId();
			//int level = common.getBarOwnerLevel(custId);
			//改成从数据表取bar_owner_level值来验证的
			String level = BarCommon.getBarOwnerLevelFromDb(custId);
			dataVerifyManager.add(new ValueVerify<Integer>(
					article.getUserBaseInfo().getBarOwnerLevel(), Integer.parseInt(level),false));
			//验证投票贴信息
			String type = digest.get("type").toString();
			if(type.equals("31")||type.equals("32")){
				List<ArticleItems> items = ArticleItemsDb.getArticleItemsId(mediaDigestId);
				if(items!=null){
					int voteCount = 0;
					for(int i=0; i<items.size(); i++){
						voteCount = voteCount+items.get(i).getVoteCount();
					}
					dataVerifyManager.add(new ValueVerify<Integer>(
							article.getVoteInfo().getItems().size(), items.size(),false));
					dataVerifyManager.add(new ValueVerify<Integer>(
							article.getVoteInfo().getVoteCount(), voteCount,false));			
				}		
			}				
			super.dataVerify();			
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
