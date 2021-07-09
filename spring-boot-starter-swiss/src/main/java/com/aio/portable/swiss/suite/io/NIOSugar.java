package com.aio.portable.swiss.suite.io;

import com.aio.portable.swiss.sugar.CollectionSugar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public abstract class NIOSugar {
    public static class Files {
        public final static void write(String path, String content, Charset charset, OpenOption... options) {
            write(Paths.get(path), content, charset, options);
        }

        public final static void write(Path path, String content, Charset charset, OpenOption... options) {
            try (BufferedWriter writer = java.nio.file.Files.newBufferedWriter(path, charset, options)) {
                writer.write(content);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public final static String read(String path, Charset charset) {
            return read(Paths.get(path), charset);
        }

        public final static String read(Path path, Charset charset) {
            try {
                String content = new String(java.nio.file.Files.readAllBytes(path), charset);
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

        public final static List<Path> listFileNames(String path) {
            return listFileNames(Paths.get(path));
        }

        public final static List<Path> listFileNames(Path path) {
            try (DirectoryStream<Path> stream = java.nio.file.Files.newDirectoryStream(path)) {
                return CollectionSugar.toList(stream.iterator()).stream().map(e -> e.getFileName()).collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public final static List<Path> listFileAbsolutePath(String path) {
            return listFileAbsolutePath(Paths.get(path));
        }

        public final static List<Path> listFileAbsolutePath(Path path) {
            try (DirectoryStream<Path> stream = java.nio.file.Files.newDirectoryStream(path)) {
                return CollectionSugar.toList(stream.iterator()).stream().map(e -> e.toAbsolutePath()).collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public final static Path createDirectories(String path, FileAttribute<?>... attrs) {
            return createDirectories(Paths.get(path), attrs);
        }

        public final static Path createDirectories(Path path, FileAttribute<?>... attrs) {
            try {
                return java.nio.file.Files.createDirectories(path, attrs);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public final static Path createParentDirectories(String path) {
            return createParentDirectories(Paths.get(path));
        }

        public final static Path createParentDirectories(Path path) {
            return createDirectories(path.getParent());
        }

        public final static void deleteIfExists(String path) {
            deleteIfExists(Paths.get(path));
        }

        public final static void deleteIfExists(Path path) {
            try {
                if (new File(path.toString()).isFile())
                    java.nio.file.Files.deleteIfExists(path);
                else {
                    deleteDirectories(path);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        private final static void deleteDirectories(String path){
            deleteDirectories(Paths.get(path));
        }

        private final static void deleteDirectories(Path path) {
            try {
                if (new File(path.toString()).exists()) {
                    java.nio.file.Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            java.nio.file.Files.deleteIfExists(file);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            java.nio.file.Files.deleteIfExists(dir);
                            return FileVisitResult.CONTINUE;
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public final static List<Path> listChildrenFilePath(String path) {
            return listChildrenFilePath(Paths.get(path));
        }

        public final static List<Path> listChildrenFilePath(Path path) {
            final List<Path> paths = new ArrayList<>();
            try {
                java.nio.file.Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (attrs.isRegularFile() && !Objects.equals(file, path)) {
                            paths.add(file);
                        }
                        return super.visitFile(file, attrs);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return paths;
        }


//    public final static <T> List<Path> listChildrenFilePath(String path, Predicate<T> predicate, T t) {
//        return listChildrenFilePath(Paths.get(path), predicate, t);
//    }

        private final static List<Path> listChildrenFilePath(Path path, BiFunction<Path, List<String>, Boolean> biFunction, List<String> condition) {
            final List<Path> paths = new ArrayList<>();
            try {
                java.nio.file.Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (attrs.isRegularFile() && !Objects.equals(file, path)) {
                            if (biFunction.apply(file, condition))
                                paths.add(file);
                        }
                        return super.visitFile(file, attrs);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return paths;
        }


        public final static List<Path> listChildrenFilePath(Path path, String endsWith) {
            List<String> anyEndsWithList = new ArrayList<>();
            anyEndsWithList.add(endsWith);
            return listChildrenFilePath(path, anyEndsWithList);
        }

        public final static List<Path> listChildrenFilePath(String path, String endsWith) {
            return listChildrenFilePath(Paths.get(path), endsWith);
        }

        public final static List<Path> listChildrenFilePath(Path path, List<String> anyEndsWithList) {
            return listChildrenFilePath(path, new BiFunction<Path, List<String>, Boolean>() {
                @Override
                public Boolean apply(Path path, List<String> condition) {
                    return condition.stream().anyMatch(c -> path.toString().endsWith(c));
                }
            }, anyEndsWithList);
        }

        public final static List<Path> listChildrenFilePath(String path, List<String> anyEndsWithList) {
            return listChildrenFilePath(Paths.get(path), anyEndsWithList);
        }
    }
}
