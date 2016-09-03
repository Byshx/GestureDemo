package HandSigns;

import java.awt.Robot;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.AWTException;

public class MouseAcceleration {

	private Robot robot;// 自动化对象

	public MouseAcceleration() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void JudgeMove() { // 鼠标移动函数
		Timer timer = new Timer();
		TimeTask task = new TimeTask();
		timer.schedule(task, new Date(), 1);
	}

	public static void main(String args[]) throws Exception {
		MouseAcceleration mmc = new MouseAcceleration();
		mmc.JudgeMove();
	}

	class TimeTask extends TimerTask {

		Point pointerInfo = MouseInfo.getPointerInfo().getLocation();

		int x = (int) pointerInfo.getX();
		int y = (int) pointerInfo.getY();
		int beforeX = x;
		int beforeY = y;
		int[] array = new int[2];
		int count = 0;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Point pointerInfo2 = MouseInfo.getPointerInfo().getLocation();
			int X = (int) pointerInfo2.getX();
			int Y = (int) pointerInfo2.getY();

			if (x != X || y != Y) {
				count++;
				System.out.println(X + " " + Y + " " + count);
				if (count == 20) {
					array[0] = X - x;
					array[1] = Y - y;
					System.out.println(array[0] + " " + array[1]);
					System.gc();
					this.cancel();
				}
			}
			beforeX = X;
			beforeY = Y;
		}
	}
}