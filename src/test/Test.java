package test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dangdang.ddframework.core.FunctionalBase;
import com.dangdang.ddframework.dataverify.verify_annotation.NotNull;
import com.dangdang.ddframework.dataverify.verify_annotation.Null;
import com.dangdang.ddframework.reponse.ReponseV2;


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
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = df1.parse("2016-01-05 18:16:53");
		System.out.println(date1.getTime());

		/*ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");

		IMMsgApi msgApi = (IMMsgApi) factory.getBean("imMsgApi");
		msgApi.sendIMUpdateMsg(null);*/

		//DbUtil.selectList(Config.SQLSERVER187Config,"select top 10 * from email_verify ");

/*		Test test=new Test();
        test.reponse.setData(new user());
		test.doWorkAndVerify();*/

		//Boolean b= Boolean.parseBoolean("true");


	}

}
