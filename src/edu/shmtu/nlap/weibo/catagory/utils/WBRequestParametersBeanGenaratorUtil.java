package edu.shmtu.nlap.weibo.catagory.utils;

import edu.shmtu.nlap.weibo.catagory.beans.WeiboRequestParametersBean;
/**
 * 参数实体类 操作生成 工具类
 * @author HP_xiaochao
 *
 */
public class WBRequestParametersBeanGenaratorUtil {
	public static WeiboRequestParametersBean genarateOriginalParametersBean(String catagory){
		WeiboRequestParametersBean wbRequestParametersBean = new WeiboRequestParametersBean(catagory);
		return wbRequestParametersBean;
	}
	/**
	 * 得到自动加载的 下一个bean
	 * @param preBean
	 * @return
	 */
	public static WeiboRequestParametersBean genarateAutoLoadingParametersBean(WeiboRequestParametersBean preBean){
		WeiboRequestParametersBean wbRequestParametersBean = new WeiboRequestParametersBean(preBean.getCatagoryStr());
		int current_page = preBean.getCurrent_page()+1;//cur_page ++
		int pre_page = preBean.getPre_page();//上页码也不变
		int page = preBean.getPage();//自动加载 页码不加
		int pagebar = (preBean.getPagebar()==0?1:0);
		
		wbRequestParametersBean.setCurrent_page(current_page);
		wbRequestParametersBean.setPre_page(pre_page);
		wbRequestParametersBean.setPage(page);
		wbRequestParametersBean.setPagebar(pagebar);
		return wbRequestParametersBean;
	}
	/**
	 * 自动加载 下一个
	 * @param preBean
	 * @return
	 */
	public static WeiboRequestParametersBean genarateSeemoreParametersBean(WeiboRequestParametersBean preBean){
		WeiboRequestParametersBean wbRequestParametersBean = new WeiboRequestParametersBean(preBean.getCatagoryStr());
		int current_page = preBean.getCurrent_page()+1;//cur_page ++
		int pre_page = preBean.getPage();//上页码
		int page = preBean.getPage()+1;// +1
		wbRequestParametersBean.setCurrent_page(current_page);
		wbRequestParametersBean.setPre_page(pre_page);
		wbRequestParametersBean.setPage(page);
		return wbRequestParametersBean;
	}
	
	public static void main(String[]args){
		String catagory = "102803_ctg1_3288_-_ctg1_3288";
		genaratorAotoUrl(catagory);
	}
	private static void genaratorAotoUrl(String catagory){
		String baseUrl = "http://d.weibo.com/p/aj/v6/mblog/mbloglist";
		WeiboRequestParametersBean originalBean = genarateOriginalParametersBean(catagory);
		System.out.println("original url:"+baseUrl+originalBean.toAutoLoadingParametersString());
		//创建自动 链接
		WeiboRequestParametersBean autoLoadingBean1 = genarateAutoLoadingParametersBean(originalBean);
		System.out.println("auto loading onece url:"+baseUrl+autoLoadingBean1.toAutoLoadingParametersString());
		WeiboRequestParametersBean autoLoadingBean2 = genarateAutoLoadingParametersBean(autoLoadingBean1);
		System.out.println("auto loading twice url:"+baseUrl+autoLoadingBean2.toAutoLoadingParametersString());
		WeiboRequestParametersBean seemoreBean = genarateSeemoreParametersBean(autoLoadingBean2);
		System.out.println("auto loading twice url:"+baseUrl+seemoreBean.toSeemoreParametersString());
		//
	}
}
