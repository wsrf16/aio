package com.aio.portable.swiss.sugar.type;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.Random;

public abstract class NumberSugar {
//    public static int random(int min, int max) {
//        return randomInt(min, max);
//    }

    public static int randomInt(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    public static long randomLong(long min, long max) {
        Random random = new Random();
        long randomNumber = random.nextLong();
        long result = Math.abs(randomNumber) % (max - min) + min;
        return result;
    }

    public static double randomDouble(double min, double max) {
        Random random = new Random();
        double randomNumber = random.nextDouble();
        double result = Math.abs(randomNumber) % (max - min) + min;
        return result;
    }

    public static boolean maybeTrue(double probability) {
        return NumberSugar.randomDouble(0, 1) < probability;
    }

    public static boolean maybeFalse(double probability) {
        return NumberSugar.randomDouble(0, 1) > probability;
    }

    private static ByteBuffer buffer = ByteBuffer.allocate(8);

    public static byte[] convertLongToBytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long convertBytesToLong(byte[] bytes) {
        buffer.put(bytes, 0, bytes.length);
        //flip方法将Buffer从写模式切换到读模式，调用flip()方法会将position设回0，从头读起
        buffer.flip();
        return buffer.getLong();
    }

//    public static double format(double number) {
//        DecimalFormat df = new DecimalFormat("#.##");
//        String s = df.format(number);
//        Double value = Double.valueOf(s);
//        return value;
//    }

//    public static long format(long number) {
//        DecimalFormat df = new DecimalFormat("#.##");
//        String s =  df.format(number);
//        return Long.valueOf(s);
//    }

//    public static String format(Object number) {
//        DecimalFormat df = new DecimalFormat("#.##");
//        String s =  df.format(number);
//        return s;
//    }

    public static double format(double number, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        String s =  df.format(number);
        return Double.valueOf(s);
    }

    public static long format(long number, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        String s =  df.format(number);
        return Long.valueOf(s);
    }

    public static String format(Object number, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        String s =  df.format(number);
        return s;
    }

    public static double around(double number) {
        int scale = 2;
        BigDecimal bd = new BigDecimal(number);
        double d = bd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
        return d;
    }

    public static double around(double number, int scale) {
        BigDecimal bd = new BigDecimal(number);
        double d = bd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
        return d;
    }
}
