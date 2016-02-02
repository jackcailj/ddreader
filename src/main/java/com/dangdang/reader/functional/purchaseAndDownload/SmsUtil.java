package com.dangdang.reader.functional.purchaseAndDownload;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;



import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * 短信工具类
 * @author maqiang
 *
 */
public class SmsUtil {
	
	// 秘钥
	public static final String MERC_KEY ="de5cfb27034ffcd88dcea12f33060183";

    /**
     * 构建签名原串
     *
     * @param sortedMap 参数map
     * @param md5key    商户秘钥
     * @return 签名原串
     */
    public static String buildSignParamStr(SortedMap<String, String> sortedMap, String md5key) {

        SortedMap<String, String> map = new TreeMap<String, String>();
        for (String pname : sortedMap.keySet()) {
            map.put(pname, sortedMap.get(pname));
        }
        map.put("merc_key", md5key);

        StringBuilder sb = new StringBuilder(500);
        for (String pname : map.keySet()) {
            if ("sign".equals(pname) || StringUtils.isEmpty(map.get(pname)) || "action".equals(pname)) {
                continue;
            }
            sb.append(pname).append("=").append(map.get(pname)).append("&");
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        String signOriginalStr = sb.toString();

        System.out.println("signOriginalStr=" + signOriginalStr);

        return signOriginalStr;
    }




    /**
     * 从请求参数中获取sign参数
     *
     * @param sortedMap
     * @return 签名，即sign参数
     */
    private static String getSignStr(SortedMap<String, String> sortedMap) {
        return sortedMap.containsKey("sign") ? sortedMap.get("sign") : null;
    }

    /**
     * 将日期转换成unix时间戳
     *
     * @param date
     * @return
     */
    public static long dateToUnixTimestamp(Date date) {
        return date.getTime() / 1000;
    }

    /******************************************************************************************************************/

    /**
     * 示例
     *
     * @param args
     */
    public static void main(String[] args) {

        // 接收通知
        receiveNotice();
    }



    /**
     * 接收通知（伪代码）
     */
    private static void receiveNotice() {

        try {

            // HttpServletRequest
           /* HttpServletRequest request = null;

            // 秘钥
            String merc_key = "你的秘钥";

            // 校验签名（当抛出异常时候，则签名失败；若不抛异常，则签名成功）
            SmsUtil.checkSign(request, merc_key);*/
        	
        	SortedMap<String, String> sortedMap = new TreeMap<String, String>();
        	//merc_id=0001&orderid=aa0001&app_orderid=A0001&time=1234567890&rec_amount=200&ch_type=2&status=0&sign=
        	sortedMap.put("merc_id", "0001");
        	sortedMap.put("orderid", "aa0001");
        	sortedMap.put("userid", "22226498");
        	sortedMap.put("app_orderid", "RA150518151798453629_1");
        	sortedMap.put("time", "1234567890");
        	sortedMap.put("rec_amount", "20000");
        	sortedMap.put("ch_type", "2");
        	sortedMap.put("status", "2");
        	sortedMap.put("sign", "3b5a43a78e7a662cbc3806c64f6fc8ae");
        	//签名原串
            String signOriginalStr = buildSignParamStr(sortedMap, MERC_KEY);
            //计算后的签名
            String afterSign = DigestUtils.md5Hex(signOriginalStr.getBytes("utf-8"));
            //传递过来的签名
            String beforeSign = getSignStr(sortedMap);

            System.out.println("checkSign signOriginalStr=" + signOriginalStr);
            System.out.println("checkSign beforeSign=" + beforeSign);
            System.out.println("checkSign afterSign=" + afterSign);

            if (StringUtils.isEmpty(beforeSign) || StringUtils.isEmpty(signOriginalStr) ||
                    !beforeSign.equalsIgnoreCase(afterSign)) {
                throw new Exception("sign error");
            }
            
            System.out.println("签名校验成功");

        } catch (Exception e) {
            System.out.println("签名校验失败");
            e.printStackTrace();
        }

    }

}
