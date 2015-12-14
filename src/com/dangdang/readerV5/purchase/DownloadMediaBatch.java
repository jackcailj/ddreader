package com.dangdang.readerV5.purchase;

import java.util.List;
import java.util.Map;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.config.Config;
import com.dangdang.db.digital.MediaChapterDb;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaChapter;
import com.dangdang.enumeration.BookStatus;
import com.dangdang.enumeration.BookType;
import com.dangdang.enumeration.GetChapterEnum;

public class DownloadMediaBatch extends FixtureBase{
	String attachMoney;
	String masterMoney;
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		 super.setParameters(params);
		 String[] str = paramMap.get("startChapterId").toString().split(",");
		 if(login!=null){
			 Map<String,Object> map = DbUtil.selectOne(Config.ACCOUNTDBConfig,
					 "select * from attach_account where cust_id="+login.getCustId());
			 attachMoney = map.get("attach_account_money").toString();
			 map = DbUtil.selectOne(Config.ACCOUNTDBConfig,
					 "select * from master_account where cust_id="+login.getCustId());
			 masterMoney = map.get("master_account_money").toString();
		 }		
		 setParamMap(str);
   }
	
	public void setParamMap(String[] str) throws Exception{
		 GetChapterEnum status = GetChapterEnum.valueOf(str[0]);
		 Media media = MediaDb.getMedia(BookType.YUANCHUANG, BookStatus.VALID);
	     MediaChapter mediaChapter = null;	     
	     mediaChapter = MediaChapterDb.getBookChapter(media.getMediaId().toString(),status);
		 paramMap.put("startChapterId", mediaChapter.getId().toString());
		 mediaChapter = MediaChapterDb.getEndChapter(media.getMediaId().toString(),status,mediaChapter.getId().toString());
		 paramMap.put("endChapterId", mediaChapter.getId().toString());
		 if(login!=null){
			 if(status==GetChapterEnum.FREE){
				 updateMasterAndAttachMoney("0", "0", login.getCustId());
			 }
			 if(status==GetChapterEnum.FU_FEI&&str[1]!=null&&str[1].equalsIgnoreCase("Pass")){
				 updateMasterAndAttachMoney("1000", "1000", login.getCustId());
			 }
			 if(status==GetChapterEnum.FU_FEI&&str[1]!=null&&str[1].equalsIgnoreCase("Fail")){
				 updateMasterAndAttachMoney("0", "0", login.getCustId());
			 }
		 }	
	}
	
	public void updateMasterAndAttachMoney(String masterMoney, String attachMoney, String custId) throws Exception{
		String[] updateSql = new String[]{"update master_account set master_account_money="+masterMoney+" where cust_id="+custId,
                                          "update attach_account set attach_account_money="+attachMoney+" where cust_id="+custId};
		// 更新主副账户的钱
		for(int i=0; i<updateSql.length; i++){
			DbUtil.executeUpdate(Config.ACCOUNTDBConfig, updateSql[i]);
		}
	}
	
	@Override
	public boolean tearDown(){
		if(login!=null){
			try {
				updateMasterAndAttachMoney(masterMoney, attachMoney, login.getCustId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.tearDown();		
    }
}
