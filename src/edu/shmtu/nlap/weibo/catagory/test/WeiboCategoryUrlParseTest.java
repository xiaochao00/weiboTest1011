package edu.shmtu.nlap.weibo.catagory.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import edu.shmtu.nlap.weibo.catagory.utils.LogInfoUtil;
import edu.shmtu.nlap.weibo.catagory.utils.WeiboConfig;

/**
 * 2016 10 11
 * ����΢�� ������ݽ�����
 * http://d.weibo.com/102803_ctg1_6688_-_ctg1_6688#
 * ������
 * 
 * @author HP_xiaochao
 *
 */
public class WeiboCategoryUrlParseTest {
	public static void main(String []args) throws IOException{
		String categoryJunshiUrl = "http://d.weibo.com/102803_ctg1_6688_-_ctg1_6688#";
		System.out.println(sendGet(categoryJunshiUrl));
	}
	/**
	 * �ο�
	 * http://www.cnblogs.com/zhuawang/archive/2012/12/08/2809380.html
	 * @param url
	 * @return
	 * @throws IOException 
	 */
	public static String sendGet(String url) throws IOException{
		String result = "";
		BufferedReader in = null;
		String myCookie = getCookie();
		try{
			URL realUrl = new URL(url);
			 // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
            connection.setRequestProperty(" Accept-Language","zh-CN,zh;q=0.8");
            //cookie
            connection.setRequestProperty("Cookie", myCookie); 
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            //
            // �������е���Ӧͷ�ֶ�
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }	
		}catch (Exception e){
			System.out.println("发送GET请求出现异常！" + e);
			LogInfoUtil log = new LogInfoUtil();
			log.addInfo("发送GET请求出现异常！");
			log.addInfo(e.getMessage());
			log.saveError();
			e.printStackTrace();
		}finally{
			try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
		}
		return result;
	}
	private static String getCookie(){
		String cookie1 = "SINAGLOBAL=4878787798807.025.1459300260099; "
				+ "wvr=6; un=1363180272@qq.com; "
				+ "YF-Page-G0=ffe43932f05408fcdf32c673d8997f97; "
				+ "SCF=Am3u9knJTmMyIW78ayIiccU_bztQfVvG0lrPcdw61zHimLnX6ZY6b0LJhPKCR4USvHFMOumicMrNHq0UZYprtCg.; "
				+ "SUB=_2A251AGbdDeTxGeRK71AV-SzJzDmIHXVWdN8VrDV8PUNbmtBeLUXHkW8QZ0vrNHY5iqCCrayjhJFXWW-Y3A..; "
				+ "SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFQU5ah6Bfk-vTFkqYEPCmD5JpX5KMhUgL.FozXShzX1KzfS0-2dJLoIEnLxKML1K.LB.-LxK-L12qL1KnLxK-LBo5L12qLxKnLBoqL1h-Eeh20; "
				+ "SUHB=0C1D7fcqghSWVN; ALF=1508198924; "
				+ "SSOLoginState=1476662925;"
				+ " _s_tentry=login.sina.com.cn; "
				+ "Apache=7381985104452.556.1476662925501;"
				+ " ULV=1476662925527:26:5:3:7381985104452.556.1476662925501:1476623300510; "
				+ "UOR=www.baidu.com,open.weibo.com,edu.yesky.com";
		String cookie2 = "SINAGLOBAL=4878787798807.025.1459300260099; "
				+ "un=1363180272@qq.com; "
				+ "SCF=Am3u9knJTmMyIW78ayIiccU_bztQfVvG0lrPcdw61zHivBmZGyJ9GUaGKppp81f_mhdpyGxlHkPM0SgsKSY_urI.; "
				+ "SUB=_2A251AKClDeTxGeRK71AV-SzJzDmIHXVWd5VtrDV8PUNbmtBeLXnHkW8AAvze5YoTUkxADfSx4ET5yESFZw..; "
				+ "SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFQU5ah6Bfk-vTFkqYEPCmD5JpX5KMhUgL.FozXShzX1KzfS0-2dJLoIEnLxKML1K.LB.-LxK-L12qL1KnLxK-LBo5L12qLxKnLBoqL1h-Eeh20; "
				+ "SUHB=0G0vT28CH3V0kM; "
				+ "ALF=1508246643; "
				+ "SSOLoginState=1476710645; "
				+ "_s_tentry=login.sina.com.cn;"
				+ " UOR=www.baidu.com,open.weibo.com,login.sina.com.cn;"
				+ " Apache=8303418693434.896.1476710648390; "
				+ "ULV=1476710648468:27:6:4:8303418693434.896.1476710648390:1476662925527";
		String cookie = WeiboConfig.getValue("cookie");
		return cookie;
	}
}
/**
 * Cookie:SINAGLOBAL=4878787798807.025.1459300260099; login_sid_t=57d6a9be56f8f1814f9cd7cf4a76bc94; _s_tentry=-; Apache=9901812860165.047.1475906383107; ULV=1475906383113:22:1:1:9901812860165.047.1475906383107:1474204012301; SSOLoginState=1475906409; UOR=www.baidu.com,open.weibo.com,www.baidu.com; SCF=Am3u9knJTmMyIW78ayIiccU_bztQfVvG0lrPcdw61zHiMp7kBpWMr8RhE1SLhsV8sdYt6PAnAS645Qot7BmXdy4.; SUB=_2A256-GptDeTxGeRK71AV-SzJzDmIHXVZjNylrDV8PUNbmtBeLXDAkW8NJobm0xfiXCezFbw0CCqL_kGZ5A..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFQU5ah6Bfk-vTFkqYEPCmD5JpX5K2hUgL.FozXShzX1KzfS0-2dJLoIEnLxKML1K.LB.-LxK-L12qL1KnLxK-LBo5L12qLxKnLBoqL1h-Eeh20; SUHB=0RyrhS-n8V2_zr; ALF=1507675580; un=1363180272@qq.com; wvr=6
 */
