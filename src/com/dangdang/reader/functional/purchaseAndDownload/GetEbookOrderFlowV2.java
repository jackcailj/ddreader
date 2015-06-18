package com.dangdang.reader.functional.purchaseAndDownload;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.common.functional.login.ILogin;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.common.PlatForm;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.core.TestDevice;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ecms.EcmsUtil;
import com.dangdang.ecms.meta.Ebook;
import com.dangdang.reader.functional.bookstore.BookList;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.GetEbookOrderFlowV2Reponse;
import com.dangdang.reader.functional.reponse.MobileEbookInfo;
import com.dangdang.digital.meta.MediaActivityInfo;

public class GetEbookOrderFlowV2 extends FunctionalBaseEx {
	ReponseV2<GetEbookOrderFlowV2Reponse> reponseResult;
	
	List<Integer> productIds =new ArrayList<Integer>();
	
	public GetEbookOrderFlowV2(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getEbookOrderFlowV2");
	}
	
	public GetEbookOrderFlowV2(ILogin login,String productIds)
	{
		paramMap.put("token",login.getToken());
		paramMap.put("productIds", productIds);
		addAction("getEbookOrderFlowV2");
		addCommonParam();
		this.login=login;
	}
	
	public ReponseV2<GetEbookOrderFlowV2Reponse> getReponseResult(){
		return reponseResult;
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetEbookOrderFlowV2Reponse>>(){});
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		login=parseResult.getLogin();
		
		
		//if(paramMap.get("token")!=null &&  StringUtils.isNotBlank(originalParamMap.get("userName").toString())){
		if(login!=null){
			if(paramMap.get("productIds")!=null )
			{
				//String custid="";
				//if(StringUtils.isNotBlank(originalParamMap.get("userName").toString())){
				//	custid=Login.getCusId(originalParamMap.get("userName").toString());
				//}
				
				Integer buyCount=1;
				try{
					String[] data=paramMap.get("productIds").toString().split(",");
					buyCount=Integer.parseInt(data[1]);
					paramMap.put("productIds", "exist");
				}catch(Exception e){
					logger.info("解析productIds为购买数量异常"+e);
				}
				
				String productIdString=paramMap.get("productIds").toString();
				if(productIdString.contains("exist")){
					String ebookSting="";
					List<Ebook> ebooks=null;
					if(StringUtils.isNotBlank(login.getCustId())){
						ebooks=EcmsUtil.getUserUnbuyBook(login.getCustId(), buyCount);
						//ebookSting ="select e.productId,e.eBookPrice from (select e.productId,e.eBookPrice from ebook e, ebook_resfile r where e.shelfStatus = 1 and e.id = r.EBOOK_ID and r.RESFILE_TYPE = 'epub' GROUP BY e.id) as e LEFT JOIN (select ue.productId from user_ebook ue where ue.CUST_ID="+login.getCustId()+") as uet on uet.productId=e.productId where uet.productId is null limit "+buyCount;
					}
					else {
						//ebookSting="select e.productId,e.eBookPrice from ebook e, ebook_resfile r where e.shelfStatus = 1 and e.id = r.EBOOK_ID and r.RESFILE_TYPE = 'epub' GROUP BY e.id  LIMIT "+buyCount;
						ebooks=EcmsUtil.getValidBook(buyCount);
					}
					//List<Map<String,Object>> ebooks = DbUtil.selectList(Config.ECMSDBConfig, ebookSting);
					
					for(Ebook book :ebooks){
						productIds.add(book.getProductId());
					}
				}
				
				if(productIdString.contains("invalid")){
					List<Ebook> soldOutEbooks=EcmsUtil.getSoldOutBook(buyCount);
					
					//String ebookSting ="select e.productId,e.eBookPrice from (select e.productId,e.eBookPrice from ebook e, ebook_resfile r where e.shelfStatus = 2 and e.id = r.EBOOK_ID and r.RESFILE_TYPE = 'epub' GROUP BY e.id) as e LEFT JOIN (select ue.productId from user_ebook ue where ue.CUST_ID="+login.getCustId()+") as uet on uet.productId=e.productId where uet.productId is null limit "+buyCount;
					//List<Map<String,Object>> ebooks = DbUtil.selectList(Config.ECMSDBConfig, ebookSting);
					
					for(Ebook book :soldOutEbooks){
						productIds.add(book.getProductId());
					}
				}
				
				if(productIdString.contains("notexist")){
					productIds.add(12);
				}
				
				if(productIdString.contains("font")){
					BookList bookList = new BookList(login,Config.getEnvironment()==TestEnvironment.TESTING?"freefont":"AndroidV4_font");
					bookList.doWork();
					List<MobileEbookInfo> fontList= bookList.getReponseResult().getData().getEbookList();
					productIds.add(Integer.parseInt(fontList.get(0).getProductId()));
					//totalCost+=fontList.get(0).getPrice();
				}
				
				if(productIds.size()>0){
					paramMap.put("productIds",StringUtils.join(productIds,","));
				}
			}
		}
		
	
		//}
		
	}
	
	
	@Override
	protected void dataVerify() throws Exception {
		// TODO Auto-generated method stub
		
		if(reponseResult.getStatus().getCode()==0){



			/*String selectString="select  deposit_gift_read_price,deposit_money,deposit_read_price,end_time,relation_product_id,start_time"
					+" from media_activity_info where from_paltform='"+PlatForm.DDREADER_ANDROID.toString()+"' and activity_type_id="+(Config.getEnvironment()==(TestEnvironment.TESTING)?"1010":"1016")+" and  status=1"
					+" ORDER BY  deposit_read_price ";*/
			List<MediaActivityInfo> infos = EcmsUtil.getDepositShowView(PlatForm.getPlatForm(Config.getDevice()).toString());
			dataVerifyManager.add(new ListVerify(infos, reponseResult.getData().getPayingProducts(), true));
		}
		else{
			//验证返回结果为null
		}
		
		super.dataVerify();
	}
	
	public String getActivityTypeCode() {
		return (Config.getEnvironment()==(TestEnvironment.TESTING)?(Config.getDevice()==TestDevice.ANDROID?"1010":"1014"):(Config.getDevice()==TestDevice.ANDROID?"1016":""));
	}
}
