package test1;

public class Q8_2 {
	public static final int MAX_N = 8;
	
	public static void printGraph(int x) {
		for(int i = 0; i < x;i++) {
			System.out.print("*");
		}
		System.out.println("");
	}
	public static void main(String[] arg) {
		for(int i = MAX_N; i >= 0; i--) {
			printGraph(i*i);
		}
		for(int i = 0; i <= MAX_N; i++) {
			printGraph(i*i);
		}
	}
}
