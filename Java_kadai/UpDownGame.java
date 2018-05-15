package Java_kadai;

/**
 * アップダウンゲーム
 * 
 * 1. プレイヤーは所持金として「10万G」持っています。
 * 
 * 2. 所持金を「50万G」まで増やすか、「0G」になったらゲーム終了です。
 * 
 * 3. ゲームが始まったら、プレイヤーがベットする金額を入力します。
 * 　 - 一度にベットできる金額は「3万G」までとします。
 * 　 - 所持金以上の金額をベットすることはできません。
 * 
 * 4. 1〜13までの数字がランダムに表示され、プレイヤーは次に表示される
 * 　 数字が現状の数字よりも大きい(UP)か小さいか(DOWN)か、同じか(SAME)かを選択します。
 * 
 * 5. UPもしくはDOWNで正解した場合にはベットした金額が二倍に、SAMEで正解した場合には
 * 　 5倍になります。プレイヤーは上がった金額でゲームを続けるか、再度ベットし直すかを
 * 　 選択します。不正解の場合には、ベットした金額は没収され、再びベットする金額を
 * 　 入力します。
 * 
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Random;

public class UpDownGame {
  private final static int GAMEOVER_GOLD = 0; //ゲームオーバーになる金額
  private final static int GAMECLEAR_GOLD = 500_000; //ゲームクリアになる金額
  private final static int INITIAL_GOLD = 100_000; // 最初の所持金
  
  public static void main(String[] args) {
    int pocket = INITIAL_GOLD; //プレイヤーの所持金を設定
    System.out.println("ゲームスタート（所持金 : " + pocket + "G");
    
    try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in))){
      while (true) {
        int betGold = decideBetGold(pocket,input); //ベット額を決める 
        pocket -= betGold; //所持金からベット額を没収
        System.out.println("----------------------------");
        System.out.println("ベット額 : " + betGold + "G");
        System.out.println("現在の所持金 : " + pocket + "G");
        System.out.println("----------------------------");
      
        pocket += playGame(betGold,input); //ゲームに勝った賞金がプラスされる(負けた場合は0)
          
        if (pocket <= GAMEOVER_GOLD) { //所持金がまだあるかのチェック
          System.out.println("++++++++++++++++++++++++++++");
          System.out.println("所持金がなくなりました。");
          break;
        }
      
        if (pocket >= GAMECLEAR_GOLD) { //目標金額に到達したかチェック
          System.out.println("++++++++++++++++++++++++++++");
          System.out.println("所持金が " + pocket + "Gになりました。");
          System.out.println("ゲームクリア");
          break;
        }

        System.out.println("現在の所持金は" + pocket + "Gです。");
      }  
    }catch (IOException e) {
      System.out.println(e);
      System.exit(1);
    }
    
    System.out.println("++++++++++++++++++++++++++++");    
    System.out.println("GAME OVER");
    System.out.println("++++++++++++++++++++++++++++"); 
  } 

  private final static int MAX_BET_G = 30_000; //ベット額の上限
  
  /**
   * ベット額を決めるメソッド
   * 正しい値を入力するまでdecideBetGを再帰呼出し
   * 
   * @param pocket : 現在の所持金
   * @param input : コンソール入力用BufferedReader
   * @return bet : ベット額
   * @throws IOException : 整数以外の値を入力した場合
   */
  static int decideBetGold(int pocket, BufferedReader input) throws IOException {
    int bet = 0; 
 
    System.out.println("ベット額を入力してください。");
    System.out.println("(1度にベットできるのは" + MAX_BET_G +"Gまでです)");
      
    try {
      bet = Integer.parseInt(input.readLine());   
      
      if(MAX_BET_G < bet) {
        System.out.println("!!" + MAX_BET_G +"G以下の金額を入力してください。!!");
        bet = decideBetGold(pocket, input);
      }else if (pocket < bet) {
        System.out.println("!!ベット額が所持金を超えています。!!");
        bet = decideBetGold(pocket, input);
      }else if (bet < 0) {
        System.out.println("!!ベット額がマイナスです。!!");
        bet = decideBetGold(pocket, input);
      }
    }catch (NumberFormatException e) {
      System.out.println("!!整数以外が入力されました。!!");
      bet = decideBetGold(pocket, input);
    }
      return bet; 
  }
  
  static int selectAnswer(BufferedReader input) throws IOException{
    while (true) {       
        System.out.print("-> DOWN[0]  SAME[1]  UP[2] :");
        String answer = input.readLine();
   
        if("0".equals(answer)) { //プレイヤーはDOWNを選択
          System.out.println("あなたの選択 -> DOWN[0]");
          return 0;
        } else if ("1".equals(answer)) { //プレイヤーはSAMEを選択
          System.out.println("あなたの選択 -> SAME[1]");
          return 1;
        } else if ("2".equals(answer)) { //プレイヤーはUPを選択
          System.out.println("あなたの選択 -> UP[2]");
          return 2;
        } else { //0,1,2以外を入力、もう一度入力させる
          System.out.println("!! 0, 1, 2のいずれかの数字を入力してください。!!");
        }       
    }
  }
  
  /**
   * はじめに1〜13までの数字をランダムに表示し、
   * 次に表示される数字がはじめの数字より大きい(UP)か小さい(DOWN)か同じ(SAME)かを
   * プレイヤーに選択させるメソッドです。
   * 
   * プレイヤーは0,1,2のいずれかの数字を入力することでUP,DOWN,SAMEを選択します。
   * UP = 2, DOWN = 0, SAME = 1
   * 
   * 正解すると獲得した賞金がreturnされます。不正解の場合は0がreturnされます。
   * 
   * また、賞金を全額ベットしてゲームを続行する場合は、賞金を引数としてgetGを
   * 再帰呼出しして、もう一度ゲームをします。
   * 
   * @param bet : ベット額
   * @param input : コンソール入力用BufferedReader
   * @return prize : 獲得賞金(不正解の場合は0)
   * @throws IOException : 整数以外の値を入力した場合
   */
  static int playGame(int bet, BufferedReader input) throws IOException{ 
    int prize = 0; 
 
    Random random = new Random();
    int firstNumber = random.nextInt(13) + 1; //はじめの数字
    System.out.println("-> はじめの数字は" + firstNumber +"です");
   
    int answer = selectAnswer(input);
    
    int secondNumber = random.nextInt(13) + 1;
    System.out.println("-> 2回目の数字は" + secondNumber +"でした"); // 2回目の数字
    int result = secondNumber - firstNumber; 
    /* result > 0 : 結果はUP
     * result == 0 : 結果はSAME
     * result < 0 : 結果はDOWN
     */
    
    System.out.println("");
    
    if(result == 0 && answer == 1) { //SAMEで正解する
      prize = bet * 5; 
      System.out.println("結果 -> おめでとうございます　[SAME]");
      
    }else if(result > 0 && answer == 2) { //UPで正解する
      prize = bet * 2;
      System.out.println("結果 -> おめでとうございます　[UP]");
      
    }else if(result < 0 && answer == 0) {//DOWNで正解する
      prize = bet * 2;
      System.out.println("結果 -> おめでとうございます　[DOWN]");
      
    }else { //不正解
      System.out.println("結果 -> 負け");
      System.out.println("");
      return 0;
    }
    
    System.out.println("-> " + prize + "Gの勝ち");
    
    /*賞金を全額ベットして続行するか、賞金を獲得するか選択*/
    if(askContinue(prize,input) == true) {
      System.out.println("*********************");
      System.out.println("BET額" + prize + "Gで続行");
      prize = playGame(prize, input);
    } else {
      System.out.println("*********************");
      System.out.println("賞金" + prize + "Gを獲得");
    }
    
    return prize;
  }
  
  /**
   * ゲームに勝った後に、獲得した賞金でそのままゲームを続けるか
   * 獲得賞金を手に入れ、再度ベットをし直すかを決める
   * 続ける場合 true、降りる場合はfalseを返す。
   * 
   * @param newBetG : 前回の獲得賞金(次のベット額)
   * @param input : コンソール入力用BufferedReader
   * @return trueの場合はゲーム続行、falseの場合は再度賞金獲得
   * @throws IOException : 整数以外の値を入力した場合
   */
  static boolean askContinue(int newBetG, BufferedReader input) throws IOException {
    boolean again = false;
    
    System.out.println("このまま続けますか？");
    System.out.println("現在の賞金 : " + newBetG);
    System.out.println("*******************");
    System.out.print("いいえ[0] はい[1] : ");
    
    try {
      int answer = Integer.parseInt(input.readLine());
      
      if(answer == 1) {
        again = true;  
      } else if(answer == 0) {
        again = false;
      } else {
        System.out.println("!!0または1を入力してください。!!");
        again = askContinue(newBetG, input);
      }
    } catch (NumberFormatException e) {
      System.out.println("!!整数以外が入力されました。!!");
      again = askContinue(newBetG, input);
    }

    return again;
  }
}
