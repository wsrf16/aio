package com.aio.portable.park.unit.swiss;

import com.aio.portable.swiss.structure.log.base.classic.impl.console.ConsoleLogger;
import com.aio.portable.swiss.structure.log.base.classic.impl.file.FileLogger;
import com.aio.portable.swiss.structure.log.base.classic.impl.slf4j.Slf4jLogger;
import com.aio.portable.swiss.structure.log.base.LogHub;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.HashMap;
import java.util.Map;

@TestComponent
public class LoghubTest {

    @Test
    public static void sample() {

        {
            ConsoleLogger consoleLogger = ConsoleLogger.build("custom");
//                consoleLogger.getSerializer().setSerializer(SerializerEnum.JACKXML);
            consoleLogger.d("this is console.");
        }

        {
            LogHub loggerSet = LogHub.build(ConsoleLogger.build(), FileLogger.build());
            loggerSet = LogHub.build(Slf4jLogger.build());
            loggerSet.d("this is loghub.");
        }
//            {
//                Log4j2Logger log4j2Logger = Log4j2Logger.build();
//                log4j2Logger.i("this is log4j2logger.");
//            }
        {
//                org.slf4j.Logger loggerBack = org.slf4j.LoggerFactory.getLogger();
            Slf4jLogger slf4jLogger = Slf4jLogger.build();
            slf4jLogger.i("this is logback.");
        }
        {
            try {
                int a = 0;
                int b = 1 / a;
            } catch (Exception e) {
                ConsoleLogger logger = ConsoleLogger.build();
                logger.e(e);
            }
        }
    }

    @Test
    public static void hubSample() {
        ConsoleLogger logger1 = ConsoleLogger.build("logDir");
        logger1.d("aaaa");

        FileLogger logger2 = FileLogger.build("logDir");
        //logger2.openSingleIPPrefix();
        logger2.d("aaaa");

        LogHub hub = LogHub.build(logger1, logger2);
        Map<String, String> map = new HashMap<String, String>();

        try {
            int a = 1;
            int b = 0;
            int c = a / b;
        } catch (Exception e) {
            map.put("a", "1");
            map.put("b", "2");
            map.put("c", "3");
            map.put("d", "4");
            hub.e("summary", map, e);
        }
    }

}
