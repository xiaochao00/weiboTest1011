package edu.shmtu.nlap.weibo.catagory.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.shmtu.nlap.weibo.catagory.beans.WeiboDemoBean;
import edu.shmtu.nlap.weibo.catagory.utils.CommonUtils;
import edu.shmtu.nlap.weibo.catagory.utils.ReadWriteFileWithEncode;
import edu.shmtu.nlap.weibo.catagory.utils.WeiboConfig;

public class WBDemoBeanUtil {
	public static int[] saveWBDemoBeanList(List<WeiboDemoBean> wbList) throws IOException{
		int []lineArr = new int[2];
		int line = 0;//添加个数
		int sameline = 0;//重复 个数
		if(wbList!=null&wbList.size()>0){
			for(WeiboDemoBean w:wbList){
				StringBuffer content = new StringBuffer();
				
				String uid = w.getUid();//
				String nickname = w.getNickname();//
				String wid = w.getWid();//
				String date = w.getDate();//
				String text = w.getText();//
				String cataory = w.getCatagory();//
				
				content.append("uid:"+uid);
				content.append(" \r\n");
				content.append("nickname:"+nickname);
				content.append("\r\n");
				content.append("wid:"+wid);
				content.append("\r\n");
				content.append("date:"+date);
				content.append("\r\n");
				content.append("text:"+text);
				content.append("\r\n");
				content.append("cataory:"+cataory);
				String catagoryType = CommonUtils.WeiboCatagory.getTypeByName(cataory);
				if(catagoryType!=null){
//					String weiboDemoBeanBaseDirectory = WeiboConfig.getValue("weiboDemoBeanBaseDirectory");
//					String directory = weiboDemoBeanBaseDirectory+catagoryType;
//					if(!hasFile(directory))
//						createFile(directory);
					String filename = getWbBeanFilename(cataory,wid);
					if(hasFile(filename))
						sameline++;
					else{
						ReadWriteFileWithEncode.writeByEncodie(filename, content.toString(), ReadWriteFileWithEncode.DEFAULT_ENCODE);
						line++;
					}
				}else{
					System.out.println("没有当前微博类别的 type");
				}
			}
		}else{
			System.out.println("保存的微博文件数目为空或0");
		}
		lineArr[0] = line;
		lineArr[1] = sameline;
		return lineArr;
	}
	public static String getWbBeanFilename(String catagory,String mid){
		String catagoryType = CommonUtils.WeiboCatagory.getTypeByName(catagory);
		String directory = WeiboConfig.getValue("weiboDemoBeanBaseDirectory")+catagoryType;
		if(!hasFile(directory))
			createFile(directory);
		String filename = directory+"/"+mid+".txt";
		return filename;
	}
	private static boolean hasFile(String path){
		boolean flag = false;
		File file = new File(path);
		flag = file.exists();
		return flag;
	}
	private static void createFile(String path){
		if(!hasFile(path)){
			File file = new File(path);
			file.mkdir();
		}
	}
}
