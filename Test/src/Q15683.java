import java.util.Scanner;

public class Q15683 {
	/*
	 * 백준 Q15683 감시
	 * https://www.acmicpc.net/problem/15683
	 */
	static int N;
	static int M;
	static int map[][];
	static int trialMap[][]; // for trial
	static int minMap[][];
	static int minCombo[];
	static int min;
	static int numCCTV;

	// for makeCombo
	static int headCCTV[];

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		/*
		 * init
		 */
		min = Integer.MAX_VALUE;
		numCCTV = 0;
		N = sc.nextInt();
		M = sc.nextInt();
		map = new int[N][M];
		trialMap = new int[N][M];

		minMap = new int[N][M];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				map[i][j] = sc.nextInt();
				if (map[i][j] != 0 && map[i][j] != 6) {
					numCCTV++; // check number of CCTV
				}
			}
		}

		headCCTV = new int[numCCTV];
		minCombo = new int[numCCTV];

		/*
		 * makeCombo -> doTrial -> checkMin
		 */
		makeCombo();

		//print min
		System.out.println(min);

		/*
		 * for (int i = 0; i < N; i++) { for (int j = 0; j < M; j++) {
		 * System.out.print(minMap[i][j] + " "); } System.out.println(); }
		 * System.out.println(); for (int i = 0; i < numCCTV; i++) {
		 * System.out.print(minCombo[i] + " "); }
		 */

	}

	static void checkRight(int x, int y) {
		for (int i = x; i < M; i++) {
			if (trialMap[y][i] == 0)
				trialMap[y][i] = 7;
			else if (trialMap[y][i] == 6)
				break;
			else
				continue;
		}
	}

	static void checkLeft(int x, int y) {
		for (int i = x; i >= 0; i--) {
			if (trialMap[y][i] == 0)
				trialMap[y][i] = 7;
			else if (trialMap[y][i] == 6)
				break;
			else
				continue;
		}
	}

	static void checkTop(int x, int y) {
		for (int i = y; i >= 0; i--) {
			if (trialMap[i][x] == 0)
				trialMap[i][x] = 7;
			else if (trialMap[i][x] == 6)
				break;
			else
				continue;
		}
	}

	static void checkBottom(int x, int y) {
		for (int i = y; i < N; i++) {
			if (trialMap[i][x] == 0)
				trialMap[i][x] = 7;
			else if (trialMap[i][x] == 6)
				break;
			else
				continue;
		}
	}

	static void activateCCTV(int type, int headPosition, int x, int y) {
		switch (type) {
		case 1:
			switch (headPosition) {
			case 0:
				checkRight(x, y);
				break;
			case 1:
				checkBottom(x, y);
				break;
			case 2:
				checkLeft(x, y);
				break;
			case 3:
				checkTop(x, y);
			default:
				break;
			}
			break;
		case 2:
			switch (headPosition) {
			case 0:
				checkRight(x, y);
				checkLeft(x, y);
				break;
			case 1:
				checkBottom(x, y);
				checkTop(x, y);
				break;
			case 2:
				checkRight(x, y);
				checkLeft(x, y);
				break;
			case 3:
				checkBottom(x, y);
				checkTop(x, y);
			default:
				break;
			}
			break;
		case 3:
			switch (headPosition) {
			case 0:
				checkTop(x, y);
				checkRight(x, y);
				break;
			case 1:
				checkRight(x, y);
				checkBottom(x, y);
				break;
			case 2:
				checkBottom(x, y);
				checkLeft(x, y);
				break;
			case 3:
				checkLeft(x, y);
				checkTop(x, y);
			default:
				break;
			}
			break;
		case 4:
			switch (headPosition) {
			case 0:
				checkLeft(x, y);
				checkTop(x, y);
				checkRight(x, y);
				break;
			case 1:
				checkTop(x, y);
				checkRight(x, y);
				checkBottom(x, y);
				break;
			case 2:
				checkRight(x, y);
				checkBottom(x, y);
				checkLeft(x, y);
				break;
			case 3:
				checkBottom(x, y);
				checkLeft(x, y);
				checkTop(x, y);
			default:
				break;
			}
			break;
		case 5:
			checkRight(x, y);
			checkBottom(x, y);
			checkLeft(x, y);
			checkTop(x, y);
			break;
		default:
			break;
		}

	}

	static void doTrial() {
		int seqCCTV = 0;

		// init
		initTrialMap();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (trialMap[i][j] != 0 && trialMap[i][j] != 6 && trialMap[i][j] != 7) {
					activateCCTV(trialMap[i][j], headCCTV[seqCCTV], j, i);
					seqCCTV++;
				}
			}
		}

		checkMin();
	}

	static void makeCombo() {
		int calculate;

		for (int i = (int) (Math.pow(4, numCCTV)); i >= 0; i--) {
			calculate = i;

			for (int j = numCCTV - 1; j >= 0; j--) {
				if (calculate > (int) Math.pow(4, j)) {
					while (calculate > (int) Math.pow(4, j)) {
						headCCTV[j]++;
						calculate -= (int) Math.pow(4, j);
					}
					// decrease case
					if (calculate == 0) {
						break;
					}
				} else {
					continue;
				}
			}
			// do trial
			doTrial();

			// init headCCTV for next step
			headCCTV = new int[numCCTV];
		}
	}

	static void initTrialMap() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				trialMap[i][j] = map[i][j];
			}
		}
	}

	static void checkMin() {
		int blank = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (trialMap[i][j] == 0) {
					blank++;
				}
			}
		}

		if (min > blank) {
			min = blank;
			/*
			 * for (int i = 0; i < N; i++) { for (int j = 0; j < M; j++) { minMap[i][j] =
			 * trialMap[i][j]; } }
			 * 
			 * for(int i=0; i<numCCTV; i++) { minCombo[i] = headCCTV[i]; }
			 */
		}
	}
}
