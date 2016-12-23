package edu.shmtu.nlap.weibo.catagory.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * ���� ����
 * @author HP_xiaochao
 *
 */
public class WeiboRequestUtil {
	public static String sendGet(String url){
		String result = "";
		BufferedReader in = null;
		String myCookie = CommonUtils.getCookie();
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
			System.out.println("����GET��������쳣��" + e);
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
}
