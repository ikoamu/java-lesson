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
        DealResult dealResult = new Deal(bet, pocket, gameclearGold).start(input);
        int prize = dealResult.prize();
        pocket += prize;

        if (dealResult.isWin()) {
          System.out.println("賞金" + prize + "Gを獲得");
        }

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
   * @param input
   *          : コンソール入力用BufferedReader
   * @return ベット額
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

  private class Deal {
    private final int bet; // ベット額
    private final int pocket; // プレイヤーの所持金
    private final int gameclearGold; // ゲームクリアの条件額

    public Deal(int bet, int pocket, int gameclearGold) {
      this.bet = bet;
      this.pocket = pocket;
      this.gameclearGold = gameclearGold;
    }

    private DealResult start(BufferedReader input) throws IOException {
      Random random = new Random();

      int firstNumber = random.nextInt(13) + 1; // はじめの数字
      System.out.println("はじめの数字は" + firstNumber + "です");

      Forecast answer = (Forecast) new ForecastSelector().ask(input);

      int secondNumber = random.nextInt(13) + 1; // 2回目の数字
      System.out.println("2回目の数字は" + secondNumber + "でした");

      int prize = answer.checkAnswer(bet, firstNumber, secondNumber);
      DealResult dealResult = new DealResult(prize);

      if (dealResult.isWin()) {
        System.out.println(prize + "Gの勝ち");
      } else {
        System.out.println("まけ");
      }

      if (checkContinue(prize, input, dealResult.isWin())) {
        System.out.println("BET額" + prize + "Gで続行");
        return new Deal(prize, pocket, gameclearGold).start(input);
      } else {
        return dealResult;
      }
    }

    /**
     * 再帰を続けるかどうか判定するメソッド
     * 
     * ゲームに勝って、なおかつ所持金と賞金の合計がgameclearGoldよりも少ない場合のみ
     * askContinue()を呼び出し、再起するかどうかを選択できる
     */
    private boolean checkContinue(int prize, BufferedReader input, boolean playerWin) throws IOException {
      String message1 = "このまま続けますか？";
      String message2 = "現在の賞金 : " + prize;

      return prize + pocket < gameclearGold && playerWin
          && ((Continuance) (new ContinuanceSelector().ask(input, message1, message2)) == Continuance.KEEP);
    }
  }

  private class DealResult {
    int prize;
    boolean playerWin;

    public DealResult(int prize) {
      this.prize = prize;
      this.playerWin = checkWin();
    }

    private boolean checkWin() {
      if (prize != 0) {
        return true;
      } else {
        return false;
      }
    }

    private int prize() {
      return prize;
    }

    private boolean isWin() {
      return playerWin;
    }
  }

  abstract class Selector {
    abstract UserSelect ask(BufferedReader input, String... preMessages) throws IOException;

    abstract UserSelect[] values();

    abstract UserSelect from(String string);
  }

  private class ContinuanceSelector extends Selector {
    @Override
    UserSelect ask(BufferedReader input, String... preMessages) throws IOException {
      UserSelect continuance = null;

      while (continuance == null) {
        for (String message : preMessages) {
          System.out.println(message);
        }

        System.out.print(Stream.of(values()).map(String::valueOf).collect(Collectors.joining(" ", " ", " : ")));
        continuance = from(input.readLine());

        if (continuance == null) {
          System.out.println(Stream.of(Continuance.values()).map(r -> r.number)
              .collect(Collectors.joining(", ", "!!", "のいずれかの数字を入力してください。!!")));
          System.out.println("もう一度入力してください。");
        }
      }

      System.out.println("あなたの選択 : " + continuance);
      return continuance;
    }

    @Override
    UserSelect[] values() {
      return Continuance.values();
    }

    @Override
    UserSelect from(String string) {
      return Continuance.from(string);
    }
  }

  private class ForecastSelector extends Selector {
    @Override
    UserSelect ask(BufferedReader input, String... preMessages) throws IOException {
      UserSelect answer = null;

      while (answer == null) {
        for (String message : preMessages) {
          System.out.println(message);
        }

        System.out.print(Stream.of(values()).map(String::valueOf).collect(Collectors.joining(" ", " ", " : ")));
        answer = from(input.readLine());

        if (answer == null) {
          System.out.println(Stream.of(Forecast.values()).map(f -> f.number)
              .collect(Collectors.joining(", ", "!!", "のいずれかの数字を入力してください。!!")));
          System.out.println("もう一度入力してください。");
        }
      }

      System.out.println("あなたの選択 : " + answer);
      return answer;
    }

    @Override
    UserSelect[] values() {
      return Forecast.values();
    }

    @Override
    UserSelect from(String string) {
      return Forecast.from(string);
    }
  }

  private enum Continuance implements UserSelect {
    STOP("0", "NO"), KEEP("1", "YES");

    private final String number;
    private final String message;

    private Continuance(final String number, String message) {
      this.number = number;
      this.message = message;
    }

    @Override
    public String toString() {
      return message + "[" + number + "]";
    }

    static Continuance from(String string) {
      for (Continuance continuance : Continuance.values()) {
        if (string.equals(continuance.number)) {
          return continuance;
        }
      }

      return null;
    }
  }

  private enum Forecast implements UserSelect {
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
}
