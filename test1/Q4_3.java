package test1;

import java.io.*;

/**
 * Q_4_3は、現在の時刻(「時」だけでよい)をキーボードで入力すると、
 * その時間に応じた挨拶をするプログラムです
 * @author aokis
 *
 */

public class Q4_3 {
	public static void main(String[] args) {
			System.out.println("現在の時刻を「時」のみで入力してください。");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				String line = reader.readLine();
				int T = Integer.parseInt(line);
				if(T < 0) {
					System.out.println("時間の範囲を超えています。");
				}else if(T < 12) {
					System.out.println("おはようございます。");
				}else if(T == 12) {
					System.out.println("お昼です。");
				}else if(T < 19) {
					System.out.println("こんにちは。");
				}else if(T < 25) {
					System.out.println("こんばんは。");
				}else {
					System.out.println("時間の範囲を超えています。");
				}
			}catch(IOException e) {
				System.out.println(e);
			}catch(NumberFormatException e) {
				System.out.println("入力が正しくありません。");
			}
	}
}
