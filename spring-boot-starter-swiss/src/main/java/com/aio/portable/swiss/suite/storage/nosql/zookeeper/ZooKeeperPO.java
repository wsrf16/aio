package com.aio.portable.swiss.suite.storage.nosql.zookeeper;

import com.aio.portable.swiss.module.zookeeper.ZooKeeperSugar;
import com.aio.portable.swiss.sugar.StringSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.storage.nosql.KeyValuePersistence;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ZooKeeperPO implements KeyValuePersistence {
    private String database;

    public void setDatabase(String database) {
        this.database = database;
    }

    private ZooKeeper zooKeeper;

    private static ZooKeeperPO instance;

    public final static ZooKeeperPO singletonInstance(ZooKeeper zooKeeper) {
        return instance = instance == null ? new ZooKeeperPO(zooKeeper) : instance;
    }

    public ZooKeeperPO(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }
    

    private static String join(String... node) {
        List<String> stringList = Arrays.asList(node);
        String join = "/" + String.join("/", node);
        return join;
    }

    @Override
    public void set(String table, String key, Object value) {
        String json = JacksonSugar.obj2Json(value);
        byte[] bytes = json.getBytes();
        String path = join(database, table, key);
        boolean exists = ZooKeeperSugar.exists(zooKeeper, path, false);
        if (exists)
            ZooKeeperSugar.setData(zooKeeper, path, bytes);
        else
            ZooKeeperSugar.create(zooKeeper, path, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Override
    public void createTable(String table) {
        String path = join(database, table);
        ZooKeeperSugar.create(zooKeeper, path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Override
    public void remove(String table, String key) {
        String path = join(database, table, key);
        ZooKeeperSugar.deleteIfExists(zooKeeper, path);
    }

    @Override
    public void clearTable(String table) {
        String path = join(database, table);

//        List<String> childrenFullPath = ZooKeeperSugar.getAbsoluteChildren(zooKeeper, path, false, false);
//        childrenFullPath.stream().forEach(c -> ZooKeeperSugar.clearIfExists(zooKeeper, c));
        ZooKeeperSugar.clearIfExists(zooKeeper, path);
    }

    @Override
    public void removeTable(String table) {
        String path = join(database, table);
        ZooKeeperSugar.deleteIfExists(zooKeeper, path);
    }

    @Override
    public void clearDatabase() {
        String path = join(database);
//        List<String> childrenFullPath = ZooKeeperSugar.getAbsoluteChildren(zooKeeper, path, false, false);
//        childrenFullPath.stream().forEach(c -> ZooKeeperSugar.clearIfExists(zooKeeper, c));
        ZooKeeperSugar.clearIfExists(zooKeeper, path);
    }

    @Override
    public void removeDatabase() {
        String path = join(database);
        ZooKeeperSugar.deleteIfExists(zooKeeper, path);
    }

    @Override
    public <T> T get(String table, String key, Class<T> clazz) {
        String path = join(database, table, key);
        byte[] bytes = ZooKeeperSugar.getData(zooKeeper, path, false);
        String json = new String(bytes);
        T t = JacksonSugar.json2T(json, clazz);
        return t;
    }

    @Override
    public <T> T get(String table, String key, TypeReference<T> valueTypeRef) {
        String path = join(database, table, key);
        byte[] bytes = ZooKeeperSugar.getData(zooKeeper, path, false);
        String json = new String(bytes);
        T t = JacksonSugar.json2T(json, valueTypeRef);
        return t;
    }

    @Override
    public List<String> getChildren(String table) {
        String path = join(database, table);
        List<String> relativeChildren = ZooKeeperSugar.getRelativeChildren(zooKeeper, path, false);
        return relativeChildren;
    }

    @Override
    public <T> Map<String, T> getAll(String table, Class<T> clazz) {
        String path = join(database, table);
        List<String> absoluteChildren = ZooKeeperSugar.getAbsoluteChildren(zooKeeper, path, false, false);
        Map<String, T> collect = absoluteChildren
                .stream()
                .collect(Collectors.toMap(key -> StringSugar.removeStart(key, path + "/"),
                        c -> {
                            byte[] bytes = ZooKeeperSugar.getData(zooKeeper, c, false);
                            T t = JacksonSugar.json2T(new String(bytes), clazz);
                            return t;
                        }));
        return collect;
    }

    @Override
    public <T> Map<String, T> getAll(String table, TypeReference<T> valueTypeRef) {
        String path = join(database, table);
        List<String> absoluteChildren = ZooKeeperSugar.getAbsoluteChildren(zooKeeper, path, false, false);
        Map<String, T> collect = absoluteChildren
                .stream()
                .collect(Collectors.toMap(key -> StringSugar.removeStart(key, path + "/"),
                        c -> {
                            byte[] bytes = ZooKeeperSugar.getData(zooKeeper, c, false);
                            T t = JacksonSugar.json2T(new String(bytes), valueTypeRef);
                            return t;
                        }));
        return collect;
    }

    @Override
    public boolean exists(String table, String key) {
        String path = join(database, table, key);
        boolean exists = ZooKeeperSugar.exists(zooKeeper, path, false);
        return exists;
    }

    @Override
    public boolean existsTable(String table) {
        String path = join(database, table);
        boolean exists = ZooKeeperSugar.exists(zooKeeper, path, false);
        return exists;
    }

    @Override
    public boolean existsDatabase() {
        String path = join(database);
        boolean exists = ZooKeeperSugar.exists(zooKeeper, path, false);
        return exists;
    }

    @Override
    public List<String> keys(String table) {
        String path = join(database, table);
        List<String> absoluteChildren = ZooKeeperSugar.getAbsoluteChildren(zooKeeper, path, false, false);
        List<String> collect = absoluteChildren.stream().map(key -> StringSugar.removeStart(key, path + "/")).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<String> tables() {
        String path = join(database);
        List<String> AbsoluteChildren = ZooKeeperSugar.getAbsoluteChildren(zooKeeper, path, false, false);
        List<String> collect = AbsoluteChildren.stream()
                .map(table -> StringSugar.removeStart(table, path + "/"))
                .collect(Collectors.toList());
        return collect;
    }
}
