package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Bar;
import com.dangdang.bookbar.meta.BarModuleContent;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BarContent;
import com.dangdang.readerV5.reponse.BarListlData;

/**
 * 查询标签详情接口
 * @author wuhaiyan
 * */
public class QueryTagDetail extends FixtureBase {
	
	ReponseV2<BarListlData>   reponseResult;
	String tagId = null;
	 
	public ReponseV2<BarListlData> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BarListlData>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		String sql = "select bar_module_tag_id from bar_module_tag limit 1";
		tagId = DbUtil.selectOne(Config.BOOKBARDBConfig, sql).get("bar_module_tag_id").toString();
		if(params.get("moduleTagId")!=null&&params.get("moduleTagId").toString().equalsIgnoreCase("FromDB")){
			params.put("moduleTagId", tagId);
		}
		super.setParameters(params);
    }
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			// fk_type: 外键类型(1.模块表的外键2.标签表的外键)
			String sql = "SELECT * from `bar_module_content` where module_tag_id="+tagId+" and "
					+ "`status`=1 and fk_type=2 and end_date > CURDATE() ORDER BY content_order DESC";
			List<BarModuleContent> list = DbUtil.selectList(Config.BOOKBARDBConfig, sql,BarModuleContent.class);
			List<BarContent> barList = new ArrayList<BarContent>();
			if(list.size() > 0){
				for(int i=0; i<list.size(); i++){
					BarContent content = new BarContent();
					sql = "select * from bar where bar_id ="+list.get(i).getContentId();
					Bar bar = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, Bar.class);
					if(bar.getBarStatus()==4){//下架吧不返回
						continue;
					}
					content.setArticleNum(bar.getArticleNum().toString());
					content.setBarDesc(bar.getBarDesc());
					content.setBarId(bar.getBarId().toString());
					content.setBarImgUrl(bar.getBarImgUrl());
					//请求返回结果中imgurl的值是缩略图，上传地址比数据表里多个_c或a或b，详见以下链接：
					//http://10.255.223.6/wiki/pages/viewpage.action?pageId=4360026 
					//所以对返回结果中的barimgurl做以下处理
					String str = reponseResult.getData().getBarList().get(i).getBarImgUrl().replaceAll("[._][a-z]\\.", ".");
					reponseResult.getData().getBarList().get(i).setBarImgUrl(str);
					content.setBarName(bar.getBarName());
					content.setMemberNum(Integer.toString(bar.getMemberNum()));
					content.setRecommendReason(list.get(i).getRecommendReason().isEmpty()?null:list.get(i).getRecommendReason());
					//content.setIsActivity();
					barList.add(content);
				}
				dataVerifyManager.add(new ListVerify(reponseResult.getData().getBarList(), barList, true));
			}
			else{
				logger.info("size is 0");
				Assert.assertEquals(reponseResult.getData().getBarList().size(),0,"吧列表信息为空");
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
