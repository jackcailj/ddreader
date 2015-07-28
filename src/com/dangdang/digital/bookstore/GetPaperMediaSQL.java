package com.dangdang.digital.bookstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.reponse.Media;

public class GetPaperMediaSQL {
	
	//频道书单下添加纸书
	//public static 
	
	//查询某个频道书单下的纸书id
	public static List<String> getBookListWithChannelId(String channelid) throws Exception{
		int _channelId = Integer.valueOf(channelid);
		String selectSQL = "SELECT mbd.product_id " +
				"FROM `media_booklist` mb,media_booklist_detail mbd, channel c " +
				"WHERE c.channel_id=mb.channel_id " +
				"AND mb.booklist_id=mbd.booklist_id " +
				"AND mbd.type=3 " +
				"AND c.channel_id="+_channelId;
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		List<String> list = new ArrayList<String>();
		for(int i=0; i<list.size(); i++){
			list.add(infos.get(i).get("product_id").toString());
		}
		return list;
	}
	
	//获取某个频道书单以外的某本纸书id
	public static String getOnePaperMedia(String channelId) throws Exception{
		List<String> poductIdList = getBookListWithChannelId(channelId);
		String productIdString = "";
		for(int i=0;i<poductIdList.size(); i++){
			productIdString += poductIdList.get(i) + ",";
		}
		System.out.println(productIdString);
		String selectSQL = "SELECT product_id " +
				"FROM `bar_product_info` " +
				"WHERE type=2 AND product_id NOT IN("+productIdString.substring(0, productIdString.length()-1)+") " +
						"ORDER BY RAND() LIMIT 1";
		
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
	 	return infos.get(0).get("product_id").toString();
	}
	
	//根据productId获取纸书信息
	public static Media getPaperMediaWithProductid(String productid) throws Exception{
		int _productId = Integer.valueOf(productid);
		String selectSQL = "SELECT book_isbn, book_price,product_id,publisher,product_name " +
				"FROM `bar_product_info` " +
				"WHERE product_id="+_productId;
		Media media = new Media();
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		media.setIsbn(infos.get(0).get("book_isbn").toString());
		media.setMediaType("3");
		media.setPrice("book_price");
		media.setProductId("product_id");
		media.setPublisher("publisher");
	 	media.setTitle("product_name");
	 	return media;
	}
	
	//根据登录账号获取频道id
	public static String getChannelIdWithUser(String user) throws Exception{
		String selectSQL = "SELECT channel.channel_id " +
				"FROM `channel_owner` , channel " +
				"WHERE channel_owner.cust_id=channel.cust_id " +
				"AND user_accout='"+user+"'";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
	 	return infos.get(0).get("channel_id").toString();
		
	}
	
	public static void main(String[] args) throws Exception{
		String s = GetPaperMediaSQL.getOnePaperMedia("78");
		System.out.println(s);
	}

}
