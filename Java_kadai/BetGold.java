package Java_kadai;

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
