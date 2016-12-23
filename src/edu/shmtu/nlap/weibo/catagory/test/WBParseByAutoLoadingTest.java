package edu.shmtu.nlap.weibo.catagory.test;


import java.io.IOException;
import java.util.List;

import org.htmlparser.util.ParserException;

import edu.shmtu.nlap.weibo.catagory.beans.WeiboDemoBean;
import edu.shmtu.nlap.weibo.catagory.utils.CommonUtils;
import edu.shmtu.nlap.weibo.catagory.utils.WBHTMLParseUtil;

public class WBParseByAutoLoadingTest {
	private static String baseurl = "http://d.weibo.com";
	private static String autoBaseUrl = "http://d.weibo.com/p/aj/v6/mblog/mbloglist";//加载 url
	private static String catagory = "102803_ctg1_6688_-_ctg1_6688";
	
	private static void parseOrginalCatagoryWB() throws ParserException, IOException{
		//1. 第一次初始请求 动作 请求：http://d.weibo.com/102803_ctg1_3288_-_ctg1_3288#
		String url = baseurl+"/"+catagory+"#";
		List<WeiboDemoBean> wbDemoBeanList = WBHTMLParseUtil.parseOrginalRequestHTML(url);
		//2.自动加载 动作，响应json
		
		//3.查看更多 动作，响应json
	}
	
}
