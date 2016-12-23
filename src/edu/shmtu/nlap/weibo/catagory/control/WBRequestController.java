package edu.shmtu.nlap.weibo.catagory.control;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.util.ParserException;

import edu.shmtu.nlap.weibo.catagory.beans.WeiboDemoBean;
import edu.shmtu.nlap.weibo.catagory.beans.WeiboRequestParametersBean;
import edu.shmtu.nlap.weibo.catagory.test.WBDemoBeanUtil;
import edu.shmtu.nlap.weibo.catagory.utils.LogInfoUtil;
import edu.shmtu.nlap.weibo.catagory.utils.SleepUtils;
import edu.shmtu.nlap.weibo.catagory.utils.WBHTMLParseUtil;
import edu.shmtu.nlap.weibo.catagory.utils.WBRequestParametersBeanGenaratorUtil;

/**
 * 请求
 * 
 * @author HP_xiaochao
 *
 */
public class WBRequestController {
	public static int repeatRequestCount = 5;// 如果自动加载 响应无 重复加载次数
	public static int autoCount = 15;// 设置模拟的次数 因为不同类别次数不同，这里尽量大点 如果获取空 就开始下一类
	/*
	 * 对指定类的请求 保存
	 */

	public static int[] requestSingleCatagory(String catagory) throws ParserException, IOException {
		List<WeiboDemoBean> currentAllDemoBeanList = new ArrayList<WeiboDemoBean>();
		LogInfoUtil log = new LogInfoUtil();

		// 1.第一次打开链接 出初始请求
		String baseUrl = "http://d.weibo.com/";
		String url = baseUrl + catagory + "#";
		System.out.println("begin request url:" + url);
		log.addInfo("begin request url:" + url);
		List<WeiboDemoBean> wbDemoBeanList = new ArrayList<WeiboDemoBean>();
		try {
			wbDemoBeanList = WBHTMLParseUtil.parseOrginalRequestHTML(url);
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println("response no content。request error !");
			log.addInfo("response no content。request error !");
		}

		currentAllDemoBeanList.addAll(wbDemoBeanList);
		// 2.开始 程序加载 模拟自动加载

		String baseAutoUrl = "http://d.weibo.com/p/aj/v6/mblog/mbloglist";
		WeiboRequestParametersBean originalBean = WBRequestParametersBeanGenaratorUtil
				.genarateOriginalParametersBean(catagory);
		System.out.println("auto original url:" + baseAutoUrl + originalBean.toAutoLoadingParametersString());
		//
		log.addInfo("auto original url:" + baseAutoUrl + originalBean.toAutoLoadingParametersString());
		//
		if (wbDemoBeanList.size() > 0) {
			for (int i = 0; i < autoCount; i++) {
				// 创建自动 链接
				WeiboRequestParametersBean autoLoadingBean1 = WBRequestParametersBeanGenaratorUtil
						.genarateAutoLoadingParametersBean(originalBean);
				String aotuLoadingUrl1 = baseAutoUrl + autoLoadingBean1.toAutoLoadingParametersString();
				System.out.println("auto loading onece url:" + aotuLoadingUrl1);
				log.addInfo("auto loading onece url for " + i + " times:" + aotuLoadingUrl1);

				List<WeiboDemoBean> autoLoadingDemoBeanList1 = new ArrayList<WeiboDemoBean>();
				int requestCount = 0;
				do {
					try {
						autoLoadingDemoBeanList1 = WBHTMLParseUtil
								.parseAutoLoadingAndSeemoreRequestJSON(aotuLoadingUrl1);
//						if(autoLoadingDemoBeanList1.size()>0)
						break;
					} catch (Exception e) {
						requestCount++;
						System.out.println("repeat request ");
						log.addInfo("repeat request " + requestCount + "times.");
					}
				} while (requestCount < repeatRequestCount);
				if(requestCount < repeatRequestCount&&requestCount > 0)
					log.addInfo("repeat request " + requestCount + "times success!.");
				requestCount = 0;
				//
				
				log.addInfo("get weibo num:" + autoLoadingDemoBeanList1.size());
				//
				WeiboRequestParametersBean autoLoadingBean2 = WBRequestParametersBeanGenaratorUtil
						.genarateAutoLoadingParametersBean(autoLoadingBean1);
				String aotuLoadingUrl2 = baseAutoUrl + autoLoadingBean2.toAutoLoadingParametersString();
				System.out.println("auto loading twice url:" + aotuLoadingUrl2);
				log.addInfo("auto loading twice url for " + i + " times:" + aotuLoadingUrl2);

				List<WeiboDemoBean> autoLoadingDemoBeanList2 = new ArrayList<WeiboDemoBean>();
				do {
					try {
						autoLoadingDemoBeanList2 = WBHTMLParseUtil
								.parseAutoLoadingAndSeemoreRequestJSON(aotuLoadingUrl2);
//						if(autoLoadingDemoBeanList2.size()>0)
						break;
					} catch (Exception e) {
						requestCount++;
						System.out.println("repeat request ");
						log.addInfo("repeat request " + requestCount + " times.");
					}
				} while (requestCount < repeatRequestCount);
				if(requestCount < repeatRequestCount&&requestCount > 0)
					log.addInfo("repeat request " + requestCount + " times success!.");
				requestCount = 0;

				log.addInfo("get weibo num:" + autoLoadingDemoBeanList2.size());
				//
				
				//
				WeiboRequestParametersBean seemoreBean = WBRequestParametersBeanGenaratorUtil
						.genarateSeemoreParametersBean(autoLoadingBean2);
				String seemoreUrl = baseAutoUrl + seemoreBean.toSeemoreParametersString();
				System.out.println("seemore twice url:" + seemoreUrl);
				log.addInfo("seemore url for " + i + " times:" + seemoreUrl);

				List<WeiboDemoBean> seemoreDemoBeanList = new ArrayList<WeiboDemoBean>();
				do {
					try {
						seemoreDemoBeanList = WBHTMLParseUtil.parseAutoLoadingAndSeemoreRequestJSON(seemoreUrl);
//						if(seemoreDemoBeanList.size()>0)
						break;
					} catch (Exception e) {
						requestCount++;
						System.out.println("repeat request ");
						log.addInfo("repeat request " + requestCount + " times.");
					}
				} while (requestCount < repeatRequestCount);
				if(requestCount < repeatRequestCount&&requestCount > 0)
					log.addInfo("repeat request " + requestCount + " times success!.");
				requestCount = 0;

				log.addInfo("get weibo num:" + seemoreDemoBeanList.size());
				//
				originalBean = seemoreBean;
				currentAllDemoBeanList.addAll(autoLoadingDemoBeanList1);
				currentAllDemoBeanList.addAll(autoLoadingDemoBeanList2);
				currentAllDemoBeanList.addAll(seemoreDemoBeanList);
				// 休眠
				SleepUtils.sleep(1000);
				
				if(autoLoadingDemoBeanList1.size()==0)
					break;
				if(autoLoadingDemoBeanList2.size()==0)
					break;
				if(seemoreDemoBeanList.size()==0)
					break;
			}
		}
		int numArr[] = WBDemoBeanUtil.saveWBDemoBeanList(currentAllDemoBeanList);
		System.out.println("url save exist weibo num:" + numArr[1]);
		System.out.println("url save noexist weibo num:" + numArr[0]);
		log.addInfo("url save exist weibo num:" + numArr[1]);
		log.addInfo("url save noexist weibo num:" + numArr[0]);
		//
		log.saveRequestInfo();
		//
		// 休眠
		SleepUtils.sleep(2000);
		return numArr;
	}

	public static void main(String args[]) throws ParserException, IOException {
		String catagory = "102803_ctg1_3288_-_ctg1_3288";
		requestSingleCatagory(catagory);
	}
}
