package com.dangdang.enumeration;

import java.util.ArrayList;
import java.util.List;

import com.dangdang.bookbar.meta.BarOwnerLevelRelation;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

public enum BarHostTitle {
	NEW("新进吧主",1),
	SILVER("白银吧主",2),
	GOLD("黄金吧主",3),
	PLATINUM("铂金吧主",4),
	DIAMOND("钻石吧主",5);
	
	private String name;
	private int index;
	
	//获取吧主级别关系表
	static List<BarOwnerLevelRelation> relation = new ArrayList<BarOwnerLevelRelation>();
	static int size=0;
	static{
		String sql = "SELECT * FROM `bar_owner_level_relation`";
		try {
			relation = DbUtil.selectList(Config.BOOKBARDBConfig, sql, BarOwnerLevelRelation.class);
			size = relation.size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//获得吧级别的（low，up）值对
	public static int[][] getLevel(){
		int[][]  level = new int[size][2];
		for(int i=0; i<size; i++){
			level[i][0] = relation.get(i).getLevelLow();
			level[i][1] = relation.get(i).getLevelUp();
		}
		return level;
	}
	
	//获得吧成员数量的（low，up）值对
	public static int[][] getMemberNum(){
		int[][]  num = new int[size][2];
		for(int i=0; i<size; i++){
			num[i][0] = relation.get(i).getMemberNumLow();
			num[i][1] = relation.get(i).getMemberNumUp();
		}
		return num;
	}
	
	//获得吧帖子数量的（low，up）值对
	public static int[][] getArticleNum(){
		int[][]  num = new int[size][2];
		for(int i=0; i<size; i++){
			num[i][0] = relation.get(i).getArticleNumLow();
			num[i][1] = relation.get(i).getArticleNumUp();
		}
		return num;
	}	
	
	private BarHostTitle(String name, int index){
		this.name = name;
		this.index = index;
	}
	
	public static int getIndex(String name){
		for(BarHostTitle p : BarHostTitle.values()){
			if(p.getName().equals(name)){
				return p.index;
			}
		}
		return -1;
	}
	
	
	public static BarHostTitle getBarHostTitle(int index){
		for(BarHostTitle p : BarHostTitle.values()){
			if(p.getIndex()==index){
				return p;
			}
		}
		return null;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getIndex(){
		return index;
	}
	
	public void setIndex(int index){
		this.index = index;
	}


}
