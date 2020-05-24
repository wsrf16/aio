package com.aio.portable.swiss.module.zookeeper;

import com.aio.portable.swiss.global.Constant;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ZooKeeperSugar {
    public final static ZooKeeper build(String connectString, int sessionTimeout, Watcher watcher) throws IOException {
        return new ZooKeeper(connectString, sessionTimeout, watcher);
    }

    private static List<String> wholePath(List<String> children, String path) {
        List<String> ret = new ArrayList<>();
        for (String child : children) {
            if (path.trim().equals("/"))
                ret.add(path + child);
            else
                ret.add(path + "/" + child);
        }
        return ret;
    }

    private String combine(String... paths) {
        StringBuffer sb = new StringBuffer();
        String item = paths[0].endsWith("/") ? paths[0].substring(0, paths[0].length() - 1) : paths[0];
        sb.append(item);
        for (int i = 1; i < paths.length; i++) {
            String path = paths[i];
            if (path.startsWith("/"))
                path = path.substring(1, path.length());
            if (path.endsWith("/"))
                path = path.substring(0, path.length() - 1);
            sb.append("/" + path);
        }
        return sb.toString();
    }

    public final static List<String> getChildrenFull1Path(ZooKeeper zookeeper, String path) throws KeeperException, InterruptedException {
        return getChildrenFull1Path(zookeeper, path, false, true);
    }

    public final static List<String> getChildrenFull1Path1(ZooKeeper zookeeper, String path, Watcher watcher, boolean recursive) throws KeeperException, InterruptedException {
        return getChildrenFullPath(zookeeper, path, watcher, recursive);
    }

    public final static List<String> getChildrenFull1Path(ZooKeeper zookeeper, String path, boolean watcher, boolean recursive) throws KeeperException, InterruptedException {
        return getChildrenFullPath(zookeeper, path, watcher, recursive);
    }





    public final static List<String> getChildrenFullPath(ZooKeeper zookeeper, String path, boolean watch, boolean recursive) throws KeeperException, InterruptedException {
        List<String> children = zookeeper.getChildren(path, watch);
        List<String> result = new ArrayList<>();;
        if (!children.isEmpty()) {
            children = wholePath(children, path);
            result.addAll(children);

            if (recursive) {
                for (String child : children) {
                    List<String> item = zookeeper.getChildren(child, watch);
                    result.addAll(item);
                }
            }
            Collections.sort(result);
        }
        return result;
    }


    public final static List<String> getChildrenFullPath(ZooKeeper zookeeper, String path, Watcher watch, boolean recursive) throws KeeperException, InterruptedException {
        List<String> children = zookeeper.getChildren(path, watch);
        List<String> result = new ArrayList<>();;
        if (!children.isEmpty()) {
            children = wholePath(children, path);
            result.addAll(children);

            if (recursive) {
                for (String child : children) {
                    List<String> item = zookeeper.getChildren(child, watch);
                    result.addAll(item);
                }
            }
            Collections.sort(result);
        }
        return result;
    }

    public final static boolean exists(ZooKeeper zookeeper, String path, boolean watch) throws KeeperException, InterruptedException {
        return zookeeper.exists(path, watch) != null;
    }

    public final static boolean exists(ZooKeeper zookeeper, String path, Watcher watcher) throws KeeperException, InterruptedException {
        return zookeeper.exists(path, watcher) != null;
    }

//    public final static String create(ZooKeeper zookeeper, String path, byte[] bytes, List<ACL> acl, CreateMode createMode, long ttl) throws KeeperException, InterruptedException {
//        return zookeeper.create(path, bytes, acl, createMode, null, ttl);
//    }

    public final static String create(ZooKeeper zookeeper, String path, byte[] bytes, List<ACL> acl, CreateMode createMode) throws KeeperException, InterruptedException {
        return zookeeper.create(path, bytes, acl, createMode);
    }

    public final static String create(ZooKeeper zookeeper, String path) throws KeeperException, InterruptedException {
        return create(zookeeper, path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public final static String create(ZooKeeper zookeeper, String path, byte[] bytes) throws KeeperException, InterruptedException {
        return create(zookeeper, path, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public final static String createIfNotExists(ZooKeeper zookeeper, String path, boolean watch) throws KeeperException, InterruptedException {
        return exists(zookeeper, path, watch) ? Constant.EMPTY : create(zookeeper, path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public final static String createIfNotExists(ZooKeeper zookeeper, String path, byte[] bytes, boolean watch) throws KeeperException, InterruptedException {
        return exists(zookeeper, path, watch) ? Constant.EMPTY : create(zookeeper, path, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public final static String createEphemeralIfNotExists(ZooKeeper zookeeper, String path, boolean watch) throws KeeperException, InterruptedException {
        return exists(zookeeper, path, watch) ? Constant.EMPTY : createEphemeral(zookeeper, path);
    }

    public final static String createEphemeralIfNotExists(ZooKeeper zookeeper, String path, byte[] bytes, boolean watch) throws KeeperException, InterruptedException {
        return exists(zookeeper, path, watch) ? Constant.EMPTY : createEphemeral(zookeeper, path, bytes);
    }

    public final static String createEphemeral(ZooKeeper zookeeper, String path) throws KeeperException, InterruptedException {
        return zookeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    public final static String createEphemeral(ZooKeeper zookeeper, String path, byte[] bytes) throws KeeperException, InterruptedException {
        return create(zookeeper, path, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    public final static String createEphemeralSequential(ZooKeeper zookeeper, String path) throws KeeperException, InterruptedException {
        return create(zookeeper, path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    public final static String createEphemeralSequential(ZooKeeper zookeeper, String path, byte[] bytes) throws KeeperException, InterruptedException {
        return create(zookeeper, path, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    public final static byte[] getData(ZooKeeper zookeeper, String path, boolean watch) throws KeeperException, InterruptedException {
        return zookeeper.getData(path, watch, null);
    }

    public final static String getDataForString(ZooKeeper zookeeper, String path, Charset charset, boolean watch) throws KeeperException, InterruptedException {
        byte[] bytes = zookeeper.getData(path, watch, null);
        String result = new String(bytes, charset);
        return result;
    }

    public final static String getDataForString(ZooKeeper zookeeper, String path, String charsetName, boolean watch) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        byte[] bytes = zookeeper.getData(path, watch, null);
        String result = new String(bytes, charsetName);
        return result;
    }

    public final static String getDataForString(ZooKeeper zookeeper, String path, boolean watch) throws KeeperException, InterruptedException {
        byte[] bytes = zookeeper.getData(path, watch, null);
        String result = new String(bytes);
        return result;
    }

    public final static Stat setData(ZooKeeper zookeeper, String path, byte[] bytes) throws KeeperException, InterruptedException {
        return zookeeper.setData(path, bytes, -1);
    }

    public final static Stat setData(ZooKeeper zookeeper, String path, String data, String charsetName) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        byte[] bytes = data.getBytes(charsetName);
        return zookeeper.setData(path, bytes, -1);
    }

    public final static Stat setData(ZooKeeper zookeeper, String path, String data, Charset charset) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        byte[] bytes = data.getBytes(charset);
        return zookeeper.setData(path, bytes, -1);
    }

    public final static Stat setData(ZooKeeper zookeeper, String path, String data) throws KeeperException, InterruptedException {
        byte[] bytes = data.getBytes();
        return zookeeper.setData(path, bytes, -1);
    }

    public final static int generateId(ZooKeeper zookeeper, String path) throws KeeperException, InterruptedException {
        Stat stat = zookeeper.setData(path, new byte[0], -1);
        return stat.getVersion();
    }

    public final static void deleteIfExists(ZooKeeper zookeeper, String groupName) throws KeeperException, InterruptedException {
        if (exists(zookeeper, groupName, false))
            zookeeper.delete(groupName, -1);
    }

    public final static void delete(ZooKeeper zookeeper, String groupName) throws KeeperException, InterruptedException {
        zookeeper.delete(groupName, -1);
    }

    public final static List<OpResult> multi(ZooKeeper zookeeper, List<Op> ops) throws KeeperException, InterruptedException {
        return zookeeper.multi(ops);
    }

    public static void multi(ZooKeeper zookeeper, List<Op> ops, AsyncCallback.MultiCallback cb, Object ctx) throws KeeperException, InterruptedException {
        zookeeper.multi(ops, cb, ctx);
    }










    public static boolean lock(ZooKeeper zooKeeper, String lockPath) throws KeeperException, InterruptedException {
        boolean exists = ZooKeeperSugar.exists(zooKeeper, lockPath, false);
        if (exists)
            return false;
        else {
            String ephemeral = ZooKeeperSugar.create(zooKeeper, lockPath, null , ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            return true;
        }
    }


    public static boolean tryLock(ZooKeeper zooKeeper, String lockPath, long tryTimeout) throws KeeperException, InterruptedException {
        long end = System.currentTimeMillis() + tryTimeout;
        while (true) {
            boolean exists = ZooKeeperSugar.exists(zooKeeper, lockPath, false);
            if (System.currentTimeMillis() > end)
                return false;
            else if (!exists)
                break;
            Thread.sleep(100);
        }
        String path = ZooKeeperSugar.create(zooKeeper, lockPath, null , ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        return true;
    }


    public static void unlock(ZooKeeper zooKeeper, String lockPath) throws KeeperException, InterruptedException {
        deleteIfExists(zooKeeper, lockPath);
    }

//    public static void ff(CuratorFramework client) {
//        new InterProcessMutex(client, "/curator/lock");
//    }

}

