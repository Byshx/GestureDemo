package HandSigns;

public class Refinment { // Zhang并行快速细化算法

	private static int pixel = 60;
	private static int[][] Pindex = { { 7, 0, 1 }, { 6, -1, 2 }, { 5, 4, 3 } };

	public int[][] Calculate(int[][] vector) {
		boolean b[][] = new boolean[pixel][pixel];
		while (true) {
			boolean stop1 = true;
			boolean stop2 = true;

			for (int i = 0; i < pixel; i++) {
				for (int j = 0; j < pixel; j++) {

					if (vector[i][j] == 0)
						continue;
					if (FirstJudge(vector, i, j, b)) {
						b[i][j] = true;
						stop1 = false;
					}
				}
			}
			if (stop1 == false)
				for (int i = 0; i < pixel; i++) { // 去除点
					for (int j = 0; j < pixel; j++) {
						if (b[i][j] == true) {
							vector[i][j] = 0;
						}
						b[i][j] = false;
					}
				}

			for (int i = 0; i < pixel; i++) {
				for (int j = 0; j < pixel; j++) {
					if (vector[i][j] == 0)
						continue;
					if (SecondJudge(vector, i, j, b)) {
						b[i][j] = true;
						stop2 = false;
					}
				}
			}
			if (stop2 == false)
				for (int i = 0; i < pixel; i++) { // 去除点
					for (int j = 0; j < pixel; j++) {
						if (b[i][j] == true) {
							vector[i][j] = 0;
						}
						b[i][j] = false;
					}
				}
			if (stop1 && stop2)
				break;
		}
		for(int i=0;i<pixel;i++){
			for(int j=0;j<pixel;j++){
				System.out.print(vector[i][j]);
			}
			System.out.println();
		}
		return vector;
	}

	public static boolean FirstJudge(int[][] vector, int i, int j, boolean b[][]) {
		int B = 0;
		int A = 0;
		int P246 = 1;
		int P468 = 1;
		int[] p = new int[8];
		for (int n = i - 1; n <= i + 1; n++) {
			if (n < 0 || n >= pixel)
				continue;
			for (int m = j - 1; m <= j + 1; m++) {
				if (m == j && n == i)
					continue;
				if (m < 0 || m >= pixel) {
					continue;
				} else {
					if (vector[n][m] == 1) {
						B++;
						p[Pindex[n - i + 1][m - j + 1]] = 1;
					}
					if (n == i && m == j - 1)
						P246 *= vector[n][m];
					else if (n == i - 1 && m == j - 1)
						P468 *= vector[n][m];
					else if ((n == i + 1 && m == j) || (n == i && m == j + 1)) {
						P246 *= vector[n][m];
						P468 *= vector[n][m];
					}
				}
			}
		}
		int before = p[0];
		for (int k = 1; k < 8; k++) {
			if (before == 0 && p[k] == 1) {
				A++;
			}
			before = p[k];
		}
		if (before == 0 && p[0] == 1)
			A++;

		if (A == 1 && B >= 2 && B <= 6 && P246 == 0 && P468 == 0) {
			return true;
		}

		return false;
	}

	public static boolean SecondJudge(int[][] vector, int i, int j, boolean b[][]) {
		int B = 0;
		int A = 0;
		int P248 = 1;
		int P268 = 1;
		int[] p = new int[8];
		for (int n = i - 1; n <= i + 1; n++) {
			if (n < 0 || n >= pixel)
				continue;
			for (int m = j - 1; m <= j + 1; m++) {
				if (m == j && n == i)
					continue;
				if (m < 0 || m >= pixel) {
					continue;
				} else {
					if (vector[n][m] == 1) {
						B++;
						p[Pindex[n - i + 1][m - j + 1]] = 1;
					}
					if (n == i && m == j + 1)
						P248 *= vector[n][m];
					else if (n == i + 1 && m == j)
						P268 *= vector[n][m];
					else if ((n == i - 1 && m == j) || (n == i - 1 && m == j - 1)) {
						P248 *= vector[n][m];
						P268 *= vector[n][m];
					}
				}
			}
		}
		int before = p[0];
		for (int k = 1; k < 8; k++) {
			if (before == 0 && p[k] == 1) {
				A++;
			}
			before = p[k];
		}
		if (before == 0 && p[0] == 1)
			A++;
		if (A == 1 && B >= 2 && B <= 6 && P248 == 0 && P268 == 0)
			return true;
		return false;
	}
}
