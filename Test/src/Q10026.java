import java.util.ArrayList;
import java.util.Scanner;

public class Q10026 {
	/*
	 * 백준 Q10026 적록색약
	 * https://www.acmicpc.net/problem/10026
	 */
	static int N;
	static int map[][];
	static int visit[][];
	static ArrayList<Object> sector;

	public static void main(String[] args) {
		String tmp;
		sector = new ArrayList<Object>();
		Scanner sc = new Scanner(System.in);

		/*
		 * init
		 */
		N = sc.nextInt();

		map = new int[N][N];
		visit = new int[N][N];

		for (int i = 0; i < N; i++) {
			tmp = sc.next();
			for (int j = 0; j < N; j++) {
				if (tmp.charAt(j) == 'B')
					map[i][j] = 1;
				else if (tmp.charAt(j) == 'G')
					map[i][j] = 2;
				else
					map[i][j] = 3;
			}
		}

		/*
		 * check() : trial
		 * BFS() : BFS 
		 * makeAbnormal() : make map to abnormal's sight
		 * print() : print num of sector (and init sector)
		 */
		check();
		print();
		System.out.print(" "); // add for answer format
		makeAbnormal();
		check();
		print();
	}

	static void check() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (visit[i][j] == 0) {
					BFS(i, j);
					sector.add(new Object());
				}
			}
		}
	}

	static void BFS(int i, int j) {
		visit[i][j] = 1;
		if (i != 0) { // going UP
			if (map[i][j] == map[i - 1][j]) {
				if (visit[i - 1][j] == 0) {
					BFS(i - 1, j);
				}
			}
		}

		if (i != N - 1) { // going DOWN
			if (map[i][j] == map[i + 1][j]) {
				if (visit[i + 1][j] == 0) {
					BFS(i + 1, j);
				}
			}
		}

		if (j != 0) { // going LEFT
			if (map[i][j] == map[i][j - 1]) {
				if (visit[i][j - 1] == 0) {
					BFS(i, j - 1);
				}
			}
		}

		if (j != N - 1) { // going RIGHT
			if (map[i][j] == map[i][j + 1]) {
				if (visit[i][j + 1] == 0) {
					BFS(i, j + 1);
				}
			}
		}

	}

	static void makeAbnormal() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 3)
					map[i][j] = 2;
			}
		}
	}

	static void print() {
		System.out.print(sector.size());
		sector.clear();
	visit = new int[N][N];
	}

}