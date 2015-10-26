package com.dangdang.digital.channel;

import java.util.List;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.reponse.UrlList;

public class GetBackgroundImgListSQL {
	//获取频道或书单的默认背景图地址
    public static List<UrlList> getBackImg(String type) throws Exception { 
    	int _type = Integer.valueOf(type);
    	String selectSQL = "SELECT img_id AS imgId, type,img_url AS imgUrl,creator FROM `channel_bg_img` WHERE type="+_type;
    	List<UrlList> backImgList = DbUtil.selectList(Config.YCDBConfig, selectSQL,UrlList.class);	
        return backImgList;
    }

    public static void main(String[] args) throws Exception{
    	List<UrlList> list = GetBackgroundImgListSQL.getBackImg("0");
    	System.out.println(list.size());
    	for(int i=0; i<list.size(); i++){
    		System.out.println(list.get(i).getImgUrl());
    		System.out.println(list.get(i).getCreator());
    		System.out.println(list.get(i).getImgId());
    		System.out.println(list.get(i).getType());
    		System.out.println("\n");
    	}
    }
}
