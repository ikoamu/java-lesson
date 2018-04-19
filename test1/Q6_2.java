package test1;

public class Q6_2 {
	public static final int MAX_N = 9;
	
	public static void main(String[] arg) {
		int nn; //i^2
		for(int i = 0; i < MAX_N; i++) {
			nn = i*i;
			System.out.print(i+":");
			for(int j = 0; j < nn; j++) {
				System.out.print("*");
			}
			System.out.print("\n");
		}
	}
	
}
