package test;

/**
 * @author wincher
 * @date 2019-09-25
 * <p> test <p>
 */
public class TestAsert {
  
  public static void main(String[] args) {
    assert args.length > 1;
    System.out.println(args.length);
    System.out.println("ok");
  }
}
