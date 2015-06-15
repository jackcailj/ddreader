package test;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.util.security.SignUtils;
import com.dangdang.ecms.meta.OrderForm;



public class Test {

	public static void main(String[] args) throws Exception {
		//DigestUtils.md5Hex(data)
		/*DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = df1.parse("2016-03-29");
		System.out.println(date1.getTime());*/

		System.out.println(50000256%32);
		
	}
}
