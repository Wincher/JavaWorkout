package com.wincher;

import lombok.extern.java.Log;
import org.openjdk.jol.info.GraphLayout;

import java.util.HashMap;

/**
 * @author wincher
 * <p> com.wincher <p>
 */
@Log
public class ReferenceAnalysis {
    public static void main(String[] args) {
        HashMap hashMap= new HashMap();
        hashMap.put("wincher","JavaWorkout");
        log.info(GraphLayout.parseInstance(hashMap).toPrintable());
    }
}
