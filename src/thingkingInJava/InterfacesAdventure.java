package thingkingInJava;

/**
 * Created by Wincher on 2017/6/20.
 * source: 9.4:java中的多继承
 *
 */
public class InterfacesAdventure {
    public static void t (CanFight x) {x.fight();}
    public static void u (CanSwim x) {x.swim();}
    public static void v (CanFly x) {x.fly();}
    public static void w (ActionCharacter x) {x.fight();}

    public static void main(String[] args) {
        Hero h = new Hero();
        t(h);
        u(h);
        v(h);
        w(h);
    }
}
interface CanFight{ void fight(); }
interface CanSwim{ void swim(); }
interface CanFly{ void fly(); }
class ActionCharacter{ public void fight(){
    System.out.println("a");
} }
class Hero extends ActionCharacter implements CanFight, CanFly, CanSwim {
    @Override
    public void swim() {}
    @Override
    public void fly() {}
    @Override
    public void fight() {
        System.out.println("b");
    }
}
