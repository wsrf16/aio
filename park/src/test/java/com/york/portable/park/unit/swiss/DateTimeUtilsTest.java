package com.york.portable.park.unit.swiss;

import com.york.portable.swiss.sugar.DateTimeUtils;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@TestComponent
public class DateTimeUtilsTest {

    @Test
    public void main() {
        long longg = DateTimeUtils.UnixTime.nowUnix();
        long longgg = DateTimeUtils.UnixTime.convertDateTime2Unix(LocalDateTime.now());
        LocalDateTime time = DateTimeUtils.UnixTime.convertUnix2DateTime(longg);
        long longggg = DateTimeUtils.UnixTime.convertUTC2Unix(time);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        Date first = DateTimeUtils.CalendarUtils.getFirstDayOfMonth(calendar).getTime();
        Date last = DateTimeUtils.CalendarUtils.getLastDayOfMonth(calendar).getTime();

        DateTimeUtils.Format.convertDate2String("yyyy-MM-dd 00:00:00", first);
        DateTimeUtils.Format.convertDate2String("yyyy-MM-dd 23:59:59", last);
    }
}
