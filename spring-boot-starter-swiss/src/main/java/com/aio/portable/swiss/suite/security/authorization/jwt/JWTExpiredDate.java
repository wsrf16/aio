package com.aio.portable.swiss.suite.security.authorization.jwt;

import com.aio.portable.swiss.sugar.type.DateTimeSugar;

import java.util.Calendar;
import java.util.Date;

public class JWTExpiredDate {
    private Date issuedAt;
    private Date expiresAt;
    private int expiredMount;
    private int calendarField;

    public Date getIssuedAt() {
        return issuedAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public int getExpiredMount() {
        return expiredMount;
    }

    public int getCalendarField() {
        return calendarField;
    }

    public boolean isValid() {
        return issuedAt != null && expiresAt != null;
    }

//    protected JWTExpiredDate(int expiredMinutes) {
//        Calendar issuedAt = DateTimeSugar.CalendarUtils.now();
//        this.expiredMinutes = expiredMinutes;
//
//        this.issuedAt = issuedAt.getTime();
//        this.expiresAt = JWTSugar.getExpiredMinutes(issuedAt, expiredMinutes);
//    }

    protected JWTExpiredDate(Calendar issuedAt, int calendarField, int mount) {
        this.issuedAt = issuedAt.getTime();
        this.expiresAt = DateTimeSugar.CalendarUtils.add(issuedAt, calendarField, mount).getTime();
    }

//    public static final JWTExpiredDate getExpiredMinutes(int expiredMinutes) {
//        Calendar now = DateTimeSugar.CalendarUtils.now();
//        return getExpiredMinutes(now, expiredMinutes);
//    }



    public static final JWTExpiredDate getExpiredSeconds(Calendar issuedAt, int expiredSeconds) {
        return new JWTExpiredDate(issuedAt, Calendar.SECOND, expiredSeconds);
    }

    public static final JWTExpiredDate getExpiredSeconds(int expiredSeconds) {
        Calendar now = DateTimeSugar.CalendarUtils.now();
        return new JWTExpiredDate(now, Calendar.SECOND, expiredSeconds);
    }

    public static final JWTExpiredDate getExpiredMinutes(Calendar issuedAt, int expiredMinutes) {
        return new JWTExpiredDate(issuedAt, Calendar.MINUTE, expiredMinutes);
    }

    public static final JWTExpiredDate getExpiredMinutes(int expiredMinutes) {
        Calendar now = DateTimeSugar.CalendarUtils.now();
        return new JWTExpiredDate(now, Calendar.MINUTE, expiredMinutes);
    }

    public static final JWTExpiredDate getExpiredHours(Calendar issuedAt, int expiredHours) {
        return new JWTExpiredDate(issuedAt, Calendar.HOUR_OF_DAY, expiredHours);
    }

    public static final JWTExpiredDate getExpiredHours(int expiredHours) {
        Calendar now = DateTimeSugar.CalendarUtils.now();
        return new JWTExpiredDate(now, Calendar.HOUR_OF_DAY, expiredHours);
    }

    public static final JWTExpiredDate getExpiredDays(Calendar issuedAt, int expiredDays) {
        return new JWTExpiredDate(issuedAt, Calendar.DAY_OF_YEAR, expiredDays);
    }

    public static final JWTExpiredDate getExpiredDays(int expiredDays) {
        Calendar now = DateTimeSugar.CalendarUtils.now();
        return new JWTExpiredDate(now, Calendar.DAY_OF_YEAR, expiredDays);
    }

}