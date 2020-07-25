package com.aio.portable.swiss.module.zookeeper;

import com.aio.portable.swiss.global.Constant;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ZooKeeperSugar {
    public final static ZooKeeper build(String connectString, int sessionTimeout, Watcher watcher) {
        try {
            return new ZooKeeper(connectString, sessionTimeout, watcher);
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
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
        return getAbsoluteChildren(zookeeper, path, watcher, recursive);
    }

    public final static List<String> getChildrenFull1Path(ZooKeeper zookeeper, String path, boolean watcher, boolean recursive) throws KeeperException, InterruptedException {
        return getAbsoluteChildren(zookeeper, path, watcher, recursive);
    }




    public final static List<String> getRelativeChildren(ZooKeeper zooKeeper, String path, boolean watch) {
        try {
            List<String> children = zooKeeper.getChildren(path, watch);
            return children;
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static List<String> getRelativeChildren(ZooKeeper zooKeeper, String path, Watcher watcher) {
        try {
            List<String> children = zooKeeper.getChildren(path, watcher);
            return children;
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static List<String> getAbsoluteChildren(ZooKeeper zookeeper, String path, boolean watch, boolean recursive) {
        List<String> children = getRelativeChildren(zookeeper, path, watch);
        List<String> result = new ArrayList<>();;
        if (!children.isEmpty()) {
            children = wholePath(children, path);
            result.addAll(children);

            if (recursive) {
                for (String child : children) {
                    List<String> item = getRelativeChildren(zookeeper, child, watch);
                    result.addAll(item);
                }
            }
            Collections.sort(result);
        }
        return result;
    }


    public final static List<String> getAbsoluteChildren(ZooKeeper zookeeper, String path, Watcher watcher, boolean recursive) {
        List<String> children = getRelativeChildren(zookeeper, path, watcher);
        List<String> result = new ArrayList<>();
        if (!children.isEmpty()) {
            children = wholePath(children, path);
            result.addAll(children);

            if (recursive) {
                for (String child : children) {
                    List<String> item = getRelativeChildren(zookeeper, child, watcher);
                    result.addAll(item);
                }
            }
            Collections.sort(result);
        }
        return result;
    }

    public final static boolean exists(ZooKeeper zookeeper, String path, boolean watch) {
        try {
            return zookeeper.exists(path, watch) != null;
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static boolean exists(ZooKeeper zookeeper, String path, Watcher watcher) {
        try {
            return zookeeper.exists(path, watcher) != null;
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    public final static String create(ZooKeeper zookeeper, String path, byte[] bytes, List<ACL> acl, CreateMode createMode, long ttl) throws KeeperException, InterruptedException {
//        return zookeeper.create(path, bytes, acl, createMode, null, ttl);
//    }

    public final static String create(ZooKeeper zookeeper, String path, byte[] bytes, List<ACL> acl, CreateMode createMode) {
        try {
            String parentPath = path.substring(0, path.lastIndexOf("/"));
            parentPath = StringUtils.isEmpty(parentPath) ? "/" : parentPath;
            if (!exists(zookeeper, parentPath, false)) {
                create(zookeeper, parentPath, null, acl, createMode);
            }
            return zookeeper.create(path, bytes, acl, createMode);

        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String create(ZooKeeper zookeeper, String path) {
        return create(zookeeper, path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public final static String create(ZooKeeper zookeeper, String path, byte[] bytes) {
        return create(zookeeper, path, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public final static String createIfNotExists(ZooKeeper zookeeper, String path, boolean watch) {
        return exists(zookeeper, path, watch) ? Constant.EMPTY : create(zookeeper, path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public final static String createIfNotExists(ZooKeeper zookeeper, String path, byte[] bytes, boolean watch) {
        return exists(zookeeper, path, watch) ? Constant.EMPTY : create(zookeeper, path, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public final static String createEphemeralIfNotExists(ZooKeeper zookeeper, String path, boolean watch) {
        return exists(zookeeper, path, watch) ? Constant.EMPTY : createEphemeral(zookeeper, path);
    }

    public final static String createEphemeralIfNotExists(ZooKeeper zookeeper, String path, byte[] bytes, boolean watch) {
        return exists(zookeeper, path, watch) ? Constant.EMPTY : createEphemeral(zookeeper, path, bytes);
    }

    public final static String createEphemeral(ZooKeeper zookeeper, String path) {
        try {
            return zookeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String createEphemeral(ZooKeeper zookeeper, String path, byte[] bytes) {
        return create(zookeeper, path, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    public final static String createEphemeralSequential(ZooKeeper zookeeper, String path) {
        return create(zookeeper, path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    public final static String createEphemeralSequential(ZooKeeper zookeeper, String path, byte[] bytes) {
        return create(zookeeper, path, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    public final static byte[] getData(ZooKeeper zookeeper, String path, boolean watch) {
        try {
            return zookeeper.getData(path, watch, null);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String getDataForString(ZooKeeper zookeeper, String path, Charset charset, boolean watch) {
        try {
            byte[] bytes = zookeeper.getData(path, watch, null);
            String result = new String(bytes, charset);
            return result;
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String getDataForString(ZooKeeper zookeeper, String path, String charsetName, boolean watch) {
        try {
            byte[] bytes = zookeeper.getData(path, watch, null);
            String result = new String(bytes, charsetName);
            return result;
        } catch (KeeperException | InterruptedException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String getDataForString(ZooKeeper zookeeper, String path, boolean watch) {
        try {
            byte[] bytes = zookeeper.getData(path, watch, null);
            String result = new String(bytes);
            return result;
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static Stat setData(ZooKeeper zookeeper, String path, byte[] bytes) {
        try {
            return zookeeper.setData(path, bytes, -1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static Stat setData(ZooKeeper zookeeper, String path, String data, String charsetName) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        byte[] bytes = data.getBytes(charsetName);
        return zookeeper.setData(path, bytes, -1);
    }

    public final static Stat setData(ZooKeeper zookeeper, String path, String data, Charset charset) {
        try {
            byte[] bytes = data.getBytes(charset);
            return zookeeper.setData(path, bytes, -1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static Stat setData(ZooKeeper zookeeper, String path, String data) {
        try {
            byte[] bytes = data.getBytes();
            return zookeeper.setData(path, bytes, -1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static int generateId(ZooKeeper zookeeper, String path) {
        try {
            Stat stat = zookeeper.setData(path, new byte[0], -1);
            return stat.getVersion();
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static void clearIfExists(ZooKeeper zookeeper, String znodePath) {
        if (exists(zookeeper, znodePath, false))
            clear(zookeeper, znodePath);
    }

    public final static void clear(ZooKeeper zookeeper, String znodePath) {
        try {
            List<String> children = zookeeper.getChildren(znodePath, false);

            for (String child : children) {
                String childNode = znodePath + "/" + child;
                if (zookeeper.getChildren(childNode, null).size() != 0) {
                    clear(zookeeper, childNode);
                }
                zookeeper.delete(childNode, -1);
            }
//            zookeeper.delete(znodePath, -1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static void deleteIfExists(ZooKeeper zookeeper, String znodePath) {
        if (exists(zookeeper, znodePath, false))
            delete(zookeeper, znodePath);
    }

    public final static void delete(ZooKeeper zookeeper, String znodePath) {
        try {
            List<String> children = zookeeper.getChildren(znodePath, false);

            for (String child : children) {
                String childNode = znodePath + "/" + child;
                if (zookeeper.getChildren(childNode, null).size() != 0) {
                    clear(zookeeper, childNode);
                }
                zookeeper.delete(childNode, -1);
            }
            zookeeper.delete(znodePath, -1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static List<OpResult> multi(ZooKeeper zookeeper, List<Op> ops) {
        try {
            return zookeeper.multi(ops);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static void multi(ZooKeeper zookeeper, List<Op> ops, AsyncCallback.MultiCallback cb, Object ctx) {
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


    public static void unlock(ZooKeeper zooKeeper, String lockPath) {
        clearIfExists(zooKeeper, lockPath);
    }

//    public static void ff(CuratorFramework client) {
//        new InterProcessMutex(client, "/curator/lock");
//    }

}

