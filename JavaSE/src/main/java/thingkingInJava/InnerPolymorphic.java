package thingkingInJava;
/**
 * @author wincher
 * @date   2017/6/20.
 *  出处: 8.3.3 缺陷:"覆盖"私有方法
 * describe:InnerPolymorphic.draw()方法设计为要被覆盖，这种覆盖是在ExtendClass中发生的，但是InnerPolymorphic构造器调用这
 * 个方法的时候，导致了ExtendClass.draw()的调用而mark并不是初始值1，而是0，这是因为在初始化过程中，在其他任何事物发生之
 * 前，分配给对象的存储空间初始化成二进制零，由于调用InnerPolymorphic构造器前ExtendClass还未初始化，所以mark为0
 *  所以才构造器初始化阶段尽量不要对子类覆盖方法进行操作，这种错误编译器不会报错，构造器可以安全调用的是基类中的private
 *  和final方法，这些方法是不可覆盖的不会出现上述问题.
 */
public class InnerPolymorphic {
    void draw(){ System.out.println("InnerPolymorphic draw()"); }

    public InnerPolymorphic() {
        System.out.println("before draw()");
        draw();
        System.out.println("after draw()");
    }

    public static void main(String[] args) {
        new ExtendClass(5);
    }
}
class ExtendClass extends InnerPolymorphic{
    private int mark = 1;

    public ExtendClass(int mark) {
        this.mark = mark;
        System.out.println("ExtendClass.ExtendClass(), mark = " + mark);
    }

    @Override
    void draw() {
        System.out.println("ExtendClass.draw(), mark = " + mark);
    }
}
