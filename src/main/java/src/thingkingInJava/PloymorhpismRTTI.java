package thingkingInJava;
/**
 * Created by Wincher on 2017/6/20.
 * source: 8.5.2 向下转型与运行时类型识别
 */
public class PloymorhpismRTTI {
    public static void main(String[] args) {
        Useful[] x = {new Useful(), new MoreUseful()};
        x[0].f();
        x[1].g();
        ((MoreUseful) x[1]).v();
        ((MoreUseful) x[0]).u();
    }
}

class Useful {
    public void f() {}
    public void g() {}
}

class MoreUseful extends Useful {
    @Override
    public void f() {}
    @Override
    public void g() {}
    public void u() {}
    public void v() {}


}