/**
 * Java言語プログラミングレッスン 下
 * p65-66のList12-9
 * 継承とアクセス制御
 *
 */

public class List_12_9{
	
	public static void main(String[] args) {
		NamedRectangle nr = new NamedRectangle();
		nr.goodMethod();
	}

}
class Rectangle{	
	
	private	int	width;
	private int height;

	int getWidth() {
		return width;
	}
	int getHeight() {
		return height;
	}
	void setSize(int width,int height) {
		this.width = width;
		this.height = height;
	}
}


class NamedRectangle extends Rectangle{
	void goodMethod() {
		setSize(456,78);
		System.out.println(getHeight());
	}
}