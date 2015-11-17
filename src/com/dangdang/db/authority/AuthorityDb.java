package com.dangdang.db.authority;

import com.dangdang.authority.meta.MediaAuthority;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-7-15.
 */
public class AuthorityDb {

    /*
    获取购买的书籍
     */
    public static List<MediaAuthority> getUserEbook(String custId,int num) throws Exception {
        Integer nCustId=Integer.parseInt(custId);
        String selectString = "select * from media_authority_"+nCustId%32+" where cust_id =  "+custId +" and relation_type!= 9999 and (authority_type =1 or (authority_type=2 and   order_no is not null and order_no!='' ))  limit  "+num;
        List<MediaAuthority> mediaAuthorities = DbUtil.selectList(Config.AUTHORITYConfig,selectString,MediaAuthority.class);
        return mediaAuthorities;
    }


    /*
    获取购买所有书籍、字体
     */
    public static List<MediaAuthority> getMediaAuthority(String custId) throws Exception {
        Integer nCustId=Integer.parseInt(custId);
        String selectString = "select * from media_authority_"+nCustId%32+" where cust_id =  "+custId +" and relation_type!= 9999 ORDER BY last_modified_date desc";
        List<MediaAuthority> mediaAuthorities = DbUtil.selectList(Config.AUTHORITYConfig,selectString,MediaAuthority.class);
        return mediaAuthorities;
    }


    /*
   获取购买的书籍信息
    */
    public static MediaAuthority getUserEbook(String custId,String productId) throws Exception {
        Integer nCustId=Integer.parseInt(custId);
        String selectString = "select * from media_authority_"+nCustId%32+" where cust_id =  "+custId +" and relation_type!= 9999 and authority_type=1 and product_id="+productId+" ORDER BY last_modified_date desc";
        MediaAuthority mediaAuthorities = DbUtil.selectOne(Config.AUTHORITYConfig,selectString,MediaAuthority.class);
        return mediaAuthorities;
    }
    
    //获取media_authority_0表中的custId
    //UserDeviceDb.java used
    //Add by guohaiying
    public static List<String> getCustIdList(String tableName) throws Exception{
    	String selectString = "SELECT DISTINCT(cust_id) FROM " +tableName;
        List<Map<String, Object>> infos = DbUtil.selectList(Config.AUTHORITYConfig,selectString);
        List<String> custIDs = new ArrayList<String>();
        for(int i=0; i<infos.size(); i++){
        	custIDs.add(infos.get(i).get("cust_id").toString());
        }
        return custIDs;    	
    }
    
    //获取所有media_authority_X表中的custId
    //UserDeviceDb.java used
    //Add by guohaiying
    public static List<String> getAllCustIdList() throws Exception{
    	List<String> all= new ArrayList<String>();
    	String[] tableNames = { "media_authority_0","media_authority_1","media_authority_2",
    			"media_authority_3","media_authority_4",
    			"media_authority_5","media_authority_6","media_authority_7","media_authority_8",
    			"media_authority_9","media_authority_10","media_authority_11","media_authority_12",
    			"media_authority_13","media_authority_14","media_authority_15","media_authority_16",
    			"media_authority_17","media_authority_18","media_authority_19","media_authority_20",
    			"media_authority_21","media_authority_22","media_authority_23","media_authority_24",
    			"media_authority_25","media_authority_26","media_authority_27",
    			"media_authority_28","media_authority_29","media_authority_30","media_authority_31"};
    	for(int i=0;i<tableNames.length; i++){
    		List<String> tmp = getCustIdList(tableNames[i]);
    		all.addAll(tmp);
    	}     
        return all;    	
    }
    
    //根据custId获取一本已购书籍
    //getTokenParse.java used
    public static String getUserAlreadyBuyBook(String custId) throws Exception{
    	int _custId = Integer.valueOf(custId);
    	String selectString = "SELECT product_id FROM media_authority_"+_custId%32+" WHERE cust_id="+custId+" AND platform_no=1002 AND authority_type IN(1,2) AND relation_type=1001 AND order_no IS NOT NULL;";
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.AUTHORITYConfig,selectString);   	
    	return infos.get(0).get("product_id").toString();
    }


}
