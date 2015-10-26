package com.dangdang.db.ecms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.util.SessionUtil;
import com.dangdang.db.ecms._enum.ColumnNameEnum;
import com.dangdang.db.ecms.meta.Ebook;
import com.dangdang.db.ecms.meta.UserEbook;

public class BookStoreTestEvnSQL extends BookStoreCommSQL{
	
	//往columnName栏目插入n条数据
	public static void insertColumnData(ColumnNameEnum columnName, int n) throws Exception{
		String name = columnName.toString();
		//获取到待插入的数据的数据id
		List<Ebook> bookList = null;						
		if(name.contains("借阅")){
			bookList = getBorrowBooksFromDB(n);	
		}else if(name.contains("免费")||name.contains("今日限免")|name.contains("免费抢")){					
			bookList = getFreeBooksFromDB(n);
		}else if(name.contains("促销")||name.contains("限时特价")||name.contains("巨便宜")){
			
		}else{
			bookList = getNormalSaleBooksFromDB(n);
		}
						
		if(bookList.size() ==0){
			log.info("获取到的插入数据为0");
			return;
		}
		
		//获取columnName栏目下的所有书
		ArrayList<Long> docIDList = getDocIDByColumnCode(getColumnCodeByName(columnName));
		
		//提取待插入数据的ID，去掉已存在column栏目的
		List<Long> bookIDList = new ArrayList<Long>();
		for(int i=0; i<bookList.size(); i++){
			long id = bookList.get(i).getId();
			if(!docIDList.contains(id)){
				log.info("【待插入数据name：】");
				log.info("title: " + bookList.get(i).getTitle() + "	productId: " + id);
				bookIDList.add(id);
			}
		}
		
		//获取待插入的columnID值
		long columnID = getColumnID(getColumnCodeByName(columnName));
				
		String insertSQL = "INSERT INTO `doc_column`(DOC_ID,COLUMN_ID,SITE_ID,`STATUS`,SORT,IS_NEVER_VALID) VALUES";
		for(int i=0; i<bookIDList.size(); i++){
			insertSQL += "("+bookIDList.get(i)+","+columnID+", 1, 1, 0, 1),";
		}
		SessionUtil.executeUpdate(SessionUtil.getSession(), insertSQL.substring(0, insertSQL.lastIndexOf(",")), "");		
		log.info("【插入操作执行完成！】");
		refreshCache();
	}
	
	
	//更改数据
	public void update(){}
	
	public static void deleteColumnData(){
		
	}
	
	//删除columnName栏目下不符合条件的数据
	public static void deleteNotConformData(ColumnNameEnum columnName) throws Exception{
		String columnCode = getColumnCodeByName(columnName);  //columnName对应的code值
		List<Long> conformList = new ArrayList<Long>(); //符合条件的数据
		List<Long> notConformList = new ArrayList<Long>(); //不符合条件的数据
		
		//获取columnName栏目下的所有书
		ArrayList<Long> docIDList = getDocIDByColumnCode(columnCode);
		
		String name = columnName.toString();
		if(name.contains("借阅")){
			conformList = filterBorrowBooks(docIDList);		
		}else if(name.contains("免费")||name.contains("今日限免")||name.contains("免费抢")){					
			conformList = filterFreeBooks(docIDList);		
		}else if(name.contains("促销")||name.contains("限时特价")||name.contains("巨便宜")){
			
		}else{
			conformList = filterNormalSaleBooks(docIDList);		
		}
		
		//搜索不符合条件的docID，加入到notConformList列表中
		for(int i=0; i<docIDList.size(); i++){
			if(!conformList.contains(docIDList.get(i))){
				notConformList.add(docIDList.get(i));
			}
		}	
		
		//如果存在不符合条件的数据，则进行删除
		if(notConformList.size() !=0){
			delDocColumn(getColumnID(columnCode), notConformList);
		}		
	}
	
	//设置书的允许的借阅时长
	public static void setBorrowTime(){
		
	}
	
	//设置用户的借阅时长
	public static void setUserBorrowTime(){
		
	}
	
	//设置书的借阅时长
	public static int setBorrowDuration(long productId, int duration) throws Exception{
		String selectSQL = "UPDATE `ebook` SET borrow_duration="+ duration +" WHERE productId=" + productId;
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.ECMSDBConfig, selectSQL);	
		int borrow_duration = Integer.valueOf(infos.get(0).get("borrow_duration").toString());	
		return borrow_duration;
	}
	
	//设置借阅本不可借阅
	public static void setBorrowDuration(long productId) throws Exception{
		String updateSQL = "UPDATE `ebook` SET borrow_duration=NULL WHERE productId=" + productId;
		DbUtil.executeUpdate(Config.ECMSDBConfig, updateSQL);	
		Thread.sleep(1000);
		log.info("【设置借阅本为不可借阅状态！】");
	}
	
	//设置用户书的借阅时长
	public static void setUserBorrowDuration(String custId, long productId, long duration) throws Exception{
		String updateSQL = "UPDATE `user_ebook` SET deadline="+ duration +" WHERE productId=" + productId + " AND CUST_ID="+custId;
		DbUtil.executeUpdate(Config.ECMSDBConfig, updateSQL);	
	}
	
	/**
	 * 
	 * @param token
	 * @param currentTime
	 * @return  -1代表没有返回合适的数据
	 * @throws Exception
	 */
	public static UserEbook getUserOverdueBorrowBook(String custId, long currentTime) throws Exception{
		String selectSQL = "SELECT productId, deadline FROM `user_ebook` WHERE CUST_ID="+ custId +" AND relation_type=1003 AND is_return_book in (0,2)";
		System.out.println("sfafsd" + selectSQL);
		List<UserEbook>  infos = DbUtil.selectList(Config.ECMSDBConfig, selectSQL, UserEbook.class);
		UserEbook returnUserEbook = null;
	//	long productId = -1;
		boolean flag = false;
		long tmpProductId;
		long deadline;
		Long borrowDuration;
		for(int i=0; i<infos.size(); i++){
			tmpProductId = infos.get(i).getProductId();
			deadline = infos.get(i).getDeadline();
			borrowDuration = getBorrowDuration(tmpProductId);		
			if(borrowDuration!=-1l){
				returnUserEbook = infos.get(i);
				if(currentTime>deadline){
					flag = true;
					return infos.get(i);
				}
			}
		}
		
		if(returnUserEbook.getProductId()!=-1l && flag == false){
			setUserBorrowDuration(custId, returnUserEbook.getProductId(), currentTime-120000);
		}
		
		return returnUserEbook;
	}
	
	
	
		
	//排序
	public void sort(){}
	//设置有效期
	public void setStartAndEnd(){}
	//下架书
	
	//设置ios价格
	public static void setIOSPrice(long productId){
		String updateSQL = "UPDATE ebook SET IOS_PRICE=NULL WHERE productId=" + productId;
		SessionUtil.executeUpdate(SessionUtil.getSession(), updateSQL, "");		
		log.info("【设置" + productId + "值为NULL】");
	}
	
	public static void main(String[] args) throws Exception{
		//ConfigCore.setDevice(TestDevice.IPHONE);
		//BookStoreTestEvnSQL.getAllColumnCode();
		//BookStoreTestEvnSQL.insertColumnData(ColumnNameEnum.borrowRead.toString(), 5);
		BookStoreTestEvnSQL.getUserOverdueBorrowBook("50098052",System.currentTimeMillis());
	}

}
