package com.dangdang.ecms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.util.SessionUtil;
import com.dangdang.ddframework.util.TelnetUtil;
import com.dangdang.ecms._enum.ColumnNameEnum;
import com.dangdang.ecms.meta.Column;
import com.dangdang.ecms.meta.Ebook;

public abstract class ABookStoreSQL {
	
	static Logger log = Logger.getLogger(ABookStoreSQL.class);	
	static Map<String, String> columnCodeMap = new HashMap<String, String>();
	static String deviceType = Config.getDevice().toString();	
	
	//获取借阅书SQL
	static String borrowSQL= "SELECT * FROM ebook WHERE docType='EBOOK' AND shelfStatus=1 AND PROMOTION_ID IS NULL" +
			" AND borrow_duration IS NOT NULL AND category IN ('bb','br')";	
	
	//获取免费书SQL
	static String freeSQL = "SELECT * FROM ebook WHERE docType='EBOOK' AND shelfStatus=1 AND PROMOTION_ID IS NOT NULL " +
			"AND borrow_duration IS NULL AND category IN ('bb','br')";
	
	//获取正常销售书SQL
	static String normalSaleSQL = "SELECT * FROM ebook WHERE docType='EBOOK' AND shelfStatus=1 AND PROMOTION_ID IS NULL " +
			"AND borrow_duration IS NULL AND category IN ('bb','br')";

	/**
	 * 根据dcms中的顶层模块名称获取column栏目的name和code
	 * @param name 参见dcms中栏目
	 */
	public static Map<String, String> getAllColumnCode() throws Exception{	
				
		String modelName = null;
		if("android".equals(deviceType)){
			modelName = ColumnNameEnum.Android.toString();
		}else if("iphone".equals(deviceType)){
			modelName = ColumnNameEnum.IPHONE.toString();		
		}else if("ipad".equals(deviceType)){
			modelName = ColumnNameEnum.IPAD.toString();
		}
	
		log.info(modelName);
		String selectString = "SELECT ID FROM `column` WHERE `NAME` LIKE '"+ modelName +"%'";
		List<Column> list = DbUtil.selectList(Config.ECMSDBConfig, selectString, Column.class);
		long id = list.get(0).getId();
		
		return getCC(id);		
	}
	
	/**
	 * 根据父ID获取column栏目的name和code
	 * @param parentID 父ID
	 * @return
	 * @throws Exception
	 */
	private static Map<String, String> getCC(long parentID) throws Exception{					
		String selectString = "SELECT * FROM `column` WHERE PARENT_ID="+ parentID +" AND DOC_TYPE='ARTICLE'";
		List<Column> articleList = DbUtil.selectList(Config.ECMSDBConfig, selectString, Column.class);
        
		String selectString2 = "SELECT * FROM `column` WHERE PARENT_ID="+ parentID +" AND DOC_TYPE='EBOOK'";
		List<Column> ebookList = DbUtil.selectList(Config.ECMSDBConfig, selectString2, Column.class);
		
		for(int i=0; i<ebookList.size(); i++){
			Column tmp = ebookList.get(i);
			log.info("栏目名称:" + tmp.getName() + "	栏目code：" + tmp.getCode());
			columnCodeMap.put(tmp.getName(), tmp.getCode());
		}
        
		if(articleList.size()==0){
			return columnCodeMap;
		}else{		
			for(int i=0;i<articleList.size();i++){
				Column tmp = articleList.get(i);
				Long id = tmp.getId();
				getCC(id);
			}
		}
		return columnCodeMap;     
	}
	
	//随机返回n本借阅的书
	public static List<Ebook> getBorrowBooksFromDB(int n) throws Exception{
		String selectSQL = borrowSQL + " ORDER BY RAND() LIMIT "+n;
		List<Ebook> infos = DbUtil.selectList(Config.ECMSDBConfig, selectSQL, Ebook.class);
		return infos;
	}
	
	//随机返回n本免费的书
	public static List<Ebook> getFreeBooksFromDB(int n) throws Exception{
		String selectSQL = freeSQL + " ORDER BY RAND() LIMIT "+n;
		List<Ebook> infos = DbUtil.selectList(Config.ECMSDBConfig, selectSQL, Ebook.class);
		return infos;
	}
	
	//返回促销的书
	
	//随机返回n本正常销售的书
	public static List<Ebook> getNormalSaleBooksFromDB(int n) throws Exception{
		String selectSQL = normalSaleSQL + " ORDER BY RAND() LIMIT "+n;
		List<Ebook> infos = DbUtil.selectList(Config.ECMSDBConfig, selectSQL, Ebook.class);
		return infos;
	}
	
	//通过column表的code值获取doc_column表的doc_id值
	protected static ArrayList<Long> getDocIDByColumnCode(String code) throws Exception{
		ArrayList<Long> docIDList = new ArrayList<Long>();
		String selectSQL = "SELECT DOC_ID FROM `doc_column` WHERE COLUMN_ID= (" +
				"SELECT ID FROM `column` WHERE `CODE` = '"+code+"')";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.ECMSDBConfig, selectSQL);	
		long tmp;
		for(int i=0; i<infos.size(); i++){
			tmp = Long.valueOf(infos.get(i).get("DOC_ID").toString());
			docIDList.add(tmp);
		}
		return docIDList;
	}
	
	//筛选符合借阅条件的书
	protected static List<Long> filterBorrowBooks(ArrayList<Long> filter) throws Exception{		
		return filterBooks(borrowSQL, filter);		
	}
	
	//筛选符合免费条件的书
	protected static List<Long> filterFreeBooks(ArrayList<Long> filter) throws Exception{		
		return filterBooks(freeSQL, filter);		
	}
	
	//筛选符合正常销售条件的书
	protected static List<Long> filterNormalSaleBooks(ArrayList<Long> filter) throws Exception{		
		return filterBooks(normalSaleSQL, filter);		
	}
	
	//筛选符合条件的书
	private static List<Long> filterBooks(String sql, ArrayList<Long> filter) throws Exception{
		List<Long> bookIDList = new ArrayList<Long>();
		String inList = "(";
		for(int i=0; i<filter.size(); i++){
			inList += filter.get(i) + ",";
		}
		inList = inList.substring(0, inList.lastIndexOf(",")) + ")";
		String selectSQL = sql + " And id in " + inList;
		List<Ebook> infos = DbUtil.selectList(Config.ECMSDBConfig, selectSQL, Ebook.class);
		
		log.info("符合条件的List:");
		for(int i=0; i<infos.size(); i++){			
			log.info("id: " + infos.get(i).getId() +"   title: " + infos.get(i).getTitle());
			bookIDList.add(infos.get(i).getId());
		}
		return bookIDList;
	}
	
	//删除栏目下不符合条件的数据
	public static void delDocColumn(long columnID, List<Long> notConformList) throws Exception{		
		String inList = "(";
		for(int i=0; i<notConformList.size(); i++){
			inList += notConformList.get(i) + ",";
		}
		inList = inList.substring(0, inList.lastIndexOf(",")) + ")";
		
		String delSQL = "DELETE FROM `doc_column` WHERE COLUMN_ID="+ columnID + " AND DOC_ID IN " + inList;
		SessionUtil.executeUpdate(SessionUtil.getSession(), delSQL, "");
		log.info("【删除操作执行完成！】");
		refreshCache();
	}
	
	
	//根据code值获取id，不能根据name，因为name可有重复值
	protected static long getColumnID(String code) throws Exception{		
		String selectSQL = "SELECT ID FROM `column` WHERE `CODE` = '"+code+"';";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.ECMSDBConfig, selectSQL);
		long columnID = Long.valueOf(infos.get(0).get("ID").toString());
		return columnID;
	}
	
	/**
	 * 清空缓存
	 * @throws Exception
	 */
	public static void refreshCache() throws Exception{
		log.info("【执行清空缓存操作】");
		TelnetUtil telnet = new TelnetUtil();
		telnet.sendCommands("10.255.223.155", "6379", "flushdb");
		Thread.sleep(2000);
	}


}
