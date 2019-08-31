package com.aio.portable.swiss.sugar;

import com.aio.portable.swiss.systeminfo.OSInfo;
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

public abstract class PathUtils {
    final static String[] intervals = new String[]{"\\/", "/\\", "\\\\", "//", "\\", "/"};

    public final static Path concatBySysteom(String first, String... more) {
        return Paths.get(first, more);
    }

    public final static String concat(String... parts) {
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
    public final static String getPathByResourceUtils(String path) throws FileNotFoundException {
        String urlPath = ResourceUtils.getURL(path).getPath();
        if (OSInfo.isWindows()) {
            urlPath = StringUtils.removeStart(urlPath, INTERVAL_CHAR);
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



    private static class BlahUnit {
        private static void todo() {
            String[] directories = new String[]{"/a/\\1\\", "/b/\\2", "c\\3\\", "d",
                    "//e\\\\", "\\/f", "g/\\", "h//"};
            String concat = PathUtils.concat(directories);
        }
    }
}
