package Q16;

/**
 * Java言語 プログラミングレッスン 下
 * p188 問題16-3
 * 
 * 全問の解答のLabelPrinterを使って、
 * 「おはよう！」「こんにちは！」「こんばんは！」
 * をそれぞれ表示するスレッド3つを動かすクラス
 * PrintHello3を宣言してください。
 * 
 * 問題16-2 LabelPrinter
 * 
 * class LabelPrinter extends Thread {
 *   String label = "no label";
 *   LabelPrinter(String label) {
 *     this.label = label;
 *   }
 *   @Override
 *   public void run() {
 *     while (true) {
 *       System.out.println(label);
 *       try {
 *       Thread.sleep(1000);
 *       }catch(InterruptedException e) {    
 *       }
 *     }
 *   }
 * }
 *
 */

public class PrintHello3 {
  public static void main(String[] args) {
    LabelPrinter th1 = new LabelPrinter("おはよう！");
    LabelPrinter th2 = new LabelPrinter("こんにちは！");
    LabelPrinter th3 = new LabelPrinter("こんばんは！");
    th1.start();
    th2.start();
    th3.start();
  }
}
