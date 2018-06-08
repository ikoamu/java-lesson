package Java_kadai;

enum Forecast implements UserSelect {
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

  final String number;

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