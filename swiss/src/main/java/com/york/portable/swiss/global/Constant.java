package com.york.portable.swiss.global;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Constant {
    public class StorageUnit {
        public static final long KB = 1024;
        public static final long MB = 1 * KB * KB;
        public static final long GB = 1 * KB * MB;
        public static final long TB = 1 * KB * GB;
        public static final long PB = 1 * KB * TB;
        public static final long EB = 1 * KB * PB;
        public static final long ZB = 1 * KB * EB;
        public static final long YB = 1 * KB * ZB;
        public static final long NB = 1 * KB * YB;
        public static final long DB = 1 * KB * NB;
    }

    public class TimeUnit {
        public static final long MilliSecond = 1;
        public static final long Second = 1000 * MilliSecond;
        public static final long Minute = 60 * Second;
        public static final long Hour = 60 * Minute;
        public static final long Day = 24 * Hour;
        public static final long Week = 7 * Day;
    }

    public static final String LIBRARY_DIRECTORY = "Lib";
    public static final String CURRENT_DIRECTORY = System.getProperty("user.dir");
    public static final String CLASS_PATH = System.getProperty("java.class.path");
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    //public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String FILE_SEPARATOR = File.separator;
    public static final String EMPTY = StringUtils.EMPTY;

    public static class OriginEnum {
        public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
        public static final HttpStatus DEFAULT_HTTPSTATUS = HttpStatus.OK;
        public static final MediaType DEFAULT_MEDIATYPE = MediaType.APPLICATION_JSON;
    }

//    class Directory {
//        public static final String ExecutingAssemblyDirectory = System.IO.Path.GetDirectoryName(System.Reflection.Assembly.GetExecutingAssembly().Location);
//        public static final String EntryAssemblyDirectory = System.IO.Path.GetDirectoryName(System.Reflection.Assembly.GetEntryAssembly().Location);
//        public static final String CallingAssemblyDirectory = System.IO.Path.GetDirectoryName(System.Reflection.Assembly.GetCallingAssembly().Location);
//        public static final String CurrentDomainBaseDirectory = System.AppDomain.CurrentDomain.BaseDirectory;
//        public static final String EnvironmentCurrentDirectory = System.Environment.CURRENT_DIRECTORY;
//        public static final String CURRENT_DIRECTORY = System.IO.Directory.GetCurrentDirectory();

//        public String DirectoryOf(System.Reflection.Assembly assembly) {
//            String directory = System.IO.Path.GetDirectoryName(assembly.Location);
//            return directory;
//        }
//    }
}
