package edu.shmtu.nlap.weibo.catagory.beans;

import java.util.Date;

/**
 * 自动加载和查看更多链接 参数 实体封装
 * @author HP_xiaochao
 *
 */
public class WeiboRequestParametersBean {
	public WeiboRequestParametersBean(String catagoryStr){
		this.catagoryStr = catagoryStr;
		this.domain = catagoryStr;
		this.id = catagoryStr;
		this.script_uri = "/"+catagoryStr;
		this.domain_op = catagoryStr;
	}
	/**
	 * 自动加载
	 * ajwvr=6&domain=102803_ctg1_3288_-_ctg1_3288&
pagebar=0&tab=home&current_page=1&pre_page=1&
page=1&pl_name=Pl_Core_NewMixFeed__5&
id=102803_ctg1_3288_-_ctg1_3288&
script_uri=/102803_ctg1_3288_-_ctg1_3288&feed_type=1&
domain_op=102803_ctg1_3288_-_ctg1_3288&__rnd=1476601635169
	 */
	private String ajwvr = "6";//常量
	private String domain = "";//catagory type
	private int pagebar = 1;//自动加载页码0或1
	private String tab = "home";//常量
	private int current_page = 0;//加载页 1-10...
	private int pre_page = 1;//上一页的page 第一页的上一页是默认1
	private int page = 1;//页码 自动加载不算分页，查看更多 才加1
	private String pl_name = "Pl_Core_NewMixFeed__5";//常量
	private String id = "";//catagory type  
	private String script_uri = "";// /+catagory type 
	private int feed_type = 1;
	private String domain_op = "";//catagory type  
	private String __rnd = "";//时间戳+三个随机数
	/*查看更多
	 * http://d.weibo.com/p/aj/v6/mblog/mbloglist
?ajwvr=6&domain=102803_ctg1_3288_-_ctg1_3288&
pre_page=1&page=2&pids=Pl_Core_NewMixFeed__5&
current_page=3&since_id=&pl_name=Pl_Core_NewMixFeed__5&
id=102803_ctg1_3288_-_ctg1_3288&script_uri=/102803_ctg1_3288_-_ctg1_3288&
feed_type=1&domain_op=102803_ctg1_3288_-_ctg1_3288&__rnd=1476606351170
	 */
	private String pids = "Pl_Core_NewMixFeed__5";//常量
	private String since_id = "";
	//
	private String catagoryStr = null;
	//

	public String getAjwvr() {
		return ajwvr;
	}

	public void setAjwvr(String ajwvr) {
		this.ajwvr = ajwvr;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getPagebar() {
		return pagebar;
	}

	public void setPagebar(int pagebar) {
		this.pagebar = pagebar;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public int getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

	public int getPre_page() {
		return pre_page;
	}

	public void setPre_page(int pre_page) {
		this.pre_page = pre_page;
	}
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getPl_name() {
		return pl_name;
	}

	public void setPl_name(String pl_name) {
		this.pl_name = pl_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScript_uri() {
		return script_uri;
	}

	public void setScript_uri(String script_uri) {
		this.script_uri = script_uri;
	}

	public int getFeed_type() {
		return feed_type;
	}

	public void setFeed_type(int feed_type) {
		this.feed_type = feed_type;
	}

	public String getDomain_op() {
		return domain_op;
	}

	public void setDomain_op(String domain_op) {
		this.domain_op = domain_op;
	}

	public String get__rnd() {
		return __rnd;
	}

	public void set__rnd(String __rnd) {
		this.__rnd = __rnd;
	}

	public String getCatagoryStr() {
		return catagoryStr;
	}

	public void setCatagoryStr(String catagoryStr) {
		this.catagoryStr = catagoryStr;
	}

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public String getSince_id() {
		return since_id;
	}

	public void setSince_id(String since_id) {
		this.since_id = since_id;
	}
	/**
	 * 加载自动 参数封装
	 * @return
	 */
	public String toAutoLoadingParametersString(){
		this.__rnd = genarateRnd();
		String parameters = "?ajwvr=6&domain="+domain+"&pagebar="+pagebar+"&tab=home&current_page="+current_page
				+"&pre_page="+pre_page+"&page="+page+"&pl_name=Pl_Core_NewMixFeed__5&id="
				+id+"&script_uri="+script_uri+"&feed_type=1&domain_op="+domain_op
				+"&__rnd="+__rnd;
		return parameters;
	}
	/**
	 * 查看更多 参数封装
	 * @return
	 * ?ajwvr=6&domain=102803_ctg1_3288_-_ctg1_3288&
pre_page=1&page=2&pids=Pl_Core_NewMixFeed__5&
current_page=3&since_id=&pl_name=Pl_Core_NewMixFeed__5&
id=102803_ctg1_3288_-_ctg1_3288&script_uri=/102803_ctg1_3288_-_ctg1_3288&
feed_type=1&domain_op=102803_ctg1_3288_-_ctg1_3288&__rnd=1476606351170
	 */
	public String toSeemoreParametersString(){
		this.__rnd = genarateRnd();
		String parameters = "?ajwvr=6&domain="+domain
				+"&pre_page="+pre_page
				+"&page="+page
				+"&pids="+pids
				+"&current_page="+current_page
				+"&tab=home"
				+"&since_id=&pl_name=Pl_Core_NewMixFeed__5&id="
				+id+"&script_uri="+script_uri+"&feed_type=1&domain_op="+domain_op
				+"&__rnd="+__rnd;
		return parameters;
	}
	/**
	 * 时间戳生成 毫秒 13位 
	 * @return
	 */
	private  String genarateRnd(){
		Date date = new Date();
		String rnd = Long.toString((date.getTime()));
		return rnd;
	}
	public static void main(String[]args){
//		System.out.println(genarateRnd());
	}
}
