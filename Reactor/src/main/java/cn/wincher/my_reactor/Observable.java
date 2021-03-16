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
        System.out.println("subscribe invoked: ");
        subscriber.onStart();
        onSubscribe.call(subscriber);
    }

    /**
     * //TODO: 还没理解， 传给Transformer#call方法的参数是T类型的，那么call方法的参数类型可以声明成是T的父类，Transformer#call方法的返回值要求是R类型的，那么它的返回值类型应该声明成R的子类
     * @param transformer
     * @param <R>
     * @return
     */
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
