package thingkingInJava;

/**
 * @author wincher
 * @date   2017/6/21.
 * source:10.8.1闭包与回调
 */
//Using inner classes for callbacks

interface  Incrementable{
    void increment();
}

class Callee1 implements Incrementable{
    private int i = 0;
    public void increment(){
        i++;
        System.out.println(i);
    }
}

class MyIncrement {
    public void increment(){
        System.out.println("Other operator!");
    }
    static void f(MyIncrement mi){ mi.increment(); };
}

//if your class must implement increment() in some other way. you must use an inner class;
class Callee2 extends MyIncrement {
    private int i = 0;
    public void increment() {
        super.increment();
        i++;
        System.out.println(i);
    }
    private class Closure implements Incrementable {
        public void increment()  {
            Callee2.this.increment();
        }
    }
    Incrementable getCallbackReference() {
        return new Closure();
    }
}

class Caller {
    private Incrementable callbackReference;
    Caller(Incrementable cbh) { callbackReference = cbh; }
    void go() { callbackReference.increment(); }
}

public class InnerclassesCallbacks {
    public static void main(String[] args) {
        Callee1 c1 = new Callee1();
        Callee2 c2 = new Callee2();
        MyIncrement.f(c2);
        Caller caller1 = new Caller(c1);
        Caller caller2 = new Caller(c2.getCallbackReference());
        caller1.go();
        caller1.go();
        caller2.go();
        caller2.go();
    }

}
