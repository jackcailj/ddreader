//package com.dangdang.readerV5.bookstore;
//
//import com.alibaba.fastjson.TypeReference;
//import com.dangdang.autotest.common.FixtureBase;
//import com.dangdang.autotest.common.ResponseVerify;
//import com.dangdang.ddframework.dataverify.ValueVerify;
//import com.dangdang.ddframework.reponse.ReponseV2;
//import BookStoreCommSQL;
//import BookStoreTestEvnSQL;
//import MediaCategorySQL;
//import com.dangdang.readerV5.reponse.CatetoryList1;
//import com.dangdang.readerV5.reponse.CatetoryList3;
//import com.dangdang.readerV5.reponse.ColumnReponse;
//import com.dangdang.readerV5.reponse.MediaCategoryReponse;
//
//import fitnesse.slim.SystemUnderTest;
//
///**
// * 分类一级页面
// * @author guohaiying
// *
// */
//public class MediaCategory extends FixtureBase{
//	
//	ReponseV2<MediaCategoryReponse> reponseResult;
//	
//	@SystemUnderTest
//	public BookStoreTestEvnSQL service = new BookStoreTestEvnSQL();
//	
//	//验证结果
//	public boolean verifyResult() throws Exception {
//		dataVerifyManager.setCaseExpectResult(true);
//		String code;
//		int id;
//		String image;
//		String image2;
//		boolean leaf;
//		String name;
//		int parentId;
//		boolean parsed;
//		
//		String result = "{\"data\":{\"catetoryList\":[{\"catetoryList\":[{\"catetoryList\":[{\"code\":\"XS2\",\"id\":805,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-WY1-XS2.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-WY1-XS22.jpg\",\"leaf\":true,\"name\":\"小说\",\"parentId\":794,\"parsed\":false},{\"code\":\"WX\",\"id\":804,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-WY1-WX.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-WY1-WX2.jpg\",\"leaf\":true,\"name\":\"文学\",\"parentId\":794,\"parsed\":false},{\"code\":\"QCWX\",\"id\":803,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-WY1-QCWX.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-WY1-QCWX2.jpg\",\"leaf\":true,\"name\":\"青春文学\",\"parentId\":794,\"parsed\":false},{\"code\":\"DMYM\",\"id\":802,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-WY1-DMYM.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-WY1-DMYM2.jpg\",\"leaf\":true,\"name\":\"动漫/幽默\",\"parentId\":794,\"parsed\":false},{\"code\":\"YS\",\"id\":806,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-WY1-YS.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-WY1-YS2.jpg\",\"leaf\":true,\"name\":\"艺术\",\"parentId\":794,\"parsed\":false}],\"code\":\"WY1\",\"id\":794,\"image\":\"http://e.dangdang.com/media/images/column/catetory_EG.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_EG.jpg\",\"leaf\":false,\"name\":\"文艺\",\"parentId\":649,\"parsed\":false},{\"catetoryList\":[{\"code\":\"CGLZ\",\"id\":807,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JG-CGLZ.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JG-CGLZ2.jpg\",\"leaf\":true,\"name\":\"成功/励志\",\"parentId\":795,\"parsed\":false},{\"code\":\"GL\",\"id\":808,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JG-GL.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JG-GL2.jpg\",\"leaf\":true,\"name\":\"管理\",\"parentId\":795,\"parsed\":false},{\"code\":\"TZLC\",\"id\":810,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JG-TZLC.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JG-TZLC2.jpg\",\"leaf\":true,\"name\":\"投资理财\",\"parentId\":795,\"parsed\":false},{\"code\":\"JJ\",\"id\":809,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JG-JJ.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JG-JJ2.jpg\",\"leaf\":true,\"name\":\"经济\",\"parentId\":795,\"parsed\":false}],\"code\":\"JG\",\"id\":795,\"image\":\"http://e.dangdang.com/media/images/column/catetory_TSS.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_TSS.jpg\",\"leaf\":false,\"name\":\"经管\",\"parentId\":649,\"parsed\":false},{\"catetoryList\":[{\"code\":\"ZXZJ\",\"id\":818,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-ZXZJ.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-ZXZJ2.jpg\",\"leaf\":true,\"name\":\"哲学/宗教\",\"parentId\":796,\"parsed\":false},{\"code\":\"LS1\",\"id\":814,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-LS1.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-LS12.jpg\",\"leaf\":true,\"name\":\"历史\",\"parentId\":796,\"parsed\":false},{\"code\":\"ZZJS\",\"id\":819,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-ZZJS.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-ZZJS2.jpg\",\"leaf\":true,\"name\":\"政治/军事\",\"parentId\":796,\"parsed\":false},{\"code\":\"WH\",\"id\":816,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-WH.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-WH2.jpg\",\"leaf\":true,\"name\":\"文化\",\"parentId\":796,\"parsed\":false},{\"code\":\"SHKX\",\"id\":815,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-SHKX.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-SHKX2.jpg\",\"leaf\":true,\"name\":\"社会科学\",\"parentId\":796,\"parsed\":false},{\"code\":\"XLX\",\"id\":817,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-XLX.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-XLX2.jpg\",\"leaf\":true,\"name\":\"心理学\",\"parentId\":796,\"parsed\":false},{\"code\":\"GJ\",\"id\":813,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-GJ.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-GJ2.jpg\",\"leaf\":true,\"name\":\"古籍\",\"parentId\":796,\"parsed\":false},{\"code\":\"FL\",\"id\":812,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-FL.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-FL2.jpg\",\"leaf\":true,\"name\":\"法律\",\"parentId\":796,\"parsed\":false},{\"code\":\"ZJ\",\"id\":811,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-ZJ.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SK-ZJ2.jpg\",\"leaf\":true,\"name\":\"传记\",\"parentId\":796,\"parsed\":false}],\"code\":\"SK\",\"id\":796,\"image\":\"http://e.dangdang.com/media/images/column/catetory_THGS.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/\",\"leaf\":false,\"name\":\"社科\",\"parentId\":649,\"parsed\":false},{\"catetoryList\":[{\"code\":\"LXGX\",\"id\":823,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-LXGX.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-LXGX2.jpg\",\"leaf\":true,\"name\":\"两性关系\",\"parentId\":797,\"parsed\":false},{\"code\":\"YCTJ\",\"id\":832,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-YCTJ.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-YCTJ2.jpg\",\"leaf\":true,\"name\":\"孕产/胎教\",\"parentId\":797,\"parsed\":false},{\"code\":\"YEZJ\",\"id\":831,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-YEZJ.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-YEZJ2.jpg\",\"leaf\":true,\"name\":\"育儿/早教\",\"parentId\":797,\"parsed\":false},{\"code\":\"QZJY\",\"id\":826,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-QZJY.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-QZJY2.jpg\",\"leaf\":true,\"name\":\"亲子/家教\",\"parentId\":797,\"parsed\":false},{\"code\":\"BYJS\",\"id\":820,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-BYJS.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-BYJS2.jpg\",\"leaf\":true,\"name\":\"保健/养生\",\"parentId\":797,\"parsed\":false},{\"code\":\"TYYD\",\"id\":829,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-TYYD.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-TYYD2.jpg\",\"leaf\":true,\"name\":\"体育/运动\",\"parentId\":797,\"parsed\":false},{\"code\":\"XSAH\",\"id\":830,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-XSAH.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-XSAH2.jpg\",\"leaf\":true,\"name\":\"休闲/爱好\",\"parentId\":797,\"parsed\":false},{\"code\":\"LYDT\",\"id\":824,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-LYDT.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-LYDT2.jpg\",\"leaf\":true,\"name\":\"旅游/地图\",\"parentId\":797,\"parsed\":false},{\"code\":\"PRMS\",\"id\":825,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-PRMS.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-PRMS2.jpg\",\"leaf\":true,\"name\":\"烹饪/美食\",\"parentId\":797,\"parsed\":false},{\"code\":\"SSMX\",\"id\":828,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-SSMX.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-SSMX2.jpg\",\"leaf\":true,\"name\":\"时尚/美妆\",\"parentId\":797,\"parsed\":false},{\"code\":\"SGDIY\",\"id\":827,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-SGDIY.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-SGDIY2.jpg\",\"leaf\":true,\"name\":\"手工/DIY\",\"parentId\":797,\"parsed\":false},{\"code\":\"JTJJ\",\"id\":822,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-JTJJ.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-JTJJ2.jpg\",\"leaf\":true,\"name\":\"家庭/家居\",\"parentId\":797,\"parsed\":false},{\"code\":\"FSZP\",\"id\":821,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-FSZP.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-SH-FSZP2.jpg\",\"leaf\":true,\"name\":\"风水/占卜\",\"parentId\":797,\"parsed\":false}],\"code\":\"SH\",\"id\":797,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YYGS.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YYGS.jpg\",\"leaf\":false,\"name\":\"生活\",\"parentId\":649,\"parsed\":false},{\"catetoryList\":[{\"code\":\"ZXXJF\",\"id\":837,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JY-ZXXJF.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JY-ZXXJF2.jpg\",\"leaf\":true,\"name\":\"中小学教辅\",\"parentId\":798,\"parsed\":false},{\"code\":\"KS\",\"id\":835,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JY-KS.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JY-KS2.jpg\",\"leaf\":true,\"name\":\"考试\",\"parentId\":798,\"parsed\":false},{\"code\":\"WY2\",\"id\":836,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JY-WY2.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JY-WY22.jpg\",\"leaf\":true,\"name\":\"外语\",\"parentId\":798,\"parsed\":false},{\"code\":\"JC\",\"id\":834,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JY-JC.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JY-JC2.jpg\",\"leaf\":true,\"name\":\"教材\",\"parentId\":798,\"parsed\":false},{\"code\":\"JJS\",\"id\":833,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JY-JJS.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JY-JJS2.jpg\",\"leaf\":true,\"name\":\"工具书\",\"parentId\":798,\"parsed\":false}],\"code\":\"JY\",\"id\":798,\"image\":\"http://e.dangdang.com/media/images/column/catetory_LSGS.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_LSGS.jpg\",\"leaf\":false,\"name\":\"教育\",\"parentId\":649,\"parsed\":false},{\"catetoryList\":[{\"code\":\"KPDW\",\"id\":841,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-KJ-KPDW.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-KJ-KPDW2.jpg\",\"leaf\":true,\"name\":\"科普读物\",\"parentId\":799,\"parsed\":false},{\"code\":\"JSJWL\",\"id\":839,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-KJ-JSJWL.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-KJ-JSJWL2.jpg\",\"leaf\":true,\"name\":\"计算机/网络\",\"parentId\":799,\"parsed\":false},{\"code\":\"YX\",\"id\":843,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-KJ-YX.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-KJ-YX2.jpg\",\"leaf\":true,\"name\":\"医学\",\"parentId\":799,\"parsed\":false},{\"code\":\"GYJS\",\"id\":838,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-KJ-GYJS.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-KJ-GYJS2.jpg\",\"leaf\":true,\"name\":\"工业技术\",\"parentId\":799,\"parsed\":false},{\"code\":\"JZ\",\"id\":840,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-KJ-JZ.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-KJ-JZ2.jpg\",\"leaf\":true,\"name\":\"建筑\",\"parentId\":799,\"parsed\":false},{\"code\":\"ZRKX\",\"id\":844,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-KJ-ZRKX.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-KJ-ZRKX2.jpg\",\"leaf\":true,\"name\":\"自然科学\",\"parentId\":799,\"parsed\":false},{\"code\":\"NYLY\",\"id\":842,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-KJ-NYLY.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-KJ-NYLY2.jpg\",\"leaf\":true,\"name\":\"农业/林业\",\"parentId\":799,\"parsed\":false}],\"code\":\"KJ\",\"id\":799,\"image\":\"http://e.dangdang.com/media/images/column/catetory_ETWX.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_ETWX.jpg\",\"leaf\":false,\"name\":\"科技\",\"parentId\":649,\"parsed\":false},{\"catetoryList\":[{\"code\":\"TS2\",\"id\":845,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-TS-TS2.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-TS-TS22.jpg\",\"leaf\":true,\"name\":\"童书\",\"parentId\":800,\"parsed\":false}],\"code\":\"TS\",\"id\":800,\"image\":\"http://e.dangdang.com/media/images/column/catetory_GXJD.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_GXJD.jpg\",\"leaf\":false,\"name\":\"童书\",\"parentId\":649,\"parsed\":false},{\"catetoryList\":[{\"code\":\"GTTS\",\"id\":847,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JKS-GTTS.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JKS-GTTS2.jpg\",\"leaf\":true,\"name\":\"港台圖書\",\"parentId\":801,\"parsed\":false},{\"code\":\"YWYBS\",\"id\":846,\"image\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JKS-YWYBS.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_DDDS-DZS-JKS-YWYBS2.jpg\",\"leaf\":true,\"name\":\"英文原版书\",\"parentId\":801,\"parsed\":false}],\"code\":\"JKS\",\"id\":801,\"image\":\"http://e.dangdang.com/media/images/column/catetory_KPBK.jpg\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_KPBK.jpg\",\"leaf\":false,\"name\":\"进口书\",\"parentId\":649,\"parsed\":false}],\"code\":\"DZS\",\"id\":649,\"image\":\"http://e.dangdang.com/media/images/column/\",\"image2\":\"http://e.dangdang.com/media/images/column/\",\"leaf\":false,\"name\":\"出版物\",\"parentId\":793,\"parsed\":false},{\"catetoryList\":[{\"code\":\"XDYQ\",\"id\":82,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-VP-XDYQ.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-VP-XDYQ2.png\",\"leaf\":true,\"name\":\"现代言情\",\"parentId\":80,\"parsed\":false},{\"code\":\"CYCS\",\"id\":83,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-VP-CYCS.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-VP-CYCS2.png\",\"leaf\":true,\"name\":\"穿越重生\",\"parentId\":80,\"parsed\":false},{\"code\":\"GZYQ\",\"id\":84,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-VP-GZYQ.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-VP-GZYQ2.png\",\"leaf\":true,\"name\":\"古装言情\",\"parentId\":80,\"parsed\":false},{\"code\":\"QCXY\",\"id\":85,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-VP-QCXY.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-VP-QCXY2.png\",\"leaf\":true,\"name\":\"青春校园\",\"parentId\":80,\"parsed\":false},{\"code\":\"HXYQ\",\"id\":86,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-VP-HXYQ.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-VP-HXYQ2.png\",\"leaf\":true,\"name\":\"幻想言情\",\"parentId\":80,\"parsed\":false},{\"code\":\"CATR\",\"id\":88,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-VP-CATR.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-VP-CATR2.png\",\"leaf\":true,\"name\":\"纯爱同人\",\"parentId\":80,\"parsed\":false},{\"code\":\"VQT\",\"id\":90,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-VP-VQT.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-VP-VQT2.png\",\"leaf\":true,\"name\":\"出版\",\"parentId\":80,\"parsed\":false}],\"code\":\"VP\",\"id\":80,\"leaf\":false,\"name\":\"女频\",\"parentId\":118,\"parsed\":false},{\"catetoryList\":[{\"code\":\"XHQH\",\"id\":87,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-XHQH.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-XHQH2.png\",\"leaf\":true,\"name\":\"玄幻奇幻\",\"parentId\":79,\"parsed\":false},{\"code\":\"XDDS\",\"id\":81,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-XDDS.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-XDDS2.png\",\"leaf\":true,\"name\":\"现代都市\",\"parentId\":79,\"parsed\":false},{\"code\":\"WXXX\",\"id\":89,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-WXXX.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-WXXX2.png\",\"leaf\":true,\"name\":\"武侠仙侠\",\"parentId\":79,\"parsed\":false},{\"code\":\"LSJS\",\"id\":91,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-LSJS.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-LSJS2.png\",\"leaf\":true,\"name\":\"历史军事\",\"parentId\":79,\"parsed\":false},{\"code\":\"YXJJ\",\"id\":92,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-YXJJ.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-YXJJ2.png\",\"leaf\":true,\"name\":\"游戏竞技\",\"parentId\":79,\"parsed\":false},{\"code\":\"KHLY\",\"id\":93,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-KHLY.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-KHLY2.png\",\"leaf\":true,\"name\":\"科幻灵异\",\"parentId\":79,\"parsed\":false},{\"code\":\"NQT\",\"id\":94,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-NQT.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-NQT2.png\",\"leaf\":true,\"name\":\"出版\",\"parentId\":79,\"parsed\":false},{\"code\":\"TR\",\"id\":104,\"image\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-TR.png\",\"image2\":\"http://e.dangdang.com/media/images/column/catetory_YC-NP-TR2.png\",\"leaf\":true,\"name\":\"同人\",\"parentId\":79,\"parsed\":false}],\"code\":\"NP\",\"id\":79,\"leaf\":false,\"name\":\"男频\",\"parentId\":118,\"parsed\":false}],\"currentDate\":\"2015-08-26 14:56:37\",\"systemDate\":\"1440572197964\"},\"status\":{\"code\":0},\"systemDate\":1440572197964}";
//		CatetoryList3 dbResponse = MediaCategorySQL.getCategoryList3();
////		
////		JSONObject jsonObject = JSONObject.fromObject(result.toString());
////		JSONObject data = jsonObject.getJSONObject("data");
////		JSONArray catetoryList3=data.getJSONArray("catetoryList");
////
////		//验证出版物
////		JSONArray catetoryList2 = catetoryList3.getJSONObject(0).getJSONArray("catetoryList");
////		for(int i=0; i<catetoryList2.length(); i++){
////			JSONArray catetoryList1 = catetoryList2.getJSONObject(i).getJSONArray("catetoryList");
////			for(int j=0; j<catetoryList1.length(); j++){
////				code = catetoryList1.getJSONObject(0).getString("code");
////				id = catetoryList1.getJSONObject(j).getInt("id");
////				image = catetoryList1.getJSONObject(j).getString("image");
////				image2 = catetoryList1.getJSONObject(j).getString("image2");
////				leaf = catetoryList1.getJSONObject(j).getBoolean("leaf");
////				name = catetoryList1.getJSONObject(j).getString("name");
////				parentId = catetoryList1.getJSONObject(j).getInt("parentId");
////				parsed = catetoryList1.getJSONObject(j).getBoolean("parsed");
////				//dataVerifyManager.add(new ValueVerify<String>(code,dbResponse.getCatetoryList().get(0).get ));
////			}
////			code = catetoryList2.getJSONObject(i).getString("code");
////			id = catetoryList2.getJSONObject(i).getInt("id");
////			image = catetoryList2.getJSONObject(i).getString("image");
////			image2 = catetoryList2.getJSONObject(i).getString("image2");
////			leaf = catetoryList2.getJSONObject(i).getBoolean("leaf");
////			name = catetoryList2.getJSONObject(i).getString("name");
////			parentId = catetoryList2.getJSONObject(i).getInt("parentId");
////			parsed = catetoryList2.getJSONObject(i).getBoolean("parsed");
////		}
//		code = catetoryList3.getJSONObject(0).getString("code");
//		//dataVerifyManager.add(new ValueVerify<String>(code,dbResponse.getCatetoryList().get(0).getCode()));
//		id = catetoryList3.getJSONObject(0).getInt("id");
//		image = catetoryList3.getJSONObject(0).getString("image");
//		image2 = catetoryList3.getJSONObject(0).getString("image2");
//		leaf = catetoryList3.getJSONObject(0).getBoolean("leaf");
//		name = catetoryList3.getJSONObject(0).getString("name");
//		parentId = catetoryList3.getJSONObject(0).getInt("parentId");
//		parsed = catetoryList3.getJSONObject(0).getBoolean("parsed");
//		
//		//验证女频
//		catetoryList2 = catetoryList3.getJSONObject(1).getJSONArray("catetoryList");		
//		id = catetoryList3.getJSONObject(1).getInt("id");
//		code = catetoryList3.getJSONObject(1).getString("code");
//		leaf = catetoryList3.getJSONObject(1).getBoolean("leaf");
//		name = catetoryList3.getJSONObject(1).getString("name");
//		parentId = catetoryList3.getJSONObject(1).getInt("parentId");
//		parsed = catetoryList3.getJSONObject(1).getBoolean("parsed");
//		CatetoryList1 vp = MediaCategorySQL.getCategoryList1(80);
////		dataVerifyManager.add(new ValueVerify<String>(code,vp.getCode()));		
////		dataVerifyManager.add(new ValueVerify<Integer>(id,vp.getId()));		
////		dataVerifyManager.add(new ValueVerify<Boolean>(leaf,vp.getLeaf()));		
////		dataVerifyManager.add(new ValueVerify<String>(name,vp.getName()));		
////		dataVerifyManager.add(new ValueVerify<Integer>(parentId,vp.getParentId()));		
////		dataVerifyManager.add(new ValueVerify<Boolean>(parsed,vp.getParsed()));
//						
//		for(int i=0; i<catetoryList2.length(); i++){
//			code = catetoryList2.getJSONObject(i).getString("code");
//			id = catetoryList2.getJSONObject(i).getInt("id");			
//			image = catetoryList2.getJSONObject(i).getString("image");
//			image2 = catetoryList2.getJSONObject(i).getString("image2");
//			leaf = catetoryList2.getJSONObject(i).getBoolean("leaf");
//			name = catetoryList2.getJSONObject(i).getString("name");
//			parentId = catetoryList2.getJSONObject(i).getInt("parentId");
//			parsed = catetoryList2.getJSONObject(i).getBoolean("parsed");
//			dataVerifyManager.add(new ValueVerify<String>(code,vp.getCatetoryList().get(i).getCode()));		
//			dataVerifyManager.add(new ValueVerify<Integer>(id,vp.getCatetoryList().get(i).getId()));		
//			dataVerifyManager.add(new ValueVerify<Boolean>(leaf,vp.getCatetoryList().get(i).getLeaf()));		
//			dataVerifyManager.add(new ValueVerify<String>(name,vp.getCatetoryList().get(i).getName()));		
//			dataVerifyManager.add(new ValueVerify<Integer>(parentId,vp.getCatetoryList().get(i).getParentId()));		
//			dataVerifyManager.add(new ValueVerify<Boolean>(parsed,vp.getCatetoryList().get(i).getParsed()));
//		}
//				
//		//验证男频
//		catetoryList2 = catetoryList3.getJSONObject(2).getJSONArray("catetoryList");
//		for(int i=0; i<catetoryList2.length(); i++){
//			code = catetoryList2.getJSONObject(i).getString("code");
//			id = catetoryList2.getJSONObject(i).getInt("id");
//			image = catetoryList2.getJSONObject(i).getString("image");
//			image2 = catetoryList2.getJSONObject(i).getString("image2");
//			leaf = catetoryList2.getJSONObject(i).getBoolean("leaf");
//			name = catetoryList2.getJSONObject(i).getString("name");
//			parentId = catetoryList2.getJSONObject(i).getInt("parentId");
//			parsed = catetoryList2.getJSONObject(i).getBoolean("parsed");
//		}
//		code = catetoryList3.getJSONObject(2).getString("code");
//		id = catetoryList3.getJSONObject(2).getInt("id");
////		image = catetoryList3.getJSONObject(2).getString("image");
////		image2 = catetoryList3.getJSONObject(2).getString("image2");
//		leaf = catetoryList3.getJSONObject(2).getBoolean("leaf");
//		name = catetoryList3.getJSONObject(2).getString("name");
//		parentId = catetoryList3.getJSONObject(2).getInt("parentId");
//		parsed = catetoryList3.getJSONObject(2).getBoolean("parsed");
//		
//			
//		//reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<MediaCategoryReponse>>(){});
//		//if(reponseResult.getStatus().getCode()==0){		
//			//验证json中返回字段
//			//log.info("验证分类一级页面的返回数据：");	
//			//int size = Integer.valueOf(paramMap.get("end"))-Integer.valueOf(paramMap.get("start")) + 1;
//			
//			//dataVerifyManager.add(new ResponseVerify(reponseResult.getData().getCatetoryList(), dbResponse));
//			//dataVerifyManager.add(new ValueVerify<MediaCategoryReponse>(reponseResult.getData(), dbResponse, true));
//			
//			
//			//#SELECT * FROM `media_catetory` WHERE path LIKE ('DDDS%') AND `status`=1;
//			//#SELECT * FROM media_catetory WHERE parent_id=596  AND `status`=1 ORDER BY index_order DESC
//			//#SELECT * FROM media_catetory WHERE parent_id=149
//			//#SELECT * FROM media_catetory WHERE `name` IN ('出版物','女频','男频','杂志') AND `status`=1 ORDER BY index_order DESC
//
//			//#SELECT * FROM media_catetory WHERE `parent_id` IN (149)AND `status`=1 ORDER BY index_order DESC;
//			//SELECT * FROM media_catetory WHERE `parent_id` IN (80)AND `status`=1 ORDER BY index_order DESC;
//			//#SELECT * FROM media_catetory WHERE `parent_id` IN (79)AND `status`=1 ORDER BY index_order DESC;
//			//#SELECT * FROM media_catetory WHERE `parent_id` IN (596)AND `status`=1 ORDER BY index_order DESC;
//		//}
//		return dataVerifyManager.dataVerify();      	
//	}
//	
//	public static void main(String[] args){
//		MediaCategory m=new MediaCategory();
//		try {
//			m.verifyResult();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}
