package com.aio.portable.swiss.swiss;

//import com.aio.portable.swiss.suite.log.factory.classic.ConsoleHubFactory;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class LoghubTest {
//
//    @Test
//    public static void foobar() {
//        {
//            LogHub log = AppLogHubFactory.singletonInstance().build();//.setSamplerRate(1f);
//            List<String> list = null;
//            try {
//                list = new ArrayList<>();
//                list.add("list");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            log.debug("a", "b");
//            log.info("a", list);
//            log.info("a{}{}{}", new String[]{"b", "c", "d"});
//            log.info("a", "list{}", new Object[]{"aaa"});
//        }
//        {
//            ConsoleLog consoleLogger = ConsoleLog.build("custom");
////                consoleLogger.getSerializer().setSerializer(SerializerEnum.JACKXML);
//            consoleLogger.d("this is console.");
//        }
//
//        {
////            LogHub loggerSet = LogHub.build(ConsoleLog.build(), FileLog.build());
////            loggerSet = LogHub.build(Slf4JLog.build());
////            loggerSet.d("this is loghub.");
//        }
////            {
////                Log4j2Logger log4j2Logger = Log4j2Logger.build();
////                log4j2Logger.i("this is log4j2logger.");
////            }
//        {
////            Slf4JLog slf4jLogger = Slf4JLog.build();
////            slf4jLogger.i("this is logback.");
//        }
//        {
//            try {
//                int a = 0;
//                int b = 1 / a;
//            } catch (Exception e) {
//                ConsoleLog logger = ConsoleLog.build();
//                logger.e(e);
//            }
//        }
//    }
//
//    @Test
//    public static void hubSample() {
//        ConsoleLog logger1 = ConsoleLog.build("logDir");
//        logger1.d("aaaa");
//
//        FileLog logger2 = FileLog.build("logDir");
//        //logger2.openSingleIPPrefix();
//        logger2.d("aaaa");
//
//        LogHub hub = LogHub.build(logger1, logger2);
//        Map<String, String> map = new HashMap<String, String>();
//
//        try {
//            int a = 1;
//            int b = 0;
//            int c = a / b;
//        } catch (Exception e) {
//            map.put("a", "1");
//            map.put("b", "2");
//            map.put("c", "3");
//            map.put("d", "4");
//            hub.e("summary", map, e);
//        }
//    }

}
