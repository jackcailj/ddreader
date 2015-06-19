package com.dangdang.autotest.common;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.config.Config;
import com.dangdang.ddframework.reponse.ReponseV2Base;
import org.apache.log4j.Logger;
import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.common.functional.login.Login;
import com.dangdang.ddframework.core.InterfaceBase;
import com.dangdang.ddframework.drivers.HttpDriver;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;

/**
 * 
 * @author guohaiying
 *
 */
public class FixtureBase extends InterfaceBase{
	protected static Logger log = Logger.getLogger(FixtureBase.class);
	protected boolean verifyResult;
	protected ILogin login;

    ReponseV2Base reponseV2Base;
	
	public FixtureBase(){
		URL = Config.getUrl();
	}
	
	/**
	 * 接收FitNesse上map形式的参数 
	 * @param params
	 * @throws Exception 
	 */
	public void setParameters(Map<String, String> params) throws Exception{
		parseParameters(params);
	}
	
	public void parseParameters(Map<String, String> params) throws Exception{
		ParseResult parseResult=ParseParamUtil.parseParameter(params);
		paramMap =  params;
		paramMap.putAll(Config.getCommonParam());
		login = parseResult.getLogin();
		handleParam();
	}
	
	/**
	 * @param actionName
	 * @throws Exception
	 */
	public boolean doGet(String actionName) throws Exception{
		return doGet(actionName, 0);
	}
	
	/**
	 * @param actionName
	 * @param exceptedCode 鎺ュ彛杩斿洖鐨刢ode鍊�
	 * @throws Exception
	 */
	public boolean doGet(String actionName, int exceptedCode) throws Exception {
		boolean flag = false;
		paramMap.put("action", actionName);
		//paramMap.putAll(Config.getCommonParam());
		result=HttpDriver.doGet(URL, paramMap, bHttps);
		
		if(result.toString().contains("\"code\":"+exceptedCode)){
			flag = true;
			log.info(result.toString());
		}
		if(flag&&exceptedCode==0){
			jsonToClass();
		}
		return flag;
	}
	
	public void doGet() throws Exception{
		paramMap.put("action", "column");
		HttpDriver.doGet(URL, paramMap, bHttps);
	}
	
	public String doRequest() throws Exception {
		result = HttpDriver.doGet(Config.getUrl(), paramMap,false);
		return result.toString();
	}
	
	public void dataVerify(String expectedCode) throws Exception {
		super.dataVerify();
		verifyResult(expectedCode);
	}
	
	public void verifyResult(String expectedCode){
		try{
			Assert.assertEquals(getDataVerifyResult(), EXCEPTSUCCESS);			
			Assert.assertTrue(result.toString().contains("\"code\":"+expectedCode));
			verifyResult = true;
		}
		catch(AssertionError e){
			verifyResult = false;
		}
	}
	
	public boolean getVerifyResult(){
		return verifyResult;
	}
	
	
	/**
	 * 
	 */
	public void jsonToClass(){
		
	}

    public ReponseV2Base getReponseStatus(){
        return reponseV2Base;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();
        try {
            reponseV2Base = JSONObject.parseObject(result.toString(), ReponseV2Base.class);
        }catch (Exception e){
            logger.error("解析结果错误:"+e);
        }
    }

    /*
    add by cailj   2015-6-18
    添加解析参数方法，方便使用
     */
    @Override
    protected void beforeParseParam() throws Exception {
        ParseParamUtil.parseOperateParam(paramMap);
        paramMap.putAll(Config.getCommonParam());
    }

    /*
    add by cailj   2015-6-18
    想参数中添加action
     */
    public void addAction(String actionName){
        paramMap.put("action", actionName);
    }

    /*
    add by cailj   2015-6-18
    增加此方法，防止将null值赋值给login
     */
    public void setLogin(ILogin loginObject){
        if(loginObject!=null){
            login=loginObject;
        }
    }


    // add by cailj ，支持DynamicDecisionTable
    /*============================与fitnesse DynamicDecisionTable集成相关函数=================================*/

	/**
	 * 接收FitNesse上ddt表的参数形式,其中？为get，不进入参数
	 * @param name
	 * @param value
	 */
	public void set(String name, String value){
        if(!name.contains("?")) {
            //字符串被以双引号开头及结尾，表示为字符串，作为参数时需去掉，主要用来处理 邮箱、url传递过程中为链接形式问题
            if(value.startsWith("\"") && value.endsWith("\"")){
                value =value.substring(1,value.length()-1);
            }
            paramMap.put(name, value);
        }
	}

    /*
    返回ddt上的带？的值
     */
	public String get(String columnName) throws Exception {
		if(columnName.startsWith("data")){
			if(getDataVerifyResult()){
				return "通过";
			}
			else {
				return "失败";
			}
		}
        else if( columnName.contains("status")){
            return reponseV2Base.getStatus().getCode().toString();
        }

        throw  new Exception("没有["+columnName+"]数据");
	}

	/*
    执行DynamicDecisionTable的每一行
     */
	public void execute() throws Exception {

		doWorkAndVerify();

	}

    /*
    每次执行前清除上次运行的数据
    */
    public void reset(){
        dataVerifyManager.clear();
        String action=paramMap.get("action");
        paramMap.clear();
        addAction(action);
        originalParamMap.clear();
        login=null;
    }

    /*============================fitnesse DynamicDecisionTable设置列值函数=================================*/
	
}
