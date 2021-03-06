package com.aio.portable.swiss.suite.log.impl.console;

import com.aio.portable.swiss.global.ColorEnum;
import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.global.Global;
import com.aio.portable.swiss.sugar.DateTimeSugar;
import com.aio.portable.swiss.sugar.StringSugar;
import com.aio.portable.swiss.suite.log.facade.Printer;
import com.aio.portable.swiss.suite.log.support.LevelEnum;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by York on 2017/11/23.
 */
public class ConsolePrinter implements Printer {
    public static String SECTION_SEPARATOR = ConsoleConfig.SECTION_SEPARATOR;
    public static String LINE_SEPARATOR = ConsoleConfig.LINE_SEPARATOR;
    public static String TIME_FORMAT = ConsoleConfig.TIME_FORMAT;
    public static int EMPTY_LINES = ConsoleConfig.EMPTY_LINES;


    private String emptyLines = String.join(Constant.EMPTY, Stream.of(Constant.LINE_SEPARATOR).limit(EMPTY_LINES).collect(Collectors.toList()));

//    private ConsolePrinter() throws Exception {
//        throw new Exception("This is a invalid construction method.");
//    }

    private String name;
    private String prefix;
    private ConsoleLogProperties properties;

    private ConsolePrinter(String name, String prefix, ConsoleLogProperties properties) {
        this.name = name;
        this.prefix = prefix;
        this.properties = properties;
    }

    private static Map<String, ConsolePrinter> instanceMaps = new HashMap<>();

    /**
     * 多单例
     *
     * @param logName
     * @param logFilePrefix
     * @return 返回日志格式：[ROOT_LOGFOLDER]\[logName]\[logFilePrefix][SEPARATOR_CHAR][OCCUPY_MAX][SEPARATOR_CHAR][TIMEFORMAT][LOG_EXTENSION]
     */
    public static synchronized ConsolePrinter instance(String logName, String logFilePrefix, ConsoleLogProperties consoleLogProperties) {
        String section = String.join(Constant.EMPTY, logName, SECTION_SEPARATOR, logFilePrefix);
        {
            if (instanceMaps.keySet().contains(section))
                return instanceMaps.get(section);
            else {
                ConsolePrinter _loc = new ConsolePrinter(logName, logFilePrefix, consoleLogProperties);
                instanceMaps.put(section, _loc);
                return _loc;
            }
        }
    }


    //    final static String prefixTimePattern = "yyyy/MM/dd HH:mm:ss.SSS";
    protected String nowTime() {
        String dateTime = DateTimeSugar.Format.convertDate2Text(DateTimeSugar.Format.FORMAT_NORMAL_LONGEST, new Date());
//        String dateTime = new SimpleDateFormat(prefixTimePattern).format(Calendar.getInstance().getTime());
        return dateTime;
    }

    /**
     * 日志行前缀
     *
     * @return
     */
    protected String prefix() {
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
        if (properties.getEnabled()) {
            String _level;
            switch (level) {
                case VERBOSE: {
                    _level = StringSugar.wrap(level.getName().toUpperCase(), ColorEnum.FG_DEFAULT);
                }
                break;
                case TRACE: {
                    _level = StringSugar.wrap(level.getName().toUpperCase(), ColorEnum.FG_DEFAULT);
                }
                break;
                case DEBUG: {
                    _level = StringSugar.wrap(level.getName().toUpperCase(), ColorEnum.FG_GREEN);
                }
                break;
                case INFORMATION: {
                    _level = StringSugar.wrap(level.getName().toUpperCase(), ColorEnum.FG_BLUE);
                }
                break;
                case WARNING: {
                    _level = StringSugar.wrap(level.getName().toUpperCase(), ColorEnum.FG_RED);
                }
                break;
                case ERROR: {
                    _level = StringSugar.wrap(level.getName().toUpperCase(), ColorEnum.FG_RED, ColorEnum.BOLD);
                }
                break;
                case FATAL: {
                    _level = StringSugar.wrap(level.getName().toUpperCase(), ColorEnum.FG_RED, ColorEnum.STRIKETHROUGH, ColorEnum.STRIKETHROUGH);
                }
                break;
                default: {
                    _level = null;
                    Global.unsupportedOperationException(name + ": unknown level");
                }
                break;
            }
            String output = MessageFormat.format("{1} {2}{0}{3}", LINE_SEPARATOR, prefix(), _level, line);
            System.out.println(output);
        }

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
