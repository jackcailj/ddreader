package com.dangdang.autotest.common;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class reflect {

	boolean flag = true;
	public void compareObjectValue(Object db, Object json) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchFieldException{
		if(db !=null && json !=null){
		Field[] fields = json.getClass().getDeclaredFields();
		for(Field field:fields){
			System.out.println(field.getGenericType());
			System.out.println();
			

			if(field.getGenericType().toString().equals("class java.lang.String")){
				System.out.println("开始对比字段" + field.getName());
				Method jsonm=(Method)json.getClass().getMethod("get"+ getMethodName(field.getName()));
				Method dbm=(Method)db.getClass().getMethod("get"+ getMethodName(field.getName()));
				String jsonval = (String)jsonm.invoke(json); //调用getter方法获取属性值
				String dbval = (String)dbm.invoke(db);
				System.out.println("json:"+ field.getName() +"="+ jsonval);
				System.out.println("db:"+ field.getName() +"="+ dbval);
				
				//System.out.println("db:" + field.getName() +" "+ dbval);
				System.out.println("对比结果:" +jsonval.equals(dbval));
			}

			if(field.getGenericType().toString().contains("class com")){
				System.out.println("开始对比字段(Class类型)" + field.getName());
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
				System.out.println("开始对比字段(List<Class>类型)" + field.getGenericType());
				Method jsonm=(Method)json.getClass().getMethod("get"+ getMethodName(field.getName()));
				Method dbm=(Method)db.getClass().getMethod("get"+ getMethodName(field.getName()));

				List jsonList = (List)jsonm.invoke(json);
				List dbList = (List)dbm.invoke(db);
				System.out.println("jsonList size:" + jsonList.size());
				System.out.println("dbList size:" +  dbList.size());
				System.out.println("对比结果:" + (jsonList.size() == dbList.size()));
				for(int i=0; i<jsonList.size(); i++){
					Object jsonval = jsonList.get(i);
					Object dbval = dbList.get(i);
					System.out.println("************"+jsonval);
					compareObjectValue(jsonval, dbval);
				}

			}
		}
		}
		}

		private static String getMethodName(String fildeName){
			byte[] items=fildeName.getBytes();
			items[0] = (byte)((char)items[0]-'a'+'A');
			return new String(items);
		}
		

		
		public static void main(String[] args) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchFieldException{
		User user1 = new User();
		user1.setAge(3);
		user1.setName("haohao");
		user1.setAddress("住址");
		user1.setEmail("");
		Course course1 = new Course();
		course1.setCourseName("c1");
		Course course3 = new Course();
		course3.setCourseName("c2");
		List<Course> course11 = new ArrayList<Course>();
		course11.add(course1);
		course11.add(course3);
		user1.setCourse(course11);
		System.out.println(user1.getCourse().get(1).getCourseName());

		User user2 = new User();
		user2.setAge(5);
		user2.setName("haohao");
		user2.setAddress("住址");
		user2.setEmail("");
		Course course2 = new Course();
		course2.setCourseName("c2");	
		Course course4 = new Course();
		course4.setCourseName("c1");
		List<Course> course22 = new ArrayList<Course>();;
		course22.add(course4);
		course22.add(course2);
		
		user2.setCourse(course22);
		System.out.println(user2.getCourse().get(1).getCourseName());
		reflect r = new reflect();
		r.compareObjectValue(user1, user2);
		//System.out.println(user1 +" "+ user2 + "------------" + user1.getCourse() +" "+ user2.getCourse());
		//r.getObjectValue(user1.getCourse(), user2.getCourse());

		}
}
