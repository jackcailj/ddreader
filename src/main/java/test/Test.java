package test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframework.core.FunctionalBase;
import com.dangdang.ddframework.dataverify.verify_annotation.NotNull;
import com.dangdang.ddframework.dataverify.verify_annotation.Null;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.personal_center.cloud_sync_read.CloudExperienceInfoEx;
import com.dangdang.readerV5.personal_center.cloud_sync_read.Remarks;


public class Test extends FunctionalBase {

	public Test(){}


    public static ReponseV2<user> reponse =new ReponseV2<user>();

	@NotNull
	Integer a;

	@Null
	Long b;

	Date d;

	public static String string2Unicode(String string) {

		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {

			// 取出每一个字符
			char c = string.charAt(i);

			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}

		return unicode.toString();
	}

	public static void main(String[] args) throws Exception {
		//DigestUtils.md5Hex(data)
/*		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=df1.parse("2015-08-27 13:23:23");
		//1452741286421L格式转换成标准日期格式


		System.out.println(date.getTime());*/

	/*	time = 1453283108950L;
		date = new Date(time);
		System.out.println(df1.format(date));

		time = 1453283420121L;
		date = new Date(time);
		System.out.println(df1.format(date));

		time = 1453283428944L;
		date = new Date(time);
		System.out.println(df1.format(date));

		time = 1453283457331L;
		date = new Date(time);
		System.out.println(df1.format(date));

		time = 1453284615896L;
		date = new Date(time);
		System.out.println(df1.format(date));

		time = 1453286396567L;
		date = new Date(time);
		System.out.println(df1.format(date));

		time = 1453359588558L;
		date = new Date(time);
		System.out.println(df1.format(date));*/
		

		/*ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");

		IMMsgApi msgApi = (IMMsgApi) factory.getBean("imMsgApi");
		msgApi.sendIMUpdateMsg(null);*/

		//DbUtil.selectList(Config.SQLSERVER187Config,"select top 10 * from email_verify ");

/*		Test test=new Test();
        test.reponse.setData(new user());
		test.doWorkAndVerify();*/

		//Boolean b= Boolean.parseBoolean("true");

		CloudExperienceInfoEx ex = new CloudExperienceInfoEx();
		ex.setRemarks(new Remarks());
		ex.getRemarks().setBookName("123");

		String test ="^{.*}$";

		System.out.print(JSONObject.toJSONString(ex));
	}


}
