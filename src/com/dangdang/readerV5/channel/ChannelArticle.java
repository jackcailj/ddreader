package com.dangdang.readerV5.channel;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.comment.CommentTargetCountDb;
import com.dangdang.db.comment.PraiseInfoDb;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.readerV5.reponse.ArticlePackage;
import com.dangdang.readerV5.reponse.ArticlePackageList;
import com.dangdang.readerV5.reponse.ChannelArticleReponse;

/**
 * 文章列表接口
 * @author guohaiying
 *
 */
public class ChannelArticle extends FixtureBase{	
	ReponseV2<ChannelArticleReponse> jsonResult;
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelArticleReponse>>(){});
    }
	
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	List<String> actualArticleId = new ArrayList<String>();
        	List<ArticlePackageList> articlePackageList = jsonResult.getData().getArticlePackageList();
        	for(int i=0; i<articlePackageList.size(); i++){
        		List<ArticlePackage> articlePackages = articlePackageList.get(i).getArticlePackage();		
        		
        		for(int j=0; j<articlePackages.size(); j++){
        			String custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
        			ArticlePackage article = articlePackages.get(j);
        			String articleId = article.getArticleId();
        			actualArticleId.add(article.getArticleId());
        			
        			//验证articleType, author, description, icon,  title,
        			MediaDigest  expectedMediaDigest = MediaDigestDb.getMediaDigest(articleId);
        			dataVerifyManager.add(new ExpressionVerify(expectedMediaDigest.getAuthor().contains(article.getAuthor())).setVerifyContent("验证Author"));
        			dataVerifyManager.add(new ValueVerify<String>(article.getIcon(), expectedMediaDigest.getPic1Path()).setVerifyContent("验证Icon"));
        			dataVerifyManager.add(new ValueVerify<String>(article.getTitle(), expectedMediaDigest.getCardTitle()).setVerifyContent("验证Title"));
        			dataVerifyManager.add(new ValueVerify<String>(article.getDescription(), expectedMediaDigest.getCardRemark()).setVerifyContent("验证Description"));
        			dataVerifyManager.add(new ValueVerify<String>(article.getArticleType(), String.valueOf(expectedMediaDigest.getType())).setVerifyContent("验证文章类型"));
        			
        			//验证commentNum,topCnt
        			CommentTargetCount count = CommentTargetCountDb.get(articleId);
        			if(count!=null&&count.getPraiseCount()>0)
        				dataVerifyManager.add(new ExpressionVerify(Math.abs(Integer.valueOf(article.getTopCnt())-count.getPraiseCount())<1?true:false).setVerifyContent("验证点赞数"));
        			else
        				dataVerifyManager.add(new ValueVerify<String>(article.getTopCnt(), "0").setVerifyContent("验证点赞数"));
        			if(count!=null)
        				dataVerifyManager.add(new ValueVerify<String>(article.getCommentNum(),String.valueOf(count.getCommentCount())).setVerifyContent("验证评论数"));
        			else
        				dataVerifyManager.add(new ValueVerify<String>(article.getCommentNum(),"0").setVerifyContent("验证评论数"));
        			
        			//验证isPraise
        			if(custId!=null)
        				dataVerifyManager.add(new ValueVerify<Boolean>(article.getIsPraise(), PraiseInfoDb.get(custId, articleId)).setVerifyContent("验证登录用户是否点赞"));
        			else
        				dataVerifyManager.add(new ValueVerify<Boolean>(article.getIsPraise(), false).setVerifyContent("验证点赞"));
        		}
        	}
        		
        	
        }
        super.dataVerify();
    }
    
}
