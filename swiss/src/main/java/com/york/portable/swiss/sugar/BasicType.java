package com.york.portable.swiss.sugar;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.*;

/**
 * Created by York on 2017/11/23.
 */
public class BasicType {
    private static Object _serialNumberLock = new Object();
    private static Lock lock = new ReentrantLock();

    /// <summary>
    /// 生成时间戳序列号（默认格式1712302359596660001）
    /// </summary>
    /// <param name="dateTimeFormat">时间格式：默认值："yyMMddHHmmssSSS"</param>
    /// <param name="minCount">起始值（默认值：1）</param>
    /// <param name="countDigit">最大位数（默认值：4）</param>
    /// <returns></returns>
    public static InnerClosure serialNumber() {
        String dateTimeFormat = "yyMMddHHmmssSSS";
        int minCount = 1;
        int countDigit = 4;
        return serialNumber(dateTimeFormat, minCount, countDigit);
    }

    public static InnerClosure serialNumber(String dateTimeFormat, int minCount, int countDigit) {
        return new InnerClosure(dateTimeFormat, minCount, countDigit);
    }


    public static class InnerClosure {
        String dateTimeFormat;
        int countInSecond;
        String latestDateTime;
        int MIN_COUNT;
        int COUNT_DIGIT;
        double MAX_COUNT;

        InnerClosure(String dateTimeFormat, int minCount, int countDigit) {
            this.dateTimeFormat = dateTimeFormat;
            this.MIN_COUNT = minCount;
            this.COUNT_DIGIT = countDigit;
            this.MAX_COUNT = Math.pow(10, COUNT_DIGIT) - 1;
        }

        public String serialNumber(){
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

                id = latestDateTime + StringUtils.leftPad(String.valueOf(countInSecond),COUNT_DIGIT, '0');
            } finally {
                lock.unlock();
            }

            return id;
        }
    }


    //public static <T> Class<T> T2Class(T)
     //(Class < T > ) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[ 0 ]
}

