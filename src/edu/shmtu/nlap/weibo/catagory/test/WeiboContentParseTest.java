package edu.shmtu.nlap.weibo.catagory.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class WeiboContentParseTest {
	private static String srcJunshiCatagotyUrl = "http://d.weibo.com/102803_ctg1_6688_-_ctg1_6688#";
	private static String junshiCatagoryStr = "102803_ctg1_6688_-_ctg1_6688";

	public static void main(String[] args) throws ParserException, IOException {
		//parseUrl(srcJunshiCatagotyUrl);
		String result  = "";
		Parser parser = Parser.createParser(result, "utf-8");
		TagNameFilter tagnameFilter = new TagNameFilter("div");
		NodeList nodeList2 = parser.parse(tagnameFilter);
		System.out.println(nodeList2.size());
		for(int i=0;i<nodeList2.size();i++){
			System.out.println(nodeList2.elementAt(i).toHtml());
			System.out.println(nodeList2.elementAt(i).toPlainTextString());
		}
	}

	private static void parseUrl(String url) throws IOException, ParserException {
		String result = "";
		BufferedReader in = null;
		String myCookie = getCookie();
		
		URL realUrl = new URL(url);
		// 打开和URL之间的连接
		URLConnection connection = realUrl.openConnection();
		// 设置通用的请求属性
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		connection.setRequestProperty("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		connection.setRequestProperty(" Accept-Language", "zh-CN,zh;q=0.8");
		// cookie
		connection.setRequestProperty("Cookie", myCookie);
		//
		Parser parser = new  Parser(connection);
		parser.setEncoding("utf-8");
		//
//		NodeFilter filter = new CssSelectorNodeFilter("div.WB_cardwrap WB_feed_type S_bg2");
//		NodeList nodeList = parser.parse(filter);
//		System.out.println(nodeList.size());
		TagNameFilter tagnameFilter = new TagNameFilter("div");
		NodeList nodeList2 = parser.parse(tagnameFilter);
		System.out.println(nodeList2.size());
		for(int i=0;i<nodeList2.size();i++){
			System.out.println(nodeList2.elementAt(i).toHtml());
			System.out.println(nodeList2.elementAt(i).toPlainTextString());
		}
	}
	private static String getCookie(){
		String cookie = "SINAGLOBAL=4878787798807.025.1459300260099; wvr=6; un=1363180272@qq.com; YF-Page-G0=ffe43932f05408fcdf32c673d8997f97; SCF=Am3u9knJTmMyIW78ayIiccU_bztQfVvG0lrPcdw61zHimLnX6ZY6b0LJhPKCR4USvHFMOumicMrNHq0UZYprtCg.; SUB=_2A251AGbdDeTxGeRK71AV-SzJzDmIHXVWdN8VrDV8PUNbmtBeLUXHkW8QZ0vrNHY5iqCCrayjhJFXWW-Y3A..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFQU5ah6Bfk-vTFkqYEPCmD5JpX5KMhUgL.FozXShzX1KzfS0-2dJLoIEnLxKML1K.LB.-LxK-L12qL1KnLxK-LBo5L12qLxKnLBoqL1h-Eeh20; SUHB=0C1D7fcqghSWVN; ALF=1508198924; SSOLoginState=1476662925; _s_tentry=login.sina.com.cn; UOR=www.baidu.com,open.weibo.com,login.sina.com.cn; Apache=7381985104452.556.1476662925501; ULV=1476662925527:26:5:3:7381985104452.556.1476662925501:1476623300510";
		return cookie;
	}
}
