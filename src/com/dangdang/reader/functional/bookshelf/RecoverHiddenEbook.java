package com.dangdang.reader.functional.bookshelf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.Assert;

import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.Login;
import com.dangdang.ddframework.dataverify.RecordExVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ecms.meta.UserEbookStatus;

public class RecoverHiddenEbook extends HiddenEbook {
	
	public RecoverHiddenEbook(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("recoverHiddenEbook");
	}
	
	public RecoverHiddenEbook() {
		
	}
	
	@Override
	protected void genrateVerifyData() throws Exception {    
		
	}
	
	@Override
	public void doWorkAndVerify() throws Exception {
		String custId = Login.getCusId(userName);
        String sql="";
        custId = (custId==null) ? "\'\'" : custId;
    	String ids = paramMap.get("productIds");    	
    	String[] pIds = (ids==null || ids.isEmpty()) ? new String[] {"\'\'"} : ids.split(",");
    	Map<String, Long[]> map1 = new HashMap<String, Long[]>();
		for(int i=0; i<pIds.length; i++){
			sql = "from user_ebook_status where cust_id="+custId+ " and product_id="+pIds[i]+" order by creation_date desc";
			map1.put(pIds[i], getMaxIDAndStatus(Config.ECMSDBConfig, "ebook_status_id", sql));
		}	
		
		doWork();
		Thread.sleep(2000);
		
		if(reponseResult.getStatus().getCode()==0){
			//The maxId and status after doing work
			Map<String, Long[]> map2 = new HashMap<String, Long[]>();
			for(int i=0; i<pIds.length; i++){
				sql = "from user_ebook_status where cust_id="+custId+ " and product_id="+pIds[i]+" order by creation_date desc";
				map2.put(pIds[i], getMaxIDAndStatus(Config.ECMSDBConfig, "ebook_status_id", sql));
			}
			
			//compare
			Iterator<Map.Entry<String, Long[]>> it = map1.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, Long[]> entry = it.next();
				String key = entry.getKey();
				Long[] value = entry.getValue();
				//如果取消隐藏前，status是0, 执行取消操作后，数据没有变化
				if(value[1]==0){
					Assert.assertEquals(map2.get(key)[0], value[0]);
					Assert.assertEquals(map2.get(key)[1], value[1]);
				}
				//如果取消隐藏前，status是0, 执行取消操作后，status变为0
				else{
					Assert.assertEquals(map2.get(key)[0], value[0]);
					Assert.assertEquals(map2.get(key)[1].longValue(), 0);
				}
			}
		}
		
	}
	
	protected Long[] getMaxIDAndStatus(String dbConf, String field, String filterSql) throws Exception
	{
		Long[] result = new Long[2];
		List<Map<String,Object>> list = DbUtil.selectList(dbConf, "select "+field+" as maxID, status " + filterSql);
		if(list.size()==0){
		    result[0] = (long) 0;
		    result[1] = (long) 0;
		}
		else
		{
			result[0]  =Long.parseLong(((Map<String,Object>)list.get(0)).get("maxID").toString());
			result[1] = Long.parseLong(((Map<String,Object>)list.get(0)).get("status").toString());
		}
		return result;		
	}

}
