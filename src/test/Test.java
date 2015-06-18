package test;

import java.util.List;

import com.dangdang.digital.meta.MediaColumn;
import com.dangdang.digital.meta.MeidaAnnouncementsCategory;

import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;


public class Test {

	public static void main(String[] args) throws Exception {
		//DigestUtils.md5Hex(data)
		/*DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = df1.parse("2016-03-29");
		System.out.println(date1.getTime());*/

		List<MediaColumn> lists= DbUtil.selectList(Config.YCDBConfig, "select * from media_column", MediaColumn.class);

		
	}
}
