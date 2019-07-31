package test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * @author huwq
 * @since 2018/8/20
 * <p> Test <p>
 */
public class Test {
  public static void main(String[] args) {
    Objects.requireNonNull(new String());
    SoftReference<String> sr = new SoftReference<>("a");
    WeakReference<Integer> wr = new WeakReference<>(1);
    ReferenceQueue queue = new ReferenceQueue();
    PhantomReference pr = new PhantomReference(new Object(), queue);
  }
}
