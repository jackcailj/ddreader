package com.dangdang.db.ecms;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.db.ecms._enum.ColumnNameEnum;
import com.dangdang.db.ecms.meta.Ebook;

/**
 * 书城相关查询SQL
 * @author guohaiying
 *
 */
public class BookStoreCommSQL extends ABookStoreSQL{
	
	static Logger log = Logger.getLogger(BookStoreCommSQL.class);	
	static Map<String, String> allColumnCodeMap = null;	
	
	/**
	 * 根据栏目name返回栏目code
	 * @return 
	 * @throws Exception 
	 */
	public static String getColumnCodeByName(ColumnNameEnum columnName) throws Exception{
		if(allColumnCodeMap == null){
			allColumnCodeMap = getAllColumnCode();
		}
		
		String code = "";
		Set set = allColumnCodeMap.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry mapentry = (Map.Entry) iterator.next();
			log.info(mapentry.getKey() +" " +mapentry.getValue());
			String name = (String) mapentry.getKey();
			if(name.equals(columnName.toString())){
				code = (String) mapentry.getValue();				
			}
		}
		log.info("columnName: " + columnName.toString() +"code：" +code);
		return code;		
	}	
	
	//获取栏目columnName下的n本书
	public static List<Ebook> getBookListByColumnName(ColumnNameEnum columnName, int n) throws Exception{
		String code = getColumnCodeByName(columnName);
		String selectSQL = "SELECT * FROM `ebook` WHERE id IN (SELECT DOC_ID FROM `doc_column` WHERE COLUMN_ID= (" +
				"SELECT ID FROM `column` WHERE `CODE` = '"+code+"')) ORDER BY RAND() LIMIT " + n;
		List<Ebook> infos = DbUtil.selectList(Config.ECMSDBConfig, selectSQL, Ebook.class);	
		log.info(infos.size());
		return infos;
	}
	
	//获取栏目columnName下的所有书
	public static List<Ebook> getBookListByColumnName(ColumnNameEnum columnName) throws Exception{
		String code = getColumnCodeByName(columnName);
		String selectSQL = "SELECT * FROM `ebook` WHERE id IN (SELECT DOC_ID FROM `doc_column` WHERE COLUMN_ID= (" +
				"SELECT ID FROM `column` WHERE `CODE` = '"+code+"'))";
		List<Ebook> infos = DbUtil.selectList(Config.ECMSDBConfig, selectSQL, Ebook.class);	
		log.info(infos.size());
		return infos;
	}
	
	public static int getBooksCountByColumnName(ColumnNameEnum columnName) throws Exception{
		String code = getColumnCodeByName(columnName);
		String selectSQL = "SELECT count(*) FROM `doc_column` WHERE COLUMN_ID= (" +
				"SELECT ID FROM `column` WHERE `CODE` = '"+code+"')";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.ECMSDBConfig, selectSQL);	
		int count = Integer.valueOf(infos.get(0).get("count(*)").toString());	
		return count;
	}
	
	//随机返回1本借阅的书
	public static Ebook getOneBorrowBook() throws Exception{
		List<Ebook> list = getBorrowBooks(1);
		return list.get(0);
	}
	
	//随机返回n本借阅的书
	public static List<Ebook> getBorrowBooks(int n) throws Exception{
		return getBookListByColumnName(ColumnNameEnum.borrowRead, n);
	}
	
	//随机返回1本免费的书
	public static Ebook getOneFreeBook() throws Exception{
		List<Ebook> list = getFreeBooks(1);
		return list.get(0);
	}
	
	//随机返回n本免费的书
	public static List<Ebook> getFreeBooks(int n) throws Exception{
		return getBookListByColumnName(ColumnNameEnum.teFreeRob, n);
	}
	
	//返回促销的书
	
	//随机返回1本正常销售的书
	public static Ebook getOneNormalSaleBook() throws Exception{
		List<Ebook> list = getNormalSaleBooks(1);
		return list.get(0);
	}
	
	//随机返回n本正常销售的书
	public static List<Ebook> getNormalSaleBooks(int n) throws Exception{
		return getBookListByColumnName(ColumnNameEnum.newBooks, n);
	}
	
	//获取书的借阅时长
	public static Long getBorrowDuration(long productId) throws Exception{
		String selectSQL = "SELECT borrow_duration FROM `ebook` WHERE productId=" + productId + " AND borrow_duration IS NOT NULL";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.ECMSDBConfig, selectSQL);	
		if(!infos.isEmpty()){
			Long borrow_duration = Long.valueOf(infos.get(0).get("borrow_duration").toString());	
			return borrow_duration;
		}else{
			return -1l;
		}
	}	
	
	//获取用户书的借阅时长
	public static Long getUserBorrowDuration(int productId) throws Exception{
		String selectSQL = "SELECT deadline FROM `user_ebook` WHERE productId=" + productId;
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.ECMSDBConfig, selectSQL);	
		Long borrow_duration = Long.valueOf(infos.get(0).get("borrow_duration").toString());	
		return borrow_duration;
	}	
	
	//根据token获取custID
	public static String getCustIdByToken(String token) throws Exception{
		String selectSQL = "SELECT CUST_ID FROM `user_device` WHERE LOGIN_TOKEN= '"+token+"'";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.ECMSDBConfig, selectSQL);	
		return infos.get(0).get("CUST_ID").toString();	
	}
	
	
	
	//返回已下架的书
	
	//返回某榜单的书
	
	//返回某分类的书
	
	public static void main(String[] args) throws Exception{
		//BookStoreCommSQL.getColumnCode("免费借阅");
		//BookStoreCommSQL.getBorrowBooks(4);
		//BookStoreCommSQL.getBookListByColumnName(, 1);
		//BookStoreCommSQL.refreshCache();
		long current = System.currentTimeMillis();
//		long db = 1430808532920l ;
//		long day = (db-current)/(24*60*60*1000);
//		System.out.println(System.currentTimeMillis());
//		System.out.println(db);
//		System.out.println(db-current + " dfs "+ day);
//		UserEbook a = BookStoreCommSQL.getUserOverdueBorrowBook("50098052", current);
//		System.out.println(a.getProductId());
//		System.out.println(current);
	
	}
	
}
