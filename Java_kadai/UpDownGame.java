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
  private final static int GAMEOVER_GOLD = 0; // ゲームオーバーになる金額
  private final static int GAMECLEAR_GOLD = 500_000; // ゲームクリアになる金額
  private final static int INITIAL_GOLD = 100_000; // 最初の所持金
  private final static int MAX_BET_GOLD = 30_000; // ベット額の上限

  private final int gameoverGold;
  private final int gameclearGold;
  private int pocket; // プレイヤーの所持金
  private final int maxBetGold;

  public UpDownGame() {
    gameoverGold = GAMEOVER_GOLD;
    gameclearGold = GAMECLEAR_GOLD;
    pocket = INITIAL_GOLD;
    maxBetGold = MAX_BET_GOLD;
  }

  public UpDownGame(int gameoverGold, int gameclearGold, int initialGold, int maxBetGold) {
    this.gameoverGold = gameoverGold;
    this.gameclearGold = gameclearGold;
    this.pocket = initialGold;
    this.maxBetGold = maxBetGold;
  }

  public static void main(String[] args) {
    new UpDownGame(GAMEOVER_GOLD, GAMECLEAR_GOLD, INITIAL_GOLD, MAX_BET_GOLD).play();
  }

  /**
   * ゲーム終了判定
   * 
   * @return trueの場合ゲーム終了、falseの場合ゲーム継続
   */
  private boolean isGameOver() {
    if (pocket >= gameclearGold) {
      System.out.println("所持金が" + gameclearGold + "Gに到達しました。");
      System.out.println("ゲームクリア");
      return true;
    }

    if (pocket <= gameoverGold) {
      System.out.println("所持金が" + gameoverGold + "G以下になりました。");
      System.out.println("ゲームオーバー");
      return true;
    }

    return false;
  }

  public void play() {
    System.out.println("ゲームスタート（所持金 : " + pocket + "G");

    try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {
      while (!isGameOver()) {
        int betGold = decideBetGold(input); // ベット額を決める
        pocket -= betGold; // 所持金からベット額を没収
        System.out.println("----------------------------");
        System.out.println("ベット額 : " + betGold + "G");
        System.out.println("現在の所持金 : " + pocket + "G");
        System.out.println("----------------------------");

        pocket += deal(betGold, input); // ゲームに勝った賞金がプラスされる(負けた場合は0)
        System.out.println("現在の所持金は" + pocket + "Gです。");
      }
    } catch (IOException e) {
      System.out.println(e);
      System.exit(1);
    }
  }

  /**
   * ベット額を決めるメソッド
   * 
   * @param pocket
   *          : 現在の所持金
   * @param input
   *          : コンソール入力用BufferedReader
   * @return bet : ベット額
   * @throws IOException
   *           k* : 整数以外の値を入力した場合
   */
  private int decideBetGold(BufferedReader input) throws IOException {
    String line = null;

    while (!isValid(line)) {
      System.out.println("ベット額を入力してください。");
      System.out.println("(1度にベットできるのは" + maxBetGold + "Gまでです)");
      line = input.readLine();
    }

    int bet = Integer.parseInt(line);

    return bet;
  }

  /**
   * 入力したベット額が正しいか確かめるメソッド
   * 
   * 正しいベッド額は 正の整数かつ、pocketとMAX_BET_GOLDよりも小さい値である
   * 
   * @param pocket
   *          : プレイヤーの所持金
   * @param line
   *          : プレイヤーから入力された文字列
   * @return : ベット額が正しければtrue、正しくなければfalse
   */

  private boolean isValid(String line) {
    if (line == null) {
      return false;
    }

    try {
      int bet = Integer.parseInt(line);

      if (maxBetGold < bet) {
        System.out.println("!!" + maxBetGold + "G以下の金額を入力してください。!!");
        return false;
      }

      if (pocket < bet) {
        System.out.println("!!ベット額が所持金を超えています。!!");
        return false;
      }

      if (bet < 0) {
        System.out.println("!!ベット額がマイナスです。!!");
        return false;
      }

      return true;
    } catch (NumberFormatException e) {
      System.out.println("!!整数以外が入力されました。!!");
      return false;
    }
  }

  /**
   * プレイヤーの回答を読み込むメソッドです。
   * 
   * プレイヤーは0,1,2のいずれかの数字を入力することでUP,DOWN,SAMEを選択します。 UP = 2, DOWN = 0, SAME = 1
   * 
   * @param input
   * @return プレイヤーの回答 : UP = 2, DOWN = 0, SAME = 1
   * @throws IOException
   */

  private int selectAnswer(BufferedReader input) throws IOException {
    while (true) {
      System.out.print("-> DOWN[0]  SAME[1]  UP[2] :");
      String answer = input.readLine();

      if ("0".equals(answer)) { // プレイヤーはDOWNを選択
        System.out.println("あなたの選択 -> DOWN[0]");
        return 0;
      } else if ("1".equals(answer)) { // プレイヤーはSAMEを選択
        System.out.println("あなたの選択 -> SAME[1]");
        return 1;
      } else if ("2".equals(answer)) { // プレイヤーはUPを選択
        System.out.println("あなたの選択 -> UP[2]");
        return 2;
      } else { // 0,1,2以外を入力、もう一度入力させる
        System.out.println("!! 0, 1, 2のいずれかの数字を入力してください。!!");
      }
    }
  }

  /**
   * はじめに1〜13までの数字をランダムに表示し、 次に表示される数字がはじめの数字より大きい(UP)か小さい(DOWN)か同じ(SAME)
   * のいずれかを予想します。
   * 
   * 正解すると獲得した賞金がreturnされます。不正解の場合は0がreturnされます。
   * 
   * また、賞金を全額ベットしてゲームを続行する場合は、賞金を引数としてgetGを 再帰呼出しして、もう一度ゲームをします。
   * 
   * @param bet
   *          : ベット額
   * @param input
   *          : コンソール入力用BufferedReader
   * @return prize : 獲得賞金(不正解の場合は0)
   * @throws IOException
   *           : 整数以外の値を入力した場合
   */
  private int deal(int bet, BufferedReader input) throws IOException {
    int prize = 0;

    Random random = new Random();
    int firstNumber = random.nextInt(13) + 1; // はじめの数字
    System.out.println("-> はじめの数字は" + firstNumber + "です");

    int answer = selectAnswer(input);

    int secondNumber = random.nextInt(13) + 1;
    System.out.println("-> 2回目の数字は" + secondNumber + "でした"); // 2回目の数字
    int result = secondNumber - firstNumber;
    /*
     * result > 0 : 結果はUP result == 0 : 結果はSAME result < 0 : 結果はDOWN
     */

    System.out.println("");

    if (result == 0 && answer == 1) { // SAMEで正解する
      prize = bet * 5;
      System.out.println("結果 -> おめでとうございます　[SAME]");

    } else if (result > 0 && answer == 2) { // UPで正解する
      prize = bet * 2;
      System.out.println("結果 -> おめでとうございます　[UP]");

    } else if (result < 0 && answer == 0) {// DOWNで正解する
      prize = bet * 2;
      System.out.println("結果 -> おめでとうございます　[DOWN]");

    } else { // 不正解
      System.out.println("結果 -> 負け");
      System.out.println("");
      return 0;
    }

    System.out.println("-> " + prize + "Gの勝ち");

    /* 賞金を全額ベットして続行するか、賞金を獲得するか選択 */
    if (askContinue(prize, input) == true) {
      System.out.println("*********************");
      System.out.println("BET額" + prize + "Gで続行");
      prize = deal(prize, input);
    } else {
      System.out.println("*********************");
      System.out.println("賞金" + prize + "Gを獲得");
    }

    return prize;
  }

  /**
   * ゲームに勝った後に、獲得した賞金でそのままゲームを続けるか 獲得賞金を手に入れ、再度ベットをし直すかを決める 続ける場合
   * true、降りる場合はfalseを返す。
   * 
   * @param newBetG
   *          : 前回の獲得賞金(次のベット額)
   * @param input
   *          : コンソール入力用BufferedReader
   * @return trueの場合はゲーム続行、falseの場合は再度賞金獲得
   * @throws IOException
   *           : 整数以外の値を入力した場合
   */
  private boolean askContinue(int newBetG, BufferedReader input) throws IOException {
    System.out.println("このまま続けますか？");
    System.out.println("現在の賞金 : " + newBetG);
    System.out.println("*******************");

    while (true) {
      System.out.print("いいえ[0] はい[1] : ");
      String answer = input.readLine();

      if ("1".equals(answer)) {
        return true;
      } else if ("0".equals(answer)) {
        return false;
      } else {
        System.out.println("!! 0, 1 いずれかの数字を入力してください。!!");
      }
    }
  }
}