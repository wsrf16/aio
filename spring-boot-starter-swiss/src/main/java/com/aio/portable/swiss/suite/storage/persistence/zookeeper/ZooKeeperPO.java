package com.aio.portable.swiss.suite.storage.persistence.zookeeper;

import com.aio.portable.swiss.middleware.zookeeper.ZooKeeperSugar;
import com.aio.portable.swiss.sugar.CollectionSugar;
import com.aio.portable.swiss.sugar.StringSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.sugar.PathSugar;
import com.aio.portable.swiss.suite.storage.persistence.NodePersistence;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ZooKeeperPO implements NodePersistence {
    private final static String DELIMITER = "/";
    private final static String EMPTY = "";

    private String database;

    public void setDatabase(String database) {
        this.database = database;
    }

    private ZooKeeper zooKeeper;

    private static ZooKeeperPO instance;

//    public final static ZooKeeperPO singletonInstance(ZooKeeper zooKeeper) {
//        return instance = instance == null ? new ZooKeeperPO(zooKeeper) : instance;
//    }

    public final static ZooKeeperPO singletonInstance(ZooKeeper zooKeeper, String database) {
        return instance = instance == null ? new ZooKeeperPO(zooKeeper, database) : instance;
    }

//    public ZooKeeperPO(ZooKeeper zooKeeper) {
//        this.zooKeeper = zooKeeper;
//    }

    public ZooKeeperPO(ZooKeeper zooKeeper, String database) {
        this.zooKeeper = zooKeeper;
        this.database = database;
    }


    @Override
    public String spellPath(String key, String... parent) {
        List<String> tableList = new ArrayList<>();
        tableList.add("/" + database);
        tableList.addAll(Arrays.asList(parent));
        if (StringUtils.hasLength(key))
            tableList.add(key);

        String[] join = CollectionSugar.toArray(tableList, String[]::new);
        String path = PathSugar.concatBy(DELIMITER, join);
        return path;
    }

    @Override
    public void set(String key, Object value, String... parent) {
        String path = spellPath(key, parent);
        boolean exists = ZooKeeperSugar.exists(zooKeeper, path, false);
        byte[] bytes = JacksonSugar.obj2Json(value).getBytes();
        if (exists)
            ZooKeeperSugar.setData(zooKeeper, path, bytes);
        else
            ZooKeeperSugar.create(zooKeeper, path, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Override
    public void remove(String key, String... parent) {
        String path = spellPath(key, parent);
        ZooKeeperSugar.deleteIfExists(zooKeeper, path);
    }

    @Override
    public void clear(String key, String... parent) {
        String path = spellPath(key, parent);

//        List<String> childrenFullPath = ZooKeeperSugar.getAbsoluteChildren(zooKeeper, path, false, false);
//        childrenFullPath.stream().forEach(c -> ZooKeeperSugar.clearIfExists(zooKeeper, c));
        ZooKeeperSugar.clearIfExists(zooKeeper, path);
    }

    @Override
    public boolean exists(String key, String... parent) {
        String path = spellPath(key, parent);
        boolean exists = ZooKeeperSugar.exists(zooKeeper, path, false);
        return exists;
    }

    @Override
    public List<String> keys(String key, String... parent) {
        String path = spellPath(key, parent);
        List<String> relativeChildren = ZooKeeperSugar.getRelativeChildren(zooKeeper, path, false);
        return relativeChildren;
    }

//    @Override
//    public List<String> keys(String table, String... parent) {
//        String path = spellPath(table, parent);
//        List<String> absoluteChildren = ZooKeeperSugar.getAbsoluteChildren(zooKeeper, path, false, false);
//        List<String> collect = absoluteChildren.stream().map(key -> StringSugar.removeStart(key, path + DELIMITER)).collect(Collectors.toList());
//        return collect;
//    }

    @Override
    public <T> T get(String key, Class<T> clazz, String... parent) {
        String path = spellPath(key, parent);
        byte[] bytes = ZooKeeperSugar.getData(zooKeeper, path, false);
        T t = JacksonSugar.json2T(new String(bytes), clazz);
        return t;
    }

    @Override
    public <T> T get(String key, TypeReference<T> valueTypeRef, String... parent) {
        String path = spellPath(key, parent);
        byte[] bytes = ZooKeeperSugar.getData(zooKeeper, path, false);
        T t = JacksonSugar.json2T(new String(bytes), valueTypeRef);
        return t;
    }

    @Override
    public <T> Map<String, T> getChildren(String key, Class<T> clazz, String... parent) {
        String path = spellPath(key, parent);
        List<String> absoluteChildren = ZooKeeperSugar.getAbsoluteChildren(zooKeeper, path, false, false);
        Map<String, T> collect = absoluteChildren
                .stream()
                .collect(Collectors.toMap(c -> StringSugar.trimStart(c, path + DELIMITER),
                        c -> {
                            byte[] bytes = ZooKeeperSugar.getData(zooKeeper, c, false);
                            T t = JacksonSugar.json2T(new String(bytes), clazz);
                            return t;
                        }));
        return collect;
    }

    @Override
    public <T> Map<String, T> getChildren(String key, TypeReference<T> valueTypeRef, String... parent) {
        String path = spellPath(key, parent);
        List<String> absoluteChildren = ZooKeeperSugar.getAbsoluteChildren(zooKeeper, path, false, false);
        Map<String, T> collect = absoluteChildren
                .stream()
                .collect(Collectors.toMap(c -> StringSugar.trimStart(c, path + DELIMITER),
                        c -> {
                            byte[] bytes = ZooKeeperSugar.getData(zooKeeper, c, false);
                            T t = JacksonSugar.json2T(new String(bytes), valueTypeRef);
                            return t;
                        }));
        return collect;
    }

}
