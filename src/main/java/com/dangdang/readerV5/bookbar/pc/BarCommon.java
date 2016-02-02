package com.dangdang.readerV5.bookbar.pc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Bar;
import com.dangdang.bookbar.meta.BarMember;
import com.dangdang.bookbar.meta.Recommend;
import com.dangdang.config.Config;
import com.dangdang.db.bookbar.BarDb;
import com.dangdang.db.bookbar.RecommendDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.enumeration.BarStatus;
import com.dangdang.readerV5.reponse.BarInfo;

public class BarCommon extends FixtureBase{

	 public void verifyRecommendBar(List<BarInfo> barInfo, String type, int barCnt) throws Exception{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = df.format(new Date());
			List<Recommend> list = RecommendDb.getRecommendInfo(dateStr, type);
			if(list.size() > barCnt){
				dataVerifyManager.add(new ValueVerify<Integer>(barInfo.size(), barCnt, false));
			}
			else{
				dataVerifyManager.add(new ValueVerify<Integer>(barInfo.size(), list.size(), false));
			}
			boolean contain = true;
			List<String> ids = new ArrayList<String>();
			for(int i=0; i<list.size(); i++){
				ids.add(Long.toString(list.get(i).getSourceId()));	
			}
			logger.info("ids are "+ids.toString());
			
			//List<BarInfo> bar = barInfo;				
			for(int j=0; j<barInfo.size(); j++){
				String barId = barInfo.get(j).getBarId().toString();
				if(!(ids.contains(barId))){
					contain = false;
					logger.info(barId+" is not contained in ids");
					break;
				}	
				
				//验证随机获取的每个吧的详细信息，且barStatus是通过状态
				List<String> list1 = new ArrayList<String>();
				List<String> list2 = new ArrayList<String>();
				if(login!=null){				
				    try{
				    	String sql = "select * from bar_member where bar_id="+barId+" "
				    			+ "and cust_id="+login.getCustId()+" order by create_date DESC limit 1";
					    BarMember member = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, BarMember.class);
					    list2.add(Integer.toString(member.getMemberStatus()));
					}
					catch(Exception e){
						list2.add("4");
					}	
				    list1.add(barInfo.get(j).getMemberStatus());				   
				}
				Bar bar = BarDb.getBarInfo(BarStatus.APPROVED, barId);			
			//	list1.add(barInfo.get(j).getBarDesc());
				list1.add(barInfo.get(j).getBarImgUrl());
				list1.add(barInfo.get(j).getBarName());
				list1.add(Integer.toString(BarStatus.APPROVED.getIndex()));			
				
			//	list2.add(bar.getBarDesc().isEmpty()?"在人生的道路上，当你的希望一个个落空的时候，你也要坚定，要沉着。":bar.getBarDesc());
				list2.add(bar.getBarImgUrl());
				list2.add(bar.getBarName());
				list2.add(Integer.toString(bar.getBarStatus()));
				
				dataVerifyManager.add(new ListVerify(list1,list2, false));
			}
			dataVerifyManager.add(new ValueVerify<Boolean>(contain,true, false));
		}
}
