package com.wincher;

import lombok.extern.java.Log;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

/**
 * @author wincher
 * <p> com.wincher <p>
 */
@Log
public class ArrayMemoryUsageAnalysis {
    /**
     * from 秦金卫的知识星球 https://wx.zsxq.com/dweb2/index/topic_detail/182424422518412
     * 可以看到，
     * 1、int[256];        => 内部=第一层1040
     * 2、int[128][2];     => 内部3072，第一层528 ==> 3600
     * 3、int[64][2][2];  => 内部4608，第一层272  ==> 4880
     *
     * 对于int[256]，数组本身是个对象占16，每个子对象指针一个int，256个int=256*4=1024，所以一共是16+1024=1040；
     * 对于int[128][2]，二维数组本身16，第一层是128*4=512，一共是528.
     * 事实上，我们可以用print(GraphLayout.parseInstance(arr2).toPrintable());
     * 把二维数组里的一维数组集合打印出来：
     * [I@27082746d, [I@66133adcd, [I@7bfcd12cd, [I@42f30e0ad, ...
     *      ADDRESS    SIZE TYPE PATH              VALUE
     *     76acd9858     24 [I  <r1>              [0, 0]
     *     76acd9870     24 [I  <r2>              [0, 0]
     *     76acd9888     24 [I  <r3>              [0, 0]
     *     76acd98a0     24 [I  <r4>              [0, 0]
     *     ......
     * 可以看到，每个二维的位置，都是一个一维的数组对象，长度为2，
     * 所以每一个占用了一个对象头加上两个int，共16+4+4=24字节。
     * 所以这一部分一共是24*128=3072字节。
     *
     * 同理，三维数组内部结构是二维数组：
     *  ADDRESS    SIZE TYPE PATH              VALUE
     *     76acda728     24 [[I <r1>              [[0, 0], [0, 0]]
     *     76acda740     24 [I  <r1>[0]            [0, 0]
     *     76acda758     24 [I  <r1>[1]            [0, 0]
     *     76acda770     24 [[I <r2>              [[0, 0], [0, 0]]
     *     76acda788     24 [I  <r2>[0]            [0, 0]
     *     76acda7a0     24 [I  <r2>[1]            [0, 0]
     *    。。。。。。
     * 这样的话，int[64][2][2]就比int[128][2]，多了一层也就是64个二维数组对象，他们占用64*24=1536字节。
     * 所以三维数组内部使用内存为3072+1536=4608字节。
     * @param args
     */
    public static void main(String[] args) {
        int[] arr1 = new int[256];
        int[][] arr2 = new int[128][2];
        int[][][] arr3 = new int[64][2][2];
        log.info("size : " + GraphLayout.parseInstance(arr1).totalSize());
        log.info(ClassLayout.parseInstance(arr1).toPrintable());
        log.info("size : " + GraphLayout.parseInstance(arr2).totalSize());
        log.info(ClassLayout.parseInstance(arr2).toPrintable());
        log.info("size : " + GraphLayout.parseInstance(arr3).totalSize());
        log.info(ClassLayout.parseInstance(arr3).toPrintable());

        log.info(GraphLayout.parseInstance(arr2).toPrintable());
    }
}
