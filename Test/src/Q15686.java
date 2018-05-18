import java.util.*;

class Q15686 {
	/*
	 * 백준 Q15686 치킨배당
	 * https://www.acmicpc.net/problem/15686
	 */
	static int N;
	static int M;
	static int[][] map;
	static ArrayList<Point> chicken;
	static ArrayList<Point> person;
	static int[] output;
	static boolean[] visited;
	static int res;
	static Scanner sc;

	public static void main(String[] args) throws Exception {
		sc = new Scanner(System.in);

		N = sc.nextInt();
		M = sc.nextInt();

		map = new int[N][N];
		res = Integer.MAX_VALUE;
		chicken = new ArrayList<Point>();
		person = new ArrayList<Point>();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = sc.nextInt();
				if (map[i][j] == 1) {
					person.add(new Point(i, j));
				} else if (map[i][j] == 2) {
					chicken.add(new Point(i, j));
				}
			}
		}

		visited = new boolean[chicken.size()];

		output = new int[chicken.size()];

		for (int i = 0; i < chicken.size(); i++) {
			visited[i] = true;
			makeCombo(i, 0);
			visited[i] = false;
		}
		System.out.println(res);
	}

	static void makeCombo(int start, int depth) {
		output[depth] = start + 1;

		for (int i = start; i < chicken.size(); i++) {
			if (visited[i]) {
				continue;
			}
			visited[i] = true;
			makeCombo(i, depth + 1);
			visited[i] = false;
		}

		if (depth == M - 1) {
			int sum = 0;
			int currentM = 0;

			for (int i = 0; i < person.size(); i++) {
				int min = Integer.MAX_VALUE;
				for (int j = 0; j < M; j++) {
					currentM = calDistance(person.get(i), chicken.get(output[j] - 1));
					min = Math.min(min, currentM);
				}

				sum += min;
			}

			res = Math.min(res, sum);
		}
	}

	static int calDistance(Point p1, Point p2) {
		return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
	}
}

class Point {
	int x;
	int y;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}