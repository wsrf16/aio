package com.aio.portable.swiss.suite.storage.nosql.file;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.sugar.StringSugar;
import com.aio.portable.swiss.suite.bean.serializer.ISerializerSelector;
import com.aio.portable.swiss.suite.bean.serializer.SerializerEnum;
import com.aio.portable.swiss.suite.bean.serializer.SerializerSelector;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.io.NIOFiles;
import com.aio.portable.swiss.suite.storage.nosql.KeyValuePersistence;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilePO implements KeyValuePersistence {
    private static String DEFAULT_DATABASE = "default";

    private static Path DEFAULT_ROOT = Paths.get(Constant.CURRENT_DIRECTORY);


    private Path root;

    private String database = "_cache";

    private static FilePO instance;

    private static String CACHE_EXTENSION = "cache";

    private Charset charset = StandardCharsets.UTF_8;

    protected ISerializerSelector serializer = new SerializerSelector(SerializerEnum.SERIALIZE_FORCE_JACKSON);


    public final static FilePO singletonInstance(Path root, String database) {
        return instance = instance == null ? new FilePO(root, database) : instance;
    }

    public final static FilePO singletonInstance(String database) {
        return instance = instance == null ? new FilePO(DEFAULT_ROOT, database) : instance;
    }

    public final static FilePO singletonInstance() {
        return instance = instance == null ? new FilePO(DEFAULT_ROOT, DEFAULT_DATABASE) : instance;
    }

    public FilePO(Path root, String database) {
        this.root = root;
        this.database = database;
    }

    public FilePO(String database) {
        this.database = database;
    }

    public FilePO() {
    }

    @Override
    public void set(String table, String key, Object value) {
        Path dirPath = Paths.get(root.toString(), database, table);
        NIOFiles.createDirectories(dirPath);

        String filename = MessageFormat.format("{0}.{1}", key, CACHE_EXTENSION);
        Path path = Paths.get(root.toString(), database, table, filename);
//        String content = serializer.serialize(value);
        String content = JacksonSugar.obj2Json(value);
        NIOFiles.write(path, charset, content, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }

    @Override
    public void createTable(String table) {
        Path dirPath = Paths.get(root.toString(), database, table);
        NIOFiles.createDirectories(dirPath);
    }

    @Override
    public void remove(String table, String key) {
        checkTable(table);
        String filename = MessageFormat.format("{0}.{1}", key, CACHE_EXTENSION);
        Path path = Paths.get(root.toString(), database, table, filename);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearTable(String table) {
        checkTable(table);
        FilePO jsonCache = FilePO.singletonInstance(database);
        List<String> keys = keys(table);
        keys.stream().forEach(key -> jsonCache.remove(table, key));
    }

    @Override
    public void removeTable(String table) {
        checkDatabase();
        Path path = Paths.get(root.toString(), database, table);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearDatabase() {
        checkDatabase();
        FilePO jsonCache = FilePO.singletonInstance(database);
        List<String> tables = tables();
        tables.stream().forEach(table -> jsonCache.removeTable(table));
    }

    @Override
    public void removeDatabase() {
        Path path = Paths.get(root.toString(), database);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T get(String table, String key, Class<T> clazz) {
        checkKey(table, key);
        String filename = MessageFormat.format("{0}.{1}", key, CACHE_EXTENSION);
        Path path = Paths.get(root.toString(), database, table, filename);
        String content = NIOFiles.read(path, charset);
        T t = JacksonSugar.json2T(content, clazz);
        return t;
    }

    @Override
    public <T> T get(String table, String key, TypeReference<T> valueTypeRef) {
        checkKey(table, key);
        String filename = MessageFormat.format("{0}.{1}", key, CACHE_EXTENSION);
        Path path = Paths.get(root.toString(), database, table, filename);
        String content = NIOFiles.read(path, charset);
        T t = JacksonSugar.json2T(content, valueTypeRef);
        return t;
    }

    @Override
    public List<String> getChildren(String table) {
//        NIOFiles.
//        return get(table, String.class);
        return null;
    }

    @Override
    public <T> Map<String, T> getAll(String table, Class<T> clazz) {
        checkTable(table);
        List<String> keys = keys(table);
        return keys.stream().collect(Collectors.toMap(key -> key, key -> this.get(table, key, clazz)));
    }

    @Override
    public <T> Map<String, T> getAll(String table, TypeReference<T> valueTypeRef) {
        checkTable(table);
        List<String> keys = keys(table);
        return keys.stream().collect(Collectors.toMap(key -> key, key -> this.get(table, key, valueTypeRef)));
    }

    @Override
    public boolean exists(String table, String key) {
        String filename = MessageFormat.format("{0}.{1}", key, CACHE_EXTENSION);
        Path path = Paths.get(root.toString(), database, table, filename);
        boolean exists = Files.exists(path);
        return exists;
    }

    @Override
    public boolean existsTable(String table) {
        Path path = Paths.get(root.toString(), database, table);
        boolean exists = Files.exists(path);
        return exists;
    }

    @Override
    public boolean existsDatabase() {
        Path path = Paths.get(root.toString());
        boolean exists = Files.exists(path);
        return exists;
    }

    @Override
    public List<String> keys(String table) {
        checkTable(table);
        Path path = Paths.get(root.toString(), database, table);
        final List<Path> files = new ArrayList<>();

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
//                    return super.visitFile(file, attrs);
                    if (!attrs.isDirectory()) {
                        files.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> keys = files.stream().map(c -> StringSugar.removeEnd(c.getFileName().toString(), "." + CACHE_EXTENSION)).collect(Collectors.toList());
        return keys;
    }

    @Override
    public List<String> tables() {
        checkDatabase();
        Path path = Paths.get(root.toString(), database);
        final List<Path> paths = new ArrayList<>();

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
//                    return super.preVisitDirectory(dir, attrs);
                    if (attrs.isDirectory() && !Objects.equals(dir, path)) {
                        paths.add(dir);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> keys = paths.stream().map(c -> c.getFileName().toString()).collect(Collectors.toList());
        return keys;
    }


    public final static void set(String database, String table, String key, Object value) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        jsonCache.set(table, key, value);
    }

    public final static void createTable(String database, String table) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        jsonCache.createTable(table);
    }

    public final static void remove(String database, String table, String key) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        jsonCache.remove(table, key);
    }

    public final static void clearTable(String database, String table) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        jsonCache.clearTable(table);
    }

    public final static void removeTable(String database, String table) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        jsonCache.removeTable(table);
    }

    public final static void clearDatabase(String database) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        jsonCache.clearDatabase();
    }

    public final static void removeDatabase(String database) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        jsonCache.removeDatabase();
    }

    public final static <T> T get(String database, String table, String key, Class<T> clazz) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        return jsonCache.get(table, key, clazz);
    }

    public final static <T> T get(String database, String table, String key, TypeReference<T> valueTypeRef) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        return jsonCache.get(table, key, valueTypeRef);
    }

    public final static <T> Map<String, T> getAll(String database, String table, Class<T> clazz) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        List<String> keys = FilePO.keys(database, table);
        return keys.stream().collect(Collectors.toMap(key -> key, key -> jsonCache.get(table, key, clazz)));
    }

    public final static <T> Map<String, T> getAll(String database, String table, TypeReference<T> valueTypeRef) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        List<String> keys = FilePO.keys(database, table);
        return keys.stream().collect(Collectors.toMap(key -> key, key -> jsonCache.get(table, key, valueTypeRef)));
    }

    public final static boolean exists(String database, String table, String key) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        return jsonCache.exists(table, key);
    }

    public final static boolean existsTable(String database, String table) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        return jsonCache.existsTable(table);
    }

    public final static boolean existsDatabase(String database, String table, String key) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        return jsonCache.existsDatabase();
    }

    public final static List<String> tables(String database) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        return jsonCache.tables();
    }

    public final static List<String> keys(String database, String table) {
        FilePO jsonCache = FilePO.singletonInstance(database);
        return jsonCache.keys(table);
    }


    private void checkKey(String table, String key) {
        if (!existsDatabase())
            throw new JsonCacheException.NotExistDatabaseException(database);
        if (!existsTable(database, table)) {
            throw new JsonCacheException.NotExistTableException(table);
        }
        if (!exists(database, table, key)) {
            throw new JsonCacheException.NotExistKeyException(key);
        }
    }

    private void checkTable(String table) {
        if (!existsDatabase())
            throw new JsonCacheException.NotExistDatabaseException(database);
        if (!existsTable(database, table)) {
            throw new JsonCacheException.NotExistTableException(table);
        }
    }

    private void checkDatabase() {
        if (!existsDatabase())
            throw new JsonCacheException.NotExistDatabaseException(database);
    }

    private static void checkKey(String database, String table, String key){
        FilePO jsonCache = FilePO.singletonInstance(database);
        jsonCache.checkKey(table, key);
    }

    private static void checkTable(String database, String table){
        FilePO jsonCache = FilePO.singletonInstance(database);
        jsonCache.checkTable(table);
    }

    private static void checkDatabase(String database){
        FilePO jsonCache = FilePO.singletonInstance(database);
        jsonCache.checkDatabase();
    }






    public static class JsonCacheException {
        public static class NotExistKeyException extends RuntimeException {
            public NotExistKeyException() {}
            public NotExistKeyException(String key) {
                super(MessageFormat.format("key {0} is not exist.", key));
            }
        }

        public static class NotExistTableException extends RuntimeException {
            public NotExistTableException() {
            }

            public NotExistTableException(String key) {
                super(MessageFormat.format("table {0} is not exist.", key));
            }
        }

        public static class NotExistDatabaseException extends RuntimeException {
            public NotExistDatabaseException() {
            }

            public NotExistDatabaseException(String key) {
                super(MessageFormat.format("database {0} is not exist.", key));
            }
        }
    }
}