package thingkingInJava;
/**
 * @author wincher
 * @date   2017/6/20.
 * source: 9.8 嵌套接口
 */
class A{
    interface B{
        void f();
    }
    public class BImp implements B{
        public void f(){}
    }
    private class BImp2 implements B{
        public void f(){}
    }
    interface C{
        void f();
    }
    class CImp implements C {
        public void f(){}
    }
    public class CImp2 implements C {
        public void f(){}
    }
    private interface D{
        void f();
    }
    private class DImp implements D {
        public void f(){}
    }
    public class DImp2 implements D {
        public void f() {}
    }
    public D getD() { return new DImp2(); }
    private D dRef;
    public void ReveivdD(D d){
        dRef = d;
        dRef.f();
    }
}
interface E {
    interface G{
        void f();
    }
    //redundant "public"
    public interface H{
        void f();
    }
    void g();

}
public class NestingInterfaces {
    public class BImp implements A.B {
        public void f(){}
    }
    class CImp implements A.C{
        public void f(){}
    }
    //private Interface 不能被实现
    //class Dimp implements A.D{public void f() {}}
    class EImp implements E {
        public void g() {}
    }
    class EGImp implements E.G{
        public void f() {}
    }
    class EImp2 implements E{
        public void g() {}
        class EG implements G{
            @Override
            public void f() {}
        }
    }
    public static void main(String[] args) {
        A a = new A();
        A a2 = new A();
        a2.ReveivdD(a.getD());

    }
}

