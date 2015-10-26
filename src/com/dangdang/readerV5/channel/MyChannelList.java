package com.dangdang.readerV5.channel;

import java.net.URLEncoder;
import java.security.Key;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.channel.MyChannelListSQL;
import com.dangdang.readerV5.reponse.MyChannelListReponse;

import fitnesse.slim.SystemUnderTest;

/**
 * 获取我的频道列表接口
 * @author guohaiying
 *
 */
public class MyChannelList extends FixtureBase{
	ReponseV2<MyChannelListReponse> reponseResult;	
	String custId_param = paramMap.get("custId");
	
	@SystemUnderTest
	public MyChannelListSQL sql = new MyChannelListSQL();
	
	public void changeParamWithKeyAndValue(String key, String value){
		paramMap.put(key, value);
	}
	
	public String setCustId(String custID) throws NumberFormatException, Exception{
		Long l = Long.parseLong(custID);
		String _custId = URLEncoder.encode(encryptCustId(l));
		paramMap.put("custId", _custId);
		return _custId;
	}
	
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<MyChannelListReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){	
			String custId = null;
			//if(custId_param.equals("")||custId_param==null){
				custId=login.getCustId();
			//}
			List<com.dangdang.readerV5.reponse.MyChannelList> myChannelList = MyChannelListSQL.getMyChannelList(custId);
			MyChannelListReponse dbReponse = new MyChannelListReponse();
			dbReponse.setCount(String.valueOf(myChannelList.size()));
			dbReponse.setChannelList(myChannelList);
			dbReponse.setTotal(MyChannelListSQL.getUserChannelListTotal(custId));
			dataVerifyManager.add(new ValueVerify<MyChannelListReponse>(dbReponse, reponseResult.getData(), true));			
		}
		return dataVerifyManager.dataVerify();
	}
	
	public boolean verifyTotal() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<MyChannelListReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){	
			dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getTotal(), "0"));		
		}
		return dataVerifyManager.dataVerify();
	}
	
	//加密custId
	public static String encryptCustId(Long custId) throws Exception {
		String keyStr = "MYabzjizBOY=";
		if (StringUtils.isBlank(keyStr)) {
			throw new Exception("请在配置文件中配置custId转换秘钥");
		}
		Key key = getKeyFromString(keyStr);
		if (custId == null) {
			throw new Exception("custId不能为空");
		}
		byte[] originalBytes = String.valueOf(custId).getBytes("UTF-8");
		byte[] encryptBytes = encryptBytes(originalBytes, key);
		return Base64.encodeBase64String(encryptBytes);
	}
	
	public static Key getKeyFromString(String keyStr) throws Exception {
		try {
			byte[] keyByte = Base64.decodeBase64(keyStr);
			DESKeySpec desKeySpec = new DESKeySpec(keyByte);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(desKeySpec);
			return key;
		} catch (Exception e) {
			throw new Exception("获取DES秘钥异常", e);
		}
	}
	
	public static byte[] encryptBytes(byte[] byteS, Key key) throws Exception {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new Exception("加密异常" + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	public static void main(String[] args){
		try {
			MyChannelList m = new MyChannelList();
			String s= m.setCustId("50098052");//MyChannelList.encryptCustId(50098052l);
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
