package edu.shmtu.nlap.weibo.catagory.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class ParserZhiWang {
	private static String zhuanliUrlStart = "http://dbpub.cnki.net/grid2008/dbpub/";//专利详细页面的开始拼接地址
	private static String zhuanliUrlTail = "&uid="+getUidFromCookie();//专利详细页面结束的拼接地址
	private static String pageUrlStart = "http://epub.cnki.net/kns/brief/brief.aspx?";//专利分页列表的开始拼接字段
	//专利分页列表结束的拼接字段
	private static String pageUrlTail = "&QueryID=3&ID=&turnpage=1&tpagemode=L&dbPrefix=SCOD&Fields=&DisplayMode=listmode&PageName=ASP.brief_default_result_aspx#J_ORDER";
//	curpage=10&RecordsPerPage=10";
	/**
	 * 发送请求方法
	 * 返回响应页面
	 */
	private static String sendGet(String url) throws IOException {
		String result = "";
		BufferedReader in = null;
		String myCookie = getCookie();
		try {
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "text/html; charset=utf-8");
			connection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			// cookie
			connection.setRequestProperty("Cookie", myCookie);
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + url);
			e.printStackTrace();
		} finally {
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
	/**
	 * 解析响应内容 解析 专利分页列表 页面的table
	 * 不能用
	 * @param content
	 * @throws ParserException
	 */
	public static void parserTable(String content) throws ParserException {
		Parser parser = Parser.createParser(content, "utf-8");
		NodeFilter tableFilter = new NodeClassFilter(TableTag.class);
		OrFilter lastFilter = new OrFilter();
		lastFilter.setPredicates(new NodeFilter[] { tableFilter });
		NodeList nodeList = parser.parse(lastFilter);
		for (int i = 0; i <= nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof TableTag) {
				TableTag tag = (TableTag) nodeList.elementAt(i);
				if (tag.getAttribute("class") != null && tag.getAttribute("class").equals("GridTableContent")) {
					System.out.println(tag.getChildrenHTML());
					System.out.println("------------------");
				}
			}
		}
	}
	/**
	 * 解析相应内容的TR 返回TR列表
	 * @param content
	 * @return
	 * @throws ParserException
	 */
	public static List<TableRow> parserTr(String content) throws ParserException {
		List<TableRow> rowList = new LinkedList<TableRow>();
		Parser parser = Parser.createParser(content, "utf-8");
		NodeFilter tableFilter = new NodeClassFilter(TableRow.class);
		OrFilter lastFilter = new OrFilter();
		lastFilter.setPredicates(new NodeFilter[] { tableFilter });
		NodeList nodeList = parser.parse(lastFilter);
		for (int i = 0; i <= nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof TableRow) {
				TableRow row = (TableRow) nodeList.elementAt(i);
				if (row.getAttribute("bgcolor") != null)
//					System.out.println(row.getAttribute("bgcolor"));
					rowList.add(row);
			}
		}
		return rowList;
	}
	/**
	 * 通过TR列表 得到TR中标题连接列表
	 * @param rowList
	 * @return
	 */
	public static List<String> parserUrl(List<TableRow> rowList){
		List<String> urlList = new LinkedList<String>();
		if(rowList!=null&&rowList.size()>0){
			for(TableRow row:rowList){
				TableColumn linkTd = (TableColumn)(row.getColumns()[1]);
				NodeList nodeList = linkTd.getChildren();
				for(int i=0;i<nodeList.size();i++){
					if(nodeList.elementAt(i) instanceof LinkTag){
						LinkTag link = (LinkTag)nodeList.elementAt(i);
						String url = link.getAttribute("href");
							urlList.add(url);
					}
				}
			}
		}
		return urlList;
	}
	/**
	 * 登录的cookie
	 * @return
	 */
	private static String getCookie() {
		String cookie = "RsPerPage=20; kc_cnki_net_uid=1af6fe48-25c3-6c43-209c-86b959a3c411; ASP.NET_SessionId=ngxahtatqmtwtfgcvmh3kbmj; c_m_LinID=LinID=WEEvREcwSlJHSldRa1FhdkJkdjAzb1J5YkNOcmdEV2RmbjVlR3FHb3Vobz0=$9A4hF_YAuvQ5obgVAqNKPCYcEjKensW4ggI8Fm4gTkoUKaID8j8gFw!!&ot=11/02/2016 20:51:25; c_m_expire=2016-11-02 20:51:25; Ecp_LoginStuts={\"IsAutoLogin\":false,\"UserName\":\"SH0010T\",\"ShowName\":\"%e4%b8%8a%e6%b5%b7%e6%b5%b7%e4%ba%8b%e5%a4%a7%e5%ad%a6\",\"UserType\":\"bk\",\"r\":\"zTMAXF\"}; Ecp_ClientId=2160621210709714672; LID=WEEvREcwSlJHSldRa1FhdkJkdjAzb1J5YkNOcmdEV2RmbjVlR3FHb3Vobz0=$9A4hF_YAuvQ5obgVAqNKPCYcEjKensW4ggI8Fm4gTkoUKaID8j8gFw!!";
		return cookie;
	}
	/**
	 * 从cookie中得到 uid字段，用在详细专利 页面的连接地址
	 * @return
	 */
	public static String getUidFromCookie(){
		String cookie = getCookie();
		String uid = "";
		String []strList = cookie.split(";");
		String LID = strList[strList.length-1].trim();
		if(LID.startsWith("LID")){
			uid = LID.substring(LID.indexOf("LID")+4, LID.length());
		}
		return uid;
	}
	/**
	 * 通过专利分页列表页面的连接列表 转换成可以直接访问的地址
	 * @param urlList
	 * @return
	 */
	public static List<String> getZhuanliDetailUrl(List<String> urlList){
		List<String> detailUrlList = new LinkedList<String>();
		if(urlList!=null&&urlList.size()>0){
			for(String url:urlList){
				String detailUrl = zhuanliUrlStart+url.replace("/kns/detail/", "/")+zhuanliUrlTail;
				detailUrlList.add(detailUrl);
			}
		}
		return detailUrlList;
	}
	/**
	 *解析详细专利 页面 返回 摘要和主权专项字段的Map
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> parserDetail(String url) throws Exception{
//		List<String> abstractList = new ArrayList<String>();
//		List<String> termList = new ArrayList<String>();
		Map<String,String> contentMap = new HashMap<String,String>();
		String result  = sendGet(url);
		TableTag boxTable = new TableTag();
		Parser parser = Parser.createParser(result, "urf-8");
		TagNameFilter tagnameFilter = new TagNameFilter("table");
		NodeList nodeList = parser.parse(tagnameFilter);
		for(int i=0;i<nodeList.size();i++){
			if(nodeList.elementAt(i) instanceof TableTag){
				TableTag table = (TableTag)nodeList.elementAt(i);
				if(table.getAttribute("id")!=null&&table.getAttribute("id").equals("box")){
					boxTable = table;
					break;					
				}
			}
		}
//		 System.out.println(boxTable.getRowCount());
		 TableRow[] rowArr = boxTable.getRows();
		 for(TableRow row:rowArr){
			 TableColumn[] columnArr = row.getColumns();
			 if(columnArr.length>1){
				 String textName = columnArr[0].getStringText();
				 String textContent = columnArr[1].getStringText();
				 if(textName.contains("摘要"))
					 contentMap.put("摘要", textContent);
				 if(textName.contains("主权项"))
					 contentMap.put("主权项", textContent);
			 }
		 }
		 return contentMap;
	}
	/**
	 * 根据 总页码数 和每页的记录条数 拼接 专利列表页面地址 返回每页访问的地址连接
	 * @param pageCount
	 * @param pageSize
	 * @return
	 */
	public static List<String> genaratorPageUrl(int pageCount,int pageSize){
		List<String> pageUrlList = new ArrayList<String>();
		int curpage = 1;
//		int RecordsPerPage = 1;
		for(int i=0;i<pageCount;i++){
			String param = pageUrlStart+"curpage="+curpage+"&RecordsPerPage="+pageSize+pageUrlTail;
			pageUrlList.add(param);
			curpage++;
		}
		return pageUrlList;
	}
	public static void main(String[] args) throws Exception {
//		String url = "http://epub.cnki.net/kns/brief/brief.aspx?pagename=ASP.brief_default_result_aspx&dbPrefix=SCOD&dbCatalog=%E4%B8%AD%E5%9B%BD%E5%AD%A6%E6%9C%AF%E6%96%87%E7%8C%AE%E7%BD%91%E7%BB%9C%E5%87%BA%E7%89%88%E6%80%BB%E5%BA%93&ConfigFile=SCDBINDEX.xml&research=off&t=1478091999013&keyValue=%E6%96%87%E6%9C%AC%E5%88%86%E7%B1%BB&S=1&queryid=1&skuakuid=1&turnpage=1&recordsperpage=50";
		//1.拼接分页列表的地址 列表，得到所有需要页面的链接地址
		List<String> pageList = genaratorPageUrl(2,50);
		for(String pageUrl:pageList){
			//此处产生解析 主页面的时间 time1 开始
			//2.访问该页的url解析相应内容
			String result = sendGet(pageUrl);
			//3.解析分页列表的相应页面，得到所有需要的 row 行
			List<TableRow> rowList= parserTr(result);
			//4.从row列表中得到 连接列表
			List<String> urlList = parserUrl(rowList);
			////此处产生解析 主页面的时间 time1 结束 
			//5.转换连接地址 转换成可以访问的详细的页面地址，得到本页 所有专利的详细页面地址
			List<String> detailList = getZhuanliDetailUrl(urlList);
			//6.循环访问 详细页面地址
			for(String str:detailList){
				//此处产生 解析详细页面的时间和保存时间 time2 开始
				//7.访问并解析 详细页面 得到 摘要和主权项 的 map
				Map<String,String> contentMap = parserDetail(str);
				//8.拼接 摘要和主权项 
				String content = contentMap.get("摘要")+contentMap.get("主权项");
				//9.在此调用保存文件方法
//				System.out.println(contentMap.get("摘要"));
//				System.out.println(contentMap.get("主权项"));
				
				
				
				//此处产生 解析详细页面的时间和保存时间 time2 结束
				
			}
		}
			//总时间复杂度 总页数*time1+总页数*每页记录数*time2
	}
}
