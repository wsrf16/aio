package com.aio.portable.swiss.sugar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by York on 2017/11/23.
 */
public class BasicType {
//    private final static Object _serialNumberLock = new Object();
    private final static Lock lock = new ReentrantLock();
    private final static String DATETIME_FORMAT = "yyyyMMddHHmmssSSS";

    public static SerialNumberBuilder serialNumberBuilder() {
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
    public static SerialNumberBuilder serialNumberBuilder(String dateTimeFormat, int minCount, int countDigit) {
        return new SerialNumberBuilder(dateTimeFormat, minCount, countDigit);
    }


    public static class SerialNumberBuilder {
        String dateTimeFormat;
        int countInSecond;
        String latestDateTime;
        int MIN_COUNT;
        int COUNT_DIGIT;
        double MAX_COUNT;

        SerialNumberBuilder(String dateTimeFormat, int minCount, int countDigit) {
            this.dateTimeFormat = dateTimeFormat;
            this.MIN_COUNT = minCount;
            this.COUNT_DIGIT = countDigit;
            this.MAX_COUNT = Math.pow(10, COUNT_DIGIT) - 1;
        }

        public String build(){
            String id;
            //lock (_serialNumberLock)
            try {
                lock.lock();
                String now = new SimpleDateFormat(dateTimeFormat).format(new Date());
                if (!now.equals(latestDateTime)) {
                    countInSecond = MIN_COUNT;
                    latestDateTime = now;
                } else
                    countInSecond = (countInSecond + 1) > MAX_COUNT ? MIN_COUNT : (countInSecond + 1);
                id = latestDateTime + StringSugar.leftPad(String.valueOf(countInSecond),COUNT_DIGIT, '0');
            } finally {
                lock.unlock();
            }

            return id;
        }
    }


    //public static <T> Class<T> T2Class(T)
     //(Class < T > ) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[ 0 ]
}

