package Q16;

/**
 * Java言語 プログラミングレッスン 下
 * p189 問題16-7
 * 
 * 問題16-6のクラスをRunnableインタフェースを実装して作ってください。
 * 
 * 問題16-6 Q16_6.java
 * 
 * public class Q16_6 {
 *   public static void main(String[] args) {
 *     new PrintAsterisk().start();
 *     new PrintEq().start();
 *    }
 * }
 * class PrintAsterisk extends Thread {
 *   @Override
 *   public void run() {
 *     for(int i = 0; i < 10; i++) {
 *       System.out.println("***");
 *       try {
 *         Thread.sleep(3000);
 *       }catch (InterruptedException e) {       
 *       }
 *     }
 *   }
 * }
 * class PrintEq extends Thread {
 *   @Override
 *   public void run() {
 *     for(int i = 0; i < 10; i++) {
 *       System.out.println("=====");
 *       try {
 *         Thread.sleep(5000);
 *       }catch (InterruptedException e) {       
 *       }
 *     }
 *   }
 * }
 * 
 */

public class Q16_7 {
  public static void main(String[] args) {
    PrintAsterisk2 printAsterisk = new PrintAsterisk2();
    PrintEq2 printEq = new PrintEq2();
    Thread threadAsterisk = new Thread(printAsterisk);
    Thread threadEq = new Thread(printEq);
    threadAsterisk.start();
    threadEq.start();
  }
}
class PrintAsterisk2 implements Runnable {
  @Override
  public void run() {
    for(int i = 0; i < 10; i++) {
      System.out.println("***");
      try {
        Thread.sleep(3000);
      }catch (InterruptedException e) {       
      }
    }
  }
}
class PrintEq2 implements Runnable {
  @Override
  public void run() {
    for(int i = 0; i < 10; i++) {
      System.out.println("=====");
      try {
        Thread.sleep(5000);
      }catch (InterruptedException e) {       
      }
    }
  }
}
