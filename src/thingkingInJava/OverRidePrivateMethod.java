package thingkingInJava;
/**
 * Created by Wincher on 2017/5/18.
 * 出处: 8.2.4 缺陷:"覆盖"私有方法
 * describe:在java方法中要继承private方法时不被允许的，会被当做final处理，对导出类屏蔽， 注意使用父类调用不到覆盖的private方法，如果需要记得加
 *  /@Override 注解，会在编译前提示错误
 */
public class OverRidePrivateMethod {
    private void privateMethod(){ System.out.println("the private method!"); }
    public static void main(String[] args) {
        OverRidePrivateMethod orm = new Derived();
        orm.privateMethod();
        Derived d = new Derived();
        d.privateMethod();
    }
}
class Derived extends OverRidePrivateMethod {
    void privateMethod() {
        System.out.println("the override method!");
    }
}
