package com.dangdang.db.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.db.SqlUtil;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dbutil.DbUtil;
import org.apache.commons.lang3.StringUtils;

public class BrowseInfoDb {
	
	//获取前两天内的用户浏览过的频道:3/攻略:5，按阅读总数倒叙排列
	//type,3:频道;5:攻略
	public static List<String> get(String id, int type,int num) throws Exception{
		String selectSQL;	
		List<Map<String, Object>> infos;
		//获取前两天日期
		selectSQL = "select date_sub(curdate(),interval 2 day)";
		infos = DbUtil.selectList(Config.BSAECOMMENT,selectSQL);
		String before2Date = infos.get(0).get("date_sub(curdate(),interval 2 day)").toString();
		System.out.println("before2Date"+before2Date);
		
		//获取当前日期
		selectSQL = "select curdate()";
		infos = DbUtil.selectList(Config.BSAECOMMENT,selectSQL);
		String currentDate = infos.get(0).get("curdate()").toString();
		System.out.println("currentDate"+currentDate);
		
		List<String> tags = TagRelationDb.getTargets(5000, id);
		if(tags.size()!=0)
			selectSQL = "SELECT a.target_id " +
				" FROM `browse_info` a, tag_relation b" +
				" where a.target_id=b.source_id " +
				" and a.target_id in ("+StringUtils.join(MediaDigestDb.getDigest(type))+")"+
				" and b.tag_id IN (" +StringUtils.join(tags)+")"+
				" and a.last_modified_date between '"+before2Date+" 00:00:00' and '"+currentDate+" 00:00:00'  " +
				" group by a.target_id " +
				" order by count(a.target_id) desc limit "+num;
		else 
			selectSQL = "SELECT target_id " +
			" FROM `browse_info` " +
			" where target_id in ("+ StringUtils.join(MediaDigestDb.getDigest(type),",")+")"+
			" and last_modified_date between '"+before2Date+" 00:00:00' and '"+currentDate+" 00:00:00'  " +
			" group by target_id " +
			" order by count(target_id) desc limit "+num;
		System.out.println("selectSQL"+selectSQL);
		infos = DbUtil.selectList(Config.BSAECOMMENT,selectSQL);
		
		List<String> targetIds = new ArrayList<String>();
		for(int i=0; i<infos.size(); i++){
			targetIds.add(infos.get(i).get("target_id").toString());
		}
		
		return targetIds;
	}
	
	public static void main(String[] args){
		try {
			//List<String> list=BrowseInfoDb.get(5, 50);
			//System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
