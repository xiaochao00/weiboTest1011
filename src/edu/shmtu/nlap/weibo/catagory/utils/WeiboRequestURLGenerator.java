package edu.shmtu.nlap.weibo.catagory.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * 发送请求
 * @author HP_xiaochao
 *
 */
public class WeiboRequestURLGenerator {
	/**
	 * 对外公开的 发送请求的实体类 如果响应为空 重传
	 * */
	public static String sendGetAndRepeat(String url) throws IOException{
		String result = "";
		LogInfoUtil log = new LogInfoUtil();
		int repeatRequestCount = 0;
		do{
			result  = sendGet(url);
			
			if(!result.equals("")){
				break;
			}
			log.addInfo("repeat request url "+repeatRequestCount+" times :"+url);
			repeatRequestCount++;
			SleepUtils.sleep(2000);
		}while(repeatRequestCount<CommonUtils.REPEAT_NORESPONSE_COUNT);
		if(repeatRequestCount>0&&repeatRequestCount<CommonUtils.REPEAT_NORESPONSE_COUNT)
			log.addInfo("repeat request url "+repeatRequestCount+" times succes!");
		if(repeatRequestCount>0)
			log.saveError();
		return result;
	}
	private static String sendGet(String url) throws IOException{
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
			System.out.println("发送GET请求出现异常！"+url);
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
		String cookie = WeiboConfig.getValue("cookie");
		return cookie;
	}
	
}
