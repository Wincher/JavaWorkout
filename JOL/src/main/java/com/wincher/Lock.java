package com.wincher;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author wincher
 * <p> com.wincher <p>
 */
public class Lock {

    public static void main(String[] args) {
        Lock lock = new Lock();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        synchronized (lock) {
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

    }
}
