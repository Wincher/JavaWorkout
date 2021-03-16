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
        Observable.create(new Observable.OnSubscribe<Son>() {
            @Override
            public void call(Subscriber<? super Son> subscriber) {
                subscriber.onNext(new GrandSon("xiaoxiaoli"));
            }
        }).subscribe(new Subscriber<Father>() {
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
