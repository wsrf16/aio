package com.aio.portable.swiss.sugar;

import com.aio.portable.swiss.global.Constant;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.Supplier;

/**
 * Created by York on 2017/11/23.
 */
public abstract class DateTimeSugar {
    public static class CalendarUtils {
        public static Calendar getFirstDayOfMonth(Calendar calendar) {
            return getFirstDayOf(calendar, Calendar.DAY_OF_MONTH);
        }

        public static Calendar getLastDayOfMonth(Calendar calendar) {
            return getLastDayOf(calendar, Calendar.DAY_OF_MONTH);
        }

        public static Calendar getFirstDayOfYear(Calendar calendar) {
            return getFirstDayOf(calendar, Calendar.DAY_OF_YEAR);
        }

        public static Calendar getLastDayOfYear(Calendar calendar) {
            return getLastDayOf(calendar, Calendar.DAY_OF_YEAR);
        }

        public static Calendar getFirstDayOfWeek(Calendar calendar) {
            return getFirstDayOf(calendar, Calendar.DAY_OF_WEEK);
        }

        public static Calendar getLastDayOfWeek(Calendar calendar) {
            return getLastDayOf(calendar, Calendar.DAY_OF_WEEK);
        }


        /**
         * add
         *
         * @param calendar
         * @param field    : Calendar. ERA YEAR MONTH WEEK_OF_YEAR WEEK_OF_MONTH DATE DAY_OF_MONTH DAY_OF_YEAR DAY_OF_WEEK DAY_OF_WEEK_IN_MONTH AM_PM HOUR HOUR_OF_DAY MINUTE SECOND MILLISECOND ZONE_OFFSET DST_OFFSET FIELD_COUNT
         * @param amount
         * @return
         */
        public static Calendar add(Calendar calendar, int field, int amount) {
            calendar.add(field, amount);
            return calendar;
        }

        public static Calendar now() {
            Calendar calendar = Calendar.getInstance();
            return calendar;
        }

        public static Calendar getFirstDayOf(Calendar calendar, int field) {
            calendar.set(field, calendar.getActualMinimum(field));
            return calendar;
        }

        public static Calendar getLastDayOf(Calendar calendar, int field) {
            calendar.set(field, calendar.getActualMaximum(field));
            return calendar;
        }

        public static Calendar fromDate(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }

        public static Date toDate(Calendar calendar) {
            Date date = calendar.getTime();
            return date;
        }

//        public static Date calendarToUTC(Calendar calendar) {
//            Calendar newCalendar = Calendar.getInstance();
//            newCalendar.add(Calendar.MILLISECOND, -calendar.getTimeZone().getRawOffset());
//            Date date = newCalendar.getTime();
//            return date;
//        }

        public static Calendar utcEpoch() {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(0);
            return calendar;
        }

        public static Calendar defaultEpoch() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(0);
            return calendar;
        }
    }

    public static class LocalDateTimeUtils {
        public LocalDateTime plusDays(LocalDateTime localDateTime, long daysToAdd) {
            return localDateTime.plusDays(daysToAdd);
        }

        public final static long nowEpochMillli() {
            long epochMilli = Instant.now().toEpochMilli();
            return epochMilli;
        }

        public final static LocalDateTime parse(@NotNull String text, String format) {
            LocalDateTime localDateTime = LocalDateTime.parse(text, DateTimeFormatter.ofPattern(format));
            return localDateTime;
        }

        public final static String format(@NotNull LocalDateTime localDateTime, String format) {
            String text = localDateTime.format(DateTimeFormatter.ofPattern(format));
            return text;
        }

        public final static Date toDate(@NotNull LocalDateTime localDateTime, ZoneId zoneId) {
            ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
            return Date.from(zonedDateTime.toInstant());
        }

        public final static Date toDate(@NotNull LocalDateTime localDateTime) {
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
            return Date.from(zonedDateTime.toInstant());
        }

        public final static LocalDateTime fromDate(Date date, ZoneId zoneId) {
            Instant instant = date.toInstant();
            return instant.atZone(zoneId).toLocalDateTime();
        }

        public final static LocalDateTime fromDate(Date date) {
            Instant instant = date.toInstant();
            return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        }

        public final static LocalDateTime now() {
            return LocalDateTime.now();
        }
    }


    public static class Format {
        public final static String FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        public final static String FORMAT_NORMAL_LONGEST = "yyyy-MM-dd HH:mm:ss.SSS";
        public final static String FORMAT_NORMAL_LONG = "yyyy-MM-dd HH:mm:ss";
        public final static String FORMAT_NORMAL_SHORT = "yyyy-MM-dd";
        public final static String FORMAT_TIGHT_LONG = "yyyyMMddHHmmssSSS";
        public final static String FORMAT_TIGHT_SHORT = "yyyyMMdd";

        /**
         * 从String转换为Date
         *
         * @param format eg. {@value #FORMAT_ISO8601}
         * @param text   "1987-06-05T44:33:22.111+0800"
         * @return Date
         * @throws ParseException
         */
        public static Date convertText2Date(String format, String text) throws ParseException {
            Date date = new SimpleDateFormat(format).parse(text);
            return date;
        }

        /**
         * 从Date转换为String
         *
         * @param format eg. {@value #FORMAT_ISO8601}
         * @param date
         * @return String "1987-06-05T44:33:22.111+0800"
         * @throws ParseException
         */
        public static String convertDate2Text(String format, Date date) {
            String text = new SimpleDateFormat(format).format(date);
            return text;
        }

        /**
         * 从String转换为Calendar
         *
         * @param format eg. {@value #FORMAT_ISO8601}
         * @param text   "1987-06-05T44:33:22.111+0800"
         * @return Date
         * @throws ParseException
         */
        public static Calendar convertText2Calendar(String format, String text) throws ParseException {
            Date date = new SimpleDateFormat(format).parse(text);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }

        /**
         * 从Calendar转换为String
         *
         * @param format   eg. {@value #FORMAT_ISO8601}
         * @param calendar
         * @return String "1987-06-05T44:33:22.111+0800"
         * @throws ParseException
         */
        public static String convertCalendar2Text(String format, Calendar calendar) {
            Date date = calendar.getTime();
            String text = new SimpleDateFormat(format).format(date);
            return text;
        }

        /**
         * 返回 {@value #FORMAT_TIGHT_LONG} 格式的时间
         *
         * @return
         */
        public static String getDateTimeSerialize() {
            String serialize = convertDate2Text(FORMAT_TIGHT_LONG, new Date());
            return serialize;
        }
    }

    public static class UnixTime {
        /**
         * 获取Unix时间（1970年至今经过了多少秒）
         *
         * @return long
         */
        public static long nowUnix() {
            long time = System.currentTimeMillis();
            long nowTimeStamp = time / 1000;
            return nowTimeStamp;
        }

        /**
         * 获取当前时区
         *
         * @return
         */
        public static long getCurrentZone() {
            long zone = java.util.Calendar.getInstance().getTimeZone().getRawOffset() / Constant.TimeUnit.Hour;
            return zone;
        }

        /**
         * 将Unix时间戳转换为本地DateTime类型时间
         *
         * @param unix double 型数字
         * @return 本地DateTime
         */
        public static LocalDateTime convertUnix2DateTime(long unix) {
            LocalDateTime time = getUTCEpochTime().plusHours(getCurrentZone()).plusSeconds(unix);
            return time;
        }

        /**
         * 将本地DateTime时间格式转换为Unix时间戳格式
         *
         * @param time 本地时间
         * @return long
         */
        public static long convertDateTime2Unix(LocalDateTime time) {
            return time.toEpochSecond(ZoneOffset.of("+8"));
        }

        /**
         * 将Unix时间戳转换为UTC时间
         *
         * @param unix long 型数字
         * @return 本地DateTime
         */
        public static LocalDateTime convertUnix2UTC(long unix) {
            LocalDateTime time = getUTCEpochTime().plusSeconds(unix);
            return time;
        }

        /**
         * 将UTC时间格式转换为Unix时间戳格式
         *
         * @param time UTC时间
         * @return long ms
         */
        public static long convertUTC2Unix(LocalDateTime time) {
            return Duration.between(getUTCEpochTime(), time).toMillis();
        }

        /**
         * 取得1970年01月01日00时00分00秒
         *
         * @return
         */
        public static LocalDateTime getUTCEpochTime() {
            return LocalDateTime.of(EpochTime.YEAR, EpochTime.MONTH, EpochTime.DAY_OF_MONTH, EpochTime.HOUR, EpochTime.MINUTE, EpochTime.SECOND);
        }

        static class EpochTime {
            public final static int YEAR = 1970;
            public final static int MONTH = 1;
            public final static int DAY_OF_MONTH = 1;
            public final static int HOUR = 0;
            public final static int MINUTE = 0;
            public final static int SECOND = 0;
        }
    }



}

