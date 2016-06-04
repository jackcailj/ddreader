package com.dangdang.readerV5.bookstore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.drivers.HttpDriver;

/**
 * 分类一级页面
 * @author guohaiying
 * @date 2016年5月25日 下午5:00:43
 */
public class MediaCategory extends FixtureBase{
	
	String jsonResult;
    @Override
    public void doWork() throws Exception {
    	addAction("mediaCategory");
    	jsonResult = HttpDriver.doGet(URL, paramMap, bHttps);    
    }
	
	//验证结果
    @Override
    protected void dataVerify() throws Exception {
        if(jsonResult.contains("\"status\":{\"code\":0}")){
        	Pattern p = Pattern.compile("\"code\":\"(.+)\",");
        	Matcher m = p.matcher(jsonResult);
        	while(m.find()){
        		//System.out.println(m.group(1));
        	}
        	

        
        }
	
    }
}
