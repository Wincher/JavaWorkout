package cn.wincher.rxjava.demo;

import io.reactivex.Observable;

/**
 * @author wincher
 * <p> cn.wincher.rxjava.demo <p>
 */
public class Hello {
    private static String result = "";

    public static void main(String[] args) {
        Observable<String> observer = Observable.just("Hello");
        observer.subscribe(s -> result=s); // Callable as subscriber
        System.out.println(result);

        observer = Observable.create(emitter -> {
            emitter.onNext("Wrold");
        }); // provides data
        observer.subscribe(s -> result=s); // Callable as subscriber
        System.out.println(result);
    }
}
