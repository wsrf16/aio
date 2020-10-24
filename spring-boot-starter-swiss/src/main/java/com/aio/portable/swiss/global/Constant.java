package com.aio.portable.swiss.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Constant {
    public class StorageUnit {
        public final static long KB = 1024;
        public final static long MB = 1 * KB * KB;
        public final static long GB = 1 * KB * MB;
        public final static long TB = 1 * KB * GB;
        public final static long PB = 1 * KB * TB;
        public final static long EB = 1 * KB * PB;
        public final static long ZB = 1 * KB * EB;
        public final static long YB = 1 * KB * ZB;
        public final static long NB = 1 * KB * YB;
        public final static long DB = 1 * KB * NB;
    }

    public class TimeUnit {
        public final static long MilliSecond = 1;
        public final static long Second = 1000 * MilliSecond;
        public final static long Minute = 60 * Second;
        public final static long Hour = 60 * Minute;
        public final static long Day = 24 * Hour;
        public final static long Week = 7 * Day;
    }


    public final static String LIBRARY_DIRECTORY = "Lib";
    public final static String CURRENT_DIRECTORY = System.getProperty("user.dir");
    public final static String CLASS_PATH = System.getProperty("java.class.path");
    // \r\n
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    // ; :
    public final static String PATH_SEPARATOR = System.getProperty("line.separator");
    //public final static String FILE_SEPARATOR = System.getProperty("file.separator");

    // \
    public final static String FILE_SEPARATOR = File.separator;
    public final static String EMPTY = "";

    public static class OriginEnum {
        public final static Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
        public final static HttpStatus DEFAULT_HTTPSTATUS = HttpStatus.OK;
        public final static MediaType DEFAULT_MEDIATYPE = MediaType.APPLICATION_JSON;
    }

//    class Directory {
//        public final static String ExecutingAssemblyDirectory = System.IO.Path.GetDirectoryName(System.Reflection.Assembly.GetExecutingAssembly().Location);
//        public final static String EntryAssemblyDirectory = System.IO.Path.GetDirectoryName(System.Reflection.Assembly.GetEntryAssembly().Location);
//        public final static String CallingAssemblyDirectory = System.IO.Path.GetDirectoryName(System.Reflection.Assembly.GetCallingAssembly().Location);
//        public final static String CurrentDomainBaseDirectory = System.AppDomain.CurrentDomain.BaseDirectory;
//        public final static String EnvironmentCurrentDirectory = System.Environment.CURRENT_DIRECTORY;
//        public final static String CURRENT_DIRECTORY = System.IO.Directory.GetCurrentDirectory();

//        public String DirectoryOf(System.Reflection.Assembly assembly) {
//            String directory = System.IO.Path.GetDirectoryName(assembly.Location);
//            return directory;
//        }
//    }
}
