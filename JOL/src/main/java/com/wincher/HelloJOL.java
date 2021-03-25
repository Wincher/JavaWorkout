package com.wincher;

import lombok.extern.java.Log;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * Hello JOL!
 *
 */
@Log
public class HelloJOL {

    public static void main( String[] args ) {
        log.info(VM.current().details());
        log.info(ClassLayout.parseInstance(new Object()).toPrintable());
    }
}
