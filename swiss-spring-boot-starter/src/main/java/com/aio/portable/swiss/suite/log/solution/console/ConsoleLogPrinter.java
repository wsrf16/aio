package com.aio.portable.swiss.suite.log.solution.console;

import com.aio.portable.swiss.global.ColorEnum;
import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.global.Global;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.log.facade.LogPrinter;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.StandardLogRecordItem;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by York on 2017/11/23.
 */
public class ConsoleLogPrinter implements LogPrinter {
    public static String SECTION_SEPARATOR = ConsoleConstant.SECTION_SEPARATOR;
    public static String LINE_SEPARATOR = ConsoleConstant.LINE_SEPARATOR;
    public static String TIME_FORMAT = ConsoleConstant.TIME_FORMAT;
    public static int EMPTY_LINES = ConsoleConstant.EMPTY_LINES;


    private String emptyLines = String.join(Constant.EMPTY, Stream.of(Constant.LINE_SEPARATOR).limit(EMPTY_LINES).collect(Collectors.toList()));

    private String name;

    private ConsoleLogProperties properties;

    private ConsoleLogPrinter(String name, ConsoleLogProperties properties) {
        this.name = name;
        this.properties = properties;
    }

    private static Map<String, ConsoleLogPrinter> instanceMaps = new HashMap<>();

    /**
     * 多单例
     *
     * @param logName
     * @return 返回日志格式：[ROOT_LOGFOLDER]\[logName]\[OCCUPY_MAX][SEPARATOR_CHAR][TIMEFORMAT][LOG_EXTENSION]
     */
    public static synchronized ConsoleLogPrinter instance(String logName, ConsoleLogProperties consoleLogProperties) {
        String section = String.join(Constant.EMPTY, logName, SECTION_SEPARATOR);
        {
            if (instanceMaps.keySet().contains(section))
                return instanceMaps.get(section);
            else {
                ConsoleLogPrinter printer = new ConsoleLogPrinter(logName, consoleLogProperties);
                instanceMaps.put(section, printer);
                return printer;
            }
        }
    }


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
     * 记录一行文字
     *
     * @param record
     */
    @Override
    public void println(Object record, LevelEnum level) {
        String line = getSmartSerializerAdapter(level).serialize(record);
        if (properties.getEnabled()) {
            String coloredLevel;
            switch (level) {
                case VERB: {
                    coloredLevel = StringSugar.paint(level.getName().toUpperCase(), ColorEnum.FG_DEFAULT);
                }
                break;
                case TRACE: {
                    coloredLevel = StringSugar.paint(level.getName().toUpperCase(), ColorEnum.FG_DEFAULT);
                }
                break;
                case DEBUG: {
                    coloredLevel = StringSugar.paint(level.getName().toUpperCase(), ColorEnum.FG_GREEN);
                }
                break;
                case INFO: {
                    coloredLevel = StringSugar.paint(level.getName().toUpperCase(), ColorEnum.FG_BLUE);
                }
                break;
                case WARN: {
                    coloredLevel = StringSugar.paint(level.getName().toUpperCase(), ColorEnum.FG_RED);
                }
                break;
                case ERROR: {
                    coloredLevel = StringSugar.paint(level.getName().toUpperCase(), ColorEnum.FG_RED, ColorEnum.BOLD);
                }
                break;
                case FATAL: {
                    coloredLevel = StringSugar.paint(level.getName().toUpperCase(), ColorEnum.FG_RED, ColorEnum.STRIKETHROUGH);
                }
                break;
                default: {
                    coloredLevel = null;
                    Global.unsupportedOperationException(name + ": unknown level");
                }
                break;
            }
            String thread = StringSugar.paint(Thread.currentThread().toString(), ColorEnum.FG_YELLOW);
            String output = MessageFormat.format("{1}{0}{2}{0}{3}{0}{4}", LINE_SEPARATOR, nowTime(), thread, coloredLevel, line);
//            output = output
//                    .replace("\"name\"", "\"" + StringSugar.paint("name", ColorEnum.UNDERLINE) + "\"")
//                    .replace("\"summary\"", "\"" + StringSugar.paint("summary", ColorEnum.UNDERLINE) + "\"")
//                    .replace("\"message\"", "\"" + StringSugar.paint("message", ColorEnum.UNDERLINE) + "\"")
//                    .replace("\"exception\"", "\"" + StringSugar.paint("exception", ColorEnum.UNDERLINE) + "\"")
//            ;
//            output = formatOutput(output, "\"level\"", "\"name\"", "\"summary\"" ,"\"message\"", "\"data\"", "\"exception\"");
            output = formatOutput(output, getPropertyNameArray());

            println(output);
        }

    }

    private static String[] propertyNameArray;
    private static final synchronized String[] getPropertyNameArray() {
        if (propertyNameArray == null) {
            propertyNameArray = ClassSugar.PropertyDescriptors.getAllPropertyNames(StandardLogRecordItem.class);
            for (int i = 0; i < propertyNameArray.length; i++) {
                propertyNameArray[i] = "\"" + propertyNameArray[i] + "\"";
            }
        }
        return propertyNameArray;
    }



    private String formatOutput(String output, String... texts) {
        String item = output;
        for (String text : texts) {
            item = item.replaceAll(text + "(?=:)", StringSugar.paint(text, ColorEnum.UNDERLINE));
        }
        return item;
    }

    private static final void println(String output) {
        System.out.println(output);
    }

}
