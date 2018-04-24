package Q12;
/**
 * Java言語プログラミングレッスン 下
 * p74問題12-4
 * [問題分]
 * 次の機能を持つPlaceRectangleを、Rectangleのサブクラスとして宣言してください。
 * - 位置を示すint型のフィールドx, yを持つ 
 * - 3つのコンストラクタを持つ
 * 　1.引数なし　2.位置付き 3.位置と大きさ付き
 * - 位置を変更するメソッドsetLocationを持つ 
 * - 標準的な文字列表現を返すメソッドtoStringを持つ
 */
public class Q12_4 {
	public static void main(String[] args) {
		PlaceRectangle a, b, c;
	  a = new PlaceRectangle();
		b = new PlaceRectangle(1,2);
		c = new PlaceRectangle(1,2,3,4);
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		System.out.println("c = " + c);
	}
}

class Rectangle{
	int width;
	int height;	
	Rectangle(){ //コンストラクタ(引数なし)
		setSize(0,0);
	}
	Rectangle(int width,int height){ //コンストラクタ(大きさ付き)
		setSize(width,height);
	}		
	void setSize(int width,int height) {
		this.width = width;
		this.height = height;
	}
	@Override
	public String toString() {
		return ("["+width+", "+height+"]");
	}
}
class PlaceRectangle extends Rectangle{
  int x;
  int y;
  PlaceRectangle(){ //コンストラクタ(引数なし)
  	setSize(0,0);
  	setLocation(0,0);
  }
  PlaceRectangle(int x,int y){//コンストラクタ(位置付き)
  	setSize(0,0);
  	setLocation(x,y);
  }
  PlaceRectangle(int x,int y,int width,int height){//コンストラクタ(位置と大きさ付き)
  	setSize(width,height);
  	setLocation(x,y);
  }
  void setLocation(int x,int y) {
  	this.x = x;
  	this.y = y;
  }
  @Override
  public String toString() {
  	return "[ (" + x + ", " + y + ") ["+width+", "+height+"] ]";
  }
}
