package edu.shmtu.nlap.weibo.catagory.utils;

public class SleepUtils {
	/**
	 * 等待时间 毫秒单位
	 * @param l
	 */
	public static void sleep(long l){
		try {
			Thread.sleep(l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
