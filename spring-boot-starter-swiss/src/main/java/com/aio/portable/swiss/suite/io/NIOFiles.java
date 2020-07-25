package com.aio.portable.swiss.suite.io;

import com.aio.portable.swiss.sugar.CollectionSugar;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.stream.Collectors;

public abstract class NIOFiles {
    public final static void write(Path path, Charset charset, String content, OpenOption... options) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, charset, options)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String read(Path path, Charset charset) {
        try {
            String content = new String(Files.readAllBytes(path), charset);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//        Files.newBufferedReader()

//    public final static void write(Path path, String content, OpenOption... options) {
//        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, options)) {
//            writer.write(content);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

    public final static List<Path> listFileNames(Path path) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)){
            return CollectionSugar.toList(stream.iterator()).stream().map(e -> e.getFileName()).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static void createDirectories(Path path, FileAttribute<?>... attrs) {
        try {
            Files.createDirectories(path, attrs);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
