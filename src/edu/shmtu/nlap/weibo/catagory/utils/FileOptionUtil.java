package edu.shmtu.nlap.weibo.catagory.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
/**
 * 文件操作 工具类
 * @author HP_xiaochao
 *
 */
public class FileOptionUtil {
	public static boolean hasFile(String path){
		boolean flag = false;
		File file = new File(path);
		flag = file.exists();
		return flag;
	}
	public static void createFile(String path){
		if(!hasFile(path)){
			File file = new File(path);
			file.mkdir();
		}
	}
	/**
	 * 返回一个目录下的所有txt文件
	 * @param directory
	 * @return 文件列表
	 */
	public static List<File> getTXTFilesFromDirector(String directory){
		List<File> fileList = new ArrayList<File>();
		//
		File file = new File(directory);
		File[] fileArr = file.listFiles(new TxtFileAccept());
		if(fileArr!=null&&fileArr.length>0){
			for(File f:fileArr){
				fileList.add(f);
			}
		}
		//
		return fileList;
	}
}
/**
 * 给定 afternoon morning night 获取上下午晚上的微博文件，分类主题
 *
 */
class TxtFileAccept implements  FilenameFilter{
	String suffix = ".txt";
	public boolean accept(File dir,String name){
		return name.endsWith(suffix);
	}
}
