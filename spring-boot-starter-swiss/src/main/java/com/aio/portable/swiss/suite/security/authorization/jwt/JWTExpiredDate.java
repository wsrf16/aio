package com.aio.portable.swiss.suite.security.authorization.jwt;

import com.aio.portable.swiss.sugar.type.DateTimeSugar;

import java.util.Calendar;
import java.util.Date;

public class JWTExpiredDate {
    private Date issuedAt;
    private Date expiresAt;
    private int expiredMinutes;

    public Date getIssuedAt() {
        return issuedAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public int getExpiredMinutes() {
        return expiredMinutes;
    }

    public boolean isValid() {
        return issuedAt != null && expiresAt != null;
    }

    public JWTExpiredDate(int expiredMinutes) {
        this.expiredMinutes = expiredMinutes;

        Calendar now = DateTimeSugar.CalendarUtils.now();
        this.issuedAt = now.getTime();
        this.expiresAt = JWTSugar.getExpiredMinutes(now, expiredMinutes);
    }
}