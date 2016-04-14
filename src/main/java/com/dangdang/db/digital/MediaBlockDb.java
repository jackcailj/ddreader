package com.dangdang.db.digital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaBlock;

public class MediaBlockDb {	
	public static String getBlock(String code) throws Exception{
		String selectSQL = "SELECT content FROM media_block WHERE code LIKE '"+code+"'";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return infos.get(0).get("content").toString();
	}
	
	//根据code返回相关信息
	public static MediaBlock getMediaBlock(String code) throws Exception{
		String selectSQL = "SELECT * FROM media_block WHERE code LIKE '"+code+"'";
		List<MediaBlock>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaBlock.class);	
		return infos.get(0);
	}
	
	//返回当当读书Banner下的所有code
	public static List<String> getBlocks() throws Exception{
		String selectSQL = "SELECT code FROM media_block where group_id in ( SELECT media_block_group_id FROM media_block_group WHERE parent_id in (" +
				"SELECT media_block_group_id " +
				"FROM media_block_group " +
				"WHERE name='当当读书' " +
				"and status=1) AND status=1) and status=1";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		List codeList = new ArrayList<String>();
		for(int i=0; i<infos.size(); i++){
			codeList.add(infos.get(i).get("code").toString());
		}
		return codeList;
	}
	
	public static void main(String[] args) throws Exception{
		List<String> list = MediaBlockDb.getBlocks();
		System.out.println(list.size());
	}
}
