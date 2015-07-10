package com.dangdang.autotest.common;

import java.lang.reflect.*;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import com.dangdang.ddframework.dataverify.VerifyBase;

public class ResponseVerify extends VerifyBase{
	protected  Logger log = Logger.getLogger(ResponseVerify.class);
	Object _json;
	Object _db;
	boolean flag = true;
	
	public ResponseVerify(Object jsonValue, Object dbValue){
		_json = jsonValue;
		_db = dbValue;
	}
	
	@Override
	public boolean dataVerify() throws Exception {
		compareObjectValue(_json, _db);
		return flag;
	}
	
	public void compareObjectValue(Object json, Object db) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchFieldException{
//		if(json==null || json.equals("")){
//			log.info("参数jsonValue为空！");
//			flag = false;
//		}else if(db==null || db.equals("")){
//			log.info("参数dbValue为空！");
//			flag = false;
//		}else{
			Field[] fields = json.getClass().getDeclaredFields();
			for(Field field:fields){
			log.info(field.getGenericType());
			log.info("\n");			

			//String类型
			if(field.getGenericType().toString().equals("class java.lang.String")){
				log.info("开始对比字段" + field.getName());
				Method jsonm=(Method)json.getClass().getMethod("get"+ getMethodName(field.getName()));
				Method dbm=(Method)db.getClass().getMethod("get"+ getMethodName(field.getName()));
				String jsonval = (String)jsonm.invoke(json); //调用getter方法获取属性值
				String dbval = (String)dbm.invoke(db);			
				log.info("json:"+ field.getName() +"="+ jsonval);
				log.info("db:"+ field.getName() +"="+ dbval);
				flag = jsonval.equals(dbval);
				log.info("对比结果:" + flag);
			}
			
			//Integer类型
			if(field.getGenericType().toString().equals("class java.lang.Integer")){
				log.info("开始对比字段" + field.getName());
				Method jsonm=(Method)json.getClass().getMethod("get"+ getMethodName(field.getName()));
				Method dbm=(Method)db.getClass().getMethod("get"+ getMethodName(field.getName()));
				Integer jsonval = (Integer)jsonm.invoke(json); //调用getter方法获取属性值
				Integer dbval = (Integer)dbm.invoke(db);
				log.info("json:"+ field.getName() +"="+ jsonval);
				log.info("db:"+ field.getName() +"="+ dbval);
				flag = jsonval.equals(dbval);
				log.info("对比结果:" + flag);
			}
			
			//Boolean类型
			if(field.getGenericType().toString().equals("class java.lang.Boolean")){
				log.info("开始对比字段" + field.getName());
				Method jsonm=(Method)json.getClass().getMethod("get"+ getMethodName(field.getName()));
				Method dbm=(Method)db.getClass().getMethod("get"+ getMethodName(field.getName()));
				Boolean jsonval = (Boolean)jsonm.invoke(json); //调用getter方法获取属性值
				Boolean dbval = (Boolean)dbm.invoke(db);
				log.info("json:"+ field.getName() +"="+ jsonval);
				log.info("db:"+ field.getName() +"="+ dbval);
				flag = jsonval.equals(dbval);
				log.info("对比结果:" + flag);
			}
			
			//Date类型
			if(field.getGenericType().toString().equals("class java.util.Date")){
				log.info("开始对比字段" + field.getName());
				Method jsonm=(Method)json.getClass().getMethod("get"+ getMethodName(field.getName()));
				Method dbm=(Method)db.getClass().getMethod("get"+ getMethodName(field.getName()));
				Date jsonval = (Date)jsonm.invoke(json); //调用getter方法获取属性值
				Date dbval = (Date)dbm.invoke(db);
				log.info("json:"+ field.getName() +"="+ jsonval);
				log.info("db:"+ field.getName() +"="+ dbval);
				flag = flag = jsonval.equals(dbval);
				log.info("对比结果:" + flag);
			}

			if(field.getGenericType().toString().contains("class com")){
				log.info("开始对比字段(Class类型)" + field.getName());
				Method jsonm=(Method)json.getClass().getMethod("get"+ getMethodName(field.getName()));
				Method dbm=(Method)db.getClass().getMethod("get"+ getMethodName(field.getName()));

				Object jsonval = jsonm.invoke(json);
				Object dbval = dbm.invoke(db);				
				//判断 如果jsonval 或dbval为空  不递归调用
				//如果jsonval为空 dbval不为空，flag=false，不递归调用
				if(jsonval == null || jsonval.equals("")||dbval==null || dbval.equals("")){
					if(dbval!=null) flag = false;				
				}else{
					compareObjectValue(jsonval, dbval);
				}
			}
			
			if(field.getGenericType().toString().contains("java.util.List")){
				log.info("开始对比字段(List<Class>类型)" + field.getGenericType());
				Method jsonm=(Method)json.getClass().getMethod("get"+ getMethodName(field.getName()));
				Method dbm=(Method)db.getClass().getMethod("get"+ getMethodName(field.getName()));

				List jsonList = (List)jsonm.invoke(json);
				List dbList = (List)dbm.invoke(db);
				if(jsonList.size()==0||dbList.size()==0){
					if(dbList.size()!=0) {
						flag = false;
						log.info("参数jsonList为空！");
					}					
					if(jsonList.size()!=0) {
						flag = false;
						log.info("参数dbList为空！");
					}
					
				}else{
					log.info("jsonList size:" + jsonList.size());
					log.info("dbList size:" +  dbList.size());
					flag = (jsonList.size() == dbList.size());
					log.info("对比结果:" + flag);
					int size = jsonList.size()<= dbList.size()?jsonList.size():dbList.size();
					for(int i=0; i<size; i++){
						Object jsonval = jsonList.get(i);
						Object dbval = dbList.get(i);
						log.info("************jsonVal"+jsonval);
						log.info("************dbVal"+jsonval);
						log.info("************调用compareObjectValue(jsonval, dbval)");
						compareObjectValue(jsonval, dbval);
					}
				}
			}
		}
		}
//		}

		private String getMethodName(String fildeName){
			byte[] items=fildeName.getBytes();
			items[0] = (byte)((char)items[0]-'a'+'A');
			return new String(items);
		}
	
//		public static void main(String[] args) throws Exception{
//			User user1 = new User();
//			user1.setAge(3);
//			user1.setName("haohao");
//			user1.setAddress("住址");
//			user1.setEmail("");
//			Course course1 = new Course();
//			course1.setCourseName("c1");
//			Course course3 = new Course();
//			course3.setCourseName("c2");
//			List<Course> course11 = new ArrayList<Course>();
//			course11.add(course1);
//			course11.add(course3);
//			user1.setCourse(course11);
//			//System.out.println(user1.getCourse().get(1).getCourseName());
//
//			User user2 = new User();
//			user2.setAge(5);
//			user2.setName("haohao");
//			user2.setAddress("住址");
//			user2.setEmail("");
//			Course course2 = new Course();
//			course2.setCourseName("c2");	
//			Course course4 = new Course();
//			course4.setCourseName("c1");
//			List<Course> course22 = new ArrayList<Course>();;
//			course22.add(course4);
//			//course22.add(course2);
//			
//			user2.setCourse(course22);
//			//System.out.println(user2.getCourse().get(1).getCourseName());
//			ResponseVerify r = new ResponseVerify(user1, user2);
//			System.out.println(r.dataVerify());
//		}
}
