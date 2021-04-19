package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.sugar.DateTimeSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@TestComponent
public class DateTimeTest {
    @Test
    public static void foobar() {
        long longg = DateTimeSugar.UnixTime.nowUnix();
        long longgg = DateTimeSugar.UnixTime.convertDateTime2Unix(LocalDateTime.now());
        LocalDateTime time = DateTimeSugar.UnixTime.convertUnix2DateTime(longg);
        long longggg = DateTimeSugar.UnixTime.convertUTC2Unix(time);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        Date first = DateTimeSugar.CalendarUtils.getFirstDayOfMonth(calendar).getTime();
        Date last = DateTimeSugar.CalendarUtils.getLastDayOfMonth(calendar).getTime();

        DateTimeSugar.Format.convertDate2Text("yyyy-MM-dd 00:00:00", first);
        DateTimeSugar.Format.convertDate2Text("yyyy-MM-dd 23:59:59", last);
    }
}
