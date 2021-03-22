package PECS;

import PECS.entity.Father;
import PECS.entity.GrandSon;
import PECS.entity.Son;

import java.util.ArrayList;
import java.util.List;

/**
 * shows why Producer Extends and Consumer Super
 *
 * @author wincher
 * <p> PECS <p>
 */
public class ListGenericTypesDemo {
    public static void just_a_demo(String[] args) {
        //lines that commented occur compile error
        List<Son> sons = new ArrayList<>();
        sons.add(new Son());
        //as you can see, when using specify Class only itself and subclasses are valid.
//        sons.add(new Father());
        sons.add(new GrandSon());
        Father f = sons.get(0);
        Son s = sons.get(0);
//        GrandSon  g = sons.get(0);

        //when use extends of class which means
        List<? extends Son> extendsSons = new ArrayList<>();
//        extendsSons.add(new Son());
//        extendsSons.add(new Father());
//        extendsSons.add(new GrandSon());
        Father f2 = extendsSons.get(0);
        Son s2 = extendsSons.get(0);
//        GrandSon g2 = extendsSons.get(0);

        List<? super Son> superSons = new ArrayList<>();
//        superSons.add(new Father());
        superSons.add(new Son());
        superSons.add(new GrandSon());

//        Father f3 = superSons.get(0);
//        Son s3 = superSons.get(0);
//        GrandSon g3 = superSons.get(0);
        Object o = superSons.get(0);
    }

}
