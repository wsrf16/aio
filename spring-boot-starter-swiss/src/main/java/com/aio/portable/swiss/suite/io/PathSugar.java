package com.aio.portable.swiss.suite.io;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.global.Global;
import com.aio.portable.swiss.sugar.CollectionSugar;
import com.aio.portable.swiss.suite.systeminfo.OSInfo;
import com.aio.portable.swiss.sugar.StringSugar;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.security.core.parameters.P;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

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
    final static String[] SEPARATORS = new String[]{"\\/", "/\\", "\\\\", "//", "\\", "/"};

    final static String SEPARATOR = File.separator;

    public final static Path concatBySystem(String first, String... more) {
        return Paths.get(first, more);
    }


    public final static String concat(String... parts) {
        return concatBy(SEPARATOR, parts);
    }

    public final static String concatBy(String separator, String... parts) {
        if (CollectionSugar.isEmpty(parts))
            return Constant.EMPTY;

        List<String> fixPartList = Arrays.stream(parts)
                .map(c -> StringSugar.replaceEach(c, SEPARATORS, new String[]{separator, separator, separator, separator, separator, separator}))
                .collect(Collectors.toList());
        String[] fixParts = fixPartList.stream().map(c -> {
            String _a = StringSugar.removeStart(c, separator);
            String _b = StringSugar.removeEnd(_a, separator);
            return _b;
        }).toArray(String[]::new);
        String combined = String.join(separator, fixParts);
        String start = fixPartList.get(0).startsWith(separator) ? separator : Constant.EMPTY;
        String end = fixPartList.get(parts.length - 1).endsWith(separator) ? separator : Constant.EMPTY;
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
