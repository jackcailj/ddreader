//package com.dangdang.db.digital;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//
//import com.dangdang.config.Config;
//import com.dangdang.ddframework.dbutil.DbUtil;
//import com.dangdang.ddframework.util.TelnetUtil;
//
//
///**
// * 后台-》推荐管理： 专题列表   Banner列表  推荐列表
// * 后台-》公告管理
// * 功能：模拟后台操作
// * @author guohaiying
// *
// */
//public class BookStoreTestEvnSQL extends BookStoreCommSQL{
//	static Logger log = Logger.getLogger(BookStoreTestEvnSQL.class);
//	
//	/*+++++++++++++++++++++++++++++++++++++++++++++后台-》推荐列表+++++++++++++++++++++++++++++++++++++++++++++++++++++*/
//	//获取xx栏目下记录数量
//	public static ArrayList<String> getColumnCount(String columnCode) throws Exception{
//		String selectSQL = "SELECT sale_id,sale_name FROM `media_column_content` WHERE column_code='" +columnCode + "' AND `status` in (1,2) AND end_date> NOW()";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		ArrayList<String> dbResult = new  ArrayList<String>();
//		for(int i=0; i<result.size(); i++){
//			Map<String, Object> m = result.get(i);
//			String sale_id = m.get("sale_id").toString();
//			dbResult.add(sale_id);
//		}
//		return dbResult;
//	}
//	
//	//判断xx栏目是否为空
//	public static boolean isColumnNull(String columnCode) throws Exception{
//		String selectSQL = "SELECT sale_id,sale_name FROM `media_column_content` WHERE column_code='" +columnCode+"' AND `status` in (1,2) AND end_date> NOW()";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		int n = result.size();
//		if(n==0) return false;
//		else return true;
//	}
//	//media表  doc_type字段 EBOOK代表电子书   空代表原创
//	//插入出版物书籍 
//	//插入原创书籍
//	//插入纸书
//	//插入借阅书籍 价格不能为0
//	//插入限免书籍 价格为0
//	
//	//模拟后台，创建书籍栏目(type=0)或频道栏目(type=1)
//	public static String createWithNameAndCodeAndType(String name, String code, int type) throws Exception{		
//		//如果media_column表中数据则清空
//		String selectSQL = "SELECT count(*) FROM `media_column` WHERE column_code='all_"+code+"'";
//		List<Map<String, Object>>  result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		int size = Integer.valueOf(result.get(0).get("count(*)").toString());
//		if(size !=0){
//			String delSQL = "DELETE FROM `media_column` WHERE column_code='all_"+code+"'";
//			DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
//		}
//		
//		//media_column表中查询name所对应的column_id
//		selectSQL="SELECT column_id FROM `media_column` WHERE name='"+name+"'";
//		result = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
//		int columnId = Integer.valueOf(result.get(0).get("column_id").toString());
//		//media_column表中插入数据
//		String insertSQL="INSERT INTO media_column(column_code,channel,path,code,name,parent_id,isactiver_forever,creator,create_date,tips,descs,is_show_horn,type) "+
//			"VALUES('all_"+code+"','all','','"+code+"','column测试专用',"+columnId+",1,'admin',now(),'请勿删除','',1,"+type+")";
//		DbUtil.executeUpdate(Config.YCDBConfig, insertSQL);
//		//如果media_column_content中数据则清空
//		selectSQL = "SELECT count(*) FROM `media_column_content` WHERE column_code='all_"+code+"'";
//		result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		size = Integer.valueOf(result.get(0).get("count(*)").toString());
//		if(size !=0){
//			String delSQL = "DELETE FROM `media_column_content` WHERE column_code='all_"+code+"'";
//			DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
//		}
//		
//		refreshCache();	
//		if(name.contains("借阅")||name.contains("限免"))
//			return code;
//		else
//			return "all_"+code;		
//	}
//
//	//模拟后台，插入书籍栏目下数据 type=0书籍  1借阅(只支持出版物类型)  2限免
//	public static void insertWithColumncodeAndSizeAndType(String columncode, int size, int type) throws Exception{	
//		String selectSQL;
//		selectSQL = "SELECT column_id FROM `media_column` WHERE column_code='"+columncode+"'";
//		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
//		int columnID = Integer.valueOf(infos.get(0).get("column_id").toString());	
//		
//		if(type==1){
//			selectSQL="SELECT s.sale_id,s.`name` " +
//					"FROM media_sale s, media m " +
//					"WHERE s.sale_id=m.sale_id " +
//					"AND s.shelf_status=1 " +
//					"AND `name` IS NOT NULL " +
//					"AND doc_type='EBOOK'" +
//					"AND s.price>0 " +
//					"AND m.price>0 " +
//					"AND m.borrow_duration IS NOT NULL " +
//					"LIMIT "+size;
//		}else if(type==2){
//			selectSQL="SELECT s.sale_id,s.`name` " +
//					"FROM media_sale s, media m " +
//					"WHERE s.sale_id=m.sale_id " +
//					"AND s.shelf_status=1 " +
//					"AND `name` IS NOT NULL " +
//					"AND s.price=0 " +
//					"AND m.price=0 " +
//					"AND m.borrow_duration IS NULL " +
//					"LIMIT "+size;
//		}else{
//			selectSQL = "SELECT sale_id, name " +
//					"FROM `media_sale` " +
//					"WHERE shelf_status=1 AND name IS NOT NULL  " +
//					"ORDER BY RAND() LIMIT " + size;
//		}
//
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);		
//		String insertSQL = "INSERT INTO media_column_content(column_code, column_id, sale_id, sale_name, creation_date, start_date, end_date, status, order_value) values";
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		for(int i=0; i<result.size(); i++){
//			Map<String, Object> temp = result.get(i);
//			int sale_id = Integer.valueOf(temp.get("sale_id").toString());
//			String name = temp.get("name").toString();
//			list.add(sale_id);
//			insertSQL += "('"+ columncode +"',"+columnID+", "+ sale_id +", '"+ name +"',now(),now(),DATE_ADD(now(),INTERVAL 1 YEAR)"+", 2, "+i+"+1),";
//
//		}
//		DbUtil.executeUpdate(Config.YCDBConfig, insertSQL.substring(0, insertSQL.lastIndexOf(",")));
//		refreshCache();
//	}
//	
//
//	
//	//下架频道
//	public static void setStatusWithChannel(int channelId) throws Exception{	
//		String updateSQL="UPDATE channel SET shelf_status=1 AND apply_times = apply_times+1 AND channel_id=" +channelId;
//		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
//		refreshCache();
//	}
//	
//	//上架频道
//	//public static void 
//	
//	
////	public static int insertColumn() throws Exception{
////		String insertSQL = "INSERT INTO media_column(column_code, channel, path, code, name, p) values()";
////		DbUtil.executeUpdate(DbUtil.getSession(), insertSQL.substring(0, insertSQL.lastIndexOf(",")),"执行"+ columnCode +"栏目下数据插入操作：");
////	}
//	
//	//模拟后台，删除xx栏目
//	public static void delColumnWithColumncode(String columncode) throws Exception{
//		String delSQL = "DELETE FROM `media_column` WHERE column_code ='" +columncode+"'";
//		DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
//		refreshCache();
//	}
//	
//	//模拟后台，删除xx栏目下数据
//	public static int delColumnDataWithColumncode(String columncode) throws Exception{
//		int sale_id = getRand(columncode);
//		String delSQL = "DELETE FROM `media_column_content` WHERE sale_id=" +sale_id;
//		DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
//		refreshCache();
//		return sale_id;
//	}
//	
//
//	
//	//随机返回xx栏目下一条数据
//	public static int getRand(String columnCode) throws Exception{
//		String selectSQL = "SELECT sale_id,sale_name FROM `media_column_content`" +
//				" WHERE column_code='" +columnCode + "'" +
//						" AND `status` in (1,2) " +
//						"AND end_date>NOW()  " +
//						"AND sale_id IN (" +
//						"SELECT sale_id " +
//						"FROM media_sale " +
//						"WHERE shelf_status=1)" +
//						" order by rand() limit 1";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		Map<String, Object> m = result.get(0);
//		int sale_id = Integer.parseInt(m.get("sale_id").toString());
//		return sale_id;
//	}
//	
//	//随机返回xx栏目下一条数据
//	public static int getRandMediaSale() throws Exception{		
//		String selectSQL = "SELECT sale_id FROM `media_sale` WHERE shelf_status=1 ORDER BY RAND() LIMIT 1";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
//		Map<String, Object> m = result.get(0);
//		int sale_id = Integer.parseInt(m.get("sale_id").toString());
//		return sale_id;
//	}
//	
//	
//
//	//返回最高排序值
//	public static int setSortWithColumncode(String columncode) throws Exception{
//		//setNormal(columncode);
//		//随机获取一本书
//		int sale_id = getRand(columncode);		
//		String selectSQL = "SELECT MAX(order_value) FROM `media_column_content` WHERE column_code='"+columncode+"'";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		Map<String, Object> m = result.get(0);
//		int value = Integer.valueOf(m.get("MAX(order_value)").toString());		
//		String updateSQL = "UPDATE `media_column_content` SET order_value = "+ (value+1)+" WHERE sale_id=" + sale_id;
//		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);		
//		refreshCache();
//		return sale_id;
//	}
//	
//	public static ArrayList<String>  getColumnCode(String code) throws Exception{
//		String selectSQL = "SELECT sale_name FROM `media_column_content` WHERE column_code='" +code + "' AND `status` in (1,2) AND end_date>NOW()  " +
//		"AND sale_id IN (SELECT sale_id FROM media_sale WHERE shelf_status=1)";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		ArrayList<String> saleNameList = new ArrayList<String>();
//		for(int i=0; i<result.size(); i++){
//			Map<String, Object> m = result.get(i);
//			String sale_name = m.get("sale_name").toString();
//			saleNameList.add(sale_name);
//		}
//		return saleNameList;		
//	}
//	
//	//刷新缓存
////	public static void refreshCache(String code) throws Exception{
////		TelnetUtil telnet = new TelnetUtil();
////		telnet.sendCommands("select 10", "del column_content_cache_"+code);
////		Thread.sleep(2000);
////	}
//	
//	public static void refreshCache() throws Exception{
//		TelnetUtil telnet = new TelnetUtil();
//		//telnet.sendCommands("select 10", "flushdb");
//		telnet.sendCommands("select 10", "flushall");
//		Thread.sleep(2000);
//	}
//	
//	
//	/*+++++++++++++++++++++++++++++++++++++++++++++后台-》专题列表+++++++++++++++++++++++++++++++++++++++++++++++++++++*/
//	/**
//	 * 添加专题分类
//	 * @param name varchar类型  长度50
//	 * @param code varchar类型 长度20
//	 * @param channelType 3中取值：ALL、NP、VP
//	 * @return 
//	 * @throws Exception 
//	 */
//	public static void insertSpecialTopicCategory(String specialTopicCategoryName, String code, String channelType) throws Exception{
//		int parent_id = 1;
//		String path = "YC_" + code;
//		String creator = "test";
//
//		String insertSQL = "INSERT INTO media_special_topic_category(category_name, parent_id, category_code, path, creator, creation_date, channel) " +
//				"values('"+specialTopicCategoryName+"', "+parent_id+", '"+code+"', '"+path+"', '"+creator+"', now(), '"+channelType+"')";
//		DbUtil.executeUpdate(Config.YCDBConfig, insertSQL);
//	}
//	
//	/**
//	 * 删除专题分类
//	 * @param nam
//	 * @throws Exception 
//	 */
//	public static void delSpecialTopicCategory(String specialTopicCategoryName) throws Exception{
//		String delSQL = "DELETE FROM media_special_topic_category WHERE category_name='" + specialTopicCategoryName+"'";
//		DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
//	}
//	
//	//随机返回一个专题id
//	public static ArrayList<String> getRandSpecialTopic() throws Exception{		
//		String selectSQL = "SELECT st_id, name FROM `media_special_topic` ORDER BY RAND() LIMIT 1";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
//		Map<String, Object> m = result.get(0);
//		ArrayList<String> tmp = new ArrayList<String>();
//		tmp.add(m.get("st_id").toString());
//		tmp.add(m.get("name").toString());
//		return tmp;
//	}
//	
//	/**
//	 * 添加专题分类
//	 * @param name varchar类型  长度50
//	 * @param code varchar类型 长度20
//	 * @param channelType 3中取值：ALL、NP、VP
//	 * @return 
//	 * @throws UnsupportedEncodingException 
//	 */
////	public static void insertSpecialTopic(String specialTopicName, String code, String channelType) throws UnsupportedEncodingException{
////		int parent_id = 1;
////		String path = "YC_" + code;
////		String creator = "test";
////
////		String insertSQL = "INSERT INTO media_special_topic_category(category_name, parent_id, category_code, path, creator, creation_date, channel) " +
////				"values('"+specialTopicCategoryName+"', "+parent_id+", '"+code+"', '"+path+"', '"+creator+"', now(), '"+channelType+"')";
////		DbUtil.executeUpdate(DbUtil.getSession(), insertSQL, "添加专题分类" + code);
////	}
//	
//	
//	/*+++++++++++++++++++++++++++++++++++++++++++++后台-》Banner列表+++++++++++++++++++++++++++++++++++++++++++++++++++++*/
//	/**
//	 * 块组管理-》添加组
//	 * @param name 组名称  varchar类型  长度100
//	 * @param descn 描述  varchar类型  长度200
//	 * @param parentName  原创  0 女频 1 男频 1 听书 0 推荐页 26 热词页 26
//	 * @throws Exception 
//	 */
//	public static void insertBlockGroup(String blockGroupName, String parentName) throws Exception{
//		int parent_id =  selectBlockGroupID(parentName);
//		String insertSQL = "INSERT INTO media_block_group(name, status, parent_id) " +
//		"values('"+blockGroupName+"',1,"+parent_id+")";
//		DbUtil.executeUpdate(Config.YCDBConfig, insertSQL);
//	}
//	
//	/**
//	 * 块组管理-》查询groupID
//	 * @param name 组名称  varchar类型  长度100
//	 * @return id
//	 * @throws Exception 
//	 */
//	public static int selectBlockGroupID(String blockGroupName) throws Exception{
//		String selectSQL = "SELECT media_block_group_id FROM `media_block_group` WHERE name='" +blockGroupName+"'";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		Map<String, Object> temp = result.get(0);
//		return Integer.valueOf(temp.get("media_block_group_id").toString());
//	}
//	
//	/**
//	 * 块组管理-》删除组
//	 * @param name 组名称  
//	 * @throws Exception 
//	 */
//	public static void delBlockGroupID(String blockGroupName) throws Exception{
//		String delSQL = "DELETE FROM `media_block_group` WHERE name='" +blockGroupName+"'";
//		DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
//	}
//	
//	
//	/*+++++++++++++++++++++++++++++++++++++++++++++后台-》公告管理+++++++++++++++++++++++++++++++++++++++++++++++++++++*/
//	/**
//	 * 公告管理-》添加公告类型
//	 * @param noticeName 公告类型名称  varchar类型  长度20
//	 * @param type 公告类型编号  int 长度11
//	 * @throws Exception 
//	 */
//	public static void insertNoticeType(String noticeName, int type) throws Exception{
//		String selectSQL = "SELECT count(*) FROM `media_notice_type` WHERE type=" +type;
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		Map<String, Object> temp = result.get(0);
//		int n = Integer.valueOf(temp.get("count(*)").toString());
//		if(n!=0){
//			//Assert.fail("【公告类型编号已经存在！】");			
//		}
//		String insertSQL = "INSERT INTO media_notice_type(name, type, creator, create_date) " +
//		"values('"+noticeName+"',"+type+", 'admin', now())";
//		DbUtil.executeUpdate(Config.YCDBConfig, insertSQL);
//	}
//	
//	/**
//	 * 公告管理-》删除公告类型
//	 * @param noticeType
//	 * @throws Exception 
//	 */
//	public static void delNoticeType(int noticeType) throws Exception{
//		String delSQL = "DELETE FROM `media_notice_type` WHERE type =" +noticeType;
//		DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
//	}	
//	
//	/**
//	 * 公告管理-》查询公告类型
//	 * @param noticeName
//	 * @throws Exception 
//	 */
//	public static int selectNoticeType(String noticeName) throws Exception{	
//		String selectSQL = "SELECT type FROM `media_notice_type` WHERE name='" +noticeName+"'";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		Map<String, Object> temp = result.get(0);
//		return Integer.valueOf(temp.get("type").toString());
//	}
//	
//	/**
//	 * 公告管理-》添加公告
//	 * @param noticeTitle
//	 * @param type
//	 * @param param
//	 * @param channelType 
//	 * @throws Exception 
//	 */
//	public static void insertNotice(String noticeTitle, int type, String param, String channelType) throws Exception{
//		String start = "now()";
//		String end = "DATE_ADD(now(),INTERVAL 1 MONTH)";
//		insertNotice(noticeTitle, type, param, start, end, channelType);
//	}
//	
//	/**
//	 * 公告管理-》添加公告
//	 * @param noticeTitle  公告标题  varchar 长度500
//	 * @param type 公告类型 int 长度11
//	 * @param param  parameter varchar 500; url varchar 200
//	 * @param start
//	 * @param end
//	 * @param channelType varchar 长度20
//	 * @throws Exception 
//	 */
//	public static void insertNotice(String noticeTitle, int type, String param, String start, String end, String channelType) throws Exception{
//		String url = "url跳转类型";
//		String insertSQL;
//		if(selectNoticeType(url)==type){
//			insertSQL = "INSERT INTO media_notice(title, type, creator, create_time, start_time, end_time, url, channel_type) " +
//			"values('"+noticeTitle+"',"+type+", 'admin', now(),"+start+","+end+",'"+param+"','"+channelType+"')";
//		}else{
//			insertSQL = "INSERT INTO media_notice(title, type, creator, create_time, start_time, end_time, parameter, channel_type) " +
//			"values('"+noticeTitle+"',"+type+", 'admin', now(),"+start+","+end+",'"+param+"','"+channelType+"')";
//		}
//		DbUtil.executeUpdate(Config.YCDBConfig, insertSQL);
//	}
//	
//	/**
//	 * 公告管理-》删除公告
//	 * @param noticeTitle
//	 */
//	public static void delNotice(String noticeTitle) throws Exception{
//		String delSQL = "DELETE FROM `media_notice` WHERE title ='" +noticeTitle+"'";
//		DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
//	}
//	
//	/**
//	 * 公告管理-》查询公告
//	 * @param noticeTitle
//	 */
//	public static int selectNoticeByURL(String noticeTitle, String url) throws Exception{
//		String selectSQL = "SELECT  count(*) FROM `media_notice` WHERE title ='" +noticeTitle+"' AND url='"+url+"'";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		Map<String, Object> temp = result.get(0);
//		return Integer.valueOf(temp.get("count(*)").toString());
//	}
//	
//	/**
//	 * 公告管理-》查询公告
//	 * @param noticeTitle
//	 */
//	public static int selectNoticeByParameter(String noticeTitle, String parameter) throws Exception{
//		String selectSQL = "SELECT count(*) FROM `media_notice` WHERE title ='" +noticeTitle+"' AND parameter='"+parameter+"'";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		Map<String, Object> temp = result.get(0);
//		return Integer.valueOf(temp.get("count(*)").toString());
//	}
//	
//	
//	
//	/*+++++++++++++++++++++++++++++++++++++++++++++打赏相关+++++++++++++++++++++++++++++++++++++++++++++++++++++*/
//	//随机返回一个用户id
//	public static int getRandUser() throws Exception{		
//		String selectSQL = "SELECT cust_id FROM `media_activity_user` ORDER BY RAND() LIMIT 1";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
//		Map<String, Object> m = result.get(0);
//		int sale_id = Integer.parseInt(m.get("cust_id").toString());
//		return sale_id;
//	}
//	
//	/*+++++++++++++++++++++++++++++++++++++++++++++榜单相关+++++++++++++++++++++++++++++++++++++++++++++++++++++*/
//	//判断是否为空
//	public static boolean isRankingNull(String listType) throws Exception{
//		String selectSQL = "SELECT count(*) FROM `media_list_ranking` WHERE list_type='" +listType+"'";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		Map<String, Object> m = result.get(0);
//		int n = Integer.parseInt(m.get("count(*)").toString());
//		if(n==0) return false;
//		else return true;
//	}
//	
//	//返回符合条件的第一条数据
//	public static int getTopRanking(String listType) throws Exception{
//		String selectSQL = "SELECT sale_id FROM `media_list_ranking` WHERE list_type='"+listType+"' AND sale_id IN " +
//				"(SELECT sale_id FROM media_sale WHERE `status`=2) ORDER BY appoint_order DESC LIMIT 1";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
//		Map<String, Object> m = result.get(0);
//		return Integer.valueOf(m.get("sale_id").toString());
//	}
//	
//	//返回符合条件的N条数据
//	public static ArrayList<Integer> getRanking(String listType, int num) throws Exception{
//		String selectSQL = "SELECT sale_id FROM `media_list_ranking` WHERE list_type='"+listType+"' AND issue = 194 AND sale_id IN " +
//				"(SELECT sale_id FROM media_sale WHERE `shelf_status`=1) ORDER BY appoint_order DESC LIMIT " + num;
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		ArrayList<Integer> tmp = new ArrayList<Integer>();
//		for(int i=0; i<result.size(); i++){
//			Map<String, Object> m = result.get(i);
//			tmp.add(Integer.valueOf(m.get("sale_id").toString()));
//		}	
//		return tmp;
//	}
//	
//	//返回随机一条数据
//	public static int getRandRanking(String listType) throws Exception{
//		String selectSQL = "SELECT sale_id FROM `media_list_ranking` WHERE list_type='"+listType+"' AND sale_id IN " +
//				"(SELECT sale_id FROM media_sale WHERE `status`=2) LIMIT 1";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
//		Map<String, Object> m = result.get(0);
//		return Integer.valueOf(m.get("sale_id").toString());
//	}
//	
//	//返回最大的appoint_order值
//	public static int getMaxAppointOrder(String listType) throws Exception{
//		String selectSQL = "SELECT MAX(appoint_order) FROM `media_list_ranking` WHERE list_type='"+listType+"' AND sale_id IN" +
//				" (SELECT sale_id FROM media_sale WHERE `status`=2)";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
//		Map<String, Object> m = result.get(0);
//		return Integer.valueOf(m.get("MAX(appoint_order)").toString());
//	}
//	
//	// 删除某条榜单数据
//	public static int delRanking(String listType) throws Exception{
//		int saleID = getTopRanking(listType);
//		String delSQL = "DELETE FROM `media_list_ranking` WHERE sale_id ='" +saleID+"'";
//		DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
//		refreshCache();
//		return saleID;
//	}
//	
//	//模拟后台，下架榜单某本书
//	public static int setRankingShelfStatus(String listType) throws Exception{
//		int sale_id = getTopRanking(listType);
//		String updateSQL = "UPDATE `media_sale` SET shelf_status=0 WHERE sale_id= "+ sale_id;
//		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
//		refreshCache();
//		return sale_id;
//	}
//	
//	//模拟后台，设置榜单列表中书为强制有效
//	public static int setRankingEffective(String listType) throws Exception{
//		String updateSQL = "UPDATE media_list_ranking SET status=2 WHERE list_type='" +listType+ "'";
//		refreshCache();
//		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
//		int sale_id = getRandRanking(listType);
//		updateSQL = "UPDATE media_list_ranking SET status=1 WHERE sale_id="+ sale_id +" AND list_type='" +listType+ "'";
//		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
//		refreshCache();
//		return sale_id;
//	}
//
//	//模拟后台，设置榜单列表中书为强制无效
//	public static int setRankingInvalid(String listType) throws Exception{
//		int sale_id = getRandRanking(listType);
//		String updateSQL = "UPDATE media_list_ranking SET status=0 WHERE sale_id="+ sale_id +" AND list_type='" +listType+ "'";
//		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
//		refreshCache();
//		return sale_id;
//	}
//	
//	//模拟后台，排序
//	public static int setOrderRanking(String listType) throws Exception{
//		int appointOrder = getMaxAppointOrder(listType) + 1;		
//		int sale_id = getRandRanking(listType);
//		String updateSQL = "UPDATE media_list_ranking SET appoint_order="+ appointOrder +" WHERE sale_id="+ sale_id +" AND list_type='" +listType+ "'";
//		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
//		refreshCache();
//		return sale_id;
//	}
//	
//	//插入数据
//	public static ArrayList<Integer> insertAndSortRanking(String listType, int num) throws Exception{
//		int appointOrder = getMaxAppointOrder(listType);		
//		String selectSQL = "SELECT a.sale_id, b.media_id, `name` FROM `media_sale` a , media b WHERE a.sale_id = b.sale_id AND a.shelf_status=1 ORDER BY RAND() LIMIT " + num;
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);		
//		String insertSQL = "INSERT INTO media_list_ranking(sale_id, media_id, media_name, list_type, issue, counts, appoint_order, operator, operate_time,rank_day,status) values";
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		for(int i=0; i<result.size(); i++){
//			Map<String, Object> temp = result.get(i);
//			int sale_id = Integer.valueOf(temp.get("sale_id").toString());
//			int media_id = Integer.valueOf(temp.get("media_id").toString());
//			String name = temp.get("name").toString();
//			appointOrder += 1;
//			list.add(sale_id);
//			insertSQL += "("+sale_id+","+media_id+",'"+name+"','"+listType+"',111,0,"+appointOrder+",'admin',now(),DATE_ADD(now(),INTERVAL 720 MINUTE)"+", 2),";
//
//		}
//		DbUtil.executeUpdate(Config.YCDBConfig, insertSQL.substring(0, insertSQL.lastIndexOf(",")));
//		refreshCache();
//		return list;	
//	}
//	
//	/*+++++++++++++++++++++++++++++++++++++++++++++分类相关+++++++++++++++++++++++++++++++++++++++++++++++++++++*/
//	//返回分类的类别
//	public static ArrayList<String> getCategory(String channelType) throws Exception{
//		String selectSQL = "SELECT code FROM `media_catetory` WHERE parent_id=(SELECT catetory_id FROM `media_catetory` WHERE `code` = '"+channelType+"');";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);				
//		ArrayList<String> list = new ArrayList<String>();
//		for(int i=0; i<result.size(); i++){
//			Map<String, Object> tmp = result.get(i);
//			list.add(tmp.get("code").toString());
//			log.info(tmp.get("code").toString());
//		}
//		return list;
//	}
//	
//	//返回分类的类别
//	public static ArrayList<String> getAllCategory() throws Exception{
//		String selectSQL = "SELECT code FROM `media_catetory` WHERE parent_id in (SELECT catetory_id FROM `media_catetory` WHERE `code`='np' or `code`='vp');";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);				
//		ArrayList<String> list = new ArrayList<String>();
//		for(int i=0; i<result.size(); i++){
//			Map<String, Object> tmp = result.get(i);
//			list.add(tmp.get("code").toString());
//		}
//		list.remove("VQT");
//		list.remove("NQT");
//		return list;
//	}
//	
//	//随机返回一个分类code
//	public static ArrayList<String> getRandCatetory() throws Exception{		
//		String selectSQL = "SELECT code, name FROM `media_catetory` ORDER BY RAND() LIMIT 1";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
//		Map<String, Object> m = result.get(0);
//		ArrayList<String> tmp = new ArrayList<String>();
//		tmp.add(m.get("code").toString());
//		tmp.add(m.get("name").toString());
//		return tmp;
//	}
//	
//	//返回分类数据
//	public static ArrayList<String> getCatetoryData(String code) throws Exception{
//		String selectSQL = "SELECT sale_id, sale_name FROM `media_column_content` WHERE column_code='"+code+"' AND `status`=2;";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
//		Map<String, Object> m = result.get(0);
//		ArrayList<String> tmp = new ArrayList<String>();
//		String saleName = m.get("sale_name").toString();
//		tmp.add(saleName);
//		return tmp;
//	}
//
//	public static void main(String[] args) throws Exception{
//		//专题测试
//		//Common.insertSpecialTopicCategory("insert","insert","NP");
//		//Common.delSpecialTopicCategory("insert");
//		
//		//块测试
//		//Common.insertBlockGroup("test1111", "原创");
//		//Common.delBlockGroupID("test1111");
//		
//		//公告测试
//		//Common.selectNoticeType("url跳转类型");
//		//Common.insertNotice("test单品", 1, "{\"id\":1980003778,\"name\":\"大宋第一盗\"}", "np" );
//		BookStoreTestEvnSQL.refreshCache();
//				
//	}
//     
//}
