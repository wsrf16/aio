package com.aio.portable.swiss.suite.algorithm.identity;

import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by York on 2017/11/23.
 */
public abstract class SerialNumberWorker {
    private static final String DATETIME_FORMAT = DateTimeSugar.Format.FORMAT_TIGHT_LONG;

    public static final SerialNumberBuilder builder() {
        int minCount = 1;
        int countDigit = 4;
        return builder(DATETIME_FORMAT, minCount, countDigit);
    }

    /**
     * serialNumber
     * @param dateTimeFormat 时间格式：默认值："yyyyMMddHHmmssSSS"
     * @param minCount 起始值（默认值：1）
     * @param countDigit 最大位数（默认值：4）
     * @return
     */
    public static final SerialNumberBuilder builder(String dateTimeFormat, int minCount, int countDigit) {
        return new SerialNumberBuilder(dateTimeFormat, minCount, countDigit);
    }


    public static final class SerialNumberBuilder {
        private int countInSecond;
        private String latestDateTime;
        private String dateTimeFormat;
        private int minCount;
        private int countDigit;
        private double maxCount;

        private SerialNumberBuilder(String dateTimeFormat, int minCount, int countDigit) {
            this.dateTimeFormat = dateTimeFormat;
            this.minCount = minCount;
            this.countDigit = countDigit;
            this.maxCount = Math.pow(10, this.countDigit) - 1;
        }

        public String build(){
            String now = new SimpleDateFormat(dateTimeFormat).format(new Date());
            if (now.equals(latestDateTime)) {
                countInSecond = (countInSecond + 1) > maxCount ? minCount : (countInSecond + 1);
            } else {
                countInSecond = minCount;
                latestDateTime = now;
            }
            String id = latestDateTime + StringSugar.leftPad(String.valueOf(countInSecond), countDigit, '0');
            return id;
        }
    }


    //public static final <T> Class<T> T2Class(T)
     //(Class < T > ) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[ 0 ]
}

