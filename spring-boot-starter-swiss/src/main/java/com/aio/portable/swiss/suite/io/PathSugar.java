package com.aio.portable.swiss.suite.io;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.suite.systeminfo.OSInfo;
import com.aio.portable.swiss.sugar.StringSugar;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class PathSugar {
    final static String[] intervals = new String[]{"\\/", "/\\", "\\\\", "//", "\\", "/"};

    public final static Path concatBySystem(String first, String... more) {
        return Paths.get(first, more);
    }

    public final static String concat(String... parts) {
        Stream<String> fixPartStream = Arrays.stream(parts).map(c -> StringSugar.replaceEach(c, intervals, new String[]{File.separator, File.separator, File.separator, File.separator, File.separator, File.separator}));
        List<String> fixPartList = fixPartStream.collect(Collectors.toList());
        String[] fixParts = fixPartList.stream().map(c -> {
            String _a = StringSugar.removeStart(c, File.separator);
            String _b = StringSugar.removeEnd(_a, File.separator);
            return _b;
        }).toArray(String[]::new);
        String combined = String.join(File.separator, fixParts);
        String start = fixPartList.get(0).startsWith(File.separator) ? File.separator : Constant.EMPTY;
        String end = fixPartList.get(parts.length - 1).endsWith(File.separator) ? File.separator : Constant.EMPTY;
        return MessageFormat.format("{0}{1}{2}", start, combined, end);
    }

    final static String INTERVAL_CHAR = "/";

    public final static String getPathByResourceUtils(String path) throws FileNotFoundException {
        String urlPath = ResourceUtils.getURL(path).getPath();
        if (OSInfo.isWindows()) {
            urlPath = StringSugar.removeStart(urlPath, INTERVAL_CHAR);
        }
        return urlPath;
    }

    public final static String getAbsolutePathByFile(String path) {
        return new File(path).getAbsolutePath();
    }

    public final static String getCanonicalPathByFile(String path) throws IOException {
        return new File(path).getCanonicalPath();
    }
    //Relative

    public final static Path getJarDirectoryPath(Class<?> sourceClass) {
        return new ApplicationHome(sourceClass).getSource().getParentFile().toPath();
    }

    public final static Path getClassesDirectoryPath(Class<?> sourceClass) {
        return new ApplicationHome(sourceClass).getSource().getParentFile().toPath();
    }
}
