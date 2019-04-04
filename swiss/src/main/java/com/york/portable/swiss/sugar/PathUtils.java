package com.york.portable.swiss.sugar;

import com.york.portable.swiss.systeminfo.OSInfo;
import org.apache.commons.lang3.StringUtils;
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

public class PathUtils {
    final static String[] intervals = new String[]{"\\/", "/\\", "\\\\", "//", "\\", "/"};

    public static Path concatBySysteom(String first, String... more) {
        return Paths.get(first, more);
    }

    public static String concat(String... parts) {
        Stream<String> fixPartStream = Arrays.stream(parts).map(c -> StringUtils.replaceEach(c, intervals, new String[]{File.separator, File.separator, File.separator, File.separator, File.separator, File.separator}));
        List<String> fixPartList = fixPartStream.collect(Collectors.toList());
        String[] fixParts = fixPartList.stream().map(c -> {
            String _a = StringUtils.removeStart(c, File.separator);
            String _b = StringUtils.removeEnd(_a, File.separator);
            return _b;
        }).toArray(String[]::new);
        String combined = String.join(File.separator, fixParts);
        String start = fixPartList.get(0).startsWith(File.separator) ? File.separator : StringUtils.EMPTY;
        String end = fixPartList.get(parts.length - 1).endsWith(File.separator) ? File.separator : StringUtils.EMPTY;
        return MessageFormat.format("{0}{1}{2}", start, combined, end);
    }

    final static String INTERVAL_CHAR = "/";
    public static String getPathByResourceUtils(String path) throws FileNotFoundException {
        String urlPath = ResourceUtils.getURL(path).getPath();
        if (OSInfo.isWindows()) {
            urlPath = StringUtils.removeStart(urlPath, INTERVAL_CHAR);
        }
        return urlPath;
    }

    public static String getAbsolutePathByFile(String path) {
        return new File(path).getAbsolutePath();
    }

    public static String getCanonicalPathByFile(String path) throws IOException {
        return new File(path).getCanonicalPath();
    }
    //Relative
}
