package com.dangdang.reader.functional.param.parse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.dangdang.ddframework.core.VariableStore;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.print.DocFlavor;


/**
 * 
 * @author guohaiying
 *
 */
public class ParseFactory {
	static  Logger log = Logger.getLogger(ParseFactory.class);
	List<String> keyList = new ArrayList<String>();
	
	/**
	 * 根据Excel中的字段值，调用相应解析类
	 * @param keyList  Excel中的字段值
	 * @param paramMap Excel传入的Map值
	 */
	public  void createParse(Map<String, String> paramMap){
		getKeyList(paramMap);
//		if(keyList.contains("".precondition)){
//			PreconditionParse pre = new PreconditionParse();
//			pre.parse(paramMap);
//		}
	}

	public static Object CreateParse(String parseMethod) throws IllegalAccessException, InstantiationException {
		String className = "com.dangdang.reader.functional.param.parse."+parseMethod.replaceFirst(parseMethod.substring(0,1),parseMethod.substring(0,1).toUpperCase())+"Parse";
		Class c = null;
		try {
			c = Class.forName(className);
			log.info("创建解析类： " + c);
		} catch (ClassNotFoundException e) {
			log.info(e);
		}

		return c.newInstance();
	}

    /*
    authro:cailj
    date:2015-6-18
    解析参数，根据参数指定的方法对参数进行解析，参数格式  method:param
    循环查询参数列表是否存在指定格式字符传，如果存在，生成类并进行解析
     */
	public  static void Parse(Map<String, String> paramMap) throws Exception {
		//循环查询参数

        Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator();
        while(it.hasNext()){
            //检查是否有符合解析规则的值
            Map.Entry<String,String> entry = it.next();
			if(entry.getValue()==null){
				continue;
			}

            //变量赋值
			Matcher matcher = Pattern.compile("^\\$(.*?)=").matcher(entry.getValue());
			String varName="";
			if(matcher.find()){
				varName=matcher.group(1).trim();
			}
            else{
                //查找变量，并使用
                matcher = Pattern.compile("^\\$(.*?)$").matcher(entry.getValue());
                if(matcher.find()){
                    varName=matcher.group(1).trim();
                    entry.setValue(VariableStore.getGlobalVar(varName).toString());

                    return;
                }
            }

			matcher = Pattern.compile("#(.+)#(.*)").matcher(entry.getValue());
			if(matcher.find()){
                //获取解析类名
				String parseMethod = matcher.group(1);

                //处理不方便用类模式处理的情况，比如删除某个字段
                if(parseMethod.trim().equals("NotPass")){
                    it.remove();
                    continue;
                }

                //创建解析类，并进行解析
				IParamParse object = (IParamParse) CreateParse(parseMethod.trim());
				object.parse(paramMap,entry.getKey(),matcher.group(2));
                //解析完成，替换字段的值
                //entry.setValue(result.toString());

                //如果存在变量，将值存起来
				if(StringUtils.isNotBlank(varName)){
                    VariableStore.addGlobalVar(varName,entry.getValue());
				}
			}
		}
	}

	
	public Object createParse(Map<String, String> paramMap, String key){
		getKeyList(paramMap);
		if(keyList.contains(key)){
			String className = "com.dangdang.reader.functional.param.parse."+key.replaceFirst(key.substring(0,1),key.substring(0,1).toUpperCase())+"Parse";
			Class c = null;
			try {
				c = Class.forName(className);
				log.info("创建解析类： " + c);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				return ((IParamParse)c.newInstance()).parse(paramMap);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	//获取Excel的字段值
	private void getKeyList(Map<String, String> paramMap){
		Iterator<Map.Entry<String, String>> iterator = paramMap.entrySet().iterator(); 
		while(iterator.hasNext()){
			Map.Entry<String, String> entry = iterator.next();
			String key = (String) entry.getKey(); 
			log.info(key);
			keyList.add(key);
		}
	}

	
}
