package com.dangdang.readerV5.bookbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dangdang.account.meta.AttachAccount;
import com.dangdang.bookbar.meta.Bar;
import com.dangdang.config.Config;
import com.dangdang.db.account.AttachAccountDb;
import com.dangdang.db.bookbar.BarDb;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.util.DesUtils;
import com.dangdang.enumeration.BarHostTitle;

public class BarCommon {
	protected static Logger log = Logger.getLogger(BarCommon.class);
	static Map<String, Integer> levelMap = new HashMap<String, Integer>();
	static{		
		//i代表用户等级为1--5
		//j代表成员数量等级为1--5
		//j代表帖子数量等级为1--5
		for(int i=1; i<=5; i++){
			for(int j=1; j<=5; j++){
				for(int k=1; k<=5; k++){
					String key = Integer.toString(i)+Integer.toString(j)+Integer.toString(k);
					//一个吧主运营一个吧，必须同一个吧同时满足吧主等级&吧成员数量&帖子数量三个条件，其中颁发吧主称号时以三条中最低的一项为准。
					int min = i;
					if(min>j){
						min=j;
					}
					else if(min>k){
						min = k;
					}
					levelMap.put(key, min);
					//System.out.println("level is "+key+","+level.get(key));
				}
			}
		}
	}
	
	
	public static String getBarOwnerLevelFromDb(String encryCustId) throws Exception{
		String cust = DesUtils.decryptCustId(encryCustId).toString();
		String sql = "select bar_owner_level from login_record where cust_id="+cust;
		Object object = DbUtil.selectOne(Config.UCENTERDBConfig, sql).get("bar_owner_level");
		String level = object==null?"0":object.toString();
		return level;
	}
	
	public static void main(String[] args){
		BarCommon common = new BarCommon();
		try {
			int l = common.getBarOwnerLevel("ROo2JOyksqNms6CjJdqf7w==");
			System.out.println("l is "+l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public int getBarOwnerLevel(String encryCustId) throws Exception{
		Long deCustId;		
		//解密加密的用户custId
		deCustId = DesUtils.decryptCustId(encryCustId);
		log.info("decrypted cust id is "+deCustId);
		//用户等级，积分信息
		AttachAccount account = AttachAccountDb.getAttachAccount(deCustId.toString());
		int l1 = getLevelByGrade(account.getAccountGrade());
		try{
			List<Bar> bars = BarDb.getBarListOfBarOwner(deCustId);		
			//存放所有吧主的等级信息
			int l[] = new int[bars.size()];
			if(bars!=null){
				for(int i=0; i<bars.size();i++){					
					int l2 = getLevelByMemberNum(bars.get(i).getMemberNum());
					int l3 = getLevelByArticleNum(bars.get(i).getArticleNum());	
					if(l1==0 || l2==0 || l3==0){
						l[i] = 0;
					}
					else{
						String key = Integer.toString(l1)+Integer.toString(l2)+Integer.toString(l3);
						log.info("key is "+ key);
						l[i]=levelMap.get(key);
					}	
					log.info("l is "+ l[i]);			
				}	
				//如果同一用户有N个吧，满足条件不能累加，一个吧也必须满足吧主等级&吧成员数量&帖子数量三个条件才可，并且以最高级别头衔为准。	
				return getMaxValue(l);
			}		
		}
		catch(Exception e){
			e.printStackTrace();
			log.info("该用户不是吧主 ");
		}
		return 0;
	}
	
	/**
	 * 获取数组内的最大值	
	 */
	public int getMaxValue(int[] value){
		int max = value[0];
		for(int i=1; i<value.length; i++){
			if(value[i]>max){
				max = value[i];
			}
		}
		return max;
		
	}
	
	/**
	 * 根据index获得吧主称号	
	 */
	public BarHostTitle getBarHostTitle(int index){
		return BarHostTitle.getBarHostTitle(index);
	}
	
   //	吧主称号	用户等级(可配置)	吧成员数量（可配置）	帖子数量(可配置)
   //	新进吧主	Lv10—Lv17	1-100	        1-200
   //	白银吧主	Lv18—LV25	101-200	        201-500
   //	黄金吧主	Lv26—LV35	201-400	        501-1000
   //	铂金吧主	Lv36—LV43	401-700	        1001-1200
   //	钻石吧主	Lv44—LV50	701-1000	    1201-1500
   //	一个吧主运营一个吧，必须同一个吧同时满足吧主等级&吧成员数量&帖子数量三个条件，其中颁发吧主称号时以三条中最低的一项为准。
   //	如果同一用户有N个吧，满足条件不能累加，一个吧也必须满足吧主等级&吧成员数量&帖子数量三个条件才可，并且以最高级别头衔为准。	
	/**
	 * 根据用户的等级，获得吧主称号所在的级别	
	 */
	public Map<String, Integer> generateMap(){
		Map<String, Integer> level = new HashMap<String, Integer>();
		//i代表用户等级为1--5
		//j代表成员数量等级为1--5
		//j代表帖子数量等级为1--5
		for(int i=1; i<=5; i++){
			for(int j=1; j<=5; j++){
				for(int k=1; k<=5; k++){
					String key = Integer.toString(i)+Integer.toString(j)+Integer.toString(k);
					//一个吧主运营一个吧，必须同一个吧同时满足吧主等级&吧成员数量&帖子数量三个条件，其中颁发吧主称号时以三条中最低的一项为准。
					int min = i;
					if(min>j){
						min=j;
					}
					else if(min>k){
						min = k;
					}
					level.put(key, min);
					//System.out.println("level is "+key+","+level.get(key));
				}
			}
		}
		return level;
	}
	
	/**
	 * 根据用户的等级，获得吧主称号所在的级别	
	 */
	public int getLevelByGrade(int grade){
		int[][] level = BarHostTitle.getLevel();
		for(int i=0; i<level.length; i++){
			if(grade>=level[i][0] && grade<=level[i][1]){
				return (i+1);
			}			
		}
		if(grade>=level[level.length-1][1]){
			return level.length+1;
		}
		return 0;
	}
	
	/**
	 * 根据用户的吧成员数量，获得吧主称号所在的级别	
	 */
	public int getLevelByMemberNum(int num){
		int[][] member = BarHostTitle.getMemberNum();
		for(int i=0; i<member.length; i++){
			if(num>=member[i][0] && num<=member[i][1]){
				return (i+1);
			}			
		}
		if(num>=member[member.length-1][1]){
			return member.length;
		}
		return 0;
	}
	
	/**
	 * 根据用户的帖子数量，获得吧主称号所在的级别	
	 */
	public int getLevelByArticleNum(int num){
		int[][] member = BarHostTitle.getArticleNum();
		for(int i=0; i<member.length; i++){
			if(num>=member[i][0] && num<=member[i][1]){
				return (i+1);
			}			
		}
		if(num>=member[member.length-1][1]){
			return member.length;
		}
		return 0;
//		if(num>=1 && num<=200){
//			return 1;
//		}
//		if(num>=201 && num<=500){
//			return 2;
//		}
//		if(num>=501 && num<=1000){
//			return 3;
//		}
//		if(num>=1001 && num<=1200){
//			return 4;
//		}
//		if(num>=1201 && num<=1500){
//			return 5;
//		}
//		if(num>=1501){
//			return 5;
//		}		
		//return 0;
	}

}
