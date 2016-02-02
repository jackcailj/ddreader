package com.dangdang.reader.functional.purchaseAndDownload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.Login;
import com.dangdang.ddframework.core.TestDevice;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.RecordExVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.security.SignUtils;
import com.dangdang.ecms.meta.OrderForm;
import com.dangdang.ecms.meta.OrderItem;
import com.dangdang.ecms.meta.UserEbook;
import com.dangdang.reader.functional.account.GetAccount;
import com.dangdang.reader.functional.bookstore.BookList;
import com.dangdang.param.model.ParseResult;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.Account;
import com.dangdang.reader.functional.reponse.MobileEbookInfo;
import com.dangdang.reader.functional.reponse.PurchaseEbookVirtualPaymentReponse;

public class PurchaseEbookVirtualPayment extends FunctionalBaseEx{
	
	public static Logger logger = Logger.getLogger(PurchaseEbookVirtualPayment.class);
	Account accountInfo;
	GetAccount getAcount;
	
	ReponseV2<PurchaseEbookVirtualPaymentReponse> reponseResult;

	Integer totalCost=0;
	//private MasterAccount masterAccount;
	//private AttachAccount attachAccount;
	//private String custid;
	List<Integer> productIds =new ArrayList<Integer>();

	public PurchaseEbookVirtualPayment(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("purchaseEbookVirtualPayment");
	}
	
	/*
	 * 参数：
	 * 			token
	 * 			productIds：购买的商品ID列表
	 * 			totalCost：总共花费的金额
	 */
	public PurchaseEbookVirtualPayment(Login login,List<Integer> productIds) {
		// TODO Auto-generated constructor stub
		paramMap.put("token", login.getToken());
		paramMap.put("timestamp", "auto");
		paramMap.put("sign", "auto");
		paramMap.put("from_url", "103");
		
		originalParamMap.put("userName", login.getLoginInfo().getUserName());
		this.productIds=productIds;
		//this.totalCost=totalCost;
		//paramMap.put("productIds", StringUtils.join(productIds,","));
		addAction("purchaseEbookVirtualPayment");
		addCommonParam();
		handleParam();
		setLogin(login);
	}

	public ReponseV2<PurchaseEbookVirtualPaymentReponse> getReponseResult() {
		// TODO Auto-generated method stub
		return reponseResult;
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		setLogin(parseResult.getLogin());
		
		
		if(login!=null){
			//custid=Login.getCusId(originalParamMap.get("userName").toString());
			
			//解析用例参数，获取期望的productid
			if(paramMap.get("productIds")!=null)
			{
				//
				//如果为数字表示要购买有效的书籍
				Integer buyCount=1;
				try{
					String[] data=paramMap.get("productIds").toString().split(",");
					buyCount=Integer.parseInt(data[1]);
					paramMap.put("productIds", "exist");
				}catch(Exception e){
					logger.info("解析productIds为购买数量异常"+e);
				}
				
				//获取有效书籍productid
				String productIdString=paramMap.get("productIds").toString();
				if(productIdString.contains("exist")){
					String ebookSting ="select e.productId,e.eBookPrice from (select e.productId,e.eBookPrice from ebook e, ebook_resfile r where "+(Config.getDevice()!=TestDevice.ANDROID?"e.IAP_SHELF_STATUS=1 and e.IAP_DEVICE_TYPE like '%"+Config.getDevice()+"%' ":"e.shelfStatus = 1")+" and e.id = r.EBOOK_ID and r.RESFILE_TYPE = 'epub' GROUP BY e.id) as e LEFT JOIN (select ue.productId from user_ebook ue where ue.CUST_ID="+login.getCustId()+") as uet on uet.productId=e.productId where uet.productId is null limit "+buyCount;
					//String ebookSting ="select e.productId,e.eBookPrice from (select e.productId,e.eBookPrice from ebook e, ebook_resfile r where "+(Config.getDevice()!=TestDevice.ANDROID?"IAP_SHELF_STATUS=1 and IAP_DEVICE_TYPE like '%"+Config.getDevice()+"%' ":"e.shelfStatus = 1")+" and e.id = r.EBOOK_ID and r.RESFILE_TYPE = 'epub' GROUP BY e.id) as e LEFT JOIN (select ue.productId from user_ebook ue where ue.CUST_ID="+login.getCustId()+") as uet on uet.productId=e.productId where uet.productId is null limit "+buyCount;
					//String ebookSting="select e.productId,e.eBookPrice from ebook e left JOIN user_ebook ue on e.productId=ue.productId and ue.CUST_ID="+login.getCustId()+" where e.shelfStatus=1 and e.category in('bb','br','be') and ue.productId is NULL LIMIT "+buyCount;
					List<Map<String,Object>> ebooks = DbUtil.selectList(Config.ECMSDBConfig, ebookSting);
					
					for(Map<String,Object> book :ebooks){
						productIds.add(Integer.parseInt(book.get("productId").toString()));
						totalCost+=Integer.parseInt(book.get("eBookPrice").toString());
					}
				}
				
				//获取无效的书籍，下架书籍
				if(productIdString.contains("invalid")){
					String ebookSting ="select e.productId,e.eBookPrice from (select e.productId,e.eBookPrice from ebook e, ebook_resfile r where "+(Config.getDevice()!=TestDevice.ANDROID?"e.IAP_SHELF_STATUS=2 and e.IAP_DEVICE_TYPE like '%"+Config.getDevice()+"%' ":"e.shelfStatus = 2")+" and e.id = r.EBOOK_ID and r.RESFILE_TYPE = 'epub' GROUP BY e.id) as e LEFT JOIN (select ue.productId from user_ebook ue where ue.CUST_ID="+login.getCustId()+") as uet on uet.productId=e.productId where uet.productId is null limit "+buyCount;
					//String ebookSting ="select e.productId,e.eBookPrice from (select e.productId,e.eBookPrice from ebook e, ebook_resfile r where e.shelfStatus = 2 and e.id = r.EBOOK_ID and r.RESFILE_TYPE = 'epub' GROUP BY e.id) as e LEFT JOIN (select ue.productId from user_ebook ue where ue.CUST_ID="+login.getCustId()+") as uet on uet.productId=e.productId where uet.productId is null limit "+buyCount;
					//String ebookSting="select e.productId,e.eBookPrice from ebook e left JOIN user_ebook ue on e.productId=ue.productId and ue.CUST_ID="+login.getCustId()+" where e.shelfStatus=2 and e.category in('bb','br','be') and ue.productId is NULL LIMIT "+buyCount;
					List<Map<String,Object>> ebooks = DbUtil.selectList(Config.ECMSDBConfig, ebookSting);
					
					for(Map<String,Object> book :ebooks){
						productIds.add(Integer.parseInt(book.get("productId").toString()));
						totalCost+=Integer.parseInt(book.get("eBookPrice").toString());
					}
				}
				
				//不存在的书籍id，随便写一个数字
				if(productIdString.contains("notexist")){
					productIds.add(12);
				}
				
				//购买字体时，获得字体的productid
				if( paramMap.get("productIds").toString().toLowerCase().trim().contains("font")){
					BookList bookList = new BookList(login,"AndroidV4_font");
					bookList.doWork();
					List<MobileEbookInfo> fontList= bookList.getReponseResult().getData().getEbookList();
					productIds.add(Integer.parseInt(fontList.get(0).getProductId()));
					totalCost+=fontList.get(0).getPrice();
				}


                if(productIds.size()>0) {
                    paramMap.put("productIds", StringUtils.join(productIds, ","));
                }

			}
			//处理productid不是从excel获取数据解析出来的，而是其他接口传递方式
			else if(productIds!=null && productIds.size()>0){
				paramMap.put("productIds",StringUtils.join(productIds,","));
				String selectString = "select e.productId,e.eBookPrice from ebook e where e.productId in("+StringUtils.join(productIds,",")+")";
				List<Map<String,Object>> ebooks = DbUtil.selectList(Config.ECMSDBConfig, selectString);
				
				for(Map<String,Object> book :ebooks){
					//productIds.add(Integer.parseInt(book.get("productId").toString()));
					totalCost+=Integer.parseInt(book.get("eBookPrice").toString());
				}
			}
			
					
			//生成订单
			if(paramMap.get("timestamp")!=null && paramMap.get("timestamp").toString().equals("auto")){
				paramMap.put("timestamp", ""+new Date().getTime());
			}
			
			//生成sign参数
			if(paramMap.get("sign")!=null && paramMap.get("sign").toString().equals("auto")){
				String productIdString=(paramMap.get("productIds")==null?"":paramMap.get("productIds").toString());
				String key = SignUtils.encryptOrderVirtualPayment(Long.parseLong(login.getCustId()), productIdString);
				//GetEbookOrderFlowV2 getEbookOrderFlowV2 = new GetEbookOrderFlowV2(paramMap.get("token").toString(),paramMap.get("productIds").toString());
				//getEbookOrderFlowV2.doWork();
				//String md5key =getEbookOrderFlowV2.getReponseResult().getData().getKey();
				//String key=RsaUtils.decryptByPublicKey(RsaUtils.generateKeyPair().get(RsaKey.publicKey), md5key);
				
				//String source = "19001011930.011427093685827";
				String source = productIdString+(paramMap.get("timestamp")==null?"":paramMap.get("timestamp").toString());
				String sign =SignUtils.createBindPermissionSign(source, key);
				paramMap.put("sign", sign);
			}
		}
		
		
		//paramMap.put("source", paramMap.get("productIds").toString()+paramMap.get("totalCost").toString()+paramMap.get("timestamp").toString());
		
	
	}

	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<PurchaseEbookVirtualPaymentReponse>>(){});
	}
	
	@Override
	protected void genrateVerifyData() throws Exception {
		//String token =paramMap.get("token").toString();
		if(login!=null){	//验证主副账户扣款正确，先扣附账户、后扣主账户
		    //attachAccount = DbUtil.selectOne(Config.ACCOUNTDBConfig, "select * from attach_account where cust_id="+custid,AttachAccount.class);
		    getAcount = new GetAccount(login, paramMap.get("deviceType").toString());
			getAcount.doWork();
			accountInfo = getAcount.getReponseResult().getData().getAccount();
			Long attachAccountMoney =Config.getDevice()==TestDevice.ANDROID?accountInfo.getAttachAccountMoney():accountInfo.getAttachAccountMoneyIOS();
		    int diff=(int) (totalCost-attachAccountMoney);
		    if(EXCEPTSUCCESS && attachAccountMoney>0){
		    	if(Config.getDevice()==TestDevice.ANDROID){
		    		accountInfo.setAttachAccountMoney((long) (diff<0? Math.abs(diff):0));
		    	}
		    	else {
		    		accountInfo.setAttachAccountMoneyIOS((long) (diff<0? Math.abs(diff):0));
				}
		    }
			
			// TODO Auto-generated method stub
			//masterAccount = DbUtil.selectOne(Config.ACCOUNTDBConfig, "select * from master_account where cust_id="+custid, MasterAccount.class);
			if(EXCEPTSUCCESS && diff>0){
				if(Config.getDevice()==TestDevice.ANDROID){
					accountInfo.setMasterAccountMoney(accountInfo.getMasterAccountMoney()-diff);
				}
				else {
					accountInfo.setMasterAccountMoneyIOS(accountInfo.getMasterAccountMoneyIOS()-diff);
				}
			}
			
			//验证订单数据正确
			if(EXCEPTSUCCESS){
				OrderForm orderForm =new OrderForm();
				orderForm.setCustId(Long.parseLong(login.getCustId()));
				//orderForm.setPrice(new BigDecimal(totalCost));
				orderForm.setOrderType("98");
				//orderForm.setPayTypeSub(2);
				
				List<OrderItem> orderItems=new ArrayList<OrderItem>();
				for(Integer productId: productIds){
					OrderItem orderItem =new OrderItem();
					orderItem.setItemId((long)productId);
					
					orderItems.add(orderItem);
				}
				dataVerifyManager.add(new RecordExVerify(Config.ECMSDBConfig, orderForm, "ID", " from order_form where CUST_ID="+login.getCustId(),orderItems,"ORDER_NO").setVerifyContent("验证订单数据是否正确"));
			}
				
			//验证user_ebook多一条记录，电子书籍权限下放
			for(Integer productId: productIds){
				UserEbook userEbook = new UserEbook();
				userEbook.setProductId(productId.longValue());
				userEbook.setCustId(Long.parseLong(login.getCustId()));
				dataVerifyManager.add(new RecordExVerify(Config.ECMSDBConfig, userEbook, "ID", "from user_ebook where CUST_ID="+login.getCustId()+" and productId="+productId).setVerifyContent("验证电子书籍权限是否下放"));
			}
		}
	}
	
	@Override
	protected void dataVerify() throws Exception {
		// TODO Auto-generated method stub
		if(reponseResult.getStatus().getCode()==0){
			dataVerifyManager.add(new ListVerify(reponseResult.getData().getProductIds(), productIds, false).setVerifyContent("验证返回值是否正确"));
			
		}
		//验证金额是否正确
		if(login !=null){
			GetAccount afterAcount = new GetAccount(login, paramMap.get("deviceType").toString());
			afterAcount.doWork();
			Account afterAccount = afterAcount.getReponseResult().getData().getAccount();
			dataVerifyManager.add(new ValueVerify<Account>(accountInfo, afterAccount,true).setVerifyContent("验证账户金额是否正确"),VerifyResult.SUCCESS);
		}
		
		super.dataVerify();
	}
}
