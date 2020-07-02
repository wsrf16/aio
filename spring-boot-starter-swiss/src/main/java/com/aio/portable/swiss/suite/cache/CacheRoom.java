package com.aio.portable.swiss.suite.cache;

import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.suite.io.IOSugar;
import com.aio.portable.swiss.suite.io.PathSugar;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.io.*;
import java.util.stream.Collectors;

/**
 * Created by York on 2017/11/28.
 */
public class CacheRoom {
    String key;
    final static String KEYISNULL = "the key can not be empty or null.";
    static String ROOT_DIRECTORY = Constant.CURRENT_DIRECTORY;
    static String CACHE_DIRECTORY_NAME = "local-cache";
    static String EXTENSION = ".cache";
    //System.Text.Encoding CahceFileEncoding = System.Text.Encoding.UTF8;

    static Hashtable<String, CacheRoom> cacheRooms;
    static Lock constructionLock = new ReentrantLock();
    static Lock ioLock = new ReentrantLock();

    /**
     * CacheRoomFactory : Factory method
     *
     * @param key
     * @return
     */
    public static CacheRoom cacheRoomFactory(String key) {
        CacheRoom val = null;
        //lock(constructionLock)
        if (cacheRooms.keySet().contains(key)) {
            val = cacheRooms.get(key);
        } else {
            try {
                constructionLock.lock();
                val = new CacheRoom(key);
                cacheRooms.put(key, val);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                constructionLock.unlock();
            }
        }

        return val;
    }

    public static Set<String> keys() {
        return cacheRooms.keySet();
    }


    static {
        File dir = new File(cacheDirectorPath());
        if (!dir.exists())
            dir.mkdir();
        List<String> keys = Arrays.stream(dir.listFiles())
                .filter(c -> c.getName().split("\\.")[1].toLowerCase().equals(EXTENSION))
                .map(c -> c.getName().replace(EXTENSION, Constant.EMPTY))
                .collect(Collectors.toList());
        cacheRooms = new Hashtable<>();
        keys.forEach(key -> {
            try {
                cacheRooms.put(key, new CacheRoom(key));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        int c = 33;
    }

    protected CacheRoom() throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    protected CacheRoom(String key) {
        if (!StringUtils.hasLength(key))
            throw new IllegalArgumentException(KEYISNULL);
        this.key = key;
    }

    public String cacheFilePath() {
        return PathSugar.concat(ROOT_DIRECTORY, CACHE_DIRECTORY_NAME, this.key + EXTENSION);
    }

    public static String cacheDirectorPath() {
        return PathSugar.concat(ROOT_DIRECTORY, CACHE_DIRECTORY_NAME);
    }

    public Boolean exist() {
        return cacheRooms.keySet().contains(this.key);
    }

    public static Boolean exist(String key) {
        return cacheRooms.keySet().contains(key);
    }


    public void saveByJson(Object cache) {
        String content = JacksonSugar.obj2Json(cache);
        try {
            constructionLock.lock();
            {
                IOSugar.writeFile(this.cacheFilePath(), content);
            }
        } finally {
            constructionLock.unlock();
        }
    }

//    public void SaveByProtobuf(Object cache) {
//        String content = ProtobufSerializerKit.Obj2Protobuf(cache);
//        lock(constructionLock)
//        {
//            File.WriteAllText(this.CacheFilePath, content, CahceFileEncoding);
//        }
//    }

    public void saveByByte(Object cache) {
//        byte[] content = ByteUtils.obj2Byte(cache);
        byte[] content = SerializationUtils.serialize(cache);
        try {
            constructionLock.lock();
            {
                //File.WriteAllBytes(this.CacheFilePath, content);
                IOSugar.writeFile(this.cacheFilePath(), content);
            }
        } finally {
            constructionLock.unlock();
        }
    }

//    public static void SaveByProtobuf(String key, Object cache) {
//        CacheRoomFactory(key).SaveByProtobuf(cache);
//    }

    public static void saveByJson(String key, Object cache) {
        cacheRoomFactory(key).saveByJson(cache);
    }


    public static void saveByByte(String key, Object cache) {
        cacheRoomFactory(key).saveByByte(cache);
    }


    public <T> T loadByJson(Class<T> clazz)// where T : class
    {
        String content = null;
        try {
            constructionLock.lock();
            {
                //content = File.ReadAllText(this.CacheFilePath, CahceFileEncoding);
                content = IOSugar.readFileForText(this.cacheFilePath());
            }
        } finally {
            constructionLock.unlock();
        }
        return JacksonSugar.json2T(content, clazz);
    }

//    public T loadByProtobuf<T>()// where T : class
//
//    {
//        String content;
//        lock(constructionLock)
//        {
//            content = File.ReadAllText(this.CacheFilePath, CahceFileEncoding);
//        }
//        return ProtobufSerializerKit.Protobuf2Obj < T > (content);
//    }

    public Object loadByByte() {
        byte[] content;
        try {
            constructionLock.lock();
            {
                //content = File.ReadAllBytes(this.CacheFilePath);
                content = IOSugar.readFileForByte(this.cacheFilePath());
            }
        } finally {
            constructionLock.unlock();
        }
//        return ByteUtils.byte2Obj(content);
        return SerializationUtils.deserialize(content);
    }

    public static <T> T loadByJson(String key, Class<T> clazz) {
        return cacheRoomFactory(key).loadByJson(clazz);
    }

//    public static <T> T LoadByProtobuf(String key) {
//        return CacheRoomFactory(key).LoadByProtobuf();
//    }

    public static Object loadByByte(String key) {
        return cacheRoomFactory(key).loadByByte();
    }

    public <T> T popByJson(Class<T> clazz) {
        String content;
        try {
            constructionLock.lock();
            {
                //content = File.ReadAllText(this.CacheFilePath, CahceFileEncoding);
                content = IOSugar.readFileForText(this.cacheFilePath());
                new File(this.cacheFilePath()).delete();
                cacheRooms.remove(this.key);
                this.key = null;
            }
        } finally {
            constructionLock.unlock();
        }
        return JacksonSugar.json2T(content, clazz);
    }

//    public <T> T popByProtobuf() {
//        String content;
//        constructionLock.lock();
//        {
//            //content = File.ReadAllText(this.CacheFilePath, CahceFileEncoding);
//            new File(this.CacheFilePath()).delete();
//            CacheRooms.remove(this.key);
//            this.key = null;
//        }
//        constructionLock.unlock();
//        return ProtobufSerializerKit.Protobuf2Obj < T > (content);
//    }

    public <T> T popByByte() {
        byte[] content;
        try {
            constructionLock.lock();
            {
                //content = File.ReadAllBytes(this.CacheFilePath());
                content = IOSugar.readFileForByte(this.cacheFilePath());
                new File(this.cacheFilePath()).delete();
                cacheRooms.remove(this.key);
                this.key = null;
            }
        } finally {
            constructionLock.unlock();
        }
        return (T) SerializationUtils.deserialize(content);
//        return ByteUtils.byte2Obj(content);
    }

    public static <T> T popByJson(String key, Class<T> clazz) {
        return cacheRoomFactory(key).popByJson(clazz);
    }

//    public static <T> T PopByProtobuf(String key) {
//        return CacheRoomFactory(key).PopByProtobuf < T > ();
//    }

    public static <T> T popByByte(String key) {
        return cacheRoomFactory(key).popByByte();
    }


    public void drop() {
        if (new File(this.cacheFilePath()).exists()) {
            try {
                constructionLock.lock();
                {
                    //File.Move(this.CacheFilePath(), this.CacheFilePath() + "~");
                    File newFile = new File(this.cacheFilePath() + "~");
                    new File(this.cacheFilePath()).renameTo(newFile);
                }
            } finally {
                constructionLock.unlock();
            }
            cacheRooms.remove(this.key);
            this.key = null;
        }
    }

    public static void drop(String key) {
        CacheRoom.cacheRoomFactory(key).drop();
    }


    public void clear() {
        try {
            File file = new File(this.cacheFilePath());
            if (file.exists()) {
                constructionLock.lock();
                {
                    file.delete();
                }
                cacheRooms.remove(this.key);
                this.key = null;
            }
        } finally {
            constructionLock.unlock();
        }
    }

    public static void clear(String key) {
        CacheRoom.cacheRoomFactory(key).clear();
    }


}
