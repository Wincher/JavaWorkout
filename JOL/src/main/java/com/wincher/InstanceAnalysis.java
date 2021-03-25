package com.wincher;

import lombok.Data;
import lombok.extern.java.Log;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author wincher
 * <p> com.wincher <p>
 */
@Log
@Data
public class InstanceAnalysis {
    private byte aByte;
    private boolean b;
    private char c;
    private short s;
    /**
     * header占用12字节, jvm以8byte分配空间, 所以会选择讲4字节的i或f放在前面,
     * 按照代码写的顺序, 这里如果i在f后可以看到不同的打印顺序,
     * 其他的成员变量分配空间的顺序按照基本变量优先 double > long > int > float > char > short > byte > boolean,
     * 基本引用类型打印完后, 会内存填充至4byte(我做的实验是执行printForInstance(), 依次删除aByte和b field, 分别对应1byte,未删除前需要2byte对齐,删除1个需要3byte对齐,删除2个已经对齐, 不需要额外对齐, 得出的4byte的结论, 望指正)
     * 其他包装类型和引用变量按代码写入顺序排序,
     * 最后在内存对齐至VM options ObjectAlignmentInBytes的值(默认8) byte
     */
    private int i;
    private float f;
    private double d;
    private long l;
    private Boolean b2;
    private Object o;

    /**
     *  Under 1.8 OpenJDK with the option -XX:ObjectAlignmentInBytes=4, and it will fail with error:
     * ObjectAlignmentInBytes=4 must be greater or equal 8
     * with -XX:ObjectAlignmentInBytes=18,
     * error: ObjectAlignmentInBytes=18 must be power of 2
     * @param args
     */
    public static void main(String[] args) {
        printForInstance();
        printForInstanceWithParent();
    }

    private static void printForInstance() {
        InstanceAnalysis jol = new InstanceAnalysis();
        jol.setAByte((byte) 1);
        jol.setB(false);
        jol.setC('a');
        jol.setS((short) 43);
        jol.setI(123);
        jol.setF(4.13F);
        jol.setD(419.536D);
        jol.setL(1949L);
        jol.setB2(true);
        jol.setO(new Object());
        log.info(ClassLayout.parseInstance(jol).toPrintable());
    }

    /**
     * 可以证明在排序上会优先将父类排在前面, 即使不能更好的内存填充 (alignment/padding gap)
     */
    private static void printForInstanceWithParent() {
        Son jol = new Son();
        jol.setPs((short) 4);
        jol.setAByte((byte) 1);
        jol.setB(false);
        jol.setC('a');
        jol.setS((short) 43);
        jol.setI(123);
        jol.setF(4.13F);
        jol.setD(419.536D);
        jol.setL(1949L);
        jol.setB2(true);
        jol.setO(new Object());
        log.info(ClassLayout.parseInstance(jol).toPrintable());
    }

    @Data
    public static class Son extends Father {
        private byte aByte;
        private boolean b;
        private char c;
        private short s;
        /**
         * header占用12字节, jvm以8byte分配空间, 所以会选择讲4字节的i或f放在前面,
         * 按照代码写的顺序, 这里如果i在f后可以看到不同的打印顺序,
         * 其他的成员变量分配空间的顺序按照基本变量优先 double > long > int > float > char > short > byte > boolean,
         * 其他包装类型和引用变量按代码写入顺序排序
         */
        private int i;
        private float f;
        private double d;
        private long l;
        private Boolean b2;
        private Object o;
    }

    @Data
    private static class Father {
        short ps;
    }
}
