package com.aio.portable.swiss.global;

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
        public static final long MILLISECOND = 1;
        public static final long SECOND = 1000 * MILLISECOND;
        public static final long MINUTE = 60 * SECOND;
        public static final long HOUR = 60 * MINUTE;
        public static final long DAY = 24 * HOUR;
        public static final long WEEK = 7 * DAY;
    }


    public static final String CURRENT_DIRECTORY = System.getProperty("user.dir");

    public static final String CLASS_PATH = System.getProperty("java.class.path");
    // \r\n
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    // ; :
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");

    //public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    // \/
    public static final String FILE_SEPARATOR = File.separator;

    public static final String EMPTY = "";


}
