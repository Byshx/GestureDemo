package HandSigns;

import java.util.ArrayDeque;

public class DirectionChanged {
	private static int checkNumber = 40;
	private static float checkSucceed = 1.0f;
	private static ArrayDeque<Integer> queueX = new ArrayDeque<Integer>();
	private static ArrayDeque<Integer> queueY = new ArrayDeque<Integer>();
	private static DirectionRecord directionRecord = null;
	private static float k1 = 0;
	private static String strike = "";

	public void Together(DirectionRecord directionRecord) {
		DirectionChanged.directionRecord = directionRecord;
	}

	public void setLocation(int x, int y) {
		if (!queueX.isEmpty()) {
			if (x != queueX.getLast() || y != queueY.getLast()) {
				queueX.add(x);
				queueY.add(y);
				DirectionJudge();
			}
		} else {
			queueX.add(x);
			queueY.add(y);
			DirectionJudge();
		}
	}

	public static void DirectionJudge() {
		if (queueX.size() == checkNumber) {
			int startX = queueX.pollFirst();
			int startY = queueY.pollFirst();
			int endX = queueX.peekLast();
			int endY = queueY.peekLast();
			if (endX - startX != 0) {
				float k2 = (float) (Math.abs(endY - startY) / Math.abs(endX - startX));
				float tan = Math.abs((k1 - k2) / (1 + k1 * k2));
				k1 = k2;
				if (tan >= checkSucceed) {
					directionRecord.setEnd(endX, endY);
					strike += directionRecord.DealDirection();
					directionRecord.setFirst(endX, endY);
				}
			}
		}
	}

	public String getStrike() {
		return strike;
	}

	public void clearStrike() {
		DirectionChanged.strike = "";
	}
}
