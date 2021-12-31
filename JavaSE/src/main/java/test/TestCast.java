package test;

public class TestCast {
	public static void main(String[] args) {
		A a = new A();
		a.id = 1;
		a.b = 2;
		B b;
//		b = (B)a;
		
	}
	
	static class A {
		int id;
		int b;
	}
	
	static class B {
		int b;
	}
}
