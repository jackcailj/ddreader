package digital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaColumn;

/**
 * 当当读书5.0 - 书城 相关查询SQL
 * @author guohaiying
 *
 */
public class BookStore5CommSQL {

	/**
	 * 根据栏目name获取栏目数据 
	 * @param Name 
	 * @param channel 频道
	 * @throws Exception 
	 */
	public static List<MediaColumn> getColumnByName(String name, String channel) throws Exception{
		String selectSQL = "SELECT * FROM `media_column` " +
				"WHERE name='"+name+"' AND channel='"+channel+"'";
		List<MediaColumn> columnList = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaColumn.class);
		return columnList;
	}
	
	public static List<MediaColumn> getColumnByCode(String code) throws Exception{
		String selectSQL = "SELECT * FROM `media_column` WHERE column_code='"+code+"'";
		List<MediaColumn> columnList = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaColumn.class);
		return columnList;
	}
	
	/**
	 * 根据栏目名称获取栏目下的所有子栏目
	 * @param args
	 * @throws Exception
	 */
	public static List getSubColumn(String name, String channel) throws Exception{
		List<MediaColumn> list = getColumnByName(name, channel);
		int id = list.get(0).getColumnId();
		String selectSQL = "SELECT * FROM media_column WHERE parent_id="+id;
		List<MediaColumn> columnList = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaColumn.class);
		List l = new ArrayList<String>();
		for(int i=0; i<columnList.size(); i++){
			l.add(columnList.get(i).getName());
			l.add(columnList.get(i).getCode());
			l.add(columnList.get(i).getChannel());
		}
		return l;
	}
	
	public static Map<String, String> getSubColumn(String code) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		List<MediaColumn> list = getColumnByCode(code);
		int id = list.get(0).getColumnId();
		String selectSQL = "SELECT * FROM media_column WHERE parent_id="+id;
		List<MediaColumn> columnList = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaColumn.class);
		for(int i=0;i<columnList.size();i++){
			map.put(columnList.get(i).getName(), columnList.get(i).getCode());
		}
		return map;
	}
	
	
	//查询栏目内容	
	public static void main(String[] args) throws Exception{
		BookStore5CommSQL.getColumnByName("vp_byzq", "vp");
		BookStore5CommSQL.getSubColumn("原创","all");
	}
}
