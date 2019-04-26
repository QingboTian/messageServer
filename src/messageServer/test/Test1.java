package messageServer.test;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Test1 {
	public static void main(String[] args) {
		 /**
         * 指定触发的时间      现在指定时间为   2019年04月24号  21点  32 分 01 秒时触发
         */
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 24);// 设置日期为27号
		calendar.set(Calendar.MONTH, 3);// 设置日期为11月份   这里10表示11月份    11就表示12月份
		calendar.set(Calendar.HOUR_OF_DAY, 21); // 设置15点的时候触发
		calendar.set(Calendar.MINUTE, 32); // 设置43分钟的时候触发
		calendar.set(Calendar.SECOND, 1); // 设置第一秒的时候触发

		Date time = calendar.getTime();
		Timer timer = new Timer();
		timer.schedule(new RemindTask(), time);
	}
}

class RemindTask extends TimerTask {

	public void run() {
		System.out.println("你指定2013-11-27号15:34:01分执行已经触发！");
	}

}
