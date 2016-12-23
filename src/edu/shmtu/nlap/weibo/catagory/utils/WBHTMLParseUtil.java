package edu.shmtu.nlap.weibo.catagory.utils;

import java.io.IOException;
import java.util.ArrayList;
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
import edu.shmtu.nlap.weibo.catagory.test.WBDemoBeanUtil;
import edu.shmtu.nlap.weibo.catagory.test.WeiboCategoryUrlParseTest;
import net.sf.json.JSONObject;

public class WBHTMLParseUtil {
	/**
	 * 解析微博分类的原页面
	 * 
	 * @return
	 * @throws ParserException
	 * @throws IOException 
	 */
	public static List<WeiboDemoBean> parseOrginalRequestHTML(String url) throws ParserException, IOException {
		List<WeiboDemoBean> wbDemoBeanList = new ArrayList<WeiboDemoBean>();
		//
		// 1.从初始链接中 得到响应html
		String result =  WeiboRequestURLGenerator.sendGetAndRepeat(url);
		//
		if(!result.equals("")){
			// 2.解析响应结果 的js得到需要渲染的 div内容
			String contentDivStr = parseContentDiv(result);
			// 3.从渲染的div内容中 解析出 细化的含有所有微博的div
			String htmlDiv = parseWBHtmlDiv(contentDivStr);
			// 4.解析细化的div 得到包含单个微博内容的 div标签 列表
			List<Div> divList = parseWBHtmlDivToDivtagList(htmlDiv);
			// System.out.println("weibo 记录个数：" + divList.size());
			// 5.便利div列表 得到 微博bean列表
			String catagory = CommonUtils.WeiboCatagory.getNameByUrl(url);
			if (catagory != null) {
				wbDemoBeanList = extralTextFromDiv(divList, catagory);
			} else {
				System.out.println("the request url have illegal catagory type ");
			}
		}
		//
		return wbDemoBeanList;
	}

	public static List<WeiboDemoBean> parseAutoLoadingAndSeemoreRequestJSON(String url) throws ParserException, IOException {
		List<WeiboDemoBean> wbDemoBeanList = new ArrayList<WeiboDemoBean>();
		// 1.从初始链接中 得到响应html div 代码
		String resultJson = WeiboRequestURLGenerator.sendGetAndRepeat(url);
		if(!resultJson.equals("")){
			// System.out.println(result);
			Map resultMap = JSONTools.parseJSON2Map(resultJson);
			// System.out.println(resultMap.get("code"));
			String data = (String) resultMap.get("data");
			// 2.与上相同 解析div 大块的
			// 3.解析细化的div 得到包含单个微博内容的 div标签 列表
			List<Div> divList = parseWBHtmlDivToDivtagList(data);
			// System.out.println("weibo 记录个数：" + divList.size());
			// 4.便利div列表 得到 微博bean列表
			String catagory = CommonUtils.WeiboCatagory.getNameByUrl(url);
			if (catagory != null) {
				wbDemoBeanList = extralTextFromDiv(divList, catagory);
			} else {
				System.out.println("the request url have illegal catagory type ");
			}
		}
		//
		return wbDemoBeanList;
	}

	/**
	 * ��ȫ����Ӧ��Ϣ�� �� js�л�ȡ ��Ҫ��div ��Ⱦ�� ����
	 * 
	 * @param result
	 * @return
	 */
	private static String parseContentDiv(String result) {
		String contentDivStrPart = "pl.content.homeFeed.index";
		String contentDivStr = "";
		// <script>FM.view({
		// "ns": "pl.content.homeFeed.index",
		Pattern p = Pattern.compile("\\<script\\>FM.view\\(\\{(.+?)\\}\\)\\</script\\>");
		Matcher m = p.matcher(result);
		while (m.find()) {
			String js = m.group(1);//
			if (js.contains(contentDivStrPart)) {
				contentDivStr = js;
			}
		}
		return contentDivStr;
	}

	/**
	 * ����Ӧ���ݵ�div�еõ� ����΢�����ݵ� div,ϸ��
	 * 
	 * @param contentDiv
	 * @return
	 */
	private static String parseWBHtmlDiv(String contentDiv) {
		String htmlDiv = contentDiv.substring(contentDiv.indexOf("\"html\":"), contentDiv.length());
		Map<String, Object> fmViewMap = JSONTools.parseJSON2Map("{" + htmlDiv + "}");
		String htmlDivkey = "html";
		return (String) fmViewMap.get(htmlDivkey);
	}

	/**
	 * �Ӻ���΢����div�� �õ� ���е���΢����Ϣ�� div��ǩ����
	 * 
	 * @param wbHtmlDiv
	 * @return
	 * @throws ParserException
	 */
	private static List<Div> parseWBHtmlDivToDivtagList(String wbHtmlDiv) throws ParserException {
		List<Div> divList = new ArrayList<Div>();
		Parser parser = Parser.createParser(wbHtmlDiv, "utf-8");
		TagNameFilter tagnameFilter = new TagNameFilter("div");
		NodeList nodeList2 = parser.parse(tagnameFilter);
		for (int i = 0; i < nodeList2.size(); i++) {
			Div d = (Div) nodeList2.elementAt(i);
			String className = d.getAttribute("class");
			if (className != null && className.contains("WB_cardwrap WB_feed_type S_bg2")) {
				divList.add(d);
			}
		}
		return divList;
	}

	/**
	 * ���� ������΢��div��ǩ���õ�΢��bean�б�
	 * 
	 * @param divList
	 * @return
	 * @throws ParserException
	 * @throws IOException 
	 */
	private static List<WeiboDemoBean> extralTextFromDiv(List<Div> divList, String catagory) throws ParserException, IOException {
		List<Div> textDiv = new ArrayList<Div>();
		List<WeiboDemoBean> wbBeanList = new ArrayList<WeiboDemoBean>();
		if (divList != null && divList.size() > 0) {
			for (Div d : divList) {
				String tbinfo = d.getAttribute("tbinfo");
				String uid = tbinfo.split("=")[1];
				String mid = d.getAttribute("mid");
//				判断 当前微博是否存在 存在的话就不需要分析了--经验证在这里添加不行的
//				String filename = WBDemoBeanUtil.getWbBeanFilename(catagory, mid);
//				if(FileOptionUtil.hasFile(filename))
//					continue;
				//
				String text = "";
				String date = "";
				String nickname = "";
				String dString = d.getChildrenHTML();
				Parser parser = Parser.createParser(dString, "utf-8");
				TagNameFilter tagnameFilter = new TagNameFilter("div");
				NodeList nodeList2 = parser.parse(tagnameFilter);
				for (int i = 0; i < nodeList2.size(); i++) {
					Div subDiv = (Div) nodeList2.elementAt(i);
					String classname = subDiv.getAttribute("class");
					// ������ text class=WB_text W_f14
					if (classname != null&&classname.contains("WB_text W_f14")) {
						textDiv.add(subDiv);
						text = subDiv.toPlainTextString();
						//如果文本包括 展开全文 需要发送链接 
						if(text.contains("展开全文")){
							String longText = getLongTextAndRepeat(subDiv);
							if(!longText.equals(""))
								text = longText;
						}
						continue;
					}
					// ������ ʱ�� class=WB_from S_txt2 ����� a ��ǩ��title
					if (classname != null && classname.contains("WB_from S_txt2")) {
						LinkTag aLink = (LinkTag) subDiv.childAt(1);
						date = aLink.getAttribute("title");
						continue;
					}
					// ������ �ǳ� class=WB_info ���ӵ� class="W_f14 W_fb S_txt1"
					// nick-name
					if (classname != null && classname.contains("WB_info")) {
						LinkTag aLink = (LinkTag) subDiv.getFirstChild();
						nickname = aLink.getAttribute("nick-name");
						continue;
					}
					//转发内容  WB_text
					if(text!=null&&classname!=null&&classname.equals("WB_text")){
						text = text + subDiv.toPlainTextString();
						continue;
					}
				}
				WeiboDemoBean wbDemoBean = new WeiboDemoBean();
				wbDemoBean.setCatagory(catagory);
				wbDemoBean.setDate(date);
				wbDemoBean.setNickname(nickname);
				wbDemoBean.setText(text);
				wbDemoBean.setUid(uid);
				wbDemoBean.setWid(mid);

				wbBeanList.add(wbDemoBean);
			}
		}
		return wbBeanList;
	}
	/**
	 * div中包括 展开全文 需要再次发送请求
	 * http://d.weibo.com/p/aj/mblog/getlongtext?ajwvr=6&mid=4033154475928980&is_settop&is_sethot&is_setfanstop&is_setyoudao&__rnd=1477193359512
	 * <a target="_blank" href="/2794283127/EdUuXoSoI" class="WB_text_opt" 
	 * suda-uatrack="key=original_blog_unfold&amp;value=click_unfold:4033154475928980:2794283127" 
	 * action-type="fl_unfold" 
	 * action-data="mid=4033154475928980&amp;is_settop&amp;is_sethot&amp;is_setfanstop&amp;is_setyoudao">
	 * 展开全文<i class="W_ficon ficon_arrow_down">c</i></a>
	 * @param d
	 * @return
	 * @throws ParserException 
	 * @throws IOException 
	 */
	private static String getLongText(Div d) throws ParserException, IOException {
		//
		SleepUtils.sleep(500);
		//
		String longText = "";
		String childHtml = d.getChildrenHTML();
		Parser parser = Parser.createParser(childHtml, "utf-8");
		TagNameFilter linkFilter = new TagNameFilter("a");
		NodeList nodeList = parser.parse(linkFilter);
		for(int j=0;j<nodeList.size();j++){
			LinkTag linkTag = (LinkTag)nodeList.elementAt(j);
			if(linkTag.toPlainTextString().contains("展开全文")){
//				System.out.println("-----展开全文 查找------");
				String actionData = linkTag.getAttribute("action-data");
				String longTextUrl = WeiboConfig.getValue("longtextUrl")+actionData;
				String result = WeiboRequestURLGenerator.sendGetAndRepeat(longTextUrl);
				if(!result.equals("")){
					Map resultMap = JSONTools.parseJSON2Map(result);
					JSONObject longTextJson = (JSONObject)resultMap.get("data");//会抛出class case Exception异常
					Map htmlMap = JSONTools.parseJSONToMap(longTextJson);
					//
					String longTextDivHtml = "<div>"+(String)htmlMap.get("html")+"</div>";
					Parser longtextDivparser = Parser.createParser(longTextDivHtml, "utf-8");
					TagNameFilter tagnameFilter = new TagNameFilter("div");
					NodeList nodelist = longtextDivparser.parse(tagnameFilter);
					if(nodelist.size()>0)
						longText = nodelist.elementAt(0).toPlainTextString();
//				System.out.println("response:"+longText);
				}
			}
		}
		return longText;
	}
	private static String getLongTextAndRepeat(Div d) throws ParserException, IOException{
		LogInfoUtil log = new LogInfoUtil();
		String longText = "";
		int repeatRequestCount = 0;
		do{
			try{
				longText = getLongText(d);
				break;
			}catch(ClassCastException e){
				log.addInfo("-----展开全文失败-----");
				log.addInfo("repeat getLongText "+repeatRequestCount+" times");
				repeatRequestCount++;
				log.addInfo(e.getMessage());
				log.addInfo(d.toHtml());
				//
				SleepUtils.sleep(2000);
//				System.out.println("展开全文失败");
			}
		}while(repeatRequestCount<CommonUtils.REPEAT_NORESPONSE_COUNT);
		if(repeatRequestCount>0&&repeatRequestCount<CommonUtils.REPEAT_NORESPONSE_COUNT)
			log.addInfo("repeat getLongText "+repeatRequestCount+"times success!");
		if(repeatRequestCount>0)
			log.saveError();
		return longText;
	}
	/*
	 * 测试用例 
	 */
	private static void testDivParseLongText() throws ParserException, IOException{
		String dHtml = "<div class=\"WB_text W_f14\" node-type=\"feed_list_content\">                                                    分享图片 我々海军の大吉祥物<a target=\"_blank\" render=\"ext\" extra-data=\"type=atname\" href=\"http://weibo.com/n/%E5%B1%80%E5%BA%A7%E5%8F%AC%E5%BF%A0?from=feed&amp;loc=at\" usercard=\"name=局座召忠\">@局座召忠</a> 年轻是个大帅哥时，曾亲自考察过的墨尔本号航母，也是中国买到手的第一艘航母。排水量只有1.8万吨，属于英国在二战期间建造的“尊严”级航母，也叫“1942型轻型舰队航母”（为缩短工期，被设定为将在战争结束后三年内拆毁的应急型航母，因此舰体大部分采用商船标...<a target=\"_blank\" href=\"/2794283127/EdUuXoSoI\" class=\"WB_text_opt\" suda-uatrack=\"key=original_blog_unfold&amp;value=click_unfold:4033154475928980:2794283127\" action-type=\"fl_unfold\" action-data=\"mid=4033154475928980&amp;is_settop&amp;is_sethot&amp;is_setfanstop&amp;is_setyoudao\">展开全文<i class=\"W_ficon ficon_arrow_down\">c</i></a> </div>";
		Parser parser = Parser.createParser(dHtml,"utf-8");
		TagNameFilter tagnameFilter = new TagNameFilter("div");
		NodeList nodeList2 = parser.parse(tagnameFilter);
		for (int i = 0; i < nodeList2.size(); i++) {
			Div subDiv = (Div) nodeList2.elementAt(i);
			String longText = subDiv.toPlainTextString();
			if(longText.contains("展开全文")){
				String childHtml = subDiv.getChildrenHTML();
				parser = Parser.createParser(childHtml, "utf-8");
				TagNameFilter linkFilter = new TagNameFilter("a");
				NodeList nodeList = parser.parse(linkFilter);
				for(int j=0;j<nodeList.size();j++){
					LinkTag linkTag = (LinkTag)nodeList.elementAt(j);
					if(linkTag.toPlainTextString().contains("展开全文")){
						
						String actionData = linkTag.getAttribute("action-data");
						String longTextUrl = "http://d.weibo.com/p/aj/mblog/getlongtext?ajwvr=6&"+actionData;
						System.out.println(longTextUrl);
						String result = WeiboCategoryUrlParseTest.sendGet(longTextUrl);
						System.out.println(result);
						Map resultMap = JSONTools.parseJSON2Map(result);
						JSONObject longTextJson = (JSONObject)resultMap.get("data");
						Map htmlMap = JSONTools.parseJSONToMap(longTextJson);
						String longTextDivHtml = "<div>"+(String)htmlMap.get("html")+"</div>";
						Parser longtextDivparser = Parser.createParser(longTextDivHtml, "utf-8");
						NodeList nodelist = longtextDivparser.parse(tagnameFilter);
						longText = nodelist.elementAt(0).toPlainTextString();
						System.out.println(longText);
						break;
					}
				}
			}
		}
	}
	private static void testDivPrint() throws ParserException{
		String divStr = "<div class=\"WB_text W_f14\" node-type=\"feed_list_content\" nick-name=\"巴萨红蓝广播站\">"
				+"<a ignore=\"ignore\" title=\"微博会员特权\" href=\"http://vip.weibo.com/prividesc?priv=1107&amp;from=zdicon\"><span class=\"W_icon_feedpin\"><i class=\"W_icon icon_feedpin_lite\" title=\"微博会员特权\"></i>置顶</span></a>"
                                                                                                   +" 【<img render=\"ext\" src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/hearta_org.gif\" title=\"[心]\" alt=\"[心]\" type=\"face\" style=\"visibility: visible;\">】新赛季和冠奖杯们合照<img render=\"ext\" src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/08/dorahaose_org.gif\" title=\"[哆啦A梦花心]\" alt=\"[哆啦A梦花心]\" type=\"face\" style=\"visibility: visible;\"><img render=\"ext\" src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/08/dorahaose_org.gif\" title=\"[哆啦A梦花心]\" alt=\"[哆啦A梦花心]\" type=\"face\" style=\"visibility: visible;\"><img render=\"ext\" src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/08/dorahaose_org.gif\" title=\"[哆啦A梦花心]\" alt=\"[哆啦A梦花心]\" type=\"face\" style=\"visibility: visible;\"> [cr.fcb]                                            </div>";
		Parser parser = Parser.createParser(divStr, "utf-8");
		TagNameFilter divFilter = new TagNameFilter("div");
		NodeList nodeList = parser.parse(divFilter);
		Div div = (Div)nodeList.elementAt(0);
		System.out.println(div.toPlainTextString());
	}
	public static void main(String[]args) throws ParserException, IOException{
//		testDivParseLongText();
		testDivPrint();
	}
}
