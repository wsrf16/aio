package com.aio.portable.swiss.suite.storage.persistence.file;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.sugar.CollectionSugar;
import com.aio.portable.swiss.sugar.StringSugar;
import com.aio.portable.swiss.suite.bean.serializer.SerializerConverter;
import com.aio.portable.swiss.suite.bean.serializer.SerializerConverters;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.io.NIOFiles;
import com.aio.portable.swiss.suite.storage.persistence.NodePersistence;
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

public class FilePO implements NodePersistence {
    private static String DEFAULT_DATABASE = "default";
    private final static String EMPTY = "";

    private static String DEFAULT_ROOT = Paths.get(Constant.CURRENT_DIRECTORY).toString();


    private String root;

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
        this.root = root;
        this.database = database;
    }

    public FilePO(String database) {
        this.database = database;
    }

    public FilePO() {
    }


    private String formatKey(String key) {
        return MessageFormat.format("{0}.{1}", key, CACHE_EXTENSION);
    }

    private String formatTable(String table) {
        return MessageFormat.format(".{0}", table);
    }

    private boolean bePropertyFile(String c) {
        return c.startsWith(".");
    }

    @Override
    public String spellPath(String node, String... prefixes) {
        Path path = Paths.get(root, database);
        path = Paths.get(path.toString(), prefixes);
        path = Paths.get(path.toString(), node);
        return path.toString();
    }

    @Override
    public void set(String key, Object value, String... tables) {
        String dirPath = spellPath(EMPTY, tables);
        NIOFiles.createDirectories(dirPath);

        String filename = formatKey(key);
        String path = spellPath(filename, tables);
        String content = JacksonSugar.obj2Json(value);
        NIOFiles.write(path, content, charset, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }

    @Override
    public void setTable(String table, Object value, String... tables) {
        String dirPath = spellPath(table, tables);
        NIOFiles.createDirectories(dirPath);

        String filename = formatTable(table);
        String content = JacksonSugar.obj2Json(value);
        String path = spellPath(filename, CollectionSugar.concat(String[]::new, tables, table));
        NIOFiles.write(path, content, charset, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }

    @Override
    public void remove(String key, String... tables) {
        String filename = formatKey(key);
        Path path = Paths.get(spellPath(filename, tables));
        NIOFiles.deleteIfExists(path);
    }

    @Override
    public void clearTable(String table, String... tables) {
        checkTable(table, tables);
        FilePO filePO = FilePO.singletonInstance(database);
        List<String> keys = keys(table, tables).stream().filter(c -> !bePropertyFile(c)).collect(Collectors.toList());
        keys.stream().forEach(key -> filePO.remove(key, tables));
    }

    @Override
    public void removeTable(String table, String... tables) {
        checkDatabase();
        String filename = formatTable(table);
        String dirTable = spellPath(table, tables);
        String path = spellPath(filename, CollectionSugar.concat(String[]::new, tables, table));

        NIOFiles.deleteIfExists(path);
        NIOFiles.deleteIfExists(dirTable);
    }

    @Override
    public void clearDatabase() {
        checkDatabase();
        FilePO filePO = FilePO.singletonInstance(database);
        List<String> tables = tables();
        tables.stream().forEach(table -> filePO.removeTable(table));
    }

    @Override
    public void removeDatabase() {
        String path = spellPath(EMPTY);
        NIOFiles.deleteIfExists(path);
    }

    @Override
    public <T> T get(String key, Class<T> clazz, String... tables) {
        checkKey(key, tables);
        String filename = formatKey(key);
        String path = spellPath(filename, tables);
        String content = NIOFiles.read(path, charset);
        T t = JacksonSugar.json2T(content, clazz);
        return t;
    }

    @Override
    public <T> T get(String key, TypeReference<T> valueTypeRef, String... tables) {
        checkKey(key, tables);
        String filename = formatKey(key);
        String path = spellPath(filename, tables);
        String content = NIOFiles.read(path, charset);
        T t = JacksonSugar.json2T(content, valueTypeRef);
        return t;
    }

    @Override
    public <T> T getTable(String table, Class<T> clazz, String... tables) {
        checkTable(table, tables);
        String filename = formatTable(table);
        String path = spellPath(filename, CollectionSugar.concat(String[]::new, tables, table));
        String content = NIOFiles.read(path, charset);
        T t = JacksonSugar.json2T(content, clazz);
        return t;
    }

    @Override
    public <T> T getTable(String table, TypeReference<T> valueTypeRef, String... tables) {
        checkTable(table, tables);
        String filename = formatTable(table);
//        String dirTable = spellPath(table, tables);
        String path = spellPath(filename, tables);
        String content = NIOFiles.read(path, charset);
        T t = JacksonSugar.json2T(content, valueTypeRef);
        return t;
    }

    @Override
    public List<String> getChildren(String table, String... tables) {
        checkTable(table, tables);
        String path = spellPath(table, tables);
        List<Path> files = new ArrayList<>();

        try {
            Files.walkFileTree(Paths.get(path), null, 1, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (attrs.isDirectory() || attrs.isRegularFile()) {
                        files.add(file);
                    }
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        List<String> keys = files.stream().filter(c -> !c.startsWith(".")).map(c -> StringSugar.removeEnd(c.getFileName().toString(), "." + CACHE_EXTENSION)).collect(Collectors.toList());
        return keys;
    }

    @Override
    public <T> Map<String, T> getAll(String table, Class<T> clazz, String... tables) {
        checkTable(table, tables);
        List<String> keys = keys(table, tables).stream().filter(c -> !bePropertyFile(c)).collect(Collectors.toList());
        return keys.stream().collect(Collectors.toMap(key -> key, key -> this.get(key, clazz, tables)));
    }

    @Override
    public <T> Map<String, T> getAll(String table, TypeReference<T> valueTypeRef, String... tables) {
        checkTable(table, tables);
        List<String> keys = keys(table, tables).stream().filter(c -> !bePropertyFile(c)).collect(Collectors.toList());
        return keys.stream().collect(Collectors.toMap(key -> key, key -> this.get(key, valueTypeRef, tables)));
    }

    @Override
    public <T> Map<String, T> getAllTable(String table, Class<T> clazz, String... tables) {
        checkTable(table, tables);
        List<String> keys = keys(table, tables).stream().filter(c -> !bePropertyFile(c)).collect(Collectors.toList());
        return keys.stream().collect(Collectors.toMap(key -> key, key -> this.getTable(key, clazz, tables)));
    }

    @Override
    public <T> Map<String, T> getAllTable(String table, TypeReference<T> valueTypeRef, String... tables) {
        checkTable(table, tables);
        List<String> keys = keys(table, tables).stream().filter(c -> !bePropertyFile(c)).collect(Collectors.toList());
        return keys.stream().collect(Collectors.toMap(key -> key, key -> this.getTable(key, valueTypeRef, tables)));
    }

    @Override
    public boolean exists(String key, String... tables) {
        String filename = formatKey(key);
        String path = spellPath(filename, tables);
        boolean exists = Files.exists(Paths.get(path));
        return exists;
    }

    @Override
    public boolean existsTable(String table, String... tables) {
        String path = spellPath(table, tables);
        boolean exists = Files.exists(Paths.get(path));
        return exists;
    }

    @Override
    public boolean existsDatabase() {
        String path = spellPath(EMPTY);
        boolean exists = Files.exists(Paths.get(path));
        return exists;
    }

    @Override
    public List<String> keys(String table, String... tables) {
        checkTable(table, tables);
        String path = spellPath(table, tables);
        List<Path> files = new ArrayList<>();

        try (DirectoryStream<Path> pathDirectoryStream = Files.newDirectoryStream(Paths.get(path))) {
//            Files.walkFileTree(path, null, 1, new SimpleFileVisitor<Path>() {
//                @Override
//                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
////                    return super.visitFile(file, attrs);
//                    if (attrs.isDirectory() || attrs.isRegularFile()) {
//                        files.add(file);
//                    }
//                    return FileVisitResult.CONTINUE;
//                }
//            });
//            Files.newDirectoryStream(path).iterator()

            files = CollectionSugar.toList(pathDirectoryStream.iterator());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        List<String> keys = files.stream().filter(c -> !c.startsWith(".")).map(c -> StringSugar.removeEnd(c.getFileName().toString(), "." + CACHE_EXTENSION)).collect(Collectors.toList());
        return keys;
    }

    @Override
    public List<String> tables() {
        checkDatabase();
        Path path = Paths.get(root, database);
        final List<Path> paths = new ArrayList<>();

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (attrs.isDirectory() && !Objects.equals(dir, path)) {
                        paths.add(dir);
                    }
                    return super.preVisitDirectory(dir, attrs);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        List<String> keys = paths.stream().map(c -> c.getFileName().toString()).collect(Collectors.toList());
        return keys;
    }


    public final static void set(String database, String table, String key, Object value, String... tables) {
        FilePO filePO = FilePO.singletonInstance(database);
        filePO.set(key, value, CollectionSugar.concat(String[]::new, tables, table));
    }

    public final static void setTable(String database, String table, String... tables) {
        FilePO filePO = FilePO.singletonInstance(database);
        filePO.setTable(table, tables);
    }

    public final static void remove(String database, String table, String key, String... tables) {
        FilePO filePO = FilePO.singletonInstance(database);
        filePO.remove(key, CollectionSugar.concat(String[]::new, tables, table));
    }

    public final static void clearTable(String database, String table, String... tables) {
        FilePO filePO = FilePO.singletonInstance(database);
        filePO.clearTable(table, tables);
    }

    public final static void removeTable(String database, String table, String... tables) {
        FilePO filePO = FilePO.singletonInstance(database);
        filePO.removeTable(table, tables);
    }

    public final static void clearDatabase(String database) {
        FilePO filePO = FilePO.singletonInstance(database);
        filePO.clearDatabase();
    }

    public final static void removeDatabase(String database) {
        FilePO filePO = FilePO.singletonInstance(database);
        filePO.removeDatabase();
    }

    public final static <T> T get(String database, String table, String key, Class<T> clazz, String... tables) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.get(key, clazz, CollectionSugar.concat(String[]::new, tables, table));
    }

    public final static <T> T get(String database, String table, String key, TypeReference<T> valueTypeRef, String... tables) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.get(key, valueTypeRef, CollectionSugar.concat(String[]::new, tables, table));
    }

    public final static <T> Map<String, T> getAll(String database, String table, Class<T> clazz, String... tables) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.getAll(table, clazz, tables);
    }

    public final static <T> Map<String, T> getAll(String database, String table, TypeReference<T> valueTypeRef, String... tables) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.getAll(table, valueTypeRef, tables);
    }

    public final static <T> Map<String, T> getAllTable(String database, String table, Class<T> clazz, String... tables) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.getAllTable(table, clazz, tables);
    }

    public final static <T> Map<String, T> getAllTable(String database, String table, TypeReference<T> valueTypeRef, String... tables) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.getAllTable(table, valueTypeRef, tables);
    }

    public final static boolean exists(String database, String key, String... tables) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.exists(key, tables);
    }

    public final static boolean existsTable(String database, String table, String... tables) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.existsTable(table, tables);
    }

    public final static boolean existsDatabase(String database) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.existsDatabase();
    }

    public final static List<String> tables(String database) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.tables();
    }

    public final static List<String> keys(String database, String table, String... tables) {
        FilePO filePO = FilePO.singletonInstance(database);
        return filePO.keys(table, tables);
    }


    private void checkKey(String key, String... tables) {
        checkDatabase();
        if (!exists(key, tables)) {
            throw new JsonCacheException.NotExistKeyException(spellPath(key, tables));
        }
    }

    private void checkTable(String table, String... tables) {
        checkDatabase();
        if (!existsTable(table, tables)) {
            throw new JsonCacheException.NotExistTableException(spellPath(table, tables));
        }
    }

    private void checkDatabase() {
        if (!existsDatabase())
            throw new JsonCacheException.NotExistDatabaseException(database);
    }

//    private static void checkKey(String database, String table, String key, String... tables){
//        FilePO filePO = FilePO.singletonInstance(database);
//        filePO.checkKey(table, key);
//    }
//
//    private static void checkTable(String database, String table, String... tables){
//        FilePO filePO = FilePO.singletonInstance(database);
//        filePO.checkTable(table, tables);
//    }
//
//    private static void checkDatabase(String database){
//        FilePO filePO = FilePO.singletonInstance(database);
//        filePO.checkDatabase();
//    }






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