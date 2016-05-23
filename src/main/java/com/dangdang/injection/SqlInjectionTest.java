package com.dangdang.injection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. 读取url地址文件，返回url数组
 * 2. 循环执行python命令，返回执行结果，并将结果存放到all文件中
 * 3. 验证执行结果，验证未通过的存放到error文件中
 * @author guohaiying
 */
public class SqlInjectionTest {
	static long current = System.currentTimeMillis();	
	static String allFilePath = "D:\\SqlInjectionTest\\all";
	static String allFile = "D:\\SqlInjectionTest\\all\\all_"+ current +".txt";
	static String errorFilePath = "D:\\SqlInjectionTest\\error";
	static String errorFile = "D:\\SqlInjectionTest\\error\\error_"+ current +".txt";
	
	public static void main(String[] args) throws IOException{
		String urlFile = "D:\\urls.txt";
		List<String> urls = readUrlFile(urlFile);
		exePython(urls, allFilePath, allFile);		
//		compareAction();
	}
	
	//读取url文件，返回url数组
	public static List<String> readUrlFile(String path){
		File file = new File(path);
		BufferedReader reader = null;
		List<String> urlList = new ArrayList<String>();
		try{
			System.out.println("读取url文件： ");
			reader = new BufferedReader(new FileReader(file));
			String tmpString = null;
			int line = 1;
			while((tmpString = reader.readLine())!=null){
				//System.out.println("line " + line + " " + tmpString);
				urlList.add(tmpString);
				line++;
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(reader != null){
				try{
					reader.close();
				}catch(IOException el){
					
				}
			}
		}		
		return urlList;
	}
	
	//写入到文件
	public static void writeToFile(String content, String path, String fileName) throws IOException{
		File filePath = new File(path);
		File file = new File(fileName);
		if(!filePath.exists()){
			System.out.println("创建文件夹 "+ filePath);
			filePath.mkdirs();
		}
		if(!file.exists()){
			System.out.println("创建文件 " + file);
			file.createNewFile();
		}
		
		FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
		PrintWriter pw = new PrintWriter(fw);
		pw.write(content);
		pw.close();
		fw.close();
		System.out.println("写入到文件！");		
	}
	
	//执行结果验证
	public static void verifyResult(String result, String errorPath, String errorFileName) throws IOException{
		System.out.println("开始进行结果验证： ");	
		String str = "all tested parameters appear to be not injectable";
		if(!result.contains(str)){
			writeToFile(result.toString(), errorPath, errorFileName);
		}		
		System.out.println("结果验证完成！ ");	
	}
	
	//逐条执行python命令，返回执行结果，并把执行结果存放到all文件中
	//为了执行速度，采用多线程执行，读取url和写文件时也进行锁定
	public static void exePython(List<String> urls, String allPath, String allFileName) throws IOException{
		String url = null;
		String command = null;
		int i=0;
		for(; i<urls.size(); i++){		
			BufferedReader br = null;
			Process p = null;
			StringBuffer result = new StringBuffer(1000000);
			command = "python \"D:\\sqlmapproject\\sqlmap.py\" -v 1 -u \""+ urls.get(i) +"\" --level=3 --risk=1 --dbms=mysql --batch";
			try{
				System.out.println("开始执行： " + command);
				result.append("开始执行： " + command);				
				Runtime r = Runtime.getRuntime();
				p = r.exec(command);	
				br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = null;
				while((line=br.readLine())!=null){
					System.out.println(line);
					result.append(line+"\n");
				}

			}catch(Exception e){
				result.append(e.getStackTrace()+"\n");
			}finally{
				if(br!=null){
					try{br.close();
					}catch(Exception e){
						result.append(e.getStackTrace()+"\n");
					}
					p.destroy();
				}
			}
			result.append("执行结束！\n\n");
			System.out.println("执行结束！\n\n");
			//调用写入文件
			writeToFile(result.toString(), allPath, allFileName);
			verifyResult(result.toString(), errorFilePath, errorFile);
		}	
		
		writeToFile("共执行 "+i+" 次", allPath, allFileName);
	}
	
	
	//对比两个文件中那些action没有
	public static void compareAction(){	
		String urlFile = "D:\\urls.txt";
		List<String> list = readUrlFile("D:\\SqlInjectionTest\\interfaceName.txt");		
		List<String> urls = readUrlFile(urlFile);
					
		List<String> urlsToAction = new ArrayList<String>();		
		for(int i=0; i<urls.size(); i++){
			String url = urls.get(i);
			int first = url.indexOf("action=");
			int end = url.indexOf("&");
			String action = url.substring(first+7, end);
			urlsToAction.add(action);
		}
		
		//情况1：
		System.out.println("情况1：   ");
		for(int i=0; i<list.size(); i++){
			if(!urlsToAction.contains(list.get(i))){
				System.out.println("list: " + list.get(i));
			}
		}	
						
		//情况2：
		System.out.println("情况2：   ");
		for(int i=0; i<urlsToAction.size(); i++){	
			if(!list.contains(urlsToAction.get(i))){
				System.out.println(urlsToAction.get(i));
			}
		}									
	}

}
