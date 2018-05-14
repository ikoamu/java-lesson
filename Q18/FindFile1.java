package Q18;

/**
 * Java言語 プログラミングレッスン 下
 * p257 問題18-2
 * 
 * ファイル中に特定の文字列を含んでいるかどうかを調べる
 * プログラム FindFile1を作ってください。起動の時の引数は、
 *   java FindFile1 検索文字列　検索対象ファイル
 * のようにして、文字列が見つかったらその行場号と、
 * その行そのものを表示してください。
 * 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FindFile1 {
  public static void main (String[] args) {
    if (args.length != 2) {
      System.out.println("使用法 : java FindFile1 検索文字列 検索対象ファイル");
      System.exit(1);
    }
    
    String findString = args[0];
    String fileName = args[1];
    System.out.println("検索文字列は「" + findString + "」です");
    
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line;
      
      for (int linenum = 1; (line = reader.readLine()) != null; linenum++) {       
        if (line.contains(findString)) {
          System.out.println(linenum + " : " + line);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("ファイルが見つかりませんでした : " + fileName);
      System.exit(1);
    } catch (IOException e) {
      System.out.println(e);
      System.exit(1);
    }
  }
}
