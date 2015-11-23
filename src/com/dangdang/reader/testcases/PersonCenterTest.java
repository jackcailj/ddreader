package com.dangdang.reader.testcases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.reader.functional.personcenter.*;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.dangdang.autotest.common.PlatForm;
import com.dangdang.common.functional.login.LogOut;
import com.dangdang.common.functional.login.Login;
import com.dangdang.common.functional.login.LoginInfo;
import com.dangdang.common.functional.login.TouristsLogin;
import com.dangdang.ddframework.core.TestCaseBase;
import com.dangdang.reader.functional.account.GetAccount;
import com.dangdang.reader.functional.purchaseAndDownload.GetRechargeVirtualPid;
import com.dangdang.reader.functional.purchaseAndDownload.PurchaseEbookVirtualPayment;


public class PersonCenterTest extends  TestCaseBase {
	public static Logger logger=Logger.getLogger(PersonCenterTest.class);
	
	@Test(dataProvider="dataProvider")
	public void DepositShowViewTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		DepositShowView depositShowView =new DepositShowView(param);
		depositShowView.doWorkAndVerify();

		assert(excpectd.equals(depositShowView.getReponseResult().getStatus().getCode().toString()));
		assert(depositShowView.getDataVerifyResult());
	}
	
/*	//@Test(dataProvider="dataProvider",groups={"testing","staging","online"})
	public void LastTimePaymentTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		LastTimePayment lastTimePayment =new LastTimePayment(param);
		lastTimePayment.doWorkAndVerify();

		assert(excpectd.equals(lastTimePayment.getReponseResult().getStatus().getCode().toString()));
		assert(lastTimePayment.getDataVerifyResult());
	}*/
	
	@Test(dataProvider="dataProvider")
	public void ConsumerDepositRecordTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		ConsumerDepositRecord functional =new ConsumerDepositRecord(param);
		functional.doWorkAndVerify();

		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}
	
	@Test(dataProvider="dataProvider")
	public void ConsumerDepositTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		ConsumerDeposit functional =new ConsumerDeposit(param);
		functional.doWorkAndVerify();

		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}
	
	@Test(dataProvider="dataProvider",enabled=false)
	public void GenerateOrderTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GenerateOrder functional =new GenerateOrder(param);
		functional.doWorkAndVerify();

		//assert(excpectd.equals(lastTimePayment.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}
	
	
	@Test(dataProvider="dataProvider")
	public void GetAccountTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GetAccount functional =new GetAccount(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}
	
	@Test(dataProvider="dataProvider")
	public void PurchaseEbookVirtualPaymentTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		PurchaseEbookVirtualPayment functional =new PurchaseEbookVirtualPayment(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}
	
	@Test(enabled=false)
	public void directBuyTest() throws Exception {
		
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setUserName("cailj_ddtest2@126.com");
		loginInfo.setPassWord("ddtest");
		loginInfo.setLoginType("email");
		
		Login login =new Login(loginInfo);
		login.doWork(); 
		String token = login.getToken();
		
		//自动解析参数并生成productid
		int productID=1900004873;
		int price=3160;
		
		//获取需要充值的需要的商品id
		GetRechargeVirtualPid getRechargeVirtualPid =new GetRechargeVirtualPid(login, price);
		getRechargeVirtualPid.doWork();
		
		//进行充值
		ConsumerDeposit consumerDeposit =new ConsumerDeposit(login,getRechargeVirtualPid.getReponseResult().getData().getProductIds(),price,PlatForm.DDREADER_ANDROID);
		consumerDeposit.setEXCEPTSUCCESS(true);
		consumerDeposit.doWorkAndVerify();
		
		assert("0".equals(consumerDeposit.getReponseResult().getStatus().getCode().toString()));
		assert(consumerDeposit.getDataVerifyResult());
		
		//购买电子书
		List<Integer> productids =new ArrayList<Integer>();
		productids.add(productID);
		PurchaseEbookVirtualPayment functional =new PurchaseEbookVirtualPayment(login,productids);
		functional.setEXCEPTSUCCESS(true);
		functional.doWorkAndVerify();

		assert("0".equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}
	
	@Test(dataProvider="dataProvider")
	public void LogOutTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		LogOut functional =new LogOut(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}

    @Test(dataProvider="dataProvider")
    public void GetPaymentTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
        GetPayment functional =new GetPayment(param);
        functional.doWorkAndVerify();

        assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
        assert(functional.getDataVerifyResult());
    }
	
	@Test(dataProvider="dataProvider")
	public void GetBuyBookListTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GetBuyBookList functional =new GetBuyBookList(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}	
	
	@Test(dataProvider="dataProvider")
	public void GetPersonalInfoTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GetPersonalInfo functional =new GetPersonalInfo(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}	
	
	@Test(dataProvider="dataProvider")
	public void GetImgheadTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GetImghead functional =new GetImghead(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}


    @Test(dataProvider="dataProvider")
    public void ChangePersonalInfoTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
        ChangePersonalInfo functional =new ChangePersonalInfo(param);
        functional.doWorkAndVerify();

        assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
        assert(functional.getDataVerifyResult());
    }


    @Test(dataProvider="dataProvider")
    public void ChangeUserProfileTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
        ChangeUserProfile functional =new ChangeUserProfile(param);
        functional.doWorkAndVerify();

        assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
        assert(functional.getDataVerifyResult());
    }

    @Test(dataProvider="dataProvider")
    public void GetUserProfileTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
        GetUserProfile functional =new GetUserProfile(param);
        functional.doWorkAndVerify();

        assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
        assert(functional.getDataVerifyResult());
    }

    @Test(dataProvider="dataProvider")
	public void GetExperienceInfoListTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GetExperienceInfoList functional =new GetExperienceInfoList(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}	
	
/*	@Test(dataProvider="dataProvider")
	public void TouristsLoginTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		TouristsLogin functional =new TouristsLogin(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}*/

	
	@Test
	public void GetDeviceStartPageInfoTest() throws Exception {
		GetDeviceStartPageInfo functional =new GetDeviceStartPageInfo(null);
		functional.doWorkAndVerify();
		
		assert("0".equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}
	
	@Test(dataProvider="dataProvider")
	public void GetPersonalBookNoteInfoListTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GetPersonalBookNoteInfoList functional =new GetPersonalBookNoteInfoList(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());

	}
	
	@Test(dataProvider="dataProvider",enabled=false)
	public void GetPersonalBookNoteInfoTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GetPersonalBookNoteInfo functional =new GetPersonalBookNoteInfo(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}
	
	@Test(dataProvider="dataProvider",enabled = false)
	public void GetPublicUnReadMessageCountTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GetPublicUnReadMessageCount functional =new GetPublicUnReadMessageCount(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}
	
	@Test(dataProvider="dataProvider",enabled = false)
	public void GetPersonUnReadMessageCountTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GetPersonalUnReadMessageCount functional =new GetPersonalUnReadMessageCount(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}
	
	@Test(dataProvider="dataProvider")
	public void GetPublicMessageInfoListTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GetPublicMessageInfoList functional =new GetPublicMessageInfoList(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}
	
	@Test(dataProvider="dataProvider")
	public void GetPersonalMessageInfoListTest(String caseName,Map<String, String> param,String excpectd) throws Exception {
		GetPersonalMessageInfoList functional =new GetPersonalMessageInfoList(param);
		functional.doWorkAndVerify();
		
		assert(excpectd.equals(functional.getReponseResult().getStatus().getCode().toString()));
		assert(functional.getDataVerifyResult());
	}
	

	@Test(dataProvider="dataProvider")
	public void getLotteryResultWithBellTest(String caseName,Map<String, String> param,String excpectd) throws Exception{
		GetLotteryResultWithBell lotteryResult =new GetLotteryResultWithBell(param);
		lotteryResult.doWorkAndVerify();
		
		if(caseName.equals("摇第四次")||caseName.equals("摇第五次")){
			if(lotteryResult.getResult().getData().getCanDoNum()!=null&&Integer.parseInt(lotteryResult.getResult().getData().getCanDoNum())>=0){
				assert("0".equals(lotteryResult.getResult().getStatus().getCode().toString()));
			}
			else{
				assert(excpectd.equals(lotteryResult.getResult().getStatus().getCode().toString()));
			}
		}
		else{
			assert(excpectd.equals(lotteryResult.getResult().getStatus().getCode().toString()));
		}
		
	}
	
	@Test(dataProvider="dataProvider")
	public void custSigninWithBellTest(String caseName,Map<String, String> param,String excpectd) throws Exception{
		CustSigninWithBell custSigninWithBell =new CustSigninWithBell(param);
		custSigninWithBell.doWorkAndVerify();
		assert(excpectd.equals(custSigninWithBell.getResult().getStatus().getCode().toString()));
		assert(custSigninWithBell.getDataVerifyResult());
	}
	
	@Test(dataProvider="dataProvider")
	public void effectiveAttachItemsListTest(String caseName,Map<String, String> param,String excpectd) throws Exception{
		EffectiveAttachItemsList list = new EffectiveAttachItemsList(param);
		list.doWorkAndVerify();
		assert(excpectd.equals(list.getResult().getStatus().getCode().toString()));
		assert(list.getDataVerifyResult());
	}
	
	@Test(dataProvider="dataProvider")
	public void recivedOrCancelPrizeTest(String caseName,Map<String, String> param,String excpectd) throws Exception{
		RecivedOrCancelPrize recive = new RecivedOrCancelPrize(param);
		recive.doWorkAndVerify();
		assert(excpectd.equals(recive.getResult().getStatus().getCode().toString()));
		assert(recive.getDataVerifyResult());
	}

}
