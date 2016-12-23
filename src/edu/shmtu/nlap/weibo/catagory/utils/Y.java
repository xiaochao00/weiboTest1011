package edu.shmtu.nlap.weibo.catagory.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * 发送请求
 * @author HP_xiaochao
 *
 */
public class Y {
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
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type","text/html; charset=utf-8");
            connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
            connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8");
            //cookie
            connection.setRequestProperty("Cookie", myCookie); 
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
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
		String cookie = "RsPerPage=20; kc_cnki_net_uid=1af6fe48-25c3-6c43-209c-86b959a3c411; ASP.NET_SessionId=ge3dvdclmh5v2fa4h2mvjjz4; LID=WEEvREcwSlJHSldRa1FhdXNXZjJDVWZpTEw2bjkxSnNBMEZsSUE0aURtdz0=$9A4hF_YAuvQ5obgVAqNKPCYcEjKensW4ggI8Fm4gTkoUKaID8j8gFw!!; Ecp_ClientId=2160621210709714672; c_m_LinID=LinID=WEEvREcwSlJHSldRa1FhdkJkdjAzb1J5YjRnOFZDSXd5bkxlc2gyb1h1bz0=$9A4hF_YAuvQ5obgVAqNKPCYcEjKensW4ggI8Fm4gTkoUKaID8j8gFw!!&ot=11/02/2016 20:20:18; c_m_expire=2016-11-02 20:20:18; Ecp_LoginStuts={\"IsAutoLogin\":false,\"UserName\":\"SH0010T\",\"ShowName\":\"%e4%b8%8a%e6%b5%b7%e6%b5%b7%e4%ba%8b%e5%a4%a7%e5%ad%a6\",\"UserType\":\"bk\",\"r\":\"WDNSJp\"}";
		return cookie;
	}
	public static void main(String[]args) throws IOException, Exception{
		String url = "http://epub.cnki.net/kns/brief/brief.aspx?pagename=ASP.brief_default_result_aspx&dbPrefix=SCDB&dbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDBINDEX.xml&research=off&t=1478075909847&keyValue=%E6%96%87%E6%9C%AC%E5%88%86%E7%B1%BB&S=1";
		String result = sendGet(url);
		System.out.println(result);
		parseWBHtmlDivToDivtagList(result);
	}
	private static void parseWBHtmlDivToDivtagList(String wbHtmlDiv) throws ParserException {
//		List<Div> divList = new ArrayList<Div>();
		Parser parser = Parser.createParser(wbHtmlDiv, "utf-8");
		TagNameFilter tagnameFilter = new TagNameFilter("table");
		NodeList nodeList2 = parser.parse(tagnameFilter);
//		NodeList nodeList = parser.parse(tagnameFilter2);
//		System.out.println(nodeList.size());
		System.out.println(nodeList2.size());
		for (int i = 0; i < nodeList2.size(); i++) {
			TableTag t = (TableTag)nodeList2.elementAt(i);
			if(t.getAttribute("class")!=null&&t.getAttribute("class").equals("GridTableContent")){
				//
				String tableHtml = t.toHtml();
				System.out.println(tableHtml);
				Parser parserTr = Parser.createParser(tableHtml, "utf-8");
				TagNameFilter tagnameFilter2 = new TagNameFilter("TR");
				NodeList nodeList = parserTr.parse(tagnameFilter2);
				for(int j=0;j<nodeList.size();j++){
					TableRow row = (TableRow)nodeList.elementAt(j);
					System.out.println(row.toHtml());
				}
			}
			
		}	
	}
	private static void parseZhaiyao(String zhaiyao) throws ParserException{
		List<Span> divList = new ArrayList<Span>();
		Parser parser = Parser.createParser(zhaiyao, "utf-8");
		TagNameFilter tagnameFilter3 = new TagNameFilter("table");
		TagNameFilter tagnameFilter = new TagNameFilter("TR");
		NodeList nodeList = parser.parse(tagnameFilter3);
		TableTag tableTag = (TableTag) nodeList.elementAt(0);
		TableRow[] tableRows = tableTag.getRows();//获取行数组          
		String andstr = "  &nbsp;";
	}
}
