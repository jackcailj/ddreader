package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Bar;
import com.dangdang.bookbar.meta.BarModule;
import com.dangdang.bookbar.meta.BarModuleContent;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BarContent;
import com.dangdang.readerV5.reponse.BarListlData;

public class MoreBarList extends FixtureBase {
	ReponseV2<BarListlData>   reponseResult;
	String moduleId = null;
	 
	public ReponseV2<BarListlData> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BarListlData>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		if(params.get("barModuleId")!=null&&params.get("barModuleId").toString().equalsIgnoreCase("FromDB")){
			String sql = "select * from bar_module where status=1 and (type=1 or type=3) limit 1";
			BarModule bModule = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, BarModule.class);
			moduleId = bModule.getBarModuleId().toString();
			params.put("barModuleId", moduleId);
		}
		super.setParameters(params);
    }
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			// fk_type: 外键类型(1.模块表的外键2.标签表的外键)
			String sql = "SELECT * from `bar_module_content` where module_tag_id="+moduleId+" and "
					+ "`status`=1 and fk_type=1 and end_date > CURDATE() ORDER BY content_order DESC";
			List<BarModuleContent> list = DbUtil.selectList(Config.BOOKBARDBConfig, sql,BarModuleContent.class);
			logger.info("list size is "+list.size());
			List<BarContent> barList = new ArrayList<BarContent>();
			if(list.size() > 0){
				for(int j=0,i=0; i<list.size(); j++,i++){
					BarContent content = new BarContent();
					try{
						sql = "select * from bar where bar_status=2 and bar_id ="+list.get(i).getContentId();
						Bar bar = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, Bar.class);
						content.setArticleNum(bar.getArticleNum().toString());
						content.setBarDesc(bar.getBarDesc().isEmpty()?"在人生的道路上，当你的希望一个个落空的时候，你也要坚定，要沉着。":bar.getBarDesc());
						content.setBarId(bar.getBarId().toString());
						//请求返回结果中imgurl的值是缩略图，上传地址比数据表里多个_c或a或b，详见以下链接：
						//http://10.255.223.6/wiki/pages/viewpage.action?pageId=4360026 
						//所以对返回结果中的barimgurl做以下处理
						content.setBarImgUrl(bar.getBarImgUrl());
						String str = reponseResult.getData().getBarList().get(j).getBarImgUrl().replaceAll("[._][a-z]\\.", ".");
						reponseResult.getData().getBarList().get(j).setBarImgUrl(str);
						content.setBarName(bar.getBarName());
						content.setMemberNum(Integer.toString(bar.getMemberNum()));
						content.setRecommendReason(list.get(i).getRecommendReason().isEmpty()?null:list.get(i).getRecommendReason());
						barList.add(content);						
					}
					catch(Exception e){
						j--;
						logger.info("此吧已不存在: "+e);
					}					
				}
				dataVerifyManager.add(new ListVerify(reponseResult.getData().getBarList(),barList, true));
			}
			else{
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
