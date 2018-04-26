package Q13;

/**
 * Java言語プログラミングレッスン 下 p100 問題13-3
 * 
 * List 13-3 (p83)の例外処理をmainメソッドからmyAssignメソッドに移してください。
 * すなわち、mainメソッドにはmyAssignメソッドの呼び出しだけ置き、 try文はmyAssignメソッド中におさめてください。
 *
 */
public class Q13_2 {
  public static void main(String[] args) {
    int[] myarray = new int[3];
    myAssign(myarray, 100, 0);
    System.out.println("終了します");
  }

  static void myAssign(int[] arr, int index, int value) {
    System.out.println("myAssignにきました");

    try {
      System.out.println("代入します");
      arr[index] = value;
      System.out.println("代入しました");
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("代入できませんでした");
      System.out.println("例外は" + e + "です");
    }

    System.out.println("myAssignから帰ります");
  }
}
