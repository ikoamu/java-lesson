package test1;

/**
 * 九九の表を作るプログラムです。
 * @author aokis
 *
 */

public class Q6_4 {
	public static final int MAX_N = 9;
	public static void main(String[] arg) {
	for(int i = 1; i <= MAX_N;i++) {
		for(int j = 1; j <= MAX_N; j++) {
			System.out.print(i + " * "+ j +" = " + i*j + ", ");
		}
		System.out.print("\n");
	}
	}

}
