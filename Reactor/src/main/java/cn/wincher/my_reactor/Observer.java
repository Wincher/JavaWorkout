package cn.wincher.my_reactor;

/**
 * @author wincher
 * <p> cn.wincher.MyReactor <p>
 */
public interface Observer<T> {
    void onCompleted();
    void onError(Throwable t);
    void onNext(T t);
}
