package edu.shmtu.nlap.weibo.catagory.utils;

import java.io.IOException;
import java.util.Date;


public class LogInfoUtil {
	public StringBuffer infoBuffer = new StringBuffer("");
	private static String directory = "D:/weibo/catagory/log";
	public void addInfo(String content){
		infoBuffer.append(content);
		infoBuffer.append("\r\n");
	}
	public void saveInfo() throws IOException{
		Date date = new Date();
		String dateStr = DateUtils.formatDateToString2(date);
		String filePath = directory+"/log_"+dateStr+".txt";
		ReadWriteFileWithEncode.writeByEncodie(filePath, infoBuffer.toString(), ReadWriteFileWithEncode.DEFAULT_ENCODE);
	}
	public void saveRequestInfo() throws IOException{
		Date date = new Date();
		String dateStr = DateUtils.formatDateToString2(date);
		String filePath = directory+"/request_"+dateStr+".txt";
		ReadWriteFileWithEncode.writeByEncodie(filePath, infoBuffer.toString(), ReadWriteFileWithEncode.DEFAULT_ENCODE);
	}
	public void saveError() throws IOException{
		Date date = new Date();
		String dateStr = DateUtils.formatDateToString2(date);
		String filePath = directory+"/error_"+dateStr+".txt";
		ReadWriteFileWithEncode.writeByEncodie(filePath, infoBuffer.toString(), ReadWriteFileWithEncode.DEFAULT_ENCODE);
	}
}
