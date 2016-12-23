package edu.shmtu.nlap.weibo.catagory.control;

import java.io.IOException;
import java.util.List;

import org.htmlparser.util.ParserException;

import edu.shmtu.nlap.weibo.catagory.beans.WeiboDemoBean;
import edu.shmtu.nlap.weibo.catagory.test.WBDemoBeanUtil;
import edu.shmtu.nlap.weibo.catagory.utils.CommonUtils;
import edu.shmtu.nlap.weibo.catagory.utils.LogInfoUtil;
import edu.shmtu.nlap.weibo.catagory.utils.WBHTMLParseUtil;

public class WBParseController {
	public static void main(String []args) throws ParserException, IOException{
		doSomethmore();
	}
	/**
	 * 各类信息保存
	 * @throws ParserException
	 * @throws IOException
	 */
	private static void doSometh() throws ParserException, IOException{
		//http://d.weibo.com/102803_ctg1_6688_-_ctg1_6688#
		LogInfoUtil logInfo = new LogInfoUtil();
		String baseUrl = "http://d.weibo.com/";
		List<String> wbCatagoryTypeArr = CommonUtils.WeiboCatagory.getTypeArray();
		int sumNumArr[] = {0,0};
		for(String type:wbCatagoryTypeArr){
			String url = baseUrl+type+"#";
			System.out.println("begin request url:"+url);
			logInfo.addInfo("begin request url:"+url);
			List<WeiboDemoBean> wbDemoBeanList = WBHTMLParseUtil.parseOrginalRequestHTML(url);
			int numArr[] = WBDemoBeanUtil.saveWBDemoBeanList(wbDemoBeanList);
			System.out.println("url save exist weibo num:" + numArr[1]);
			System.out.println("url save noexist weibo num:" + numArr[0]);
			logInfo.addInfo("url save exist weibo num:" + numArr[1]);
			logInfo.addInfo("url save noexist weibo num:" + numArr[0]);
			sumNumArr[0]+=numArr[0];
			sumNumArr[1]+=numArr[1];
		}
		System.out.println("url save exist weibo num:" + sumNumArr[1]);
		System.out.println("url save noexist weibo num:" + sumNumArr[0]);
		logInfo.addInfo("url save exist weibo num:" + sumNumArr[1]);
		logInfo.addInfo("url save noexist weibo num:" + sumNumArr[0]);
		logInfo.saveInfo();
	}
	
	public static void doSomethmore() throws ParserException, IOException{
		LogInfoUtil logInfo = new LogInfoUtil();
		List<String> wbCatagoryTypeArr = CommonUtils.WeiboCatagory.getTypeArray();
		int sumNumArr[] = {0,0};
		for(String type:wbCatagoryTypeArr){
			int numArr[] = WBRequestController.requestSingleCatagory(type);
//			System.out.println("url save exist weibo num:" + numArr[1]);
//			System.out.println("url save noexist weibo num:" + numArr[0]);
			logInfo.addInfo("url save exist weibo num:" + numArr[1]);
			logInfo.addInfo("url save noexist weibo num:" + numArr[0]);
			sumNumArr[0]+=numArr[0];
			sumNumArr[1]+=numArr[1];
		}
		if(sumNumArr[0]==0&&sumNumArr[1]==0){
			//所有类别请求都为0 证明是 cookie 不行，需要更换
			System.out.println("all catagory request no response . cookie overdue,please change cookie .");
			logInfo.addInfo("all catagory request no response . cookie overdue,please change cookie .");
		}
		System.out.println("url save exist weibo num:" + sumNumArr[1]);
		System.out.println("url save noexist weibo num:" + sumNumArr[0]);
		logInfo.addInfo("url save exist weibo num:" + sumNumArr[1]);
		logInfo.addInfo("url save noexist weibo num:" + sumNumArr[0]);
		logInfo.saveInfo();
	}
	
}
