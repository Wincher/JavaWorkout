package test;

public class extendTest {
	public static void main(String[] args) {
		B b = (B)getA();
		System.out.println(b.getB());
		System.out.println(b.getA());
	}
	private static Parent getA() {
		Parent a = new B();
		a.setA(1);
		((B)a).setB(3);
		return a;
	}
}
class Parent {
	public int getA() {
		return a;
	}
	
	public void setA(int a) {
		this.a = a;
	}
	
	int a;
}

class B extends Parent {
	int b;
	
	public int getB() {
		return b;
	}
	
	public void setB(int b) {
		this.b = b;
	}
}
