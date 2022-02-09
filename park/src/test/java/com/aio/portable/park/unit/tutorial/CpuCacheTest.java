package com.aio.portable.park.unit.tutorial;

import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.swiss.sugar.UnsafeSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class CpuCacheTest {
    private static final int RUNS = 10;
    private static final int DIMENSION_1 = 10 * 1024 * 1024;
    private static final int DIMENSION_2 = 6;

    private static long[][] longs;

    @Test
    public void spatialLocality() throws Exception {
        longs = new long[DIMENSION_1][];
        for (int i = 0; i < DIMENSION_1; i++) {
            longs[i] = new long[DIMENSION_2];
            for (int j = 0; j < DIMENSION_2; j++) {
                longs[i][j] = 1L;
            }
        }
        System.out.println("初始化完毕....");

        long sum = 0L;
        long start = System.currentTimeMillis();
        for (int r = 0; r < RUNS; r++) {
            for (int i = 0; i < DIMENSION_1; i++) {//DIMENSION_1=1024*1024
                for (int j = 0; j < DIMENSION_2; j++) {//6
                    sum += longs[i][j];
                }
            }
        }
        System.out.println("spend time1:" + (System.currentTimeMillis() - start));
        System.out.println("sum1:" + sum);

        sum = 0L;
        start = System.currentTimeMillis();
        for (int r = 0; r < RUNS; r++) {
            for (int j = 0; j < DIMENSION_2; j++) {//6
                for (int i = 0; i < DIMENSION_1; i++) {//1024*1024
                    sum += longs[i][j];
                }
            }
        }
        System.out.println("spend time2:" + (System.currentTimeMillis() - start));
        System.out.println("sum2:" + sum);

    }

    @Test
    public void temporalLocality() throws Exception {
        long start = System.currentTimeMillis();
        int x = 0, y = 0, z = 0;
        for (int i = 0; i < 100000000; i++) {
            x = 1;
            y = 1;
            z = x + 6;
        }
        System.out.println(z);
        System.out.println("spend time1:" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            y = 1;
            x = 1;
            z = x + 6;
        }
        System.out.println(z);
        System.out.println("spend time2:" + (System.currentTimeMillis() - start));

    }












    private  static int x = 0, y = 0;
    private  static int a = 0, b = 0;

    static LogHub log = AppLogHubFactory.staticBuild();
    @Test
    public void main() throws InterruptedException {
        int i = 0;
        for (;;){
            i++;
            x = 0; y = 0;
            a = 0; b = 0;
            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    shortWait(10000);
                    a = 1;
                    x = b;
                    UnsafeSugar.getUnsafe().fullFence();
                    ///
                    //
                    //
                }
            });

            Thread t2 = new Thread(new Runnable() {
                public void run() {
                    b = 1;
                    UnsafeSugar.getUnsafe().fullFence();
                    y = a;
                }
            });

            t1.start();
            t2.start();
            t1.join();
            t2.join();

            String result = "第" + i + "次 (" + x + "," + y + "）";
            if(x == 0 && y == 0) {
                System.out.println(result);
                break;
            } else {
                System.out.println(result);
//                log.info(result);
            }
        }

    }

    /**
     * 等待一段时间，时间单位纳秒
     * @param interval
     */
    public static void shortWait(long interval){
        long start = System.nanoTime();
        long end;
        do{
            end = System.nanoTime();
        }while(start + interval >= end);
    }
}
