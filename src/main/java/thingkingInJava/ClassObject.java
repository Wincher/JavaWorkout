package thingkingInJava;


/**
 * Created by wincher on 16/08/2017.
 * 出处: 14.2 Class对象
 * describe:要理解RTTI在Java中的工作原理，首先必须知道类型信息在运行时如何表示的。
 * 这项工作是由成为Class对象的特殊对象完成的，它包含了与类相关的信息。
 * 事实上，Class对象就是用来创建类的所有的"常规"对象的。
 * Java使用Class对象来执行其RTTI，即使你正在执行的是类似转型这样的操作。
 * Class类还拥有大量的使用RTTI的其它方式。
 */
class Candy {
    static { System.out.println("Loading Candy");}
}
class Gum {
    static { System.out.println("Loading Gum");}
}
class Cookie {
    static { System.out.println("Loading Cookie");}
}
public class ClassObject {
    public static void main(String[] args) {
        System.out.println("inside main");
        new Candy();
        System.out.println("After creating Candy");
        try {
            Class.forName("Gum");
        } catch (ClassNotFoundException e) {
            System.out.println("Couldn't find Gum");
        }
        System.out.println("After Class.forName(\"Gum)\"");
        new Cookie();
        System.out.println("After Creating Cookie");
    }
}
