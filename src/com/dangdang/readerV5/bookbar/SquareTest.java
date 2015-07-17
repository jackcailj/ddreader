package com.dangdang.readerV5.bookbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
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
	 
	public ReponseV2<SquareInfoData> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<SquareInfoData>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		Map<String, String> commonParam = Config.getCommonParam();
		if(params.get("deviceType").equalsIgnoreCase("Default")){
			params.remove("deviceType");
		}
		else{
			if(params.get("deviceType").equalsIgnoreCase("REMOVE")){
				params.remove("deviceType");				
			}
			commonParam.remove("deviceType");
		}
		paramMap =  params;
		paramMap.putAll(commonParam);
		handleParam();
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
		String sql = "select * from bar_module where status=1 and (type=1 or type=3) ORDER BY module_order DESC";
		List<BarModule> bModuleList = DbUtil.selectList(Config.BOOKBARDBConfig, sql, BarModule.class);
		for(int i=0; i< bModuleList.size(); i++){
			SquareInfo info = new SquareInfo();
			Module moduleOfDB = new Module();
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
				int moduleSize;
				if(showNum > bCotentList.size()){
					moduleSize = bCotentList.size();
				}
				else{
					moduleSize = showNum;
				}
				for(int j=0; j<moduleSize; j++){
					//bar的状态是 待审核或者通过，这两个状态下都会在客户端显示，预审核的显示默认的吧简介和图片
					//bar_status(1.待审核，2.通过，3.干预审核，4.下架)
					sql = "select * from bar where bar_status!=4 and bar_id ="+bCotentList.get(j).getContentId();
					List<Bar> barList = DbUtil.selectList(Config.BOOKBARDBConfig, sql, Bar.class);
					BarContent bContentOfDB = new BarContent();
					bContentOfDB.setArticleNum(barList.get(0).getArticleNum().toString());
					bContentOfDB.setBarDesc(barList.get(0).getBarDesc().toString());
					bContentOfDB.setBarId(barList.get(0).getBarId().toString());
					if(barList.get(0).getBarImgUrl()!=null){
						String str = reponseResult.getData().getSquareInfo().get(i).getBarContent().get(j).getBarImgUrl().replaceAll("[._][a-z]\\.", ".");
						reponseResult.getData().getSquareInfo().get(i).getBarContent().get(j).setBarImgUrl(str);
						bContentOfDB.setBarImgUrl(barList.get(0).getBarImgUrl().toString());
					}
					bContentOfDB.setBarName(barList.get(0).getBarName().toString());
					bContentOfDB.setMemberNum(barList.get(0).getMemberNum().toString());
					if(bCotentList.get(j).getRecommendReason()!=null){
						bContentOfDB.setRecommendReason(bCotentList.get(j).getRecommendReason().toString());
					}
					bContentListOfDB.add(bContentOfDB);	
				}
			}
			if(bModuleList.get(i).getType().toString().equals("3")){
				// 查找标签信息(status为1且end_date大于当前日期), 在客户端会以权重大小为顺序排列，权重最大的排在第一个
				// tag_type 标签类型（1.吧标签 2.帖子标签 3.单品标签）
				sql = "SELECT * FROM `bar_module_tag` where bar_module_id="+bModuleList.get(i).getBarModuleId().toString()+" and "
						+ "status=1 and end_date > CURDATE() ORDER BY sort DESC";
				List<BarModuleTag> tCotentList = DbUtil.selectList(Config.BOOKBARDBConfig, sql, BarModuleTag.class);
				for(int k=0; k<tCotentList.size(); k++){					
					TagContent tContentOfDB = new TagContent();
					tContentOfDB.setBarModuleId(Long.toString(tCotentList.get(k).getBarModuleId()));
					tContentOfDB.setBarModuleTagId(Long.toString(tCotentList.get(k).getBarModuleTagId()));
					//Date date1 = df.parse(tCotentList.get(k).getBeginDate().toString());
					tContentOfDB.setBeginDate(Long.toString(tCotentList.get(k).getBeginDate().getTime()));
					//Date date2 = df.parse(tCotentList.get(k).getEndDate().toString());
					tContentOfDB.setEndDate(Long.toString(tCotentList.get(k).getEndDate().getTime()));
					tContentOfDB.setSort(tCotentList.get(k).getSort().toString());
					tContentOfDB.setStatus(Integer.toString(tCotentList.get(k).getStatus()));
					tContentOfDB.setTagName(tCotentList.get(k).getTagName());
					tContentOfDB.setTagType(Integer.toString(tCotentList.get(k).getTagType()));
					tContentListOfDB.add(tContentOfDB);
				}
			}			
//			info.setModule(moduleOfDB);
//			if(bContentListOfDB.size()!=0){
//				info.setBarContent(bContentListOfDB);
//			}
//			if(tContentListOfDB.size()!=0){
//				info.setTagContent(tContentListOfDB);
//			}
//			squareInfoOfDB.add(info);
			dataVerifyManager.add(new ValueVerify(moduleOfDB, reponseResult.getData().getSquareInfo().get(i).getModule(),true));
			if(bContentListOfDB.size()!=0){
				dataVerifyManager.add(new ListVerify(bContentListOfDB, reponseResult.getData().getSquareInfo().get(i).getBarContent(),true));
			}
			if(tContentListOfDB.size()!=0){
				dataVerifyManager.add(new ListVerify(tContentListOfDB, reponseResult.getData().getSquareInfo().get(i).getTagContent(),true));
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
		String sql = "select * from bar_module where status=1 and type=2 ORDER BY module_order DESC";
		List<BarModule> bModuleList = DbUtil.selectList(Config.BOOKBARDBConfig, sql, BarModule.class);
		for(int i=0; i< bModuleList.size(); i++){
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
			for(int j=0; j<bCotentList.size(); j++){
				ArticleContent aContentOfDB = new ArticleContent();
				sql = "select * from article where media_digest_id ="+bCotentList.get(j).getContentId();
				Article article = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, Article.class);
				sql = "select * from comment_target_count where target_id=1141";
				Map<String,Object> comment = DbUtil.selectOne(Config.BSAECOMMENT, sql);
				aContentOfDB.setCommentNum(comment.get("comment_count").toString());
				sql ="SELECT * FROM `media_digest` where id="+bCotentList.get(j).getContentId()+" and is_show=1";
				Map<String,Object> digest = DbUtil.selectOne(Config.YCDBConfig, sql);
				aContentOfDB.setContent(digest.get("content").toString());
				aContentOfDB.setCustId(reponseResult.getData().getSquareInfo().get(i).getArticleContent().get(j).getCustId());
				int imgSize = reponseResult.getData().getSquareInfo().get(i).getArticleContent().get(j).getImgList().size();
				List<String> imgList = new ArrayList<String>();
				for(int k=0; k<imgSize; k++){
					imgList.add(digest.get("small_pic"+(k+1)+"_path").toString());
				}
				aContentOfDB.setImgList(imgList);
				aContentOfDB.setIsPraise("0");
				aContentOfDB.setIsTop(article.getIsTop().toString());
				aContentOfDB.setLastModifiedDateMsec(article.getLastModifiedDateMsec().toString());
				aContentOfDB.setMediaDigestId(Long.toString(article.getMediaDigestId()));
				aContentOfDB.setPraiseNum(comment.get("praise_count").toString());
				aContentOfDB.setTitle(digest.get("title").toString());
				aContentOfDB.setType("2");
				aContentListOfDB.add(aContentOfDB);
			}
//			info.setModule(moduleOfDB);
//			if(aContentListOfDB.size()!=0){
//				info.setArticleContent(aContentListOfDB);
//			}
//			squareInfoOfDB.add(info);
			dataVerifyManager.add(new ValueVerify(moduleOfDB, reponseResult.getData().getSquareInfo().get(i).getModule(),true));
			if(aContentListOfDB.size()!=0){
				dataVerifyManager.add(new ListVerify(aContentListOfDB, reponseResult.getData().getSquareInfo().get(i).getArticleContent(),true));
			}
		}
		super.dataVerify();
	}
	
	// {"data":{"currentDate":"2015-07-06 16:52:55","squareInfo":[{"barContent":[{"articleNum":"0","barDesc":"修改吧简介1","barId":9,"barImgUrl":"http://img61.ddimg.cn/digital/bar/11/30/e434791801534af9a42ace14bd1b821f_c.jpg","barName":"fhghj","memberNum":"7"},{"articleNum":"0","barDesc":"修改吧简介1","barId":77,"barImgUrl":"http://img61.ddimg.cn/upload_img/00421/pcsz/ncbc960-301_c.jpg","barName":"1hKnY4tKcareatebar-1618868578","memberNum":"2"},{"articleNum":"0","barDesc":"introduction1","barId":78,"barImgUrl":"","barName":"avUphXpgcareatebar-468112708","memberNum":"2"}],"module":{"barModuleId":2,"moduleName":"人气吧","showNum":3,"templateNo":3,"type":1}},{"module":{"barModuleId":4,"moduleName":"标签","showNum":20,"type":3},"tagContent":[{"barModuleId":4,"barModuleTagId":2,"beginDate":1421489727000,"endDate":1450347335000,"sort":10,"status":1,"tagName":"白鹿原","tagType":1},{"barModuleId":4,"barModuleTagId":1,"beginDate":1421489727000,"endDate":1450347335000,"sort":4,"status":1,"tagName":"狼图腾","tagType":1},{"barModuleId":4,"barModuleTagId":3,"beginDate":1421489727000,"endDate":1450347335000,"sort":3,"status":1,"tagName":"侏罗纪世界","tagType":1},{"barModuleId":4,"barModuleTagId":4,"beginDate":1421489727000,"endDate":1450347335000,"sort":1,"status":1,"tagName":"末日崩塌","tagType":1},{"barModuleId":4,"barModuleTagId":5,"beginDate":1421489727000,"endDate":1450347335000,"sort":1,"status":1,"tagName":"多啦A梦","tagType":1}]},{"barContent":[{"articleNum":"2390","barDesc":"修改吧简介1","barId":8,"barImgUrl":"http://img63.ddimg.cn/digital/bar/32/0/3fa14a0eebfb42b8936aeb38e35d4efe_c.jpg","barName":"abdc吧","memberNum":"353","recommendReason":"奶茶好看"},{"articleNum":"2390","barDesc":"修改吧简介1","barId":7,"barImgUrl":"http://img61.ddimg.cn/upload_img/00421/pcsz/ncbc960-301_c.jpg","barName":"abc吧","memberNum":"350","recommendReason":"不好喝"},{"articleNum":"2390","barDesc":"修改吧1","barId":6,"barImgUrl":"http://img60.ddimg.cn/digital/bar/18/47/ab385feb17704a958168fbb78737b6a2_c.jpg","barName":"白鹿原吧","memberNum":"350","recommendReason":"当当比某东低一元"}],"module":{"barModuleId":1,"moduleName":"值得推荐的吧","showNum":3,"templateNo":1,"type":1}},{"barContent":[],"module":{"barModuleId":25,"moduleName":"testyw1","showNum":1,"templateNo":1,"type":1}}],"systemDate":"1436172775040"},"status":{"code":0},"systemDate":1436172775040}
	//{"data":{"currentDate":"2015-07-06 16:52:55","squareInfo":[{"articleContent":[{"commentNum":6,"content":"Fog","custId":"s1RqgMv12lhms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1435906731000,"mediaDigestId":886,"praiseNum":0,"title":"Diff","type":2},{"commentNum":1,"content":"哈哈哈","custId":"m1D8ko5e1PVms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1435893304000,"mediaDigestId":994,"praiseNum":0,"title":"嘿嘿嘿","type":2},{"commentNum":5,"content":"囧囧囧就觉得没考好BN","custId":"b+p2ifNYxT5ms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1435909901000,"mediaDigestId":972,"praiseNum":0,"title":"姐姐","type":2},{"commentNum":0,"content":"随便写写","custId":"m1D8ko5e1PVms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1435713409648,"mediaDigestId":959,"praiseNum":0,"title":"帖子1","type":2},{"commentNum":12,"content":"黄金季节","custId":"s1RqgMv12lhms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1435655985000,"mediaDigestId":924,"praiseNum":1,"title":"经济","type":2},{"commentNum":5,"content":"LOMO推广LOMO","custId":"dNPaEcgDAd1ms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1435830342000,"mediaDigestId":908,"praiseNum":1,"title":"本努力将计就计","type":2},{"commentNum":21,"content":"静静的摸摸摸摸","custId":"dNPaEcgDAd1ms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1435656654000,"mediaDigestId":899,"praiseNum":1,"title":"简简单单的模","type":2},{"commentNum":1,"content":"推荐门徒投资者","custId":"dNPaEcgDAd1ms6CjJdqf7w==","imgList":["http://img31.ddimg.cn/62/24/1900408121-1_e_4.jpg","http://img31.ddimg.cn/62/24/1900408121-1_e_4.jpg","http://img31.ddimg.cn/62/24/1900408121-1_e_4.jpg"],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1435214832000,"mediaDigestId":898,"praiseNum":0,"title":"姐姐们末","type":2},{"commentNum":0,"content":"墨菲定律","custId":"UyFgrQr+jcxms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1435199659135,"mediaDigestId":897,"praiseNum":0,"title":"定律","type":2},{"commentNum":1,"content":"但不知道怎么着怎么着都有了、但，但凡领导干部廉洁奉公：你就不知道自己想象出来的时候我会告诉你就会议上海代表团","custId":"s1RqgMv12lhms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1435230792000,"mediaDigestId":896,"praiseNum":0,"title":"你的时候就要开始啦。你们的话就是没想到的确很好很好","type":2},{"commentNum":0,"content":"成长就是将哭声调成静音的过程","custId":"UyFgrQr+jcxms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1435131033750,"mediaDigestId":895,"praiseNum":0,"title":"成长定律","type":2},{"commentNum":3,"content":"啊啊啊啊","custId":"c15We8hK8cBms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1435798391000,"mediaDigestId":995,"praiseNum":0,"title":"aaaa","type":2}],"module":{"barModuleId":3,"moduleName":"默认帖子模块","showNum":40,"type":2}},{"articleContent":[{"commentNum":0,"content":"人家每天魔法门票据我的世界","custId":"dNPaEcgDAd1ms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1434704233050,"mediaDigestId":804,"praiseNum":0,"title":"fhjgcxff","type":2},{"commentNum":0,"content":"科技局膜的是什么的一种特殊身份认证蒙","custId":"dNPaEcgDAd1ms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1434704806423,"mediaDigestId":805,"praiseNum":0,"title":"label尽善尽美","type":2},{"commentNum":0,"content":"你们的士","custId":"s1RqgMv12lhms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1435047746431,"mediaDigestId":854,"praiseNum":0,"title":"方法","type":2},{"commentNum":0,"content":"你们的士","custId":"s1RqgMv12lhms6CjJdqf7w==","imgList":[],"isPraise":0,"isTop":0,"lastModifiedDateMsec":1435047762852,"mediaDigestId":855,"praiseNum":0,"title":"方法","type":2}],"module":{"barModuleId":24,"moduleName":"t","showNum":4,"type":2}}],"systemDate":"1436172775396"},"status":{"code":0},"systemDate":1436172775396}

}
