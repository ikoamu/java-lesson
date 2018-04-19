package test1;

import java.io.*;

/**
 * Q3_3はユーザが入力した文字列の各文字の文字コードを表示するプログラムです。
 * @author aokis
 */

public class Q3_3 {
	public static void main(String[] args) {
		System.out.println("Input any string");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String line = reader.readLine();
			int stlen = line.length(); //文字列の長さ
			char c;
	
			for(int i=0;i < stlen;i++) {
					c = line.charAt(i);
					System.out.println("'"+c+"'の文字コードは"+(int)c+"です。");
				}	
			}catch(IOException e) {
				System.out.println(e);
			}
		
		System.out.println("ty");
	}

}		
