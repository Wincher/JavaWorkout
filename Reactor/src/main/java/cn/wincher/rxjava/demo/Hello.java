package cn.wincher.rxjava.demo;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wincher
 * <p> cn.wincher.rxjava.demo <p>
 */
public class Hello {
    private static String result = "";
    static int count = 0;

    public static void main(String[] args) {
        createObservable();
//        subscribe();
//        map();
//        concatMap();
//        buffer();
//        filter();
//        distinct();
//        takeAndSkip();
//        zip();
//        mergeAndConcat();
//        subscribeOnAndObserveOn();
//        singleMaybeCompletableSource();
//        onError();
//        deferAndRetry();
    }

    private static void deferAndRetry() {
        AtomicInteger counter = new AtomicInteger(0);
        Observable<Integer> observable = Observable.defer(() -> {
            if (counter.getAndIncrement() < 2) {
                System.out.println(Thread.currentThread().getName() + " : " + counter);
                return Observable.error(new RuntimeException("Intentional error"));
            } else {
                System.out.println(Thread.currentThread().getName() + " enter else: counter: " +  counter);
                return Observable.just(1, 2, 3);
            }
        });
        observable.retry(2) // 出始1次+重试2次,改为1走异常
                .subscribe(
                        System.out::println,
                        Throwable::printStackTrace,
                        () -> System.out.println("Completed")
                );
    }

    private static void onError() {
        Observable<Integer> observable = Observable.just(1, 2, 0, 4, 5)
                .map(x -> 10 / x); // 除以 0 会抛出异常
//        observable.onErrorReturn(e -> -1) // 如果发生错误，返回 -1,后面的不执行
//                .subscribe(
//                        System.out::println,
//                        Throwable::printStackTrace, // 不会调用
//                        () -> System.out.println("Completed")
//                );
        observable.onErrorResumeNext(Observable.just(-1, -2, -3)) // 如果发生错误，使用备用 Observable
                .subscribe(
                        System.out::println,
                        Throwable::printStackTrace, // 不会调用
                        () -> System.out.println("Completed")
                );
    }

    private static void singleMaybeCompletableSource() {
        Single<String> single = Single.just("Hello Single");
        single.subscribe(
                System.out::println, // onSuccess
                Throwable::printStackTrace // onError
        );

        Maybe<String> maybe = Maybe.just("Hello Maybe");
        maybe.subscribe(
                System.out::println, // onSuccess
                Throwable::printStackTrace, // onError
                () -> System.out.println("Completed") // onComplete
        );
        Maybe<String> emptyMaybe = Maybe.empty();
        emptyMaybe.subscribe(
                System.out::println, // onSuccess (不会被调用)
                Throwable::printStackTrace, // onError (不会被调用)
                () -> System.out.println("Empty Maybe Completed") // onComplete
        );

        Completable completable = Completable.complete();
        completable.subscribe(
                () -> System.out.println("Completable Completed"), // onComplete
                Throwable::printStackTrace // onError
        );
    }

    private static void subscribeOnAndObserveOn() {
        Observable<Integer> observable = Observable.range(1, 5)
                .subscribeOn(Schedulers.io()) // 在 IO 线程池中执行
//                .subscribeOn(Schedulers.computation())
//                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.from(Executors.newCachedThreadPool()))
                .map(x -> {
                    System.out.println("Processing " + x + " on thread: " + Thread.currentThread().getName());
                    return x * 2;
                }).observeOn(Schedulers.computation());

        observable.subscribe(x ->
                System.out.println(Thread.currentThread().getName() + " : " + x),
                Throwable::printStackTrace,
                () -> System.out.println("Completed")
        );
        try {
            Thread.sleep(1000); // 等待异步操作完成
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void mergeAndConcat() {
        Observable<Long> observable1 = Observable.intervalRange(1, 3, 0, 200, TimeUnit.MILLISECONDS);
        Observable<Long> observable2 = Observable.intervalRange(4, 3, 100, 200, TimeUnit.MILLISECONDS);
        // 使用 merge() 将两个 Observable 合并成一个 (无序)
        Observable<Long> mergeObservable = Observable.merge(observable1, observable2);
        mergeObservable.subscribe(s->{
                System.out.println(Thread.currentThread().getName() + " value: " + s);
        });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 使用 concat() 将两个 Observable 连接成一个 (有序)
        Observable<Long> concatObservable = Observable.concat(observable2, observable1);
        concatObservable.subscribe(s->{
            System.out.println(Thread.currentThread().getName() + " value: " + s);
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void zip() {
        Observable<String> observable1 = Observable.just("A", "B", "C");
        Observable<Integer> observable2 = Observable.just(1, 2, 3);
        // 使用 zip() 将两个 Observable 的元素合并成一个
        Observable<String> zipObservable = Observable.zip(observable1, observable2, (s, i) -> s + i);
        zipObservable.subscribe(System.out::println);
    }

    private static void takeAndSkip() {
        Observable<Integer> observable = Observable.range(1, 10);
        // 使用 take() 取前 3 个元素
        Observable<Integer> takeObservable = observable.take(3);
        takeObservable.subscribe(System.out::println);
        System.out.println("------------------");
        // 使用 skip() 跳过前 5 个元素
        Observable<Integer> skipObservable = observable.skip(5);
        skipObservable.subscribe(System.out::println);
    }

    private static void distinct() {
        Observable<String> observable = Observable.just("A", "B", "A", "C", "B", "D");
        // 使用 distinct() 去除重复的元素
        Observable<String> distinctObservable = observable.distinct();
        distinctObservable.subscribe(System.out::println);
    }

    private static void filter() {
        Observable<Integer> observable = Observable.range(1, 10);
        Observable<Integer> evenNumbers = observable.filter(x -> x % 2 == 0);
        evenNumbers.subscribe(System.out::println);
    }

    private static void buffer() {
        Observable<Integer> observable = Observable.range(1, 10);
        // 使用 buffer() 将 Observable 发射的元素缓存到 List 中，每 3 个元素缓存一次
        Observable<List<Integer>> bufferObservable = observable.buffer(3);
        bufferObservable.subscribe(System.out::println);
    }

    private static void concatMap() {
        Observable<Integer> observable = Observable.just(1,20,10);
        //concatMap(function): 与 flatMap() 类似，但它会按顺序合并 Observable，保证元素的顺序。
        Observable<Integer> integerObservable = observable.concatMap(x ->
                //以x为起始,每200ms发射一个x++
                Observable.intervalRange(x, x, 0, 200, TimeUnit.MILLISECONDS)
                        .take(x)
                        .map(Long::intValue)
        );
        integerObservable.subscribe(System.out::println);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void map() {
        Observable<String> observable = Observable.just("apple", "banana", "orange");
        observable.map(String::toUpperCase).subscribe(System.out::println);
        observable.flatMap(s -> {
            System.out.println("flat Map: " + s);
			return Observable.fromIterable(Arrays.asList(s,s.toUpperCase(),s.concat(" delicious")));
		}).subscribe(System.out::println);
    }

    private static void subscribe() {
        Observable<String> observable = Observable.just("Apple", "Banana", "Orange");
        observable.subscribe(System.out::println);
        observable.subscribe(
                System.out::println, // onNext
                Throwable::printStackTrace, // onError
                () -> System.out.println("Completed!") // onComplete
        );

        observable.subscribe(new io.reactivex.Observer<String>() {
            @Override
            public void onSubscribe(io.reactivex.disposables.Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(String s) {
                System.out.println("Received: " + s);
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed!!");
            }
        });
    }

    private static void createObservable() {
        Observable<String> observer = Observable.just("Hello","world");
        observer.subscribe(s -> {
            System.out.println(s);
            count++;}); // Callable as subscriber
        System.out.println(count);

        observer = Observable.create(emitter -> {
            emitter.onNext(" World");
        }); // provides data
        observer.subscribe(s -> result+=s); // Callable as subscriber
        observer.subscribe(s -> result+=s);
        System.out.println(result);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Observable<Integer> fromIterableObservable = Observable.fromIterable(numbers);
        fromIterableObservable.subscribe(System.out::println);
    }
}
