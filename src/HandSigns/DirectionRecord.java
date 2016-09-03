package HandSigns;

public class DirectionRecord {
	static int x1, y1, x2, y2;

	public void setFirst(int X1, int Y1) {
		x1 = X1;
		y1 = Y1;
	}

	public void setEnd(int X2, int Y2) {
		x2 = X2;
		y2 = Y2;
	}

	public int DealDirection() {
		float a = x2 - x1;
		float b = y2 - y1;
		if (a == 0) {
			if (b > 0)
				return 5;
			else
				return 1;
		} else if (a > 0) {
			if (b / a > 3.732)
				return 1;
			else if (b / a < 3.732 && b / a > 0.466)
				return 2;
			else if (b / a < 0.466 && b / a > -0.466)
				return 3;
			else if (b / a < -0.466 && b / a > -3.732)
				return 4;
			else {
				return 5;
			}
		} else {
			if (b / a > 3.732)
				return 5;
			else if (b / a < 3.732 && b / a > 0.466)
				return 6;
			else if (b / a < 0.466 && b / a > -0.466)
				return 7;
			else if (b / a < -0.466 && b / a > -3.732)
				return 8;
			else {
				return 1;
			}
		}
	}
}
