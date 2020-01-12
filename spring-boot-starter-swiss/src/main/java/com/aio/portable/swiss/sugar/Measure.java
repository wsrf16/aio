package com.aio.portable.swiss.sugar;

import java.util.function.Supplier;

public class Measure {
    public static class Time {
        public static long timing(Supplier<Void> supplier) {
            long prevTime = System.currentTimeMillis();
            supplier.get();
            long nextTime = System.currentTimeMillis();
            long during = nextTime - prevTime;
//            if (during < 2) {
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            return during;
        }
    }
}
