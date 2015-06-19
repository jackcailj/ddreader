package test;

import java.util.List;

import com.dangdang.ucenter.UserInfoSql;
import com.dangdang.ucenter.meta.LoginRecord;


public class Test {

	public static void main(String[] args) throws Exception {
		//DigestUtils.md5Hex(data)
		/*DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = df1.parse("2016-03-29");
		System.out.println(date1.getTime());*/

        System.out.println( UserInfoSql.getCustIdByPubId("25873346"));
	}
}
