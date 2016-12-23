package edu.shmtu.nlap.weibo.catagory.control;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.htmlparser.util.ParserException;
/**
 * 定时任务类
 * @author HP_xiaochao
 *
 */
public class MyTimerTaskController {
	
	public static void main(String []args){
		Timer timer = new Timer();
		timer.schedule(new MyTask(), 10000, 4800000);//10秒后执行 隔80分钟执行一次
	}
	
	static class MyTask extends TimerTask{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				WBParseController.doSomethmore();
			} catch (ParserException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
