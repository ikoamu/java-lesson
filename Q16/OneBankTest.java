package Q16;

/**
 * Java言語 プログラミングレッスン 下
 * p191 問題16-11
 * 
 * List16-6のGoodBankTestは、GoodBankのインスタンスを生成して、
 * 預金の預入と引出しを行いました。これを変更して銀行口座がたった
 * 一つしかないプログラムにしてください。クラスの名前はOneBankTestと
 * OneBankにすること。
 * 
 * ヒント:OneBankのフィールドやメソッドをstaticにします。
 * 　　　 それに合わせてOneBankTestを作成してください。
 * 
 * List16-6 GoodBankTest.java
 * public class GoodBankTest extends Thread {
 *   GoodBank bank;
 *   pulic GoodBankTest(Good bank) {
 *     this.bank = bank;
 *   }
 *   @Override
 *   public void run() {
 *     while (true) {
 *       //100円預入
 *       bank.addMoney(100);
 *       //100円引出し
 *       bank.addMoney(-100);
 *     }
 *   }
 *   public static void main(String[] args) {
 *     GoodBank bank = new GoodBank(); // GoodBankを生成
 *     new GoodBankTest(bank).start();
 *     new GoodBankTest(bank).start();
 *   }
 * }
 * 
 * List16-5 Good Bank.java
 * public class GoodBank {
 *   //預金残高
 *   private int value = 0;
 *   //預入・引出し
 *   public synchronized void addMoney(int money) {
 *     //現在残高を保存
 *     int currentValue = value;
 *     //状況を表示
 *     System.out.println(Thread.currentThread() + "が addMoneyに入りました。");
 *     //現在残高を変更
 *     value += money;
 *     //矛盾が無いかどうかチェック
 *     if(currentValue + money != value) {
 *       System.out.println(Thread.currentThread() + "で矛盾が発生しました。");
 *       System.exit(-1);
 *     }
 *     //状況を表示
 *     System.out.println(Thread.currentThread() + "が addMoneyから出ました。");
 *   }
 * }  
 * 
 */

public class OneBankTest extends Thread {
  public static void main(String[] args) {
     new OneBankTest().start();
     new OneBankTest().start();
  }
  @Override
  public void run() {
    while(true) {
      OneBank.addMoney(100);
      OneBank.addMoney(-100);
    }
  }
}

class OneBank {
  //預金残高
  private static int value = 0;
  //預入・引出し
  public static synchronized void addMoney(int money) {
    //現在残高を保存
    int currentValue = value;
    //状況を表示
    System.out.println(Thread.currentThread() + "が addMoney に入りました。");
    //現在残高を変更
    value += money;
    //矛盾が無いかどうかチェック
    if(currentValue + money != value) {
      System.out.println(Thread.currentThread() + "で矛盾が発生しました。");
      System.exit(-1);
    }
    //状況を表示
    System.out.println(Thread.currentThread() + "が addMoney から出ました。");
  }
}
