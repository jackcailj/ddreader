package com.dangdang.readerV5.purchase;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.dangdang.config.Config;
import com.dangdang.promotion.api.ISalePromotionApi;
import com.dangdang.promotion.vo.ProductPromotionVo;
import com.dangdang.promotion.vo.PromotionPriceVo;
import com.dangdang.promotion.vo.SalePromotionVo;


/**
 * 满减活动
 * @author guohaiying
 * @date 2016年6月1日 上午11:28:33
 */
public class SalePromotionApiDubbo {
	protected static Logger log = Logger.getLogger(SalePromotionApiDubbo.class);
	String env = Config.getEnvironment().toString();
	ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");
	ISalePromotionApi api = (ISalePromotionApi)factory.getBean("salePromotionApi");

	//批量获取单品的促销信息
	public void get(List<Long> productIds) throws Exception{
		ProductPromotionVo  vo = api.getPromotionList(productIds);		
		//List<SalePromotionVo> list = vo.getPromotionList(productIds.get(0));	
		System.out.println("1: "+ JSONArray.toJSONString(vo));
	}
	
	public void getShoppingList(List<Long> productIds) throws Exception {
		List<SalePromotionVo> vo = api.getShoppingCartPromotionList(productIds);
		System.out.println("2: " + JSONArray.toJSONString(vo));
	}
	
	public void getPromotionPrice(List<Long> productIds) throws Exception{
		PromotionPriceVo vo = api.getPromotionPrice(productIds);
		System.out.println("3: "+ JSONArray.toJSONString(vo));
	}
	
	public static void main(String[] args) throws Exception{
		SalePromotionApiDubbo dubbo = new SalePromotionApiDubbo();
		List<Long> list = new ArrayList<Long>();
		//1900906778  限时抢
		//1900906927l满减  
		//1900906766l 限时抢，满减   		
		//1900906758限免
		//1900906892l 频道VIP
//		list.add(190090751l);
//		list.add(1900906758l);  //限免   media表中 promotion_id=3的是免费的书
		list.add(1900906766l);  //限时抢，满减		
		list.add(1900906892l); //频道VIP
//		list.add(1900906989l); 
//		list.add(1900906949l); 
//		list.add(1900907032l); 
//		list.add(1900907169l);
//		list.add(1900906739l);
//		list.add(1900906937l);
//		list.add(1900906945l);
//		list.add(1900906739l);
//		list.add(1900907169l);
		list.add(1900907161l);
//		list.add(1900906943l);
		dubbo.get(list);
		dubbo.getShoppingList(list);
		dubbo.getPromotionPrice(list);
		
	}
	
}
