package cn.wincher.projectreactor.demo;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author wincher
 * <p> PACKAGE_NAME <p>
 */
public class OfficialDemo {
    public static void main(String[] args) {
        Flux.just("tom")
            .map(s -> {
                System.out.println("(concat @qq.com) at [" + Thread.currentThread() + "]");
                return s.concat("@qq.com");
            })
            .publishOn(Schedulers.newSingle("thread-a"))
            .map(s -> {
                System.out.println("(concat foo) at [" + Thread.currentThread() + "]");
                return s.concat("foo");
            })
            .filter(s -> {
                System.out.println("(startsWith f) at [" + Thread.currentThread() + "]");
                return s.startsWith("t");
            })
            .publishOn(Schedulers.newSingle("thread-b"))
            .map(s -> {
                System.out.println("(to length) at [" + Thread.currentThread() + "]");
                return s.length();
            })
            .subscribeOn(Schedulers.newSingle("source"))
            .subscribe(System.out::println);
    }
}
