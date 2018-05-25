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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

  public void play() {
    System.out.println("ゲームスタート（所持金 : " + pocket + "G");

    try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {
      while (!isFinish()) {
        int bet = decideBetGold(input); // ベット額を決める
        pocket -= bet; // 所持金からベット額を没収
        System.out.println("現在の所持金 : " + pocket + "G");
        System.out.println("----------------------------");

        pocket += deal(bet, input); // ゲームに勝った賞金がプラスされる(負けた場合は0)
        System.out.println("現在の所持金は" + pocket + "Gです。");
      }
    } catch (IOException e) {
      System.out.println(e);
      System.exit(1);
    }
  }

  /**
   * ゲーム終了判定
   * 
   * @return trueの場合ゲーム終了、falseの場合ゲーム継続
   */
  private boolean isFinish() {
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

  /**
   * ベット額を決めるメソッド
   * 
   * @param pocket
   *          : 現在の所持金
   * @param input
   *          : コンソール入力用BufferedReader
   * @return bet : ベット額
   * @throws IOException
   *           : 整数以外の値を入力した場合
   */
  private int decideBetGold(BufferedReader input) throws IOException {

    System.out.println("ベット額を入力してください。");
    System.out.println("(1度にベットできるのは" + maxBetGold + "Gまでです)");
    BetGold betgold = new BetGold(input.readLine(), maxBetGold, pocket);

    while (!betgold.checkValidity()) {
      System.out.println(betgold.getMessage());
      System.out.println("もう一度ベット額を入力してください。");
      betgold = new BetGold(input.readLine(), maxBetGold, pocket);
    }

    System.out.println(betgold.getMessage());
    return betgold.getBet();
  }

  private enum Forecast {
    DOWN("0") {
      @Override
      int checkAnswer(int bet, int firstNumber, int secondNumber) {
        return (firstNumber > secondNumber) ? bet * 2 : 0;
      }
    },

    SAME("1") {
      @Override
      int checkAnswer(int bet, int firstNumber, int secondNumber) {
        return (firstNumber == secondNumber) ? bet * 5 : 0;
      }
    },

    UP("2") {
      @Override
      int checkAnswer(int bet, int firstNumber, int secondNumber) {
        return (firstNumber < secondNumber) ? bet * 2 : 0;
      }
    };

    private final String number;

    private Forecast(final String number) {
      this.number = number;
    }

    abstract int checkAnswer(int bet, int firstNumber, int secondNumber);

    @Override
    public String toString() {
      return this.name() + "[" + number + "]";
    }

    static Forecast from(String string) {
      for (Forecast forecast : Forecast.values()) {
        if (string.equals(forecast.number)) {
          return forecast;
        }
      }

      return null;
    }
  }

  private int deal(int bet, BufferedReader input) throws IOException {
    Random random = new Random();
    int firstNumber = random.nextInt(13) + 1; // はじめの数字
    System.out.println("-> はじめの数字は" + firstNumber + "です");

    Forecast answer = selectForecast(input);

    int secondNumber = random.nextInt(13) + 1;
    System.out.println("-> 2回目の数字は" + secondNumber + "でした"); // 2回目の数字

    int prize = answer.checkAnswer(bet, firstNumber, secondNumber);

    boolean playerWin = isWin(prize); // プレイヤーの勝敗

    if (playerWin) {
      System.out.println("-> " + prize + "Gの勝ち");
    } else {
      System.out.println("-> まけ");
    }

    if (checkContinue(prize, input, playerWin)) {
      System.out.println("BET額" + prize + "Gで続行");
      return deal(prize, input);
    } else {
      if (playerWin) {
        System.out.println("賞金" + prize + "Gを獲得");
      }

      return prize;
    }
  }

  private boolean isWin(int prize) {
    if (prize != 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 再帰を続けるかどうか判定するメソッド
   * 
   * ゲームに勝って、なおかつ所持金と賞金の合計がgameclearGoldよりも少ない場合のみ
   * askContinue()を呼び出し、再起するかどうかを選択できる
   */
  private boolean checkContinue(int prize, BufferedReader input, boolean playerWin) throws IOException {
    boolean continueFlag = false;

    if (prize + pocket < gameclearGold && playerWin) {
      continueFlag = askContinue(prize, input);
    }

    return continueFlag;
  }

  private Forecast selectForecast(BufferedReader input) throws IOException {
    Forecast answer = null;

    while (answer == null) {
      System.out
          .print(Stream.of(Forecast.values()).map(String::valueOf).collect(Collectors.joining(" ", "-> ", " : ")));
      answer = Forecast.from(input.readLine());

      if (answer == null) {
        System.out.println(Stream.of(Forecast.values()).map(f -> f.number)
            .collect(Collectors.joining(", ", "!!", "のいずれかの数字を入力してください。!!")));
        System.out.println("もう一度入力してください。");
      }
    }

    System.out.println("あなたの選択 : " + answer);
    return answer;
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
    String reply = null;

    while (!isValidReply(reply)) {
      System.out.print("いいえ[0] はい[1] : ");
      reply = input.readLine();
      if (!isValidReply(reply)) {
        System.out.println("!! 0, 1 いずれかの数字を入力してください。!!");
        System.out.println("もう一度入力してください。");
      }
    }

    if ("1".equals(reply)) {
      return true;
    }

    return false;
  }

  private boolean isValidReply(String reply) {
    if ("1".equals(reply) || "0".equals(reply)) {
      return true;
    }

    return false;
  }
}

class BetGold {
  final private int bet;
  private boolean isValid;
  private String message;

  public BetGold(String bet, int maxBetGold, int pocket) {
    int tmpBet = 0;
    try {
      tmpBet = Integer.parseInt(bet);
      if (maxBetGold < tmpBet) {
        this.message = "!!" + maxBetGold + "G以下の金額を入力してください。!!";
        this.isValid = false;
      } else if (pocket < tmpBet) {
        this.message = "!!ベット額が所持金を超えています。!!";
        this.isValid = false;
      } else if (tmpBet < 0) {
        this.message = "!!ベット額がマイナスです。!!";
        this.isValid = false;
      } else {
        this.message = "ベット額 : " + tmpBet + "G";
        this.isValid = true;
      }
    } catch (NumberFormatException e) {
      this.message = "!!整数以外が入力されました。!!";
      this.isValid = false;
    } finally {
      this.bet = tmpBet;
    }
  }

  public int getBet() {
    return bet;
  }

  public boolean checkValidity() {
    return isValid;
  }

  public String getMessage() {
    return message;
  }
}
