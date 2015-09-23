package com.dangdang.digital.bookstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import com.dangdang.autotest.config.Config;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.reponse.Catetory;
import com.dangdang.readerV5.reponse.CatetoryList1;
import com.dangdang.readerV5.reponse.CatetoryList2;
import com.dangdang.readerV5.reponse.CatetoryList3;

/**
 * 书城-分类接口 
 * @author guohaiying
 *
 */
public class MediaCategorySQL {
	
	public static CatetoryList3 getCategoryList3() throws Exception{
		CatetoryList3 catetoryList3 = new CatetoryList3();		
		List<CatetoryList2> list2 = new ArrayList<CatetoryList2>();
		CatetoryList2 catetoryList2 = getCategoryList2(getCatetoryId("出版物"));
		CatetoryList2 list1 = getCategoryList2(getCatetoryId("女频"));
		list2.add(catetoryList2);
		list2.add(list1);
		CatetoryList2 list3 = getCategoryList2(getCatetoryId("男频"));
		list2.add(list3);
		catetoryList3.setCatetoryList(list2);
		return catetoryList3;
	}
	
	//出版物下的分类
	public static CatetoryList2 getCategoryList2(int catetoryId) throws Exception{
		Catetory catetory = getCatetory(catetoryId);
		List<Catetory> list = getCatetoryList(catetoryId);
		CatetoryList2 catetoryList2 = new CatetoryList2();
		List<CatetoryList1> catetoryList1 = new ArrayList<CatetoryList1>();
		CatetoryList1 catetory1 = new CatetoryList1();
		for(int i=0; i<list.size(); i++){		
			int id = list.get(i).getId();
			if(isLeaf(id)){
				List<Catetory> tmp = getCatetoryList(catetoryId);
				List<CatetoryList1> tmpList = new ArrayList<CatetoryList1>();
				for(int j=0; j<tmpList.size(); j++){
					catetory1 = getCategoryList1(id);
					catetory = getCatetory(catetoryId);
					tmpList.add(catetory1);
					catetoryList2.setCatetoryList(tmpList);
				}			
			}
			catetory1 = getCategoryList1(id);
			catetoryList1.add(catetory1);
		}
		catetoryList2.setCatetoryList(catetoryList1);
		catetoryList2.setCode(catetory.getCode());
		//catetoryList2.setId(catetory.getId());
		catetoryList2.setImage(catetory.getImage());
		catetoryList2.setImage2(catetory.getImage2());
		//catetoryList2.setLeaf(isLeaf(catetoryId));
		catetoryList2.setName(catetory.getName());
		//catetoryList2.setParentId(catetory.getParentId());
		//catetoryList2.setParsed(catetory.getParsed());			
		return catetoryList2;
	}
		
	
	//原创女生  原创男生
	public static CatetoryList1 getCategoryList1(int catetoryId) throws Exception{
		Catetory catetory = getCatetory(catetoryId);
		List<Catetory> list = getCatetoryList(catetoryId);
		CatetoryList1 catetoryList = new CatetoryList1();
		catetoryList.setCatetoryList(list);
		catetoryList.setCode(catetory.getCode());
		catetoryList.setId(catetory.getId());
		catetoryList.setImage(catetory.getImage());
		catetoryList.setImage2(catetory.getImage2());
		catetoryList.setLeaf(catetory.getLeaf());
		catetoryList.setName(catetory.getName());
		catetoryList.setParentId(catetory.getParentId());
		catetoryList.setParsed(false);
		return catetoryList;
	}
	
	public static Integer getCatetoryId(String name) throws Exception{
		String selectSQL = "SELECT catetory_id FROM media_catetory WHERE `name` LIKE '"+name+"'";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return Integer.valueOf(infos.get(0).get("catetory_id").toString());
	}
	
	public static Catetory getCatetory(int catetoryId) throws Exception{
		String selectSQL = "SELECT code, catetory_id,image, image2, name, parent_id " +
				" FROM media_catetory " +
				" WHERE catetory_id="+catetoryId +
				" AND `status`=1";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		Catetory catetory = new Catetory();
		catetory.setCode(infos.get(0).get("code").toString());
		int id = Integer.valueOf(infos.get(0).get("catetory_id").toString());
		catetory.setId(id);
		if(infos.get(0).get("image")==null)
			catetory.setImage("");
		else			
			catetory.setImage(infos.get(0).get("image").toString());
		if(infos.get(0).get("image2")==null)
			catetory.setImage2("");
		else
			catetory.setImage2(infos.get(0).get("image2").toString());
		catetory.setLeaf(isLeaf(id));
		catetory.setName(infos.get(0).get("name").toString());
		catetory.setParentId(Integer.valueOf(infos.get(0).get("parent_id").toString()));
		catetory.setParsed(false);
		return catetory;
	}
	
	//通过字段catetoryId查询media_catetory表数据信息
	public static List<Catetory> getCatetoryList(int catetoryId) throws Exception{
		String selectSQL = "SELECT code, catetory_id, image, image2, name, parent_id " +
				" FROM media_catetory " +
				" WHERE parent_id=(SELECT catetory_id " +
				" FROM `media_catetory` " +
				" WHERE catetory_id="+catetoryId+
				" AND `status`=1)  " +
				" AND `status`=1 " +
				" ORDER BY index_order DESC";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		List<Catetory> catetoryList = new ArrayList<Catetory>();
		for(int i=0; i<infos.size(); i++){
			Catetory catetory = new Catetory();
			catetory.setCode(infos.get(i).get("code").toString());
			int id = Integer.valueOf(infos.get(i).get("catetory_id").toString());
			catetory.setId(id);
			if(infos.get(i).get("image")==null)
				catetory.setImage("");
			else
				catetory.setImage(infos.get(i).get("image").toString());
			if(infos.get(i).get("image2")==null)
				catetory.setImage2("");
			else
				catetory.setImage2(infos.get(i).get("image2").toString());
			catetory.setLeaf(isLeaf(id));
			catetory.setName(infos.get(i).get("name").toString());
			catetory.setParentId(Integer.valueOf(infos.get(i).get("parent_id").toString()));
			catetory.setParsed(false);
			catetoryList.add(catetory);
		}
		return catetoryList;
	}

	//判断某分类是否为叶节点
	public static Boolean isLeaf(int catetoryId) throws Exception{
		String selectSQL = "SELECT count(1) FROM media_catetory WHERE parent_id="+catetoryId;
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		int count = Integer.valueOf(infos.get(0).get("count(1)").toString());
		if(count==0)
			return true;
		else 
			return false;
	}
	
	public static void main(String[] args) throws Exception{
		CatetoryList2 list = MediaCategorySQL.getCategoryList2(79);
		Boolean b = MediaCategorySQL.isLeaf(92);
		System.out.println(list.getId());
		System.out.println(list.getImage());
		System.out.println(list.getCatetoryList().size());
		System.out.println(list.getCatetoryList().get(0).getCatetoryList().get(0).getCode());
		System.out.println(list.getCatetoryList().get(0).getCode());
	}
}
