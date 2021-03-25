package com.wincher;

import lombok.extern.java.Log;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @author wincher
 * <p> com.wincher <p>
 */
@Log
public class StringAnalysis {
    public static void main(String[] args) {
        log.info(ClassLayout.parseClass(String.class).toPrintable());
        //可以看到无论是多长的字符串都是占用24bytes,只持有char[]的句柄
        log.info(ClassLayout.parseInstance("JavaWorkOut").toPrintable());
        log.info(ClassLayout.parseInstance("JavaWorkOutalsdfjkhasjhdflaksjdfkashdfjkl").toPrintable());

        //可见数组是会随着内容变大而占用更大空间的
        log.info(ClassLayout.parseClass(byte[].class).toPrintable());
        log.info(ClassLayout.parseInstance("JavaWork".getBytes()).toPrintable());
        log.info(ClassLayout.parseInstance("JavaWorkadsfasdfasdfasdfasdfadgdasfasdfasdf".getBytes()).toPrintable());
    }
}
