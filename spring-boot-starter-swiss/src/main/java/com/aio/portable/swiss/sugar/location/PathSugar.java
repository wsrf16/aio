package com.aio.portable.swiss.sugar.location;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.system.OSInfo;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PathSugar {
    static final String[] SEPARATORS = new String[]{"\\/", "/\\", "\\\\", "//", "\\", "/"};

    static final String SEPARATOR = File.separator;

    public static final String path(String first, String... more) {
        return Paths.get(first, more).toString();
    }


    public static final String concatByOS(String... parts) {
        return concatBy(SEPARATOR, parts);
    }

    public static final String concatBy(String separator, String... parts) {
        if (CollectionSugar.isEmpty(parts))
            return Constant.EMPTY;

        List<String> fixPartList = Arrays.stream(parts)
                .filter(c -> StringUtils.hasText(c)).collect(Collectors.toList());
        fixPartList = fixPartList.stream().map(c -> StringSugar.replaceEach(c, SEPARATORS, new String[]{separator, separator, separator, separator, separator, separator}))
                .collect(Collectors.toList());
        String[] fixParts = fixPartList.stream().map(c -> {
            c = StringSugar.trim(c, separator);
            return c;
        }).toArray(String[]::new);
        String combined = String.join(separator, fixParts);
        String start = fixPartList.get(0).startsWith(separator) ? separator : Constant.EMPTY;
        String end = fixPartList.get(fixPartList.size() - 1).endsWith(separator) ? separator : Constant.EMPTY;
        return MessageFormat.format("{0}{1}{2}", start, combined, end);
    }

    static final String DELIMITER_CHAR = "/";

    public static final String getPathByResourceUtils(String path) throws FileNotFoundException {
        String urlPath = ResourceUtils.getURL(path).getPath();
        if (OSInfo.isWindows()) {
            urlPath = StringSugar.trimStart(urlPath, DELIMITER_CHAR);
        }
        return urlPath;
    }

    public static final String getAbsolutePathByFile(String path) {
        return new File(path).getAbsolutePath();
    }

    public static final String getCanonicalPathByFile(String path) throws IOException {
        return new File(path).getCanonicalPath();
    }
    //Relative

    public static final Path getJarDirectoryPath(Class<?> sourceClass) {
        return new ApplicationHome(sourceClass).getSource().getParentFile().toPath();
    }

    public static final Path getClassesDirectoryPath(Class<?> sourceClass) {
        return new ApplicationHome(sourceClass).getSource().getParentFile().toPath();
    }

    public static final Path getPath(URL url) {
        try {
            return Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // UriComponentsBuilder UriComponents


}
