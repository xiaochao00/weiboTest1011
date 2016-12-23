package edu.shmtu.nlap.weibo.catagory.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import edu.shmtu.nlap.weibo.catagory.beans.WeiboDemoBean;
import edu.shmtu.nlap.weibo.catagory.utils.JSONTools;

public class WBHTMLPareserTest {
	public static void testFMView() throws ParserException, IOException {
		//1.���� ��ȡ��һ����Ӧ����
		String url = "http://d.weibo.com/102803_ctg1_6688_-_ctg1_6688#";
		String result = WeiboCategoryUrlParseTest.sendGet(url);
		//2.ģʽƥ���ȡ ��Ҫ��js
		String contentDivStrPart = "pl.content.homeFeed.index";
		String contentDivStr = "";
		//
		Pattern p = Pattern.compile("\\<script\\>FM.view\\(\\{(.+?)\\}\\)\\</script\\>");
		Matcher m = p.matcher(result);
		while (m.find()) {
			String js = m.group(1);
			if(js.contains(contentDivStrPart)){
				contentDivStr = js;
			}
		}
//		System.out.println(contentDivStr);
		//3.����js��ȡ��Ҫ��div ����
		String htmlDivkey = "html";
		Map<String, Object> fmViewMap = transforFMViewToMapSimple(contentDivStr);
		String htmlDiv = (String)fmViewMap.get(htmlDivkey);
//		System.out.println(htmlDiv);
		//4.����div �õ�΢�����ݵ�div
		System.out.println(htmlDiv);
		List<Div> divList = parseDiv(htmlDiv);
		System.out.println("weibo ��Ŀ��"+divList.size());
		//5.����΢������
		extralTextFromDiv(divList);
	}
	private static Map<String,Object> transforFMViewJSONToMap(String fmViewJson){
		fmViewJson.replace("[\"", "[\\\"");
		fmViewJson.replace("\"]", "\\\"]");
		return JSONTools.parseJSON2Map("{"+fmViewJson+"}");
	}
	private static Map<String,String> transforFMViewToMap(String fmView){
		Map<String,String> fmViewMap = new HashMap<String,String>();
		String[] strArr1 = fmView.split(",");
		System.out.println("map�ܳ���"+strArr1.length);
		for(String s:strArr1){
			String[] strMapArr = s.split(":");
			fmViewMap.put(strMapArr[0], strMapArr[1]);
		}
		return fmViewMap;
	}
	private static  Map<String,Object> transforFMViewToMapSimple(String fmView){
		Map<String,Object> fmViewMap = new HashMap<String,Object>();
		String htmlDiv = fmView.substring(fmView.indexOf("\"html\":"),fmView.length());
		fmViewMap = JSONTools.parseJSON2Map("{"+htmlDiv+"}");
		return fmViewMap;
	}
	public static void main(String[]args) throws ParserException, IOException{
		testFMView();
	}
	public static List<Div> parseDiv(String htmlDiv) throws ParserException{
		List<Div> divList = new ArrayList<Div>();
		Parser parser = Parser.createParser(htmlDiv, "utf-8");
		TagNameFilter tagnameFilter = new TagNameFilter("div");
		NodeList nodeList2 = parser.parse(tagnameFilter);
//		NodeFilter cssFilter = new CssSelectorNodeFilter("div.WB_cardwrap");
//		NodeFilter cssFilter2 = new CssSelectorNodeFilter("div.WB_feed_type S_bg2");
//		NodeFilter filter = new AndFilter(cssFilter1,cssFilter2);
//		NodeList nodeList2 = parser.parse(cssFilter);
//		System.out.println(nodeList2.size());
		for(int i=0;i<nodeList2.size();i++){
//			System.out.println(nodeList2.elementAt(i).toHtml());
//			System.out.println(nodeList2.elementAt(i).toPlainTextString());
			Div d = (Div)nodeList2.elementAt(i);
			String className = d.getAttribute("class");
			
			if(className!=null&&className.contains("WB_cardwrap WB_feed_type S_bg2")){
				divList.add(d);
			}
		}
		return divList;
	}
	private static void extralTextFromDiv(List<Div> divList) throws ParserException, IOException{
		List<Div> textDiv = new ArrayList<Div>();
		List<WeiboDemoBean> wbBeanList = new ArrayList<WeiboDemoBean>();
		
		if(divList!=null&&divList.size()>0){
			for(Div d:divList){
				String tbinfo = d.getAttribute("tbinfo");
				
				String uid = tbinfo.split("=")[1];
				String mid = d.getAttribute("mid");
				String text = "";
				String date = "";
				String nickname = "";
				
				String dString = d.getChildrenHTML();
				Parser parser = Parser.createParser(dString, "utf-8");
				TagNameFilter tagnameFilter = new TagNameFilter("div");
				NodeList nodeList2 = parser.parse(tagnameFilter);
				
				for(int i=0;i<nodeList2.size();i++){
					Div subDiv = (Div)nodeList2.elementAt(i);
					String classname = subDiv.getAttribute("class");
					//������ text class=WB_text W_f14
					if(classname.contains("WB_text W_f14")){
						textDiv.add(subDiv);
						text = subDiv.toPlainTextString();
						continue;
					}
					//������ ʱ�� class=WB_from S_txt2 ����� a ��ǩ��title
					if(classname.contains("WB_from S_txt2")){
						LinkTag aLink = (LinkTag)subDiv.childAt(1);
						date = aLink.getAttribute("title");
						continue;
					}
					//������ �ǳ� class=WB_info ���ӵ� class="W_f14 W_fb S_txt1" nick-name
					if(classname.contains("WB_info")){
						LinkTag aLink = (LinkTag)subDiv.getFirstChild();
						nickname = aLink.getAttribute("nick-name");
						continue;
					}
				}
				WeiboDemoBean wbDemoBean = new WeiboDemoBean();
				wbDemoBean.setCatagory("军事");
				wbDemoBean.setDate(date);
				wbDemoBean.setNickname(nickname);
				wbDemoBean.setText(text);
				wbDemoBean.setUid(uid);
				wbDemoBean.setWid(mid);
				
				wbBeanList.add(wbDemoBean);
			}
		}
		WBDemoBeanUtil.saveWBDemoBeanList(wbBeanList);
	}
}
