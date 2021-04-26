package com.wincher;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @author wincher
 * <p> com.wincher <p>
 */
public class AgeTenuring {

    volatile static Object consumer;

    /**
     * 09 00 00 00 (00001001 00000000 00000000 00000000)
     *               ^^^^
     * 11 00 00 00 (00010001 00000000 00000000 00000000)
     *               ^^^^
     * 19 00 00 00 (00011001 00000000 00000000 00000000)
     *               ^^^^
     * 21 00 00 00 (00100001 00000000 00000000 00000000)
     *               ^^^^
     * 29 00 00 00 (00101001 00000000 00000000 00000000)
     *               ^^^^
     * 31 00 00 00 (00110001 00000000 00000000 00000000)
     *               ^^^^
     * 31 00 00 00 (00110001 00000000 00000000 00000000)
     *
     * show that 1-5 bit show the age
     * @param args
     */

    public static void main(String[] args) {

        Object instance = new Object();
        long lastAddr = VM.current().addressOf(instance);
        ClassLayout layout = ClassLayout.parseInstance(instance);

        for (int i = 0; i < 10_000; i++) {
            long currentAddr = VM.current().addressOf(instance);
            if (currentAddr != lastAddr) {
                System.out.println(layout.toPrintable());
            }

            for (int j = 0; j < 10_000; j++) {
                consumer = new Object();
            }

            lastAddr = currentAddr;
        }
    }
}
