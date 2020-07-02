package com.aio.portable.swiss.suite.log.impl.console;

import com.aio.portable.swiss.sugar.DateTimeSugar;
import com.aio.portable.swiss.suite.document.method.PropertiesMapping;
import com.aio.portable.swiss.suite.log.Printer;
import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by York on 2017/11/23.
 */
public class ConsolePrinter implements Printer {
    public static String SECTION_SEPARATOR = PropertiesMapping.instance().getString("SECTION_SEPARATOR", ConsoleConfig.SECTION_SEPARATOR);
    public static String LINE_SEPARATOR = PropertiesMapping.instance().getString("LINE_SEPARATOR", ConsoleConfig.LINE_SEPARATOR);
    public static String TIME_FORMAT = PropertiesMapping.instance().getString("TIME_FORMAT", ConsoleConfig.TIME_FORMAT);
    public static int EMPTY_LINES = PropertiesMapping.instance().getInt("EMPTY_LINES", ConsoleConfig.EMPTY_LINES);


    private String emptyLines = String.join(Constant.EMPTY, Stream.of(Constant.LINE_SEPARATOR).limit(EMPTY_LINES).collect(Collectors.toList()));

    private ConsolePrinter() throws Exception {
        throw new Exception("This is a invalid construction method.");
    }

    String logName;
    String logfilePrefix;

    private ConsolePrinter(String logName, String logfilePrefix) {
        this.logName = logName;
        this.logfilePrefix = logfilePrefix;
    }

    private static Map<String, ConsolePrinter> instanceMaps = new HashMap<>();

    /**
     * 多单例
     *
     * @param logName
     * @param logFilePrefix
     * @return 返回日志格式：[ROOT_LOGFOLDER]\[logName]\[logFilePrefix][SEPARATOR_CHAR][OCCUPY_MAX][SEPARATOR_CHAR][TIMEFORMAT][LOG_EXTENSION]
     */
    public static synchronized ConsolePrinter instance(String logName, String logFilePrefix) {
        String section = String.join(Constant.EMPTY, logName, SECTION_SEPARATOR, logFilePrefix);
        {
            if (instanceMaps.keySet().contains(section))
                return instanceMaps.get(section);
            else {
                ConsolePrinter _loc = new ConsolePrinter(logName, logFilePrefix);
                instanceMaps.put(section, _loc);
                return _loc;
            }
        }
    }


//    final static String prefixTimePattern = "yyyy/MM/dd HH:mm:ss.SSS";

    private static String nowTime() {
        String dateTime = DateTimeSugar.Format.convertDate2Text(DateTimeSugar.Format.FORMAT_ISO8601, new Date());
//        String dateTime = new SimpleDateFormat(prefixTimePattern).format(Calendar.getInstance().getTime());
        return dateTime;
    }

    /**
     * 日志行前缀
     *
     * @return
     */
    private static String LINEPRIFIX() {
        return MessageFormat.format("{0} {1}", nowTime(), Thread.currentThread());
    }

    /**
     * 记录文字
     *
     * @param word
     */
//    public void print(String word) {
//        System.out.print(word);
//        System.out.print(emptyLines);
//    }

    /**
     * 记录一行文字
     *
     * @param line
     */
    @Override
    public void println(String line, LevelEnum level) {
        System.out.println(LINEPRIFIX() + LINE_SEPARATOR + line);
        System.out.print(emptyLines);
    }

    /**
     * 记录多行文字
     *
     * @param lines
     */
//    public void println(List<String> lines) {
//        System.out.println(LINEPRIFIX() + LINE_SEPARATOR);
//        for (String line : lines) {
//            System.out.println(line);
//        }
//        System.out.print(emptyLines);
//    }


}
