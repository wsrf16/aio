package com.aio.portable.swiss.suite.storage.persistence.file;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.sugar.CollectionSugar;
import com.aio.portable.swiss.suite.bean.serializer.SerializerConverter;
import com.aio.portable.swiss.suite.bean.serializer.SerializerConverters;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.io.NIOSugar;
import com.aio.portable.swiss.suite.storage.persistence.NodePersistence;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class FilePO implements NodePersistence {
    private static String DEFAULT_DATABASE = "default";
    private final static String EMPTY = "";

    private static String DEFAULT_ROOT = Paths.get(Constant.CURRENT_DIRECTORY).toString();

    private String database = "_cache";

    private static FilePO instance;

    private static String CACHE_EXTENSION = "cache";

    private static Charset charset = StandardCharsets.UTF_8;

//    protected ISerializerSelector serializer = new SerializerSelector(SerializerEnum.SERIALIZE_FORCE_JACKSON);
    protected SerializerConverter serializerConverter = new SerializerConverters.JacksonConverter();


        public final static FilePO singletonInstance(String root, String database) {
        return instance = instance == null ? new FilePO(root, database) : instance;
    }

    public final static FilePO singletonInstance(String database) {
        return instance = instance == null ? new FilePO(DEFAULT_ROOT, database) : instance;
    }

    public final static FilePO singletonInstance() {
        return instance = instance == null ? new FilePO(DEFAULT_ROOT, DEFAULT_DATABASE) : instance;
    }

    public FilePO(String root, String database) {
        this.database = database;
    }

    public FilePO(String database) {
        this.database = database;
    }

    public FilePO() {
    }


//    private String formatKey(String key) {
//        return MessageFormat.format("{0}.{1}", key, CACHE_EXTENSION);
//    }

    private String formatKey(String node) {
        return MessageFormat.format(".{0}", node);
    }

    private boolean bePropertyFile(String c) {
        return c.startsWith(".");
    }

    @Override
    public String spellPath(String key, String... parent) {
        Path path = Paths.get(database, parent);
        path = Paths.get(path.toString(), key);
        return path.toString();
    }

//    @Override
//    public void set(String key, Object value, String... tables) {
//        String dirPath = spellPath(EMPTY, tables);
//        NIOSugar.Files.createDirectories(dirPath);
//
//        String filename = formatKey(key);
//        String path = spellPath(filename, tables);
//        String content = JacksonSugar.obj2Json(value);
//        NIOSugar.Files.write(path, content, charset, StandardOpenOption.TRUNCATE_EXISTING , StandardOpenOption.CREATE);
//    }

    @Override
    public void set(String key, Object value, String... parent) {
        String dir = spellPath(key, parent);
        NIOSugar.Files.createDirectories(dir);

        String file = spellPath(formatKey(key), CollectionSugar.concat(parent, key));
        NIOSugar.Files.write(file, JacksonSugar.obj2Json(value), charset, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
    }

    @Override
    public void remove(String key, String... parent) {
        check(key, parent);
//        String file = spellPath(formatKey(key), CollectionSugar.concat(parent, key));
//        NIOSugar.Files.deleteIfExists(file);
        String dir = spellPath(key, parent);
        NIOSugar.Files.deleteIfExists(dir);
    }

    @Override
    public void clear(String key, String... parent) {
        check(key, parent);
        List<String> subKeys = keys(key, parent).stream().filter(c -> !bePropertyFile(c)).collect(Collectors.toList());
        subKeys.stream().forEach(c -> remove(c, CollectionSugar.concat(parent, key)));
    }

    @Override
    public boolean exists(String key, String... parent) {
        String filename = formatKey(key);
        String path = spellPath(filename, CollectionSugar.concat(parent, key));
        boolean exists = Files.exists(Paths.get(path));
        return exists;
    }

    @Override
    public List<String> keys(String key, String... parent) {
        check(key, parent);
        String path = spellPath(key, parent);
        List<Path> files = new ArrayList<>();

        try {
            Files.walkFileTree(Paths.get(path), Collections.emptySet(), 1, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (attrs.isDirectory() || attrs.isRegularFile()) {
                        files.add(file);
                    }
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> keys = files.stream().filter(c -> !c.endsWith("." + key)).map(c -> c.getFileName().toString()).collect(Collectors.toList());
        return keys;
    }

//    @Override
//    public List<String> keys(String key, String... parent) {
//        check(key, parent);
//        String path = spellPath(key, parent);
//        List<Path> files = new ArrayList<>();
//
//        try (DirectoryStream<Path> pathDirectoryStream = Files.newDirectoryStream(Paths.get(path))) {
//            files = CollectionSugar.toList(pathDirectoryStream.iterator());
//        } catch (IOException e) {
////            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//        List<String> keys = files.stream().filter(c -> !c.startsWith(".")).map(c -> StringSugar.removeEnd(c.getFileName().toString(), "." + CACHE_EXTENSION)).collect(Collectors.toList());
//        return keys;
//    }

    @Override
    public <T> T get(String key, Class<T> clazz, String... parent) {
        check(key, parent);
        String path = spellPath(formatKey(key), CollectionSugar.concat(parent, key));
        String content = NIOSugar.Files.read(path, charset);
        T t = JacksonSugar.json2T(content, clazz);
        return t;
    }

    @Override
    public <T> T get(String key, TypeReference<T> valueTypeRef, String... parent) {
        check(key, parent);
        String path = spellPath(formatKey(key), CollectionSugar.concat(parent, key));
        String content = NIOSugar.Files.read(path, charset);
        T t = JacksonSugar.json2T(content, valueTypeRef);
        return t;
    }

    @Override
    public <T> Map<String, T> getChildren(String key, Class<T> clazz, String... parent) {
        check(key, parent);
        List<String> subKeys = keys(key, parent).stream().filter(c -> !bePropertyFile(c)).collect(Collectors.toList());
        return subKeys.stream().collect(Collectors.toMap(c -> c, c -> this.get(c, clazz, CollectionSugar.concat(parent, key))));
    }

    @Override
    public <T> Map<String, T> getChildren(String key, TypeReference<T> valueTypeRef, String... parent) {
        check(key, parent);
        List<String> keys = keys(key, parent).stream().filter(c -> !bePropertyFile(c)).collect(Collectors.toList());
        return keys.stream().collect(Collectors.toMap(c -> c, c -> this.get(c, valueTypeRef, CollectionSugar.concat(parent, key))));
    }


    public final static void set(String database, String key, Object value, String... parent) {
        FilePO filePO = FilePO.singletonInstance(database);
        filePO.set(key, value, parent);
    }

    public final static void remove(String database, String key, String... parent) {
        FilePO filePO = FilePO.singletonInstance(database);
        filePO.remove(key, parent);
    }

    public final static void remove(String database) {
        FilePO filePO = FilePO.singletonInstance(database);
        filePO.remove();
    }

    public final static void clear(String database) {
        FilePO filePO = FilePO.singletonInstance(database);
        filePO.clear();
    }

    private final static boolean exists(String database, String key, String... parent) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.exists(key, parent);
    }

    public final static boolean exists(String database) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.exists();
    }

    public final static List<String> keys(String database, String key, String... parent) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.keys(key, parent);
    }

    public final static <T> T get(String database, String key, Class<T> clazz, String... parent) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.get(key, clazz, parent);
    }

    public final static <T> T get(String database, String key, TypeReference<T> valueTypeRef, String... parent) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.get(key, valueTypeRef, parent);
    }

    public final static <T> Map<String, T> getChildren(String database, String key, Class<T> clazz, String... parent) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.getChildren(key, clazz, parent);
    }

    public final static <T> Map<String, T> getChildren(String database, String key, TypeReference<T> valueTypeRef, String... parent) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.getChildren(key, valueTypeRef, parent);
    }

    private void check(String key, String... parent) {
        if (!exists(key, parent)) {
            throw new JsonCacheException.NotExistKeyException(spellPath(key, parent));
        }
    }






    public static class JsonCacheException {
        public static class NotExistKeyException extends RuntimeException {
            public NotExistKeyException() {}
            public NotExistKeyException(String key) {
                super(MessageFormat.format("key {0} is not exist.", key));
            }
        }

//        public static class NotExistTableException extends RuntimeException {
//            public NotExistTableException() {
//            }
//
//            public NotExistTableException(String key) {
//                super(MessageFormat.format("table {0} is not exist.", key));
//            }
//        }

        public static class NotExistDatabaseException extends RuntimeException {
            public NotExistDatabaseException() {
            }

            public NotExistDatabaseException(String key) {
                super(MessageFormat.format("database {0} is not exist.", key));
            }
        }
    }
}