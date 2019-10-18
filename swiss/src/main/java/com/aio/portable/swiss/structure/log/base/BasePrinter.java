package com.aio.portable.swiss.structure.log.base;

//public class BasePrinter implements Printer {
//    static class LoggerConfig {
//        public final static String PATH_PROGRAM_ROOTFOLDER = Constant.CURRENT_DIRECTORY;
//        public final static String SECTION_SEPARATOR = Constant.FILE_SEPARATOR;
//        public final static String LINE_SEPARATOR = " : ";
//        public final static String OCCUPY_SEPARATOR = "_";
//        public final static String SEPARATOR_CHAR = "_";
//        public final static String TIME_FORMAT = "yyyyMMdd";
//        public final static int EMPTY_LINES = 1;
//    }
//
//
//    public static String SECTION_SEPARATOR = PropertiesMapping.instance().getString("SECTION_SEPARATOR", ConsolePrinter.LoggerConfig.SECTION_SEPARATOR);
//    public static String LINE_SEPARATOR = PropertiesMapping.instance().getString("LINE_SEPARATOR", ConsolePrinter.LoggerConfig.LINE_SEPARATOR);
//    public static String TIME_FORMAT = PropertiesMapping.instance().getString("TIME_FORMAT", ConsolePrinter.LoggerConfig.TIME_FORMAT);
//    public static int EMPTYLINES = PropertiesMapping.instance().getInt("EMPTY_LINES", ConsolePrinter.LoggerConfig.EMPTY_LINES);
//
//
//    private static Map<String, RabbitPrinter> instanceMaps = new HashMap<>();
//
//
//    String logName;
//    String logfilePrefix;
//    LogRabbitMQProperties configuration;
//
//    private BasePrinter(String logName, String logfilePrefix, LogRabbitMQProperties configuration) {
//        this.logName = logName;
//        this.logfilePrefix = logfilePrefix;
//        this.configuration = configuration;
//    }
//
//    /**
//     * 多单例
//     *
//     * @param logName
//     * @param logFilePrefix
//     * @return 返回日志格式：[ROOT_LOGFOLDER]\[logName]\[logFilePrefix][SEPARATOR_CHAR][OCCUPY_MAX][SEPARATOR_CHAR][TIMEFORMAT][LOG_EXTENSION]
//     */
//    public static synchronized BasePrinter instance(String logName, String logFilePrefix, LogRabbitMQProperties configuration) {
//        String section = String.join(Constant.EMPTY, logName, SECTION_SEPARATOR, logFilePrefix);
//        {
//            if (instanceMaps.keySet().contains(section))
//                return instanceMaps.get(section);
//            else {
//                RabbitPrinter _loc = new BasePrinter(logName, logFilePrefix, configuration);
//                instanceMaps.put(section, _loc);
//                return _loc;
//            }
//        }
//    }
//}
