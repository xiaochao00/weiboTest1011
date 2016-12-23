package edu.shmtu.nlap.weibo.catagory.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.shmtu.nlap.weibo.catagory.beans.WeiboDemoBean;

public class WbDemoBeanExtractUtil {
	/**
	 * 从指定的目录中 提取出 目录下所有文件存储的 微博对象
	 * @param directory
	 * @return
	 */
	public static List<WeiboDemoBean> readWeiboDemoBeanByDirectory(String directory){
		List<WeiboDemoBean> wbList = new ArrayList<WeiboDemoBean>();
		//
		List<File> wbFileList = FileOptionUtil.getTXTFilesFromDirector(directory);
		if(wbFileList.size()>0){
			for(File file:wbFileList){
				List<String> textList = ReadWriteFileWithEncode.readlinesByEncode(file,ReadWriteFileWithEncode.DEFAULT_ENCODE);
				String uid = "";//�û����
				String nickname = "";//�ǳ�
				String wid = "";//΢��id��
				String date = "";//
				String text = "";//΢������
				String catagory = "";//���
				if(textList!=null&&textList.size()>0){
					for(String content:textList){
						int contentLength = content.length();
						if(content.startsWith("uid"))
							uid = content.split(":")[1];
						if(content.startsWith("wid"))
							wid = content.split(":")[1];
						if(content.startsWith("nickname"))
							nickname = content.substring(content.indexOf("nickname")+9, contentLength);
						if(content.startsWith(date))
							date = content.substring(content.indexOf("date")+5,contentLength);
						if(content.startsWith("text"))
							text = content.substring(content.indexOf("text")+5,contentLength);
						if(content.startsWith("catagory"))
							catagory = content.substring(content.indexOf("catagory")+9,contentLength);
					}
				}
				//
				WeiboDemoBean wbBean = new WeiboDemoBean();
				wbBean.setCatagory(catagory);
				wbBean.setDate(date);
				wbBean.setNickname(nickname);
				wbBean.setText(text);
				wbBean.setUid(uid);
				wbBean.setWid(wid);
				//
				wbList.add(wbBean);
			}
		}
		//
		return wbList;
	}
	/**
	 * 根据指定的目录和 微博实体列表 保存提取出来的txt字段文件
	 * @param toDirectory
	 * @param wbBeanList
	 * @throws IOException
	 */
	public static void saveWBText(String toDirectory,List<WeiboDemoBean> wbBeanList) throws IOException{
		
		if(!FileOptionUtil.hasFile(toDirectory)){
			FileOptionUtil.createFile(toDirectory);
		}
		//
		if(wbBeanList!=null&&wbBeanList.size()>0){
			for(WeiboDemoBean wbBean:wbBeanList){
				String fileName = toDirectory+"/"+wbBean.getWid()+".txt";
				String content = wbBean.getText();
				if(FileOptionUtil.hasFile(fileName))
					continue;
				ReadWriteFileWithEncode.writeByEncodie(fileName, content, ReadWriteFileWithEncode.DEFAULT_ENCODE);
			}
		}
	}
}
