package Java_kadai;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class Selector {
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

  abstract UserSelect[] values();

  abstract UserSelect from(String string);
}

class ContinuanceSelector extends Selector {
  @Override
  UserSelect[] values() {
    return Continuance.values();
  }

  @Override
  UserSelect from(String string) {
    return Continuance.from(string);
  }
}

class ForecastSelector extends Selector {
  @Override
  UserSelect[] values() {
    return Forecast.values();
  }

  @Override
  UserSelect from(String string) {
    return Forecast.from(string);
  }
}