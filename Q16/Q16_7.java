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
 *         Thread.sleep(5000);
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
 *         Thread.sleep(10000);
 *       }catch (InterruptedException e) {       
 *       }
 *     }
 *   }
 * }
 * 
 */

public class Q16_7 {
  public static void main(String[] args) {
    PrintAsterisk2 p_as = new PrintAsterisk2();
    PrintEq2 p_eq = new PrintEq2();
    Thread t_as = new Thread(p_as);
    Thread t_eq = new Thread(p_eq);
    t_as.start();
    t_eq.start();
  }
}
class PrintAsterisk2 implements Runnable {
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
class PrintEq2 implements Runnable {
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
