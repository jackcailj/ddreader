package com.dangdang.db.digital;

import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.ChannelOwner;

/**
 * 
 * @author guohaiying
 *
 */
public class ChannelOwnerDb {
	
    //根据owner_id获取type
	//MediaColumnContentDb.class used
    public static String getOwnerType(String ownerId) throws Exception{
    	int _ownerId = Integer.valueOf(ownerId);
    	String selectSQL="SELECT type FROM `channel_owner` WHERE owner_id=" + _ownerId;
    	List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
    	if(infos.size()==0)
    		return null;
    	else
    		return infos.get(0).get("type").toString();	
    }
    
    //根据type和status获取custId
    //type 1个人  2企业
    //status 0待审核    1审核不通过   2审核通过    -1未申请认证的用户
    public static String getCustIdWithTypeAndStatus(int type, int status) throws Exception{
    	String selectSQL="";
    	if(status!=-1){
    		selectSQL="SELECT cust_id FROM `channel_owner` WHERE type="+type+" AND `status`="+status;
    		List<ChannelOwner> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL,ChannelOwner.class);	
    		if(infos.size()==0) return null;
    		int n= (int)Math.random()*(infos.size()-1);
    		return infos.get(n).getCustId().toString();
    	}else{
    		selectSQL = "SELECT cust_id FROM `channel` WHERE cust_id NOT IN(SELECT cust_id FROM channel_owner)";
    		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
    		int n=(int)Math.random()*(infos.size()-1);
    		return infos.get(n).get("cust_id").toString();
    	}
    }
    
    public static void main(String[] args){
    	try {
			String s = ChannelOwnerDb.getCustIdWithTypeAndStatus(1,0);
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
