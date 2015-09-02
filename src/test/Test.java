package test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dangdang.account.IntegralItem;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.core.FunctionalBase;
import com.dangdang.ddframework.dataverify.verify_annotation.NotNull;
import com.dangdang.ddframework.dataverify.verify_annotation.Null;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.DesUtils;
import com.dangdang.ddframework.util.security.RsaUtils;
import com.dangdang.digital.api.IMMsgApi;
import com.dangdang.ucenter.UserInfoSql;
import com.dangdang.ucenter.meta.LoginRecord;
import com.easemob.chat.EMChat;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test extends FunctionalBase {

	public Test(){}


    public static ReponseV2<user> reponse =new ReponseV2<user>();

	@NotNull
	Integer a;

	@Null
	Long b;

	Date d;

	public static void main(String[] args) throws Exception {
		//DigestUtils.md5Hex(data)
	/*	DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = df1.parse("2015-07-10");
		System.out.println(date1.getTime());
*/

		/*ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");

		IMMsgApi msgApi = (IMMsgApi) factory.getBean("imMsgApi");
		msgApi.sendIMUpdateMsg(null);*/

		//DbUtil.selectList(Config.SQLSERVER187Config,"select top 10 * from email_verify ");

		Test test=new Test();
        test.reponse.setData(new user());
		test.doWorkAndVerify();

	}

}
