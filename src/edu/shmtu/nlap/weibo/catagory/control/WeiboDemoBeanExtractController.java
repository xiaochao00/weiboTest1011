package edu.shmtu.nlap.weibo.catagory.control;

import java.io.IOException;
import java.util.List;

import edu.shmtu.nlap.weibo.catagory.beans.WeiboDemoBean;
import edu.shmtu.nlap.weibo.catagory.utils.CommonUtils;
import edu.shmtu.nlap.weibo.catagory.utils.FileOptionUtil;
import edu.shmtu.nlap.weibo.catagory.utils.WbDemoBeanExtractUtil;
import edu.shmtu.nlap.weibo.catagory.utils.WeiboConfig;

/**
 * 分类的微博数据 提取text字段 控制器
 * @author HP_xiaochao
 *
 */
public class WeiboDemoBeanExtractController {
	//抽取到的目录
	private static final String extractDirectory = WeiboConfig.getValue("extractWeiboTxtToDirectory");
	//提取的微博实体 存放的基目录
	private static final String weiboDemoBeanBaseDirectory = WeiboConfig.getValue("weiboDemoBeanBaseDirectory");
	/**
	 * 从微博实体目录中把text字段提取出来 保存到指定的文件中
	 * @throws IOException
	 */
	public static void extractText() throws IOException{
		List<String> catagoryTypeList = CommonUtils.WeiboCatagory.getTypeArray();
		if(catagoryTypeList!=null&&catagoryTypeList.size()>0){
			for(String catagoryType:catagoryTypeList){
				String wbSrcDirectory = weiboDemoBeanBaseDirectory+catagoryType;
				String wbToDirectory = extractDirectory+catagoryType;
				if(FileOptionUtil.hasFile(wbSrcDirectory)){
					List<WeiboDemoBean> wbBeanList = WbDemoBeanExtractUtil.readWeiboDemoBeanByDirectory(wbSrcDirectory);
					//保存
					WbDemoBeanExtractUtil.saveWBText(wbToDirectory, wbBeanList);
					
				}
			}
		}
	}
	
	public static void main(String[]args) throws IOException{
		extractText();
	}
}
