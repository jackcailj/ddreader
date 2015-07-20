package com.dangdang.digital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaColumn;
import com.dangdang.readerV5.reponse.BorrowReponse;
import com.dangdang.readerV5.reponse.BorrowSaleList;
import com.dangdang.readerV5.reponse.EbookMediaList;
import com.dangdang.readerV5.reponse.FreeForLimitedReponse;
import com.dangdang.readerV5.reponse.GetMediaReponse;
import com.dangdang.readerV5.reponse.MediaList;
import com.dangdang.readerV5.reponse.MediaSale;
import com.dangdang.readerV5.reponse.SaleList;
import com.dangdang.readerV5.reponse.SpecialTopicHistoryReponse;
import com.dangdang.readerV5.reponse.SpecialTopicList;
import com.dangdang.readerV5.reponse.ColumnReponse;

/**
 * 当当读书5.0 - 书城 相关查询SQL
 * @author guohaiying
 *
 */
public class BookStoreCommSQL {
	static Logger log = Logger.getLogger(BookStoreCommSQL.class);
	
	//获取可借阅的书
	public static String getBorrowBook() throws Exception{
		String selectSQL = "SELECT ms.sale_id FROM media m, media_sale ms WHERE m.sale_id=ms.sale_id AND ms.shelf_status=1 AND m.shelf_status=1 AND m.borrow_duration IS NOT NULL AND doc_type='EBOOK' LIMIT 1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return infos.get(0).get("sale_id").toString();
	}
	
	/**
	 * 根据栏目name获取栏目数据 
	 * @param Name 
	 * @param channel 频道
	 * @throws Exception 
	 */
	public static List<MediaColumn> getColumnByName(String name, String channel) throws Exception{
		String selectSQL = "SELECT * FROM `media_column` " +
				"WHERE name='"+name+"' AND channel='"+channel+"'";
		List<MediaColumn> columnList = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaColumn.class);
		return columnList;
	}
	
	public static List<MediaColumn> getColumnByCode(String code) throws Exception{
		String selectSQL = "SELECT * FROM `media_column` WHERE column_code='"+code+"'";
		List<MediaColumn> columnList = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaColumn.class);
		return columnList;
	}
	
	/**
	 * 根据栏目名称获取栏目下的所有子栏目
	 * @param args
	 * @throws Exception
	 */
	public static List getSubColumn(String name, String channel) throws Exception{
		List<MediaColumn> list = getColumnByName(name, channel);
		int id = list.get(0).getColumnId();
		String selectSQL = "SELECT * FROM media_column WHERE parent_id="+id;
		List<MediaColumn> columnList = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaColumn.class);
		List l = new ArrayList<String>();
		for(int i=0; i<columnList.size(); i++){
			l.add(columnList.get(i).getName());
			l.add(columnList.get(i).getCode());
			l.add(columnList.get(i).getChannel());
		}
		return l;
	}
	
	public static Map<String, String> getSubColumn(String code) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		List<MediaColumn> list = getColumnByCode(code);
		int id = list.get(0).getColumnId();
		String selectSQL = "SELECT * FROM media_column WHERE parent_id="+id;
		List<MediaColumn> columnList = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaColumn.class);
		for(int i=0;i<columnList.size();i++){
			map.put(columnList.get(i).getName(), columnList.get(i).getCode());
		}
		return map;
	}
	
	/**************************************** 借阅、限免、栏目结果验证   ****************************************/
	
	//获取栏目数据
	public static ColumnReponse getColumnReponse(String columnCode, int size) throws Exception{ //all_pub_borrow_free
		String selectSQL= "SELECT column_code,icon, is_show_horn,name,tips " +
				"FROM media_column " +
				"WHERE column_code='"+columnCode+"'";
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		ColumnReponse reponse = new ColumnReponse();
		List<SaleList> saleList = new ArrayList<SaleList>();
		saleList = getSaleList(columnCode, size);
		reponse.setColumnCode(result.get(0).get("column_code").toString());
		//设置count
		reponse.setCount(String.valueOf(saleList.size()));
		//reponse.setIcon(result.get(0).get("icon").toString());
		reponse.setIsShowHorn(result.get(0).get("is_show_horn").toString());
		reponse.setName(result.get(0).get("name").toString());
		reponse.setSaleList(saleList);
		reponse.setTips(result.get(0).get("tips").toString());		
		//设置total
		reponse.setTotal(getSaleListCount(columnCode)); 
		return reponse;
	}
	
	//获取栏目saleList
	public static List<SaleList> getSaleList(String columnCode, int size) throws Exception{ //all_pub_borrow_free
		String selectSQL = "SELECT ms.is_support_full_buy,ms.price,ms.sale_id, ms.type " +
				" from media_column_content mcc,media_sale ms"+
				 " where column_code ='"+columnCode+"'"+
				 " and  status in(1,2)"+
				 " and  now() between start_date and end_date"+
				 " and mcc.sale_id = ms.sale_id"+
				 " and ms.shelf_status =1"+
				 " order by status asc , IF(ISNULL(order_value),1,0) asc,order_value desc limit "+size;
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		List<SaleList> saleList = new ArrayList<SaleList>();
		List<MediaList> mediaList = new ArrayList<MediaList>();
		for(int i=0; i<result.size(); i++){
			SaleList tmp = new SaleList();
			tmp.setIsStore("0");
			tmp.setIsSupportFullBuy(result.get(i).get("is_support_full_buy").toString());
			tmp.setPrice(result.get(i).get("price").toString());
			tmp.setSaleId(result.get(i).get("sale_id").toString());
			tmp.setType(result.get(i).get("type").toString());
			mediaList = getMediaList(Integer.valueOf(result.get(i).get("sale_id").toString()));
			tmp.setMediaList(mediaList);
			saleList.add(tmp);
		}
		return saleList;
	}
	
	//获取栏目saleList的数量
	public static String getSaleListCount(String columnCode) throws Exception{
		String selectSQL = "SELECT count(*) " +
		" from media_column_content mcc,media_sale ms"+
		" where column_code ='"+columnCode+"'"+
		" and  status in(1,2)"+
		" and  now() between start_date and end_date"+
		" and mcc.sale_id = ms.sale_id"+
		" and ms.shelf_status =1";		 
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return result.get(0).get("count(*)").toString();		
	}
	
	//获取栏目MediaList
	public static List<MediaList> getMediaList(int saleID) throws Exception{
		String selectSQL = "SELECT author_id,author_penname,chapter_cnt, cover_pic,descs,is_full, media_id,recommand_words,sale_id,title,doc_type " +
				"FROM `media` " +
				"WHERE sale_id ="+saleID+" AND shelf_status=1";//AND is_show=1
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		List<MediaList> mediaList = new ArrayList<MediaList>();
		MediaList tmp = new MediaList();
		if(result.size()==0) {
			log.info("saleID="+saleID+"的MediaList列表为空");
			return null;
		}
		for(int i=0; i<result.size(); i++){
			if(result.get(i).get("author_id")==null)
				tmp.setAuthorId(null);
			else
				tmp.setAuthorId(result.get(i).get("author_id").toString());
			if(result.get(i).get("author_penname")==null)
				tmp.setAuthorPenname(null);
			else
				tmp.setAuthorPenname(result.get(i).get("author_penname").toString());
			
			//设置categoryIds
//			Map<String, String> map = getCategory(Integer.valueOf(result.get(i).get("media_id").toString()));
//			tmp.setCategoryIds(map.get("code"));
//			tmp.setCategorys(map.get("name"));
//			if(result.get(i).get("chapter_cnt")==null)
//				tmp.setChapterCnt(null);
//			else
//				tmp.setChapterCnt(result.get(i).get("chapter_cnt").toString());
			//tmp.setCoverPic(result.get(i).get("cover_pic").toString());
			tmp.setDescs(result.get(i).get("descs").toString());
			tmp.setIsFull(result.get(i).get("is_full").toString());
			//是否收藏
			tmp.setIsStore("0");
			tmp.setMediaId(result.get(i).get("media_id").toString());
			//设置mediaType
			if(result.get(i).get("doc_type")==null)
				tmp.setMediaType("1");
			else
				tmp.setMediaType("2");
				
			if(result.get(i).get("recommand_words")==null)
				tmp.setRecommandWords("");
			else
				tmp.setRecommandWords(result.get(i).get("recommand_words").toString());
			tmp.setSaleId(result.get(i).get("sale_id").toString());
			tmp.setTitle(result.get(i).get("title").toString());
			mediaList.add(tmp);
		}
		return mediaList;
	}
	
	//获取免费数据
	public static FreeForLimitedReponse getFreeForLimitedReponse(String columnCode, int size) throws Exception{ //all_pub_borrow_free
		String selectSQL= "SELECT code,icon, is_show_horn,name,tips " +
				"FROM media_column " +
				"WHERE column_code='"+columnCode+"'";
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		FreeForLimitedReponse reponse = new FreeForLimitedReponse();
		List<BorrowSaleList> saleList = new ArrayList<BorrowSaleList>();
		saleList = getBorrowSaleList(columnCode, size);
		reponse.setColumnCode(result.get(0).get("code").toString());
		//设置count
		reponse.setCount(String.valueOf(saleList.size()));
		//reponse.setIcon(result.get(0).get("icon").toString());
		reponse.setIsShowHorn(result.get(0).get("is_show_horn").toString());
		reponse.setName(result.get(0).get("name").toString());
		reponse.setSaleList(saleList);
		reponse.setTips(result.get(0).get("tips").toString());		
		//设置total
		reponse.setTotal(getBorrowSaleListCount(columnCode)); 
		return reponse;
	}
	
	//获取借阅数据
	public static BorrowReponse getBorrowReponse(String columnCode, int size) throws Exception{ //all_pub_borrow_free
		String selectSQL= "SELECT code,icon, is_show_horn,name,tips " +
				"FROM media_column " +
				"WHERE column_code='"+columnCode+"'";
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		BorrowReponse reponse = new BorrowReponse();
		List<BorrowSaleList> saleList = new ArrayList<BorrowSaleList>();
		saleList = getBorrowSaleList(columnCode, size);
		reponse.setColumnCode(result.get(0).get("code").toString());
		//设置count
		reponse.setCount(String.valueOf(saleList.size()));
		//reponse.setIcon(result.get(0).get("icon").toString());
		reponse.setIsShowHorn(result.get(0).get("is_show_horn").toString());
		reponse.setName(result.get(0).get("name").toString());
		reponse.setSaleList(saleList);
		reponse.setTips(result.get(0).get("tips").toString());		
		//设置total
		reponse.setTotal(getBorrowSaleListCount(columnCode)); 
		return reponse;
	}
	
	//获取借阅saleList
	public static List<BorrowSaleList> getBorrowSaleList(String columnCode, int size) throws Exception{ //all_pub_borrow_free
		String selectSQL = "SELECT ms.is_support_full_buy,ms.price,ms.sale_id, ms.type " +
				" from media_column_content mcc,media_sale ms"+
				 " where column_code ='"+columnCode+"'"+
				 " and  status in(1,2)"+
				 " and  now() between start_date and end_date"+
				 " and mcc.sale_id = ms.sale_id"+
				 " and ms.shelf_status =1"+
				 " order by IF(ISNULL(order_value),1,0) asc,order_value desc limit "+size;
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		List<BorrowSaleList> saleList = new ArrayList<BorrowSaleList>();
		List<MediaList> mediaList = new ArrayList<MediaList>();
		for(int i=0; i<result.size(); i++){
			BorrowSaleList tmp = new BorrowSaleList();
			tmp.setIsStore("0");
			tmp.setIsSupportFullBuy(result.get(i).get("is_support_full_buy").toString());
			tmp.setPrice(result.get(i).get("price").toString());
			tmp.setSaleId(result.get(i).get("sale_id").toString());
			tmp.setType(result.get(i).get("type").toString());
			mediaList = getMediaList(Integer.valueOf(result.get(i).get("sale_id").toString()));
			tmp.setMediaList(mediaList);
			saleList.add(tmp);
		}
		return saleList;
	}
	
	//获取借阅saleList的数量
	public static String getBorrowSaleListCount(String columnCode) throws Exception{
		String selectSQL = "SELECT count(*) " +
		" from media_column_content mcc,media_sale ms"+
		" where column_code ='"+columnCode+"'"+
		" and  status in(1,2)"+
		" and  now() between start_date and end_date"+
		" and mcc.sale_id = ms.sale_id"+
		" and ms.shelf_status =1";		 
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return result.get(0).get("count(*)").toString();		
	}
	
	//根据media_id查询 书分类的code和name
	public static Map<String, String> getCategory(int mediaID) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		String selectSQL= "SELECT code, name FROM `media_catetory` where catetory_id IN ( " +
				"SELECT catetory_id " +
				"FROM `media_book_catetory` " +
				"WHERE media_id="+mediaID+") ";
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		if(result.size()==0){
			map.put("code",null);
			map.put("name",null);
			return map;
		}
		String code="";
		String name="";
		for(int i=0;i<result.size();i++){
			code +=result.get(i).get("code").toString()+",";
			name +=result.get(i).get("name").toString()+",";
		}
		map.put("code",code.substring(0, code.lastIndexOf(",")));
		map.put("name",name.substring(0, name.lastIndexOf(",")));
		return map;
	}
	/*************************************************** 块  ***********************************************/
	public static String getBlockReponse(String code) throws Exception{//rec_ads_head
		String selectSQL= "SELECT * FROM `media_block` WHERE code='"+code+"'";
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		
		return null;// result.get(0).get().toString();
	}
	
	
	/****************************************** 单品页  ******************************************************/
	
	//是否收藏
	public static String isStore(int mediaID, int custID) throws Exception{
		String selectSQL= "SELECT count(*) " +
				"FROM `media_storeup` " +
				"WHERE target_id="+mediaID+" AND cust_id="+custID+
				" AND platform='DDDS-P'";;//Config.getCommonParam().get("platformSource")
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		int size = Integer.valueOf(result.get(0).get("count(*)").toString());
		if(size==1) 
			return "1";
		else
			return "0";
	}
	
	/****************************************** 专题  ******************************************************/
	//获取某历史专题下专题信息
	public static SpecialTopicHistoryReponse getSpecialTopicHistory(String deviceType) throws Exception{
		String selectSQL = "SELECT name, pic_path, st_id " +
				"FROM `media_special_topic` " +
				"WHERE device_type='"+deviceType+"'" +
				" AND `status`=1";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		List<SpecialTopicList> specialTopicList = new ArrayList<SpecialTopicList>();	
		int size = infos.size();
		for(int i=0; i<size; i++){
			SpecialTopicList tmp = new SpecialTopicList();
			tmp.setName(infos.get(i).get("name").toString());
			if(infos.get(i).get("pic_path")==null)
				tmp.setPicPath(null);
			else
				tmp.setPicPath(infos.get(i).get("pic_path").toString());
			tmp.setStId(infos.get(i).get("st_id").toString());
			specialTopicList.add(tmp);
		}
		SpecialTopicHistoryReponse reponse = new SpecialTopicHistoryReponse();
		reponse.setCount(String.valueOf(size));
		reponse.setSpecialTopicList(specialTopicList);
		reponse.setTotal(getSpecialTopicHistoryTotal(deviceType));
		
		return reponse;		
	}
	
	//获取某历史专题下专题总数
	public static String getSpecialTopicHistoryTotal(String deviceType) throws Exception{
		String selectSQL = "SELECT count(*) " +
				"FROM `media_special_topic` " +
				"WHERE device_type='"+deviceType+"' " +
						"AND `status`=1";
		  List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		  return infos.get(0).get("count(*)").toString();
	}
	
	public static GetMediaReponse getMediaReponse(int saleID, int custID)throws Exception{
		String selectSQL = "SELECT price,sale_id,type FROM `media_sale` WHERE sale_id="+saleID;
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		GetMediaReponse  reponse = new GetMediaReponse();
		MediaSale mediaSale = new MediaSale();
		//设置isStore
		mediaSale.setIsStore("0");
		mediaSale.setMediaList(getMedia(saleID, custID));
		mediaSale.setPrice(infos.get(0).get("price").toString());
		mediaSale.setSaleId(infos.get(0).get("sale_id").toString());
		mediaSale.setType(infos.get(0).get("type").toString());
		reponse.setMediaSale(mediaSale);
		return reponse;	
	}
	
	//获取电子书单品页信息
	public static List<EbookMediaList> getMedia(int saleID, int custID) throws Exception{
		String selectSQL = "SELECT author_id,author_penname,category,chapter_cnt,cover_pic,descs," +
				"promotion_id,is_bn,media_id,paper_book_price,paper_book_id,price,publisher,sale_id," +
				"shelf_status,title,word_cnt,doc_type,borrow_duration " +
				"FROM `media` " +
				"WHERE sale_id="+saleID;
		 List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		 List<EbookMediaList> list = new ArrayList<EbookMediaList>();
		 for(int i=0;i<infos.size(); i++){
			 int mediaID = Integer.valueOf(infos.get(0).get("media_id").toString());
			 EbookMediaList ebookMediaList = new EbookMediaList();
			 ebookMediaList.setAuthorId(infos.get(0).get("author_id").toString());
			 ebookMediaList.setAuthorPenname(infos.get(0).get("author_penname").toString());		 
			 //设置canBorrow字段
			 if(infos.get(0).get("borrow_duration")==null){
				 ebookMediaList.setCanBorrow("0");
			 }else{
				 ebookMediaList.setCanBorrow("1");
			 }		 
			 ebookMediaList.setCategory(infos.get(0).get("category").toString());
			 //设置categoryIds
			 Map<String, String> map = getCategory(Integer.valueOf(infos.get(0).get("media_id").toString()));
			 ebookMediaList.setCategoryIds(map.get("code"));
			 ebookMediaList.setCategorys(map.get("name"));
			 if(infos.get(0).get("chapter_cnt")==null)
				 ebookMediaList.setChapterCnt(null);
			 else
				 ebookMediaList.setChapterCnt(infos.get(0).get("chapter_cnt").toString());
			 //ebookMediaList.setCoverPic(infos.get(0).get("cover_pic").toString());
			 ebookMediaList.setDescs(infos.get(0).get("descs").toString());
			 //设置fileSize
			 ebookMediaList.setFileSize(getFileSize(mediaID, "epub"));
			 //设置freeBook
			 if(infos.get(0).get("price").toString().equals("0"))
				 ebookMediaList.setFreeBook("1");
			 else if(infos.get(0).get("promotion_id") !=null && infos.get(0).get("promotion_id").toString().equals("3"))
				 ebookMediaList.setFreeBook("1");
			 else
				 ebookMediaList.setFreeBook("0");
			 //设置freeFileSize
		 
			 //设置isChannelMonth
		 
			 //设置isChapterAuthority
		 
			 //设置isStore		
			 ebookMediaList.setIsStore(isStore(mediaID, custID));
			 //设置isSupportDevice
			 ebookMediaList.setIsSupportDevice(isSupportDevice(mediaID));
			 //设置isWholeAuthority
		 
			 ebookMediaList.setIsbn(infos.get(0).get("is_bn").toString());
			 ebookMediaList.setMediaId(infos.get(0).get("media_id").toString());
			 //设置mediaType
			 if(infos.get(0).get("doc_type")==null)
				 ebookMediaList.setMediaType("1");
			 else
				 ebookMediaList.setMediaType("2");
			 if(infos.get(0).get("paper_book_price")==null)
				 ebookMediaList.setPaperMediaPrice(null);
			 else
				 ebookMediaList.setPaperMediaPrice(infos.get(0).get("paper_book_price").toString());
			 if(infos.get(0).get("paper_book_id")==null)
				 ebookMediaList.setPaperMediaProductId(null);
			 else
				 ebookMediaList.setPaperMediaProductId(infos.get(0).get("paper_book_id").toString());
			 ebookMediaList.setPrice(infos.get(0).get("price").toString());
			 ebookMediaList.setPublisher(infos.get(0).get("publisher").toString());
			 ebookMediaList.setSaleId(infos.get(0).get("sale_id").toString());		 
			 //设置score
		 
			 ebookMediaList.setShelfStatus(infos.get(0).get("shelf_status").toString());
			 ebookMediaList.setTitle(infos.get(0).get("title").toString());
			 ebookMediaList.setWordCnt(infos.get(0).get("word_cnt").toString());
			 list.add(ebookMediaList);
		 }
		 return list;
	}
	
	//获取fileSize
	public static String getFileSize(int mediaID, String type) throws Exception{
		String selectSQL = "SELECT SIZE FROM `resfile` WHERE MEDIA_ID="+mediaID+" AND TYPE='"+type+"'";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return infos.get(0).get("SIZE").toString();
	}
	
	//
	public static String isSupportDevice(int mediaID) throws Exception{
		String selectSQL = "SELECT DEVICE_TYPE_NAME FROM `media_resfile` WHERE MEDIA_ID="+mediaID;
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		for(int i=0;i<infos.size(); i++){
			if(infos.get(i).get("DEVICE_TYPE_NAME").toString().contains(Config.getCommonParam().get("deviceType")))
				return "1";
		}
		return "0";
	}
	
	//查询栏目内容	
	public static void main(String[] args) throws Exception{
		//BookStoreCommSQL.getColumnByName("vp_byzq", "vp");
		SpecialTopicHistoryReponse p = BookStoreCommSQL.getSpecialTopicHistory("DDDS_ALL");
		//System.out.println(BookStoreCommSQL.getMediaList(1980125886).size());
		System.out.println(p.getSpecialTopicList().get(0).getStId());
		System.out.println(p.getSpecialTopicList().get(1).getStId());
	
	}
}
