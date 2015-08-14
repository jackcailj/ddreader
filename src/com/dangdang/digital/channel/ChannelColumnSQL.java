package com.dangdang.digital.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.util.TelnetUtil;
import com.dangdang.digital.BookStoreTestEvnSQL;
import com.dangdang.readerV5.channel.IChannelApiDubbo;
import com.dangdang.readerV5.reponse.ChannelColumnReponse;
import com.dangdang.readerV5.reponse.ChannelList;


/**
 * 频道列表接口相关sql
 * @author guohaiying
 *
 */
public class ChannelColumnSQL {

	//获取频道栏目基本信息
    public static ChannelColumnReponse getChannelColumn(String columnCode, String custId, int num) throws Exception {          
    	String selectSQL = "SELECT column_code, is_show_horn, name, tips FROM media_column WHERE column_code='"+columnCode+"'";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		Map<String, Object> map = infos.get(0);		
		ChannelColumnReponse column = new ChannelColumnReponse();
    	column.setChannelList(getChannelList(columnCode,custId,num));
    	column.setColumnCode(map.get("column_code").toString());
    	column.setCount(getChannelList(columnCode,custId,num).size());
    	//column.setIsShowHorn(map.get("is_show_horn").toString());   	
    	column.setName(map.get("name").toString());
    	column.setTips(map.get("tips").toString());
    	column.setTotal(getTotal(columnCode));      
        return column;
    }
    
	//获取频道栏目下频道列表
    public static List<ChannelList> getChannelList(String columnCode, String custId, int num) throws Exception {
       
    	String selectSQL = "SELECT channel.channel_id,channel.owner_id,channel.title,channel.description,channel.icon,channel.sub_number	" +
    			" from media_column_content mcc left join channel on mcc.sale_id= channel.channel_id"+
				" where column_code ='"+columnCode+"'"+
				" and channel.shelf_status=1"+
				" and channel.is_completed=1"+
				" and  mcc.status in(1,2)"+
				" and  now() between start_date and end_date"+
				" order by mcc.status asc , IF(ISNULL(order_value),1,0) asc,order_value desc LIMIT "+num;      
        List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
        List<ChannelList> channelList = new ArrayList<ChannelList>();
        for(int i=0; i<infos.size(); i++){
        	ChannelList tmp = new ChannelList();
        	tmp.setChannelId(infos.get(i).get("channel_id").toString());
        	tmp.setDescription(infos.get(i).get("description").toString());
        	//设置hasBoughtMonthly  是否有包月权限  
        	if(custId==null)
        		tmp.setHasBoughtMonthly(0);  
        	else if(hasBoughtMonthly(custId, infos.get(i).get("channel_id").toString())==0){
        		tmp.setHasBoughtMonthly(0);  
        	}else 
        		tmp.setHasBoughtMonthly(1);  
        	//设置type
        	tmp.setOwnerType(getOwnerType(infos.get(i).get("owner_id").toString()));      	
        	tmp.setIcon(infos.get(i).get("icon").toString());
        	tmp.setSubNumber(infos.get(i).get("sub_number").toString());
        	tmp.setTitle(infos.get(i).get("title").toString());       	
        	channelList.add(tmp);
        }
        return channelList;
    }
    
    //判断用户对某频道是否有包月权限
    public static int hasBoughtMonthly(String custId, String channel) throws Exception{
    	int _custId = Integer.valueOf(custId);
    	int _channelId = Integer.valueOf(channel);
    	String selectSQL="SELECT count(1) FROM `media_monthly_authority` " +
    			" WHERE  platform_no='1002' " +
    			" AND monthly_type=2 " +
    			" AND cust_id= " +_custId +
    			" AND relation_id=" + _channelId;
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.AUTHORITYConfig, selectSQL);	
		return Integer.valueOf(infos.get(0).get("count(1)").toString());	
    }
    
    //获取某个频道栏目下频道总数量（没有书单的频道不计数）
    public static String getTotal(String columnCode) throws Exception{
       	String selectSQL = "SELECT count(*)	" +
       		" from media_column_content mcc left join channel on mcc.sale_id= channel.channel_id"+
       		" where column_code ='"+columnCode+"'"+
       		" and channel.shelf_status=1"+
       		" and channel.is_completed=1"+
       		" and  mcc.status in(1,2)"+
       		" and  now() between start_date and end_date";  
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return infos.get(0).get("count(*)").toString();	
    }
    
    //根据owner_id获取type
    public static String getOwnerType(String ownerId) throws Exception{
    	int _ownerId = Integer.valueOf(ownerId);
    	String selectSQL="SELECT type FROM `channel_owner` WHERE owner_id=" + _ownerId;
    	List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
    	if(infos.size()==0)
    		return null;
    	else
    		return infos.get(0).get("type").toString();	
    }
    
    //获取channel_sub_user表中的频道订阅数
    public static String getChannelSub(String channelId) throws Exception{
    	Long _channelId = Long.valueOf(channelId);
    	String selectSQL = "SELECT count(*) " +
    			"FROM `channel_sub_user` " +
    			"WHERE type=1 AND channel_id=" + _channelId;
     	List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return infos.get(0).get("count(*)").toString();	
    }
    
    
    /*+++++++++++++++++++++++++++++++++++++++++++++后台-》推荐列表+++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    
	/**
	 * 创建频道
	 * @param name 在哪个栏目下创建频道栏目
	 * @param code 频道栏目code
	 * @return 返回column_code值
	 */
	public static String createWithNameAndCode(String name, String code) throws Exception{		
		return BookStoreTestEvnSQL.createWithNameAndCodeAndType(name, code, 1);
	}	
	
	//往频道栏目中添加频道	
	public static void insertChannelWithColumncodeAndSize(String columncode, int size) throws Exception{
		String selectSQL;
		selectSQL = "SELECT column_id FROM `media_column` WHERE column_code='"+columncode+"'";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		int columnID = Integer.valueOf(infos.get(0).get("column_id").toString());	
		
		selectSQL="SELECT channel_id, title " +
		"FROM channel " +
		"WHERE shelf_status=1 " +
		"LIMIT "+ size;
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);		
		String insertSQL = "INSERT INTO media_column_content(column_code, column_id, sale_id, sale_name, creation_date, start_date, end_date, status, order_value) values";
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<result.size(); i++){
			Map<String, Object> temp = result.get(i);
			int sale_id = Integer.valueOf(temp.get("channel_id").toString());
			String name = temp.get("title").toString();
			if(name.contains("'"))
				name = name.replace("'","''");
			list.add(sale_id);
			insertSQL += "('"+ columncode +"',"+columnID+", "+ sale_id +", '"+ name +"',now(),now(),DATE_ADD(now(),INTERVAL 1 YEAR)"+", 2, "+i+"+1),";
		}
		DbUtil.executeUpdate(Config.YCDBConfig, insertSQL.substring(0, insertSQL.lastIndexOf(",")));
		BookStoreTestEvnSQL.refreshCache();
	}
	
	//往频道书单中添加出版物的书
	
	//往频道书单中添加原创的书
	
	//往频道书单中添加纸质书
	
	//获取xx栏目下记录数量
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
	
	//判断xx栏目是否为空
//	public static boolean isColumnNull(String columnCode) throws Exception{
//		String selectSQL = "SELECT sale_id,sale_name FROM `media_column_content` WHERE column_code='" +columnCode+"' AND `status` in (1,2) AND end_date> NOW()";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		int n = result.size();
//		if(n==0) return false;
//		else return true;
//	}

	
	//上下架频道 shelf_statue:1上架   0：下架
	public static String setStatusWithChannelAndStatus(String channelId, String status) throws Exception{
		Long _channelId = Long.valueOf(channelId);
		int _status = Integer.valueOf(status);
		String updateSQL="UPDATE channel SET shelf_status="+_status+", apply_times = apply_times+1 WHERE channel_id=" +_channelId;
		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
		refreshCache();
		return channelId;
	}
	
	public static String setShelfStatusWithColumnCode(String columnCode) throws Exception{
		//随机获取频道栏目下的某个频道
		String selectSQL = "SELECT sale_id FROM `media_column_content` " +
				"WHERE column_code='"+columnCode+"' " +
				"ORDER BY RAND() " +
				"LIMIT 1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		String channelId = infos.get(0).get("sale_id").toString();
		return setStatusWithChannelAndStatus(channelId, "0");
	}
	
	//模拟后台，删除xx栏目
	public static void delColumnWithColumncode(String columncode) throws Exception{
		String delSQL = "DELETE FROM `media_column` WHERE column_code ='" +columncode+"'";
		DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
		refreshCache();
	}
	
	//模拟后台，删除xx栏目下数据
	public static int delColumnDataWithColumncode(String columncode) throws Exception{
		int sale_id = getRandChannel(columncode);
		String delSQL = "DELETE FROM `media_column_content` WHERE sale_id=" +sale_id;
		DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
		refreshCache();
		return sale_id;
	}
//	
//	//模拟后台，删除xx栏目下所有数据
//	public static void delColumnAllData(String columnCode) throws Exception{
//		String delSQL = "DELETE FROM `media_column_content` WHERE column_code='" +columnCode + "'";
//		DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
//		refreshCache();
//	}
	
	//随机返回xx栏目下的一个频道
	public static int getRandChannel(String columnCode) throws Exception{
		String selectSQL = "SELECT sale_id,sale_name FROM `media_column_content`" +
				" WHERE column_code='" +columnCode + "'" +
						" AND `status` in (1,2) " +
						"AND end_date>NOW()  " +
						"AND sale_id IN (" +
						"SELECT channel_id " +
						"FROM channel " +
						"WHERE shelf_status=1 " +
						"AND is_completed=1)" +
						" order by rand() limit 1";
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		Map<String, Object> m = result.get(0);
		int sale_id = Integer.parseInt(m.get("sale_id").toString());
		return sale_id;
	}
	
	//随机返回xx栏目下一条数据
//	public static int getRandMediaSale() throws Exception{		
//		String selectSQL = "SELECT sale_id FROM `media_sale` WHERE shelf_status=1 ORDER BY RAND() LIMIT 1";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
//		Map<String, Object> m = result.get(0);
//		int sale_id = Integer.parseInt(m.get("sale_id").toString());
//		return sale_id;
//	}
	
	
	//模拟后台，下架某本书
	public static int setShelfStatusWithColumncode(String columncode) throws Exception{
		int sale_id = getRandChannel(columncode);
		String updateSQL = "UPDATE `media_sale` SET shelf_status=0 WHERE sale_id= "+ sale_id;
		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
		refreshCache();
		return sale_id;
	}
	
	//模拟后台，上架某本书
	public static int setShelfStatusWithSaleid(int saleid) throws Exception{
		String updateSQL = "UPDATE `media_sale` SET shelf_status=1 WHERE sale_id= "+ saleid;
		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
		refreshCache();
		return saleid;
	}
	
	//删除频道下的书单
	public static void delBookListWithChannelId(String channelId) throws Exception{
		Long _channelId = Long.valueOf(channelId);
		String selectSQL="SELECT booklist_id FROM `media_booklist` WHERE channel_id="+_channelId;
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		int booklistId;
		if(infos.size()==0) return;
		else{
			booklistId=Integer.valueOf(infos.get(0).get("booklist_id").toString());
		}
		
		String delSQL = "DELETE FROM `media_booklist` WHERE channel_id="+_channelId;
		DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
		
		delSQL = "DELETE FROM `media_booklist_detail` WHERE booklist_id=" + booklistId;
		DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
		refreshCache();
	} 
	
	//模拟后台，设置书为强制有效
	public static int setEffective(String columnCode) throws Exception{
		setNormal(columnCode);
		int sale_id = getRandChannel(columnCode);
		String updateSQL = "UPDATE media_column_content SET status=1 WHERE sale_id="+ sale_id +" and column_code='" +columnCode+ "'";
		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
		refreshCache();
		return sale_id;
	}

	//模拟后台，设置书为强制无效
	public static int setInvalidWithColumncode(String columncode) throws Exception{
		int sale_id = getRandChannel(columncode);
		String updateSQL = "UPDATE media_column_content SET status=0 WHERE sale_id="+ sale_id +" and column_code='" +columncode+ "'";
		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
		refreshCache();
		return sale_id;
	}
	
	//模拟后台，设置书为正常显示
	public static void setNormal(String columncode) throws Exception{
		String updateSQL = "UPDATE media_column_content SET status=2 WHERE column_code='" +columncode+ "'";
		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
		refreshCache();
	}
	
	//模拟后台，设置书的有效期
	public static int setEffectiveWithColumncode(String columncode) throws Exception{
		int sale_id = getRandChannel(columncode);
		String updateSQL = "UPDATE media_column_content " +
				"SET end_date=DATE_ADD(now(),INTERVAL 2 MINUTE) " +
				"WHERE sale_id="+ sale_id +" and column_code='" +columncode+ "'";
		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
		refreshCache();
		Thread.sleep(12000);
		return sale_id;
	}
	
	//返回最高排序值
	public static int setSortWithColumncode(String columncode) throws Exception{
		//setNormal(columncode);
		//随机获取一本书
		int sale_id = getRandChannel(columncode);		
		String selectSQL = "SELECT MAX(order_value) FROM `media_column_content` WHERE column_code='"+columncode+"'";
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		Map<String, Object> m = result.get(0);
		int value = Integer.valueOf(m.get("MAX(order_value)").toString());		
		String updateSQL = "UPDATE `media_column_content` SET order_value = "+ (value+1)+" WHERE sale_id=" + sale_id;
		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);		
		refreshCache();
		return sale_id;
	}
	
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
	
	public static void refreshCache() throws Exception{
		TelnetUtil telnet = new TelnetUtil();
		//telnet.sendCommands("select 10", "flushdb");
		telnet.sendCommands("select 10", "flushall");
		Thread.sleep(2000);
	}
	
    public static void main(String[] args) throws Exception{
    	refreshCache();
 
    }
    
}
