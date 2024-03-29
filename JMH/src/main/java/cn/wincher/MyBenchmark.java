/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package cn.wincher;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;

public class MyBenchmark {


//    # Run complete. Total time: 00:16:32
//
//    Benchmark                                Mode  Samples          Score   Score error  Units
//    c.w.MyBenchmark.testWithException       thrpt      200     687386.576     13257.941  ops/s
//    c.w.MyBenchmark.testWithoutException    thrpt      200  873689904.252  24124812.111  ops/s

    private static String s = "hello world!";

    public static void main(String[] args) throws RunnerException, IOException {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    public void testWithoutException() {
        try {
            s.substring(s.length());
        } catch (RuntimeException e) {
            // ignore
        }
    }

    @Benchmark
    public void testWithException() {
        try {
            s.substring(s.length()+1);
        } catch (RuntimeException e) {
            //ignore
        }
    }
}
