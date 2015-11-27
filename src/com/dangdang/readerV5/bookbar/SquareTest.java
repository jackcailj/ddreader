package com.dangdang.readerV5.bookbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Article;
import com.dangdang.bookbar.meta.Bar;
import com.dangdang.bookbar.meta.BarModule;
import com.dangdang.bookbar.meta.BarModuleContent;
import com.dangdang.bookbar.meta.BarModuleTag;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.ArticleContent;
import com.dangdang.readerV5.reponse.BarContent;
import com.dangdang.readerV5.reponse.Module;
import com.dangdang.readerV5.reponse.SquareInfo;
import com.dangdang.readerV5.reponse.SquareInfoData;
import com.dangdang.readerV5.reponse.TagContent;
/**
 * 书吧广场接口
 * @author wuhaiyan
 * 
 * bar的状态是 待审核或者通过，这两个状态下都会在客户端显示，预审核的显示默认的吧简介和图片
 */
public class SquareTest extends FixtureBase{
	ReponseV2<SquareInfoData>   reponseResult;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	String defaultDesc = "在人生的道路上，当你的希望一个个落空的时候，你也要坚定，要沉着。";
	 
	public ReponseV2<SquareInfoData> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<SquareInfoData>>(){});
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			if(paramMap.get("moduleLocation").equals("square")){
				verifySquareLocationInfo();
			}
			else{
				verifyHotArticleLocationInfo();
			}
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
		
	}
	
	public void verifySquareLocationInfo() throws Exception{
		List<SquareInfo> squareInfoOfDB = new ArrayList<SquareInfo>();
		//type: 类型（1.吧模块 2.帖子模块 3.标签模块）
		//status: 状态（1.显示 2.屏蔽 3.已删除）
		//查找广场显示的模块信息，在客户端会以权重大小为顺序排列，权重最大的排在第一个
		String sql = "select * from bar_module where status=1 and (type=1 or type=3) "
				   + "and (device_type='all' or device_type='"+paramMap.get("deviceType")+"') "
				   + "and module_order>0 ORDER BY module_order DESC, templet_no DESC limit 10";
		List<BarModule> bModuleList = DbUtil.selectList(Config.BOOKBARDBConfig, sql, BarModule.class);
		for(int h=0,i=0; i< bModuleList.size(); i++,h++){
			SquareInfo info = new SquareInfo();
			Module moduleOfDB = new Module();
			int moduleSize = 0;
			List<BarContent> bContentListOfDB = new ArrayList<BarContent>();
			List<TagContent> tContentListOfDB = new ArrayList<TagContent>();
			moduleOfDB.setBarModuleId(bModuleList.get(i).getBarModuleId().toString());
			moduleOfDB.setModuleName(bModuleList.get(i).getModuleName().toString());
			moduleOfDB.setShowNum(bModuleList.get(i).getShowNum().toString());
			moduleOfDB.setType(bModuleList.get(i).getType().toString());
			//type: 类型（1.吧模块 2.帖子模块 3.标签模块）
			if(bModuleList.get(i).getType().toString().equals("1")){	
				moduleOfDB.setTemplateNo(bModuleList.get(i).getTempletNo().toString());
				// 查某模块下展示的内容(status为1且end_date大于当前日期)，在客户端会以权重大小为顺序排列，权重最大的排在第一个
				sql = "SELECT * FROM `bar_module_content` where module_tag_id="+bModuleList.get(i).getBarModuleId().toString()+" "
						+ "and `status`=1 and fk_type=1 and end_date > CURDATE() ORDER BY content_order DESC";
				List<BarModuleContent> bCotentList = DbUtil.selectList(Config.BOOKBARDBConfig, sql, BarModuleContent.class);
				//showNum是该模块要显示的吧的数量
				int showNum = Integer.parseInt(bModuleList.get(i).getShowNum().toString());
				if(showNum > bCotentList.size()){
					moduleSize = bCotentList.size();
				}
				else{
					moduleSize = showNum;
				}
				if(moduleSize==0){
					h--;
				}
				for(int j=0; j<moduleSize; j++){
					//bar的状态是 待审核或者通过，这两个状态下都会在客户端显示，预审核的显示默认的吧简介和图片
					//bar_status(1.待审核，2.通过，3.干预审核，4.下架)
					logger.info("i is "+i+", j is "+j);
					sql = "select * from bar where bar_status!=4 and bar_id ="+bCotentList.get(j).getContentId();
					Bar bar = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, Bar.class);
					BarContent bContentOfDB = new BarContent();
					bContentOfDB.setArticleNum(bar.getArticleNum().toString());
					bContentOfDB.setBarDesc(bar.getBarDesc().toString().isEmpty()?
							defaultDesc:bar.getBarDesc().toString());
					bContentOfDB.setBarId(bar.getBarId().toString());					
					if(bar.getBarImgUrl()!=null){
						String str = reponseResult.getData().getSquareInfo().get(h).getBarContent().get(j).getBarImgUrl();
						str	= str!=null?str.replaceAll("[._][a-z]\\.", "."):null;
						reponseResult.getData().getSquareInfo().get(h).getBarContent().get(j).setBarImgUrl(str);
						//有时数据表里有img url的值，但接口不一定返回barImgUrl字段
						bContentOfDB.setBarImgUrl(str!=null?bar.getBarImgUrl().toString():null);
					}
					bContentOfDB.setBarName(bar.getBarName().toString());
					bContentOfDB.setMemberNum(bar.getMemberNum().toString());
					if(bCotentList.get(j).getRecommendReason()!=null&&!(bCotentList.get(j).getRecommendReason().isEmpty())){
						bContentOfDB.setRecommendReason(bCotentList.get(j).getRecommendReason().toString());
					}
					bContentListOfDB.add(bContentOfDB);	
				}
			}
			if(bModuleList.get(i).getType().toString().equals("3")){
				// 查找标签信息(status为1且end_date大于当前日期), 在客户端会以权重大小为顺序排列，权重最大的排在第一个
				// tag_type 标签类型（1.吧标签 2.帖子标签 3.单品标签）
				sql = "SELECT * FROM `bar_module_tag` where bar_module_id="+bModuleList.get(i).getBarModuleId().toString()+" and "
						+ "status=1 and end_date > CURDATE() ORDER BY sort DESC limit 9";
				List<BarModuleTag> tCotentList = DbUtil.selectList(Config.BOOKBARDBConfig, sql, BarModuleTag.class);
				for(int k=0; k<tCotentList.size(); k++){	
					logger.info("i is "+i+", k is "+k);
					TagContent tContentOfDB = new TagContent();
					tContentOfDB.setBarModuleId(Long.toString(tCotentList.get(k).getBarModuleId()));
					tContentOfDB.setBarModuleTagId(Long.toString(tCotentList.get(k).getBarModuleTagId()));
					tContentOfDB.setBeginDate(Long.toString(tCotentList.get(k).getBeginDate().getTime()));
					tContentOfDB.setEndDate(Long.toString(tCotentList.get(k).getEndDate().getTime()));
					tContentOfDB.setSort(tCotentList.get(k).getSort().toString());
					tContentOfDB.setStatus(Integer.toString(tCotentList.get(k).getStatus()));
					tContentOfDB.setTagName(tCotentList.get(k).getTagName());
					tContentOfDB.setTagType(Integer.toString(tCotentList.get(k).getTagType()));
					tContentListOfDB.add(tContentOfDB);
				}
			}	
			if(moduleSize!=0){
				dataVerifyManager.add(new ValueVerify(reponseResult.getData().getSquareInfo().get(h).getModule(),moduleOfDB, true));
			}
			if(bContentListOfDB.size()!=0){
				dataVerifyManager.add(new ListVerify(reponseResult.getData().getSquareInfo().get(h).getBarContent(),bContentListOfDB, true));
			}
			if(tContentListOfDB.size()!=0){
				dataVerifyManager.add(new ListVerify(reponseResult.getData().getSquareInfo().get(h).getTagContent(), tContentListOfDB, true));
			}
		}
		//dataVerifyManager.add(new ListVerify(squareInfoOfDB, reponseResult.getData().getSquareInfo(),true));
		super.dataVerify();
	}
	
	public void verifyHotArticleLocationInfo() throws Exception{
		List<SquareInfo> squareInfoOfDB = new ArrayList<SquareInfo>();
		//type: 类型（1.吧模块 2.帖子模块 3.标签模块）
		//status: 状态（1.显示 2.屏蔽 3.已删除）
		//查找广场显示的热帖信息，在客户端会以权重大小为顺序排列，权重最大的排在第一个
		String sql = "select * from bar_module where status=1 and type=2 and module_order!=0 ORDER BY module_order DESC";
		List<BarModule> bModuleList = DbUtil.selectList(Config.BOOKBARDBConfig, sql, BarModule.class);
		for(int l=0,i=0; i< bModuleList.size(); i++,l++){
			SquareInfo info = new SquareInfo();
			Module moduleOfDB = new Module();
			moduleOfDB.setBarModuleId(bModuleList.get(i).getBarModuleId().toString());
			moduleOfDB.setModuleName(bModuleList.get(i).getModuleName().toString());
			moduleOfDB.setShowNum(bModuleList.get(i).getShowNum().toString());
			moduleOfDB.setType(bModuleList.get(i).getType().toString());
			List<ArticleContent> aContentListOfDB = new ArrayList<ArticleContent>();
			// 查某模块下展示的内容(status为1且end_date大于当前日期)，在客户端会以权重大小为顺序排列，权重最大的排在第一个
			sql = "SELECT * FROM `bar_module_content` where module_tag_id="+bModuleList.get(i).getBarModuleId().toString()+" "
					+ "and `status`=1 and fk_type=1 and end_date > CURDATE() ORDER BY content_order DESC";
			List<BarModuleContent> bCotentList = DbUtil.selectList(Config.BOOKBARDBConfig, sql, BarModuleContent.class);
			if(bCotentList.size()==0){
				l--;
			}
			for(int m=0,j=0; j<bCotentList.size(); j++,m++){				
				try{
					ArticleContent aContentOfDB = new ArticleContent();
					sql = "select * from article where is_show=1 and is_del=0 and media_digest_id ="+bCotentList.get(j).getContentId();
					Article article = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, Article.class);
								
					try{
						sql = "select * from comment_target_count where target_id="+bCotentList.get(j).getContentId();	
						CommentTargetCount count = DbUtil.selectOne(Config.BSAECOMMENT, sql, CommentTargetCount.class);
						aContentOfDB.setCommentNum(Integer.toString(count.getCommentCount()));
						aContentOfDB.setPraiseNum(Integer.toString(count.getPraiseCount()));
					}
					catch(Exception e){
						//没有点赞和评论时，得到null，所以catch 空指针异常
						e.printStackTrace();
						aContentOfDB.setCommentNum("0");
						aContentOfDB.setPraiseNum("0");
					}	
					
					sql ="SELECT * FROM `media_digest` where id="+bCotentList.get(j).getContentId()+" and is_show=1";
					Map<String,Object> digest = DbUtil.selectOne(Config.YCDBConfig, sql);
					aContentOfDB.setContent(digest.get("card_remark").toString());
					aContentOfDB.setCustId(reponseResult.getData().getSquareInfo().get(l).getArticleContent().get(m).getCustId());
					List<String> img = reponseResult.getData().getSquareInfo().get(l).getArticleContent().get(m).getImgList();
					aContentOfDB.setImgList(img==null?null:img);
					aContentOfDB.setIsPraise("0");
					aContentOfDB.setIsTop(article.getIsTop().toString());
					aContentOfDB.setLastModifiedDateMsec(article.getLastModifiedDateMsec().toString());
					aContentOfDB.setMediaDigestId(Long.toString(article.getMediaDigestId()));
					aContentOfDB.setTitle(digest.get("title")==null?null:digest.get("title").toString());
					aContentOfDB.setType(Integer.toString(article.getType()));
					aContentListOfDB.add(aContentOfDB);
					if(aContentListOfDB.size()==11){
						break;
					}					
				}
				catch(NullPointerException e){
					logger.info("该帖子已不存在 "+e);
					m--;
				}				
			}
			dataVerifyManager.add(new ValueVerify(reponseResult.getData().getSquareInfo().get(l).getModule(),moduleOfDB, true));
			if(aContentListOfDB.size()!=0){
				dataVerifyManager.add(new ListVerify(reponseResult.getData().getSquareInfo().get(l).getArticleContent(), aContentListOfDB, true));
			}
		}
		super.dataVerify();
	}

}
