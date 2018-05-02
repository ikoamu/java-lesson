package Q16;

/**
 * Java言語 プログラミングレッスン 下
 * p187 問題16-2
 * 
 * 「こんにちは！」というあいさつを1秒おきに表示するPrintHelloクラスと、
 *  それを実現するスレッドとなるLabelPrinterクラスを、List16-12のように
 *  して作ろうとしています。まだ誤りがあるので、実行結果がFig.16-17になる
 *  ように修正してください。
 *
 * List16-12 誤り修正前
 * 
 * class LabelPrinter {
 *   String label = "no label";
 *   LabelPrinter(String label) {
 *     this.label = label;
 *   }
 *   @Override
 *   public static void run() {
 *     while(true) {
 *       System.out.println(label);
 *       Thread.sleep(1000);
 *     }
 *   }
 * }
 * public class PrintHello {
 *   public static void main(String[] args) {
 *     LabelPrinter th = new LabelPrinter("こんにちは！");
 *     th.start();
 *   }
 * }
 * 
 * Fig.16-17 実行結果
 * 
 * こんにちは！
 * こんにちは！
 * こんにちは！
 * ・・・　←　Ctrl + c で止める
 * 
 */

class LabelPrinter extends Thread {
  String label = "no label";
  LabelPrinter(String label) {
    this.label = label;
  }
  @Override
  public void run() {
    while (true) {
      System.out.println(label);
      try {
      Thread.sleep(1000);
      }catch(InterruptedException e) {    
      }
    }
  }
}
public class PrintHello {
  public static void main(String[] args) {
    LabelPrinter th = new LabelPrinter("こんにちは！");
    th.start();
  }
}



  
  