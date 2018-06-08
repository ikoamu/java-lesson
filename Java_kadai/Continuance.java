package Java_kadai;

enum Continuance implements UserSelect {
  STOP("0", "NO"), KEEP("1", "YES");

  final String number;
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