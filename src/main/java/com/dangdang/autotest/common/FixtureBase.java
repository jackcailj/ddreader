package com.dangdang.autotest.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.config.Config;

import com.dangdang.ddframework.core.VariableType;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.ddframework.fitnesse.CommandFactory;
import com.dangdang.ddframework.fitnesse.FitnesseKey;
import com.dangdang.ddframework.fitnesse.ICommand;
import com.dangdang.ddframework.reponse.ReponseV2Base;
import com.dangdang.enumeration.RunLevel;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframework.core.InterfaceBase;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.drivers.HttpDriver;
import com.dangdang.param.model.ParseResult;
import com.dangdang.param.parse.ParseParamUtil;


/**
 * 
 * @author guohaiying
 *
 */
public class FixtureBase extends InterfaceBase{
	protected static Logger log = Logger.getLogger(FixtureBase.class);
	protected boolean verifyResult = true;
	protected ILogin login = null;
    protected String exceptStatusCode;

	protected List<ICommand> commandList = new ArrayList<>();

	protected static Integer interval=0;

    protected ReponseV2Base reponseV2Base;
	
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
	
	/**
	 * 接收FitNesse上map形式的参数 
	 * @param params
	 * @throws Exception 
	 */
	public void setParams(Map<String, String> params) throws Exception{
//      //add by Haiyan
//      //wiki上控制执行哪个环境下的用例 
//      String env = Config.getEnvironment().toString();        
//      if(params.get("environment")!=null){
//          setEnviroment(params.get("environment"));
//          if(!getEnviroment().contains(env)){
//              return;
//          }    
//      }

		setParameters(params);
	}
	
	public void parseParameters(Map<String, String> params) throws Exception{

		ParseResult parseResult=ParseParamUtil.parseParameter(params);
		paramMap =  params;
		ParseParamUtil.parseOperateParam(paramMap);		
		paramMap.putAll(Config.getCommonParam());
		login = parseResult.getLogin();
		handleParam();

	}
	
	public String getCustId() throws Exception{
		return login.getCustId();
	}
	
	public String getToken() throws Exception{
		return login.getToken();
	}
	/**
	 * @param actionName
	 * @throws Exception
	 */
//	public boolean doGet() throws Exception{
//		return doGet(0);
//	}
	
	/**

	 * @param exceptedCode 鎺ュ彛杩斿洖鐨刢ode鍊�
	 * @throws Exception
	 */
	
	public boolean doGet(String exceptedCode) throws Exception {
		String en = Config.getEnvironment().toString();
		if(paramMap.get("enviroment")!=null){
			if(!paramMap.get("enviroment").contains(en)){
				return true;
			}
			paramMap.remove("enviroment");
		}	
		
		genrateVerifyData();
		boolean statusCode = false;
		//genrateVerifyData();
		result=HttpDriver.doGet(URL, paramMap, bHttps);
		
		if(result.toString().contains("\"code\":"+exceptedCode)){ 
			statusCode = true;
			log.info(result.toString());
		}
		return statusCode;
	}
	
	public boolean doPost(String exceptedCode) throws Exception {
		boolean statusCode = false;
		genrateVerifyData();
		result=HttpDriver.doPost(URL,paramMap);
		
		if(result.toString().contains("\"code\":"+exceptedCode)){ 
			statusCode = true;
			log.info(result.toString());
		}
		return statusCode;
	}
	
	
	public boolean doGet() throws Exception{
		return doGet("0");
	}
	
	public String doRequest() throws Exception {
		result = HttpDriver.doGet(Config.getUrl(), paramMap,false);
		return result.toString();
	}
		
	public void dataVerify(String expectedCode) throws Exception {
        doRequest();
        super.dataVerify();
        verifyResult(expectedCode);

	}

    @Override
    protected void dataVerify() throws Exception {
        super.dataVerify();

        for(ICommand command:commandList){
            command.execute(this);
        }
    }

    public void dataVerified(String expectedCode) throws Exception {
		if(CheckIsRun()){
			doRequest();
			dataVerify(expectedCode);
		}
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
	
	//Add by guohaiying
	public boolean verifyResult() throws Exception{
		//读取配置文件中是否执行数据验证参数
		if(true){
			dataVerify();
			return dataVerifyResult; 
		}else
			return true;
	}

	public String verifiedResult(){
		if(verifyResult||result==null){
			return "pass";
		}
		else 
			return "fail";
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
     * add by cailj   2015-6-18
     * 添加解析参数方法，方便使用
     */
    @Override
    protected void beforeParseParam() throws Exception {
    	addAction(lowerFirst(this.getClass().getSimpleName()));

		//解析参数
        ParseParamUtil.parseOperateParam(paramMap);

		//添加公共参数
		for(Map.Entry<String,String> entry: Config.getCommonParam().entrySet()){
			if(!paramMap.containsKey(entry.getKey())){
				paramMap.put(entry.getKey(),entry.getValue());
			}
		}
		
		 String env = Config.getEnvironment().toString();
	     System.out.println("env: " + env   +  env.equals(TestEnvironment.ONLINE.toString())+ "  " + env.equals(TestEnvironment.STAGING.toString()));
	     //增加action字段
	     if((env.equals(TestEnvironment.ONLINE.toString())||env.equals(TestEnvironment.STAGING.toString()))) {
	    	 if(paramMap.get("userName")!=null&&!paramMap.get("userName").equals("")){
	    		 System.out.println("aaaa");
	    		 paramMap.put("token", UserDeviceDb.getTokenByUserName(paramMap.get("userName"),paramMap.get("deviceType")));
	    	 }
	     }else{
	    	 //Ilogin登录时不需要设置login，否则会死循环
	    	 if(!(this instanceof ILogin)) {
	    		 setLogin(ParseParamUtil.parseLogin(paramMap));
	    	 }
	     }
		

        //paramMap.putAll(Config.getCommonParam());
    }

    /*
     * add by cailj   2015-6-18
     *  想参数中添加action
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

	/*
	检测是否运行用例

	 */
	public boolean CheckIsRun(){
		if(Config.getRunLevel()== RunLevel.ALL || (Config.getRunLevel()==RunLevel.FAST && EXCEPTSUCCESS)){
			return true;
		}

		return false;
	}



    // add by cailj ，支持DynamicDecisionTable
    /*============================与fitnesse DynamicDecisionTable集成相关函数=================================*/

	/**
	 * 接收FitNesse上ddt表的参数形式,其中？为get，不进入参数
	 * @param name
	 * @param value
	 */
	public void set(String name, String value) throws Exception {
        if(!name.contains("?")) {
            //字符串被以双引号开头及结尾，表示为字符串，作为参数时需去掉，主要用来处理 邮箱、url传递过程中为链接形式问题
            if(value.startsWith("\"") && value.endsWith("\"")){
                value =value.substring(1,value.length()-1);
            }
            if(name.replaceAll(" ","").equals("statuscode")){
                paramMap.put(EXPECTED,value);
                exceptStatusCode=value;
            }
			else if(name.equals("enviroment")){
            	setEnviroment(value);
            }
			else if(name.startsWith("interval")){
				parseInterval(name);
			}
			else if(name.equals("method")){
				if(value.trim().equals("post")) {
					bPost = true;
				}
			}
			else if(name.equals("script")){
				commandList.add(CommandFactory.createCommand(FitnesseKey.valueOf(name.toUpperCase()),value));
			}
            else {
				paramMap.put(name, value);
            }
        }
	}

    /*
     * 返回ddt上的带？的值
     */
	public String get(String columnName) throws Exception {
		if(columnName.startsWith("data")){
            String result="";//"耗时:"+elapsedTime+"秒\r\n";
			if(getDataVerifyResult()){
                result+= "通过";
			}
			else {
                result+= "数据验证失败";
			}
            if(reponseV2Base==null){//处理下载书籍这种不能返回statusCode的情况，如果httpCode为200，就认为成功。

            }
            else {
                if(StringUtils.isNotBlank(exceptStatusCode) && !exceptStatusCode.equals(reponseV2Base.getStatus().getCode().toString())){
                    result+="  status code【"+reponseV2Base.getStatus().getCode()+"】 与期望【"+exceptStatusCode+"】不符";
                }
            }
            return result;
		}
        else if(columnName.contains("status")){
			if(reponseV2Base==null){//处理下载书籍这种不能返回statusCode的情况，如果httpCode为200，就认为成功。
				return "0";
			}
			else {
				return reponseV2Base.getStatus().getCode().toString();
			}
        }
		else if(columnName.equals("耗时")){
			return elapsedTime+"秒";
		}
        throw  new Exception("没有["+columnName+"]数据");
	}

	/*
	 * 执行DynamicDecisionTable的每一行
     */
	public void execute() throws Exception {
		Interval();
		
		//wiki上控制执行哪个环境下的用例 
		String env = Config.getEnvironment().toString();
		if(getEnviroment() == null || getEnviroment().equals("all")||getEnviroment().equals("")){

		}else{
			if(!getEnviroment().contains(env)){
				return;
			}	
		}
        handleParam();
		if(CheckIsRun()) {
			doWorkAndVerify();
		}
	}

	/*
	 * 解析每个用例执行间隔，用来应对防刷机制
	 */
	public void parseInterval(String columnName){
		Matcher matcher = Pattern.compile("interval#(\\d+)").matcher(columnName);
		if(matcher.find()){
			interval=Integer.parseInt(matcher.group(1));
		}
	}
	public void Interval() throws InterruptedException {
		Thread.sleep(interval*1000);
	}

	/*
	 * 每次执行前清除上次运行的数据
     */
    public void reset(){
		reponseV2Base=null;
        dataVerifyManager.clear();
        String action=paramMap.get("action");
        paramMap.clear();
        addAction(action);
        originalParamMap.clear();
        login=null;
        result=null;
//		VariableStore.clear(VariableType.CASE);
		dataVerifyResult=true;
		bPost =false;
    }
    
    /*
    使首字母小写。
     */
    public  String lowerFirst(String name) {
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        return  name;

    }

    /*============================fitnesse DynamicDecisionTable设置列值函数=================================*/
	
    public boolean tearDown(){
    	try{
    		reset();
            return true;
    	}
    	catch(Exception e){
    		return false;
    	}
    }
    
    public static void main(String[] args){
    	String result="\"status\":{\"code\":200,\"message\":\"系统错误200\"}}";
    	if(result.contains("\"code\":"+12000)){
    		System.out.println("aaaa");
    	}else
    		System.out.println("bbbb");
    }
   
}
