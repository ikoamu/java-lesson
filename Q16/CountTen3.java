package Q16;

/**
 * Java言語 プログラミングレッスン 下
 * p188 問題16-5 
 * 
 * List 16-1(CountTenA.java)では、runメソッドを実行しているのは
 * 1つのスレッドでした。新たなスレッドを1つだけ生成するのではなく、
 * 3個生成して動作するようにプログラムを書き換えてください。
 * 
 * List 16-1 CountTenA.java
 * 
 * public class CountTenA extends Thread {
 *   public static void main(String[] args) {
 *     CountTenA ct = new CountTenA();
 *     ct.start();
 *     for(int i = 0; i < 10; i++){
 *       System.out.println("main:i = " + i);
 *     }
 *   }
 *   
 *   @Override
 *   public void run(){
 *     for(int i = 0; i < 10; i++){
 *       System.out.println("run:i = " + i);
 *     }
 *   }
 * }
 */

public class CountTen3 extends Thread {
  public static void main(String[] args) {
    for(int i = 0; i < 3; i++) {
      new CountTen3().start();
    }
    
    for(int i = 0; i < 10; i++) {
      System.out.println("main:i = " + i);
    }
  }
  
  @Override
  public void run() {
    for(int i = 0; i < 10; i++ ) {
      System.out.println(Thread.currentThread().getName() + ":i =" + i);
    }
  }
}
