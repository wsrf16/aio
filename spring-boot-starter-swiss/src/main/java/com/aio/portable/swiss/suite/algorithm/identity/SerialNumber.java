package com.aio.portable.swiss.suite.algorithm.identity;

import com.aio.portable.swiss.sugar.DateTimeSugar;
import com.aio.portable.swiss.sugar.StringSugar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by York on 2017/11/23.
 */
public class SerialNumber {
//    private final static Object _serialNumberLock = new Object();
//    private final static Lock lock = new ReentrantLock();
    private final static String DATETIME_FORMAT = DateTimeSugar.Format.FORMAT_TIGHT_LONG; //"yyyyMMddHHmmssSSS";

    public final static SerialNumberBuilder serialNumberBuilder() {
        String dateTimeFormat = DATETIME_FORMAT;
        int minCount = 1;
        int countDigit = 4;
        return serialNumberBuilder(dateTimeFormat, minCount, countDigit);
    }

    /**
     * serialNumber
     * @param dateTimeFormat 时间格式：默认值："yyyyMMddHHmmssSSS"
     * @param minCount 起始值（默认值：1）
     * @param countDigit 最大位数（默认值：4）
     * @return
     */
    public final static SerialNumberBuilder serialNumberBuilder(String dateTimeFormat, int minCount, int countDigit) {
        return new SerialNumberBuilder(dateTimeFormat, minCount, countDigit);
    }


    public final static class SerialNumberBuilder {
        String dateTimeFormat;
        int countInSecond;
        String latestDateTime;
        int MIN_COUNT;
        int COUNT_DIGIT;
        double MAX_COUNT;

        private SerialNumberBuilder(String dateTimeFormat, int minCount, int countDigit) {
            this.dateTimeFormat = dateTimeFormat;
            this.MIN_COUNT = minCount;
            this.COUNT_DIGIT = countDigit;
            this.MAX_COUNT = Math.pow(10, COUNT_DIGIT) - 1;
        }

        public synchronized String build(){
            String now = new SimpleDateFormat(dateTimeFormat).format(new Date());
            if (now.equals(latestDateTime)) {
                countInSecond = (countInSecond + 1) > MAX_COUNT ? MIN_COUNT : (countInSecond + 1);
            } else {
                countInSecond = MIN_COUNT;
                latestDateTime = now;
            }
            String id = latestDateTime + StringSugar.leftPad(String.valueOf(countInSecond),COUNT_DIGIT, '0');
            return id;
        }
    }


    //public final static <T> Class<T> T2Class(T)
     //(Class < T > ) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[ 0 ]
}

