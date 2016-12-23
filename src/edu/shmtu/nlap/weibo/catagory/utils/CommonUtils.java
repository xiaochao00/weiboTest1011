package edu.shmtu.nlap.weibo.catagory.utils;

import java.util.ArrayList;
import java.util.List;

public class CommonUtils {
	public static final int AUTO_REQUEST_COUNT = 100;//程序加载次数 100*3*15*类数目
	public static final int REPEAT_NORESPONSE_COUNT = 8;
	public static final String BASE_PRIMARY_URL = "";//"http://d.weibo.com/";
	public static final String BASE_AUTO_URL = "";//"http://d.weibo.com/p/aj/v6/mblog/mbloglist";
	public static final String LOG_DIRECTORY = "";//"D:/weibo/catagory/log"
	/**
	 * 微博数据列别 枚举类
	 * 
军事 102803_ctg1_6688_-_ctg1_6688
历史 102803_ctg1_6788_-_ctg1_6788
美食 102803_ctg1_2688_-_ctg1_2688
体育	102803_ctg1_1388_-_ctg1_1388

时事 102803_ctg1_4188_-_ctg1_4188 
汽车 102803_ctg1_5188_-_ctg1_5188
科技 102803_ctg1_2088_-_ctg1_2088
综艺娱乐 102803_ctg1_4688_-_ctg1_4688
电影	102803_ctg1_3288_-_ctg1_3288
//
 * 科普 102803_ctg1_5988_-_ctg1_5988
 * 数码 102803_ctg1_5088_-_ctg1_5088
 * 财经 102803_ctg1_6388_-_ctg1_6388
 * 股市 102803_ctg1_1288_-_ctg1_1288 和财经一样
 * 国际资讯 102803_ctg1_6288_-_ctg1_6288 和实事一样 
 * 电视剧 102803_ctg1_2488_-_ctg1_2488
 * 电影 102803_ctg1_3288_-_ctg1_3288
 * 音乐 102803_ctg1_5288_-_ctg1_5288
 * 汽车 102803_ctg1_5188_-_ctg1_5188
 * 运动健身 102803_ctg1_4788_-_ctg1_4788
 * 健康 102803_ctg1_2188_-_ctg1_2188
 * 情感 102803_ctg1_1988_-_ctg1_1988
 * 搞笑 102803_ctg1_4388_-_ctg1_4388
 * 游戏 102803_ctg1_4888_-_ctg1_4888
 * 旅游 102803_ctg1_2588_-_ctg1_2588
 * 育儿 102803_ctg1_3188_-_ctg1_3188
 * 校园  102803_ctg1_1488_-_ctg1_1488
 * 房产 102803_ctg1_5588_-_ctg1_5588
 * 家居 102803_ctg1_5888_-_ctg1_5888
 * 星座 102803_ctg1_1688_-_ctg1_1688
 * 读书 102803_ctg1_4588_-_ctg1_4588
 * 
 * 
 * 三农 102803_ctg1_7188_-_ctg1_7188
 * 设计 102803_ctg1_5388_-_ctg1_5388
 * 艺术 102803_ctg1_5488_-_ctg1_5488
 * 时尚 102803_ctg1_4488_-_ctg1_4488
 * 美妆 102803_ctg1_1588_-_ctg1_1588
 * 动漫 102803_ctg1_2388_-_ctg1_2388
 * 宗教 102803_ctg1_5688_-_ctg1_5688
 * 萌宠 102803_ctg1_2788_-_ctg1_2788
 * 法律 102803_ctg1_7388_-_ctg1_7388
 * 
	 * @author HP_xiaochao
	 *
	 */
	public enum WeiboCatagory{
		
		junshi(1,"军事","102803_ctg1_6688_-_ctg1_6688"),
		lishi(2,"历史","102803_ctg1_6788_-_ctg1_6788"),
		meishi(3,"美食","102803_ctg1_2688_-_ctg1_2688"),
		tiyu(4,"体育","102803_ctg1_1388_-_ctg1_1388"),
		shishi(5,"时事","102803_ctg1_4188_-_ctg1_4188"),
		qiche(6,"汽车","102803_ctg1_5188_-_ctg1_5188"),
		keji(7,"科技","102803_ctg1_2088_-_ctg1_2088"),
		zongyi(8,"综艺娱乐","102803_ctg1_4688_-_ctg1_4688"),
		dianying(9,"电影","102803_ctg1_3288_-_ctg1_3288"),
		
		falv(10,"法律","102803_ctg1_7388_-_ctg1_7388"),
		mengchong(11,"萌宠","102803_ctg1_2788_-_ctg1_2788"),
		zongjiao(12,"宗教","102803_ctg1_5688_-_ctg1_5688"),
		dongma(13,"动漫","102803_ctg1_2388_-_ctg1_2388"),
		meizhuang(14,"美妆","102803_ctg1_1588_-_ctg1_1588"),
		shishang(15,"时尚","102803_ctg1_4488_-_ctg1_4488"),
		yishu(16,"艺术","102803_ctg1_5488_-_ctg1_5488"),
		sheji(17,"设计","102803_ctg1_5388_-_ctg1_5388"),
		sannong(18,"三农","102803_ctg1_7188_-_ctg1_7188");
		
//		dushu(19,"读书","102803_ctg1_4588_-_ctg1_4588"),
//		xingzuo(10,"星座","102803_ctg1_1688_-_ctg1_1688"),
//		jiaju(21,"家居","102803_ctg1_5888_-_ctg1_5888"),
//		fangchan(22,"房产","102803_ctg1_5588_-_ctg1_5588"),
//		xiaoyuan(23,"校园","102803_ctg1_1488_-_ctg1_1488"),
//		yuer(24,"育儿","102803_ctg1_3188_-_ctg1_3188"),
//		lvyou(25,"旅游","102803_ctg1_2588_-_ctg1_2588"),
//		youxi(26,"游戏","102803_ctg1_4888_-_ctg1_4888"),
//		gaoxiao(27,"搞笑","102803_ctg1_4388_-_ctg1_4388"),
//		 qinggan(28,"情感","102803_ctg1_1988_-_ctg1_1988"),
//		jiankang(29,"健康","102803_ctg1_2188_-_ctg1_2188"),
//		yundongjianshen(30,"运动健身","102803_ctg1_4788_-_ctg1_4788"),
//		yinyue(31,"音乐","102803_ctg1_5288_-_ctg1_5288"),
//		dianshiju(32,"电视剧","102803_ctg1_2488_-_ctg1_2488"),
//		guojizixun(33,"国际资讯","102803_ctg1_6288_-_ctg1_6288"),
//		gushi(34,"股市","102803_ctg1_1288_-_ctg1_1288"),
//		caijing(35,"财经","102803_ctg1_6388_-_ctg1_6388"),
//		shuma(36,"数码","102803_ctg1_5088_-_ctg1_5088"),
//		kepu(37,"科普","102803_ctg1_5988_-_ctg1_5988");
		
		private Integer classNum;
		private String name;
		private String type;
		WeiboCatagory(Integer classNum,String name,String type){
			this.name = name;
			this.type = type;
			this.classNum = classNum;
		}
		public Integer getClassNum() {
			return classNum;
		}
		public void setClassNum(Integer classNum) {
			this.classNum = classNum;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public static String getTypeByName(String name){
			WeiboCatagory[] list = WeiboCatagory.values();
			for(WeiboCatagory w:list){
				if(w.getName().equals(name))
					return w.getType();
			}
			return null;
		}
		public static List<String>getTypeArray(){
			List<String>typeList = new ArrayList<String>();
			WeiboCatagory[] list = WeiboCatagory.values();
			for(WeiboCatagory w:list){
				typeList.add(w.getType());
			}
			return typeList;
		}
		public static String getNameByUrl(String url){
			WeiboCatagory[] list = WeiboCatagory.values();
			for(WeiboCatagory w:list){
				String type = w.getType();
				if(url.contains(type)){
					return w.getName();
				}
			}
			return null;
		}
	}
	/**
	 * 锟斤拷锟斤拷 cookie
	 * @return
	 */
	public static String getCookie(){
		String cookie = WeiboConfig.getValue("cookie");
		return cookie;
	}
}
