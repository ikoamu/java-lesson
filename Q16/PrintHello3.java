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
 *   
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
    LabelPrinter goodmorningPrinter = new LabelPrinter("おはよう！");
    LabelPrinter goodafternoonPrinter = new LabelPrinter("こんにちは！");
    LabelPrinter goodeveningPrinter = new LabelPrinter("こんばんは！");
    goodmorningPrinter.start();
    goodafternoonPrinter.start();
    goodeveningPrinter.start();
  }
}
