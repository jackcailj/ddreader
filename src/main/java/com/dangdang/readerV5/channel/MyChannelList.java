package com.dangdang.readerV5.channel;

import java.security.Key;
import java.util.ArrayList;
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
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.Channel;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.db.digital.ChannelSubUserDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.readerV5.reponse.MyChannelListReponse;

/**
 * 获取我的频道列表接口
 * @author guohaiying
 *
 */
public class MyChannelList extends FixtureBase{
	ReponseV2<MyChannelListReponse> jsonResult;	

    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<MyChannelListReponse>>(){});
    }

	@Override
	protected void dataVerify() throws Exception {
		if(reponseV2Base.getStatus().getCode()==0){
	        String custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
	        List<String> expectedMySubChannel = ChannelSubUserDb.getUserAllSubChannel(custId);       	
	        List<com.dangdang.readerV5.reponse.MyChannelList> actualList = jsonResult.getData().getChannelList();
	        List<String> actualMySubChannels = new ArrayList<String>();
	        for(int i=0; i<actualList.size(); i++){
	        	actualMySubChannels.add(actualList.get(i).getChannelId());
	        }
	        dataVerifyManager.add(new ExpressionVerify(expectedMySubChannel.containsAll(actualMySubChannels)).setVerifyContent("验证是否是订阅的频道"));
	        	
	        //如果自己有频道，验证第一条是否显示自己的频道
	    	String myChannelId = ChannelDb.getChannelIdByCustId(custId);
	    	if(actualList.get(0).getChannelId().equals(myChannelId))
	    		dataVerifyManager.add(new ValueVerify<String>(actualList.get(0).getIsMine(), "1").setVerifyContent("验证是否是自己的频道"));
	        	
	        // 验证title description icon isMine shelfStatus
	        for(int i=1; i<actualMySubChannels.size(); i++){
	        	Channel expectedChannel = ChannelDb.getChannel(actualMySubChannels.get(i));
	        	dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getTitle(), expectedChannel.getTitle()).setVerifyContent("验证title"));
	        	dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getDescription(), expectedChannel.getDescription()).setVerifyContent("验证description"));
	        	dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getIcon(), expectedChannel.getIcon()).setVerifyContent("验证icon"));
	        	dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getIsMine(), "0").setVerifyContent("验证isMine"));
	        	dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getShelfStatus(), "1").setVerifyContent("验证shelfStatus"));
	        }	
		}
		super.dataVerify();
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
//			MyChannelList m = new MyChannelList();
//			String s= m.setCustId("50098052");//MyChannelList.encryptCustId(50098052l);
//			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
