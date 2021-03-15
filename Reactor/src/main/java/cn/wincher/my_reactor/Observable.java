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

    public <R> Observable<R> map(Transformer<? super T, ? extends R> transformer) {
        return create(new OnSubscribe<R>() {
            @Override
            public void call(Subscriber<? super R> subscriber) {
                Observable.this.subscribe(new Subscriber<T>() { // 订阅上层的Observable
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }
                    @Override
                    public void onError(Throwable t) {
                        subscriber.onError(t);
                    }
                    @Override
                    public void onNext(T t) {
                        // 将上层的onSubscribe发送过来的Event，通过转换和处理，转发给目标的subscriber
                        subscriber.onNext(transformer.call(t));
                    }
                });
            }
        });
    }

    public void subscribe(Subscriber<? super T> subscriber) {
        System.out.println(subscriber.getClass().getSimpleName());
        subscriber.onStart();
        onSubscribe.call(subscriber);
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
