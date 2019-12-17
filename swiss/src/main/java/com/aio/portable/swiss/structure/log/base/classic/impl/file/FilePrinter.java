package com.aio.portable.swiss.structure.log.base.classic.impl.file;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.structure.document.method.PropertiesMapping;
import com.aio.portable.swiss.structure.io.PathSugar;
import com.aio.portable.swiss.structure.log.base.Printer;
import com.aio.portable.swiss.structure.log.base.classic.impl.LoggerConfig;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by York on 2017/11/27.
 */
public class FilePrinter implements Printer {
//    static class LoggerConfig {
//        public final static String PATH_PROGRAM_ROOTFOLDER = Constant.CURRENT_DIRECTORY;
//        public final static String NAME_LOG_ROOTFOLDER = "logs";
//        public final static String LOG_EXTENSION = ".log";
//        public final static String ZIP_EXTENSION = ".zip";
//        public final static String SECTION_SEPARATOR = "\\";
//        public final static String LINE_SEPARATOR = " : ";
//        public final static String OCCUPY_SEPARATOR = "_";
//        public final static String SEPARATOR_CHAR = "_";
//        public final static int OCCUPY_MAX = 30;
//        public final static String TIME_FORMAT = "yyyyMMdd";
//        public final static int EMPTY_LINES = 1;
//    }


    public static String PATH_PROGRAM_ROOTFOLDER = PropertiesMapping.instance().getString("PATH_PROGRAM_ROOTFOLDER", LoggerConfig.PATH_PROGRAM_ROOTFOLDER);
    public static String NAME_LOG_ROOTFOLDER = PropertiesMapping.instance().getString("NAME_LOG_ROOTFOLDER", LoggerConfig.NAME_LOG_ROOTFOLDER);
    public static String LOG_EXTENSION = PropertiesMapping.instance().getString("LOG_EXTENSION", LoggerConfig.LOG_EXTENSION);
    public static String ZIP_EXTENSION = PropertiesMapping.instance().getString("ZIP_EXTENSION", LoggerConfig.ZIP_EXTENSION);
    public static String SECTION_SEPARATOR = PropertiesMapping.instance().getString("SECTION_SEPARATOR", LoggerConfig.SECTION_SEPARATOR);
    public static String LINE_SEPARATOR = PropertiesMapping.instance().getString("LINE_SEPARATOR", LoggerConfig.LINE_SEPARATOR);
    public static String OCCUPY_SEPARATOR = PropertiesMapping.instance().getString("OCCUPY_SEPARATOR", LoggerConfig.OCCUPY_SEPARATOR);
    public static String SEPARATOR_CHAR = PropertiesMapping.instance().getString("SEPARATOR_CHAR", LoggerConfig.SEPARATOR_CHAR);
    public static int OCCUPY_MAX = PropertiesMapping.instance().getInt("OCCUPY_MAX", LoggerConfig.OCCUPY_MAX);
    public static String TIME_FORMAT = PropertiesMapping.instance().getString("TIME_FORMAT", LoggerConfig.TIME_FORMAT);
    public static int EMPTYLINES = PropertiesMapping.instance().getInt("EMPTY_LINES", LoggerConfig.EMPTY_LINES);


    private OutputStreamWriter sw;

    private HashSet<String> swHash = new HashSet<>();
    private Lock lockObject = new ReentrantLock();
    private Lock zipLockObject = new ReentrantLock();
    private static Lock _constructionLock = new ReentrantLock();
    private String emptyLines = String.join(Constant.EMPTY, Stream.of(Constant.LINE_SEPARATOR).limit(EMPTYLINES).collect(Collectors.toList()));
    //private Object _disposeLock = new Object();

//    public HashSet<String> popHasChangedLogs() {
//        swHash.forEach(c ->
//                swHash.remove(c)
//            );
//        return swHash;
//    }

    public String Files() {
        return declareLogFile(this.logName, this.logfilePrefix);
    }


    private FilePrinter() throws Exception {
        throw new NoSuchMethodException("This is a invalid constrction method.");
    }

    String logName;

    String logfilePrefix;

    private FilePrinter(String logName, String logfilePrefix) {
        //lock (_constructionLock)
        {
            this.logName = logName;
            this.logfilePrefix = logfilePrefix;

            //createOrUpdate();
        }
    }

    private static Map<String, FilePrinter> instanceMaps = new HashMap<>();

    /**
     * 多单例
     * @param logName
     * @param logFilePrefix
     * @return 返回日志格式：[ROOT_LOGFOLDER]\[logName]\[logFilePrefix][SEPARATOR_CHAR][OCCUPY_MAX][SEPARATOR_CHAR][TIMEFORMAT][LOG_EXTENSION]
     */
    public synchronized static FilePrinter instance(String logName, String logFilePrefix) {
        String section = String.join(Constant.EMPTY, logName, SECTION_SEPARATOR, logFilePrefix);

        try {
            _constructionLock.lock();
            if (instanceMaps.keySet().contains(section))
                return instanceMaps.get(section);
            else {
                FilePrinter _loc = new FilePrinter(logName, logFilePrefix);
                instanceMaps.put(section, _loc);
                return _loc;
            }
        } finally {
            _constructionLock.unlock();
        }
    }

    /**
     * 建立日志文件夹
     * @param logName
     * @return
     */
    private static String createRootLogFoldIfNotExist(String logName) {
        String _logFolder = PathSugar.concat(PATH_PROGRAM_ROOTFOLDER, NAME_LOG_ROOTFOLDER, logName);
        File dir = new File(_logFolder);
        if (!dir.exists())
            dir.mkdirs();
        return _logFolder;
    }

    /**
     * 定义日志文件名称
     * @param logName
     * @param logFilePrefixes
     * @return
     */
    private static String declareLogFile(String logName, String... logFilePrefixes) {
        String _logFolder = createRootLogFoldIfNotExist(logName);
        String _prefixString = String.join(SEPARATOR_CHAR, logFilePrefixes);
        String dateTime = new SimpleDateFormat(TIME_FORMAT).format(Calendar.getInstance().getTime());
        String logFile = PathSugar.concat(_logFolder, _prefixString + SEPARATOR_CHAR + dateTime + LOG_EXTENSION);
        return logFile;
    }
//private static String declareLogFile(String logName, String logFilePrefix, int occupyNO)
//{
//    return declareLogFile(logName, logFilePrefix + SEPARATOR_CHAR + occupyNO.ToString());
//}

    /**
     * 定义待压缩日志文件名称
     * @param logName
     * @param logfilePrefix
     * @param extension
     * @return
     */
    private static String declareLogFileOfLastDay(String logName, String logfilePrefix, String extension) {
        String _logFolder = createRootLogFoldIfNotExist(logName);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        String dateTime = new SimpleDateFormat(TIME_FORMAT).format(cal.getTime());

        String logFile = PathSugar.concat(_logFolder, logfilePrefix + SEPARATOR_CHAR + dateTime + extension);
        return logFile;
    }

    /**
     * 根据时间，产生或更新日志文件
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    private synchronized void createOrUpdate() throws UnsupportedEncodingException, FileNotFoundException {
        String logFile = declareLogFile(this.logName, this.logfilePrefix);
        String lastLogFile_absolute = declareLogFileOfLastDay(this.logName, this.logfilePrefix, LOG_EXTENSION);
        String lastLogFile_relative = lastLogFile_absolute.substring(lastLogFile_absolute.indexOf(PathSugar.concat(NAME_LOG_ROOTFOLDER, this.logName)));
        String lastZipFile_absolute = declareLogFileOfLastDay(this.logName, this.logfilePrefix, ZIP_EXTENSION);

        try {
            zipLockObject.lock();
            //lock(zipLockObject)
            File _logFile = new File(logFile);
            File _lastZipFile_absolute = new File(lastZipFile_absolute);
//            if (!_logFile.exists() && !_logFile.exists())
//                CompressFileAsync(lastLogFile_relative);
            if (sw != null)
                dispose();
            sw = buildStreamWriter();
        } finally {
            zipLockObject.unlock();
        }


    }

    /**
     * 根据时间，产生或更新日志文件
     * @return
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    private OutputStreamWriter buildStreamWriter() throws UnsupportedEncodingException, FileNotFoundException {
        return buildStreamWriter(0);
    }

    private OutputStreamWriter buildStreamWriter(int occupyNO) throws UnsupportedEncodingException, FileNotFoundException {
        OutputStreamWriter sw = null;
        String logFile;
        //if (occupyNO == 0)
        //    logFile = declareLogFile(this.logName, this.logfilePrefix);
        //else
        //    logFile = declareLogFile(this.logName, this.logfilePrefix, occupyNO);
        logFile = occupyNO == 0 ? declareLogFile(this.logName, this.logfilePrefix)
                : declareLogFile(this.logName, this.logfilePrefix, String.valueOf(occupyNO));
        swHash.add(logFile);
        try {
            sw = new OutputStreamWriter(new FileOutputStream(logFile, true), "UTF-8");
        } catch (Exception e) {
            int iNext = occupyNO + 1;
            //if (e.HResult == -2147024864 && (iNext) <= OCCUPY_MAX)  //如果被占用
            if (iNext <= OCCUPY_MAX)  //如果被占用
            {
                sw = buildStreamWriter(iNext);
            } else {
                throw e;
            }
        }
        return sw;
    }


    final static String prefixTimePattern = "yyyy/MM/dd HH:mm:ss";

    private static String nowTime() {
        String dateTime = new SimpleDateFormat(prefixTimePattern).format(Calendar.getInstance().getTime());
        return dateTime;
    }

    /// <summary>
/// 日志行前缀
/// </summary>
/// <returns></returns>
    private static String LINEPRIFIX() {
        return nowTime();
    }

    /**
     * 记录文字
     * @param word
     */
    public void print(String word) {
        try {
            lockObject.lock();
            try {
                createOrUpdate();       //当开始记录时，才产生日志文件，否则将出现空日志文件
                sw.write(word);
                sw.write(emptyLines);
                sw.flush();
                sw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            lockObject.unlock();
        }
    }

    /**
     * 记录一行文字
     * @param line
     */
    public void println(String line) {
        try
        {
            lockObject.lock();
            try {
                createOrUpdate();       //当开始记录时，才产生日志文件，否则将出现空日志文件
                sw.write(LINEPRIFIX() + LINE_SEPARATOR + line + Constant.LINE_SEPARATOR);
                sw.write(emptyLines);
                sw.flush();
                sw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        finally {
            lockObject.unlock();
        }
    }


    /**
     * 记录多行文字
     * @param lines
     */
    public void println(List<String> lines) {
        try
        {
            lockObject.lock();
            try {
                createOrUpdate();       //当开始记录时，才产生日志文件，否则将出现空日志文件
                sw.write(LINEPRIFIX() + LINE_SEPARATOR + Constant.LINE_SEPARATOR);
                for (String line : lines) {
                    sw.write(line + Constant.LINE_SEPARATOR);
                }
                sw.write(emptyLines);
                sw.flush();
                sw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        finally {
            lockObject.unlock();
        }
    }

    /**
     * 释放日志文件
     */
    public void dispose() {
        try
        {
            lockObject.lock();
            try {
                if (sw != null) {
                    sw.close();
                    String kid = String.join(Constant.EMPTY, this.logName, SECTION_SEPARATOR, this.logfilePrefix);
                    if (instanceMaps.keySet().contains(kid))
                        instanceMaps.remove(kid);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        finally {
            lockObject.unlock();
        }
    }

//    class CustomZip {
//        public static void Compress(String file) {
//            //new SoloMid.Compress.SevenZipper().CompressFile(file);
//            //Library.Compress.ZipHelper.Compress(file);
//        }
//
//        public static void CompressAsync(String file) {
//            //new SoloMid.Compress.SevenZipper().CompressFileAsync(file);
//        }
//    }
}

//public class FilePrinterSample {
//    public static void Do() {
//        for (int i = 0; i < 50000; i++) {
//            FilePrinter logger = FilePrinter.bean("name", "prefix");
//            logger.println("今天是星期天");
//        }
//    }
//}
