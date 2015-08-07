package test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.util.DesUtils;
import com.dangdang.ddframework.util.security.RsaUtils;
import com.dangdang.digital.api.IMMsgApi;
import com.dangdang.ucenter.UserInfoSql;
import com.dangdang.ucenter.meta.LoginRecord;
import com.easemob.chat.EMChat;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test {

	public static void main(String[] args) throws Exception {
		//DigestUtils.md5Hex(data)
	/*	DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = df1.parse("2015-07-10");
		System.out.println(date1.getTime());
*/

		/*ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");

		IMMsgApi msgApi = (IMMsgApi) factory.getBean("imMsgApi");
		msgApi.sendIMUpdateMsg(null);*/

		DbUtil.selectList(Config.YCDBConfig,"select type *1 as type from channel_monthly_strategy");


	}
}
