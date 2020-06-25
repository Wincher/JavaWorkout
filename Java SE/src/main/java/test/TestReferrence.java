package test;

import javafx.util.Pair;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Stack;

/**
 * @author huwq
 * @since 2018/8/20
 * <p> TestReferrence <p>
 */
public class TestReferrence {
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
    new Pair<Integer, Integer>(1,1).getKey();
  }
}
