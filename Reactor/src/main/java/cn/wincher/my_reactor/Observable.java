package cn.wincher.my_reactor;

/**
 * @author wincher
 * <p> cn.wincher.MyReactor <p>
 */
public class Observable<T> {

    final OnSubscribe<T> onSubscribe;

    private Observable(OnSubscribe<T> onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe) {
        return new Observable<>(onSubscribe);
    }

    public void subscribe(Subscriber<? super T> subscriber) {
        subscriber.onStart();
        onSubscribe.call(subscriber);
    }

    /**
     * subscribeOn是作用于上层OnSubscribe的, 可以让OnSubscribe的call方法在新线程中执行.
     * @param scheduler
     * @return
     */
    public Observable<T> subscribeOn(Scheduler scheduler) {
        return Observable.create(subscriber -> {
            subscriber.onStart();
            // 将事件的生产切换到新的线程。
            scheduler.createWorker().schedule(() -> Observable.this.onSubscribe.call(subscriber));
        });
    }

    /**
     * observeOn，需要让下层Subscriber的事件处理方法放到新线程中执行。
     * @param scheduler
     * @return
     */
    public Observable<T> observeOn(Scheduler scheduler) {
        return Observable.create(subscriber -> {
            subscriber.onStart();
            Scheduler.Worker worker = scheduler.createWorker();
            Observable.this.onSubscribe.call(new Subscriber<T>() {
                @Override
                public void onCompleted() {
                    worker.schedule(() -> subscriber.onCompleted());
                }
                @Override
                public void onError(Throwable t) {
                    worker.schedule(() -> subscriber.onError(t));
                }
                @Override
                public void onNext(T var1) {
                    worker.schedule(() -> subscriber.onNext(var1));
                }
            });
        });
    }

    /**
     * //TODO: 还没理解， 传给Transformer#call方法的参数是T类型的，那么call方法的参数类型可以声明成是T的父类，Transformer#call方法的返回值要求是R类型的，那么它的返回值类型应该声明成R的子类
     * @param transformer
     * @param <R>
     * @return
     */
    public <R> Observable<R> map(Transformer<? super T, ? extends R> transformer) {
        return create(new MapOnSubscribe<T, R>(this, transformer));
    }
    public class MapOnSubscribe<T, R> implements Observable.OnSubscribe<R> {

        final Observable<T> source;
        final Observable.Transformer<? super T, ? extends R> transformer;
        public MapOnSubscribe(Observable<T> source, Observable.Transformer<? super T, ? extends R> transformer) {
            this.source = source;
            this.transformer = transformer;
        }
        @Override
        public void call(Subscriber<? super R> subscriber) {
            source.subscribe(new MapSubscriber<R, T>(subscriber, transformer));
        }
    }
    public class MapSubscriber<T, R> extends Subscriber<R> {

        final Subscriber<? super T> actual;
        final Observable.Transformer<? super R, ? extends T> transformer;
        public MapSubscriber(Subscriber<? super T> actual, Observable.Transformer<? super R, ? extends T> transformer) {
            this.actual = actual;
            this.transformer = transformer;
        }
        @Override
        public void onCompleted() {
            actual.onCompleted();
        }
        @Override
        public void onError(Throwable t) {
            actual.onError(t);
        }
        @Override
        public void onNext(R var1) {
            actual.onNext(transformer.call(var1));
        }
    }

    /**
     * 相当于一个中间层, 延后到subscribe方法执行才传入 Subscriber,
     * @param <T>
     */
    public interface OnSubscribe<T> {
        void call(Subscriber<? super T> subscriber);
    }

    public interface Transformer<T, R> {
        R call(T from);
    }
}
