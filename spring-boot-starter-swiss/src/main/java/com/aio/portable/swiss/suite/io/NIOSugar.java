package com.aio.portable.swiss.suite.io;

import com.aio.portable.swiss.sugar.type.CollectionSugar;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class NIOSugar {
    public static class Files {
        public static final void write(String path, String content, Charset charset, OpenOption... options) {
            write(Paths.get(path), content, charset, options);
        }

        public static final void write(Path path, String content, Charset charset, OpenOption... options) {
            try (BufferedWriter writer = java.nio.file.Files.newBufferedWriter(path, charset, options)) {
                writer.write(content);
            } catch (IOException e) {
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public static final String read(String path, Charset charset) {
            return read(Paths.get(path), charset);
        }

        public static final String read(String path) {
            return read(Paths.get(path), Charset.defaultCharset());
        }

        public static final String read(Path path, Charset charset) {
            try {
                return new String(java.nio.file.Files.readAllBytes(path), charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static final String read(Path path) {
            return read(path, Charset.defaultCharset());
        }

//        Files.newBufferedReader()

//    public static final void write(Path path, String content, OpenOption... options) {
//        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, options)) {
//            writer.write(content);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

        public static final List<Path> listFileNames(String path) {
            return listFileNames(Paths.get(path));
        }

        public static final List<Path> listFileNames(Path path) {
            try (DirectoryStream<Path> stream = java.nio.file.Files.newDirectoryStream(path)) {
                return CollectionSugar.toList(stream.iterator()).stream().map(e -> e.getFileName()).collect(Collectors.toList());
            } catch (IOException e) {
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public static final List<Path> listFileAbsolutePath(String path) {
            return listFileAbsolutePath(Paths.get(path));
        }

        public static final List<Path> listFileAbsolutePath(Path path) {
            try (DirectoryStream<Path> stream = java.nio.file.Files.newDirectoryStream(path)) {
                return CollectionSugar.toList(stream.iterator()).stream().map(e -> e.toAbsolutePath()).collect(Collectors.toList());
            } catch (IOException e) {
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public static final Path createDirectories(String path, FileAttribute<?>... attrs) {
            return createDirectories(Paths.get(path), attrs);
        }

        public static final Path createDirectories(Path path, FileAttribute<?>... attrs) {
            try {
                return java.nio.file.Files.createDirectories(path, attrs);
            } catch (IOException e) {
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public static final Path createParentDirectories(String path) {
            return createParentDirectories(Paths.get(path));
        }

        public static final Path createParentDirectories(Path path) {
            return createDirectories(path.getParent());
        }

        public static final void deleteIfExists(String path) {
            deleteIfExists(Paths.get(path));
        }

        public static final void deleteIfExists(Path path) {
            try {
                if (new File(path.toString()).isFile())
                    java.nio.file.Files.deleteIfExists(path);
                else {
                    deleteDirectories(path);
                }
            } catch (IOException e) {
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        private static final void deleteDirectories(String path) {
            deleteDirectories(Paths.get(path));
        }

        private static final void deleteDirectories(Path path) {
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
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }


        public static final List<Path> listChildrenFilePath(String path) {
            return listChildrenFilePath(Paths.get(path));
        }

        public static final List<Path> listChildrenFilePath(Path path) {
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
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return paths;
        }


//    public static final <T> List<Path> listChildrenFilePath(String path, Predicate<T> predicate, T t) {
//        return listChildrenFilePath(Paths.get(path), predicate, t);
//    }

        private static final List<Path> listChildrenFilePath(Path path, BiFunction<Path, List<String>, Boolean> biFunction, List<String> condition) {
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
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return paths;
        }


        public static final List<Path> listChildrenFilePath(Path path, String endsWith) {
            List<String> anyEndsWithList = new ArrayList<>();
            anyEndsWithList.add(endsWith);
            return listChildrenFilePath(path, anyEndsWithList);
        }

        public static final List<Path> listChildrenFilePath(String path, String endsWith) {
            return listChildrenFilePath(Paths.get(path), endsWith);
        }

        public static final List<Path> listChildrenFilePath(Path path, List<String> anyEndsWithList) {
            return listChildrenFilePath(path, new BiFunction<Path, List<String>, Boolean>() {
                @Override
                public Boolean apply(Path path, List<String> condition) {
                    return condition.stream().anyMatch(c -> path.toString().endsWith(c));
                }
            }, anyEndsWithList);
        }

        public static final List<Path> listChildrenFilePath(String path, List<String> anyEndsWithList) {
            return listChildrenFilePath(Paths.get(path), anyEndsWithList);
        }
    }


//    public static long orderWrite(byte[] buffer, int offset, File file) {
//        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
//            try (FileChannel channel = randomAccessFile.getChannel()) {
//                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 1);
//                map.position(offset);
//                map.put(buffer);
//                return map.position();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public static MappedByteBuffer mmapRead(Path from, long position, long size) {
        OpenOption[] options = {StandardOpenOption.READ, StandardOpenOption.WRITE};
        return mmapRead(from, position, size, options);
    }

    public static MappedByteBuffer mmapRead(Path from, long position, long size, OpenOption... options) {
            try (FileChannel channel = FileChannel.open(from, options)) {
                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, position, size);
                return map;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, mode)) {
//            try (FileChannel channel = randomAccessFile.getChannel()) {
//                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, position, size);
//                return map;
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void mmapWrite(MappedByteBuffer mappedByteBuffer, Path to, OpenOption... options) {
        try (FileChannel fileChannel = FileChannel.open(to, options)) {
            fileChannel.write(mappedByteBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mmapWrite(MappedByteBuffer mappedByteBuffer, Path to) {
        OpenOption[] options = {StandardOpenOption.READ, StandardOpenOption.WRITE};
        mmapWrite(mappedByteBuffer, to, options);
    }

    public static void mmapCopy(Path from, long position, long size, OpenOption[] fromOptions, Path to, OpenOption[] toOptions) {
        MappedByteBuffer mappedByteBuffer = mmapRead(from, position, size, fromOptions);
        mmapWrite(mappedByteBuffer, to, toOptions);
    }

    public static void mmapCopy(Path from, long position, long size, Path to) {
        OpenOption[] options = {StandardOpenOption.READ, StandardOpenOption.WRITE};
        MappedByteBuffer mappedByteBuffer = mmapRead(from, position, size, options);
        mmapWrite(mappedByteBuffer, to, options);
    }

    public static void sendFile(Path from, Path to, long position, long size, OpenOption... options) {
        try (FileChannel readChannel = FileChannel.open(from, StandardOpenOption.READ)) {
            try (FileChannel writeChannel = FileChannel.open(to, options)) {
                readChannel.transferTo(position, size, writeChannel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
