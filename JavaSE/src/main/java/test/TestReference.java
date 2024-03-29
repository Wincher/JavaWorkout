package test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Stack;

/**
 * @author wincher
 * @date 2018/8/20
 * <p> TestReference <p>
 */
public class TestReference {
  public static void main(String[] args) {
    Objects.requireNonNull(new String());
    SoftReference<String> sr = new SoftReference<>("a");
    WeakReference<Integer> wr = new WeakReference<>(1);
    ReferenceQueue queue = new ReferenceQueue();
    PhantomReference pr = new PhantomReference(new Object(), queue);
    Stack<Integer> stack = new Stack<>();
    LinkedList<Integer> l = new LinkedList<>();
    l.pollLast();
    new ArrayList<>();
  }
}
