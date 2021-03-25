package com.wincher;

import lombok.extern.java.Log;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author wincher
 * <p> com.wincher <p>
 */
@Log
public class AutoBoxingAnalysis {
    public static void main(String[] args) {
        //想想也知道, 基本数据类型定义好了长度, 自然是不会超过预定大小的, 使用的内存也是固定的
        log.info(ClassLayout.parseClass(Integer.class).toPrintable());
        log.info(ClassLayout.parseInstance(321123).toPrintable());

        log.info(ClassLayout.parseClass(Long.class).toPrintable());
        log.info(ClassLayout.parseInstance(1654565678901543452L).toPrintable());


    }
}
