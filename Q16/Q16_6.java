package Q16;

/**
 * Java言語 プログラミングレッスン 下
 * p188 問題16-6 
 * 
 * 約3秒ごとに"***"を10回表示するスレッドと、
 * 約5秒ごとに"====="を10回表示するスレッドを、
 * それぞれ1つずつ動かすクラスをThreadクラスの
 * 拡張クラスとして作ってください。
 * 
 */

public class Q16_6 {
  public static void main(String[] args) {
    new PrintAsterisk().start();
    new PrintEq().start();
  }
}
class PrintAsterisk extends Thread {
  @Override
  public void run() {
    for(int i = 0; i < 10; i++) {
      System.out.println("***");
      try {
        Thread.sleep(5000);
      }catch (InterruptedException e) {       
      }
    }
  }
}
class PrintEq extends Thread {
  @Override
  public void run() {
    for(int i = 0; i < 10; i++) {
      System.out.println("=====");
      try {
        Thread.sleep(10000);
      }catch (InterruptedException e) {       
      }
    }
  }
}
