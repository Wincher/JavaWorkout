package cn.wincher.my_reactor;

import cn.wincher.my_reactor.entity.Father;
import cn.wincher.my_reactor.entity.GrandSon;
import cn.wincher.my_reactor.entity.Son;

/**
 * @author wincher
 * <p> cn.wincher.MyReactor <p>
 */
public class Client {
    public static void main(String[] args) {
        creatSubscribeDemo();
        mapDemo();
    }

    private static void mapDemo() {
        Observable.create((Observable.OnSubscribe<Integer>) subscriber ->  {
            for (int i = 0; i < 10; i++) {
                subscriber.onNext(i);
            }
        }).map(from -> "mapping: " + from).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void onError(Throwable t) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });
    }

    private static void creatSubscribeDemo() {
        //onNext T为 Son,由于void call(Subscriber<? super T> subscriber);使用了super， 所以在使用的时候要使用Son及其子类
        Observable.create((Observable.OnSubscribe<Son>) subscriber -> subscriber.onNext(new GrandSon("XiaoXiaoLee")))
                //而public void subscribe(Subscriber<? super T> subscriber)， Subscribe本身是 ？ super T， 泛型为 Son或其父类
            .subscribe(new Subscriber<Father>() {
                @Override
                public void onCompleted() {
                    throw new UnsupportedOperationException();
                }
                @Override
                public void onError(Throwable t) {
                    throw new UnsupportedOperationException();
                }
                @Override
                public void onNext(Father f) {
                    System.out.println(f);
                }
        });
    }
}
