package com.aio.portable.swiss.sugar.type;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.Random;

public abstract class NumberSugar {
//    public static final int random(int min, int max) {
//        return randomInt(min, max);
//    }

    public static final int randomInt(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    public static final long randomLong(long min, long max) {
        Random random = new Random();
        long randomNumber = random.nextLong();
        long result = Math.abs(randomNumber) % (max - min) + min;
        return result;
    }

    private static ByteBuffer buffer = ByteBuffer.allocate(8);

    public static final byte[] convertLongToBytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static final long convertBytesToLong(byte[] bytes) {
        buffer.put(bytes, 0, bytes.length);
        //flip方法将Buffer从写模式切换到读模式，调用flip()方法会将position设回0，从头读起
        buffer.flip();
        return buffer.getLong();
    }

//    public static final double format(double number) {
//        DecimalFormat df = new DecimalFormat("#.##");
//        String s = df.format(number);
//        Double value = Double.valueOf(s);
//        return value;
//    }

//    public static final long format(long number) {
//        DecimalFormat df = new DecimalFormat("#.##");
//        String s =  df.format(number);
//        return Long.valueOf(s);
//    }

//    public static final String format(Object number) {
//        DecimalFormat df = new DecimalFormat("#.##");
//        String s =  df.format(number);
//        return s;
//    }

    public static final double format(double number, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        String s =  df.format(number);
        return Double.valueOf(s);
    }

    public static final long format(long number, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        String s =  df.format(number);
        return Long.valueOf(s);
    }

    public static final String format(Object number, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        String s =  df.format(number);
        return s;
    }

    public static final double around(double number) {
        int scale = 2;
        BigDecimal bd = new BigDecimal(number);
        double d = bd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
        return d;
    }

    public static final double around(double number, int scale) {
        BigDecimal bd = new BigDecimal(number);
        double d = bd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
        return d;
    }
}
