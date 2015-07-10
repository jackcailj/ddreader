package com.dangdang.ecms;

import java.util.List;

import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaActivityInfo;
import org.apache.commons.lang3.StringUtils;

import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.core.TestDevice;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ecms.meta.BookNote;
import com.dangdang.ecms.meta.Ebook;
import com.dangdang.ecms.meta.ExperienceInfo;

public class EcmsUtil {


	
	/*
	 * 获取用户没有购买过的有效书籍信息，依次返回
	 * Args：
	 * 		custid：用户id，可以通过Login类获得
	 * 		number：获取书籍的数量
	 * return：
	 * 		返回Ebook列表，没有查找到数据抛出异常
	 */	
	public static List<Ebook> getUserUnbuyBook(String custid,int number) throws Exception {
		
		//组织返回值字段，
	/*	String returnCol="";
		if(ReturnColumns.length==0){
			returnCol="e.*";
		}
		else {
			List<String> columns=new ArrayList<String>();
			for(String column: ReturnColumns){
				columns.add("e."+column);
			}
			returnCol=StringUtils.join(columns,",");
		}*/
		
		//String selectSting ="select e.* from (select "+returnCol+" from ebook e, ebook_resfile r where e.shelfStatus = 1 and e.id = r.EBOOK_ID and r.RESFILE_TYPE = 'epub' GROUP BY e.id) as e LEFT JOIN (select ue.productId from user_ebook ue where ue.CUST_ID="+custid+") as uet on uet.productId=e.productId where uet.productId is null limit "+number;
		String selectSting ="select e.* from ebook e, ebook_resfile r where "+(Config.getDevice()!=TestDevice.ANDROID?"e.IAP_SHELF_STATUS=1 and e.IAP_DEVICE_TYPE like '%"+Config.getDevice()+"%' ":"e.shelfStatus = 1")+" and e.id = r.EBOOK_ID and r.RESFILE_TYPE = 'epub' and e.productId not in (select ue.productId from user_ebook ue where ue.CUST_ID="+custid+") GROUP BY e.id  limit 1";
		List<Ebook> results=DbUtil.selectList(Config.ECMSDBConfig, selectSting, Ebook.class);
		if(results.size()==0){
			throw new Exception("获取用户未购买过的有效书籍失败");
		}
		
		return results;
	}
	
	/*
	 * 获取下架书籍，目前返回前几个
	 * Args：
	 * 		custid：用户id，可以通过Login类获得
	 * 		number：获取书籍的数量
	 * return：
	 * 		返回Ebook列表，没有查找到数据抛出异常
	 */	
	public static List<Ebook> getSoldOutBook(int number) throws Exception {
		String selectSting ="select e.* from ebook e, ebook_resfile r where "+(Config.getDevice()!=TestDevice.ANDROID?"e.IAP_SHELF_STATUS=2 and e.IAP_DEVICE_TYPE like '%"+Config.getDevice()+"%' ":"e.shelfStatus = 2")+" and e.id = r.EBOOK_ID and r.RESFILE_TYPE = 'epub' GROUP BY e.id  limit "+number;
		
		List<Ebook> results=DbUtil.selectList(Config.ECMSDBConfig, selectSting, Ebook.class);
		if(results.size()==0){
			throw new Exception("获取下架书籍失败");
		}
		
		return results;
	}
	
	/*
	 * 获取有效书籍，固定返回前几个
	 * Args：
	 * 		custid：用户id，可以通过Login类获得
	 * 		number：获取书籍的数量
	 * return：
	 * 		返回Ebook列表，没有查找到数据抛出异常
	 */	
	public static List<Ebook> getValidBook(int number) throws Exception {
		String selectSting ="select e.* from ebook e, ebook_resfile r where e.shelfStatus = 1 and e.id = r.EBOOK_ID and r.RESFILE_TYPE = 'epub' GROUP BY e.id  limit "+number;
		
		List<Ebook> results=DbUtil.selectList(Config.ECMSDBConfig, selectSting, Ebook.class);
		if(results.size()==0){
			throw new Exception("获取有效书籍失败");
		}
		
		return results;
	}
	
	
	/*
	 * 获取用户笔记
	 */
	public static List<BookNote> getBookNote(String custid) throws Exception{
		String selectSting ="select * from book_note where cust_id="+custid;
		
		List<BookNote> results=DbUtil.selectList(Config.ECMSDBConfig, selectSting, BookNote.class);
		
		return results;
	}
	
	/*
	 * 获取用户阅历,没有返回remarks，因为
	 */
	public static List<ExperienceInfo> getExperienceInfo(String custid,String recordTime, int pageSize) throws Exception{
		
		String selectSting ="select experience_id , cust_id,product_id,record_time,type,device_type,remarks from experience_info where cust_id="+custid + (StringUtils.isBlank(recordTime)? "":" and record_time<"+recordTime)+" order by record_time limit "+pageSize;
		
		List<ExperienceInfo> results=DbUtil.selectList(Config.ECMSDBConfig, selectSting, ExperienceInfo.class);
		
		return results;
	}

	/*
	 * 获取充值页面数据
	 */
	public static List<MediaActivityInfo> getDepositShowView(String fromPaltform) throws Exception{

		String selectString="select  deposit_gift_read_price,deposit_money,deposit_read_price,end_time,relation_product_id,start_time"
				+" from media_activity_info where end_time>NOW() and from_paltform='"+fromPaltform+"'  and activity_type_id = "+(Config.getDevice()== TestDevice.ANDROID?"1016":(Config.getDevice()== TestDevice.IPHONE?"1014":"1015"))+ " and status=1"
				+" ORDER BY  deposit_read_price";
		List<MediaActivityInfo> results=DbUtil.selectList(Config.YCDBConfig, selectString, MediaActivityInfo.class);

		return results;
	}


	/*
		获取我的借阅列表
	 */
	public  static void getBorrowAuthorityList(String custId){

	}



}
