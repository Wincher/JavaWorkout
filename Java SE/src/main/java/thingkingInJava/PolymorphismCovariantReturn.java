package thingkingInJava;
/**
 * @author wincher
 * @date   2017/6/20.
 * 出处: 8.4协变返回类型
 * describe:JAVA SE5中添加了协变返回类型,他表示在导出类中的被覆盖方法可以返回基类方法的返回值类型的某种导出类型
 */
public class PolymorphismCovariantReturn {
    public static void main(String[] args) {
        Mill m = new Mill();
        Grain g = m.process();
        System.out.println(g);
        m = new WheatMill();
        g = m.process();
        System.out.println(g);

    }
}
class Grain{
    public String toString(){return "Grain";}
}
class Wheat extends Grain{
    public String toString(){return "Wheat";}
}
class Mill{
    public Grain process(){return new Grain();}
}
class WheatMill extends Mill{
    public Wheat process(){return new Wheat();}
}
