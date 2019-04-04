package com.york.portable.swiss.assist.cache;

import com.york.portable.swiss.bean.serializer.json.JacksonUtil;
import com.york.portable.swiss.sugar.FileUtils;
import com.york.portable.swiss.global.Constant;
import com.york.portable.swiss.bean.ByteUtils;
import com.york.portable.swiss.sugar.PathUtils;

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
    static final String KEYISNULL = "the key can not be empty or null.";
    static final String ROOTDIRECTORY = Constant.CURRENT_DIRECTORY;
    static final String CACHEDIRECTORYNAME = "Cache";
    static final String EXTENSION = ".cache";
    //System.Text.Encoding CahceFileEncoding = System.Text.Encoding.UTF8;

    static Hashtable<String, CacheRoom> CacheRooms;
    static Lock constructionLock = new ReentrantLock();
    static Lock ioLock = new ReentrantLock();

    /**
     * CacheRoomFactory : Factory method
     * @param key
     * @return
     */
    public static CacheRoom CacheRoomFactory(String key) {
        CacheRoom val = null;
        //lock(constructionLock)
        constructionLock.lock();
        {
            if (CacheRooms.keySet().contains(key))
                val = CacheRooms.get(key);
            else {
                try {
                    val = new CacheRoom(key);
                    CacheRooms.put(key, val);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        constructionLock.unlock();
        return val;
    }

    public static Set<String> Keys() {
        return CacheRooms.keySet();
    }


    static {
        //Directory.GetFiles(CacheDirectorPath).Where(c => c.EndsWith(EXTENSION));
        //Directory.CreateDirectory(CacheDirectorPath);
        File dir = new File(CacheDirectorPath());
        if (!dir.exists())
            dir.mkdir();
//        Stream<File> aa = Arrays.stream(dir.listFiles()).filter(c -> c.getName().split("\\.")[1].toLowerCase().equals(EXTENSION));
//        List<File> aa1 = aa.collect(Collectors.toList());
//        Stream<String> bb = aa.map(c -> c.getName().replace(EXTENSION, Constant.EMPTY));
//        List<String> bb1 = bb.collect(Collectors.toList());
        List<String> keys = Arrays.stream(dir.listFiles()).filter(c -> c.getName().split("\\.")[1].toLowerCase().equals(EXTENSION)).
                map(c -> c.getName().replace(EXTENSION, Constant.EMPTY)).collect(Collectors.toList());
        CacheRooms = new Hashtable<String, CacheRoom>();
        keys.forEach(key -> {
            try {
                CacheRooms.put(key, new CacheRoom(key));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        int c=33;
    }

    protected CacheRoom() throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    protected CacheRoom(String key) throws Exception {
        if (key == null || key.isEmpty()) throw new Exception(KEYISNULL);
        this.key = key;
    }

    public String CacheFilePath() {
        return PathUtils.concat(ROOTDIRECTORY, CACHEDIRECTORYNAME, this.key + EXTENSION);
    }

    public static String CacheDirectorPath() {
        return PathUtils.concat(ROOTDIRECTORY, CACHEDIRECTORYNAME);
    }

    public Boolean exist() {
        return CacheRooms.keySet().contains(this.key);
    }

    public static Boolean exist(String key) {
        return CacheRoom.CacheRooms.keySet().contains(key);
    }


    public void saveByJson(Object cache) throws IOException {
        String content = JacksonUtil.obj2Json(cache);
        constructionLock.lock();
        {
            //File.WriteAllText(this.CacheFilePath, content, CahceFileEncoding);
            //new FileWriter(this.CacheFilePath()).write(content);
            FileUtils.writeFile(this.CacheFilePath(), content);
        }
        constructionLock.unlock();
    }

//    public void SaveByProtobuf(Object cache) {
//        String content = ProtobufSerializerKit.Obj2Protobuf(cache);
//        lock(constructionLock)
//        {
//            File.WriteAllText(this.CacheFilePath, content, CahceFileEncoding);
//        }
//    }

    public void saveByByte(Object cache) throws IOException {
        byte[] content = ByteUtils.obj2Byte(cache);
        constructionLock.lock();
        {
            //File.WriteAllBytes(this.CacheFilePath, content);
            FileUtils.writeFile(this.CacheFilePath(), content);
        }
        constructionLock.unlock();
    }

//    public static void SaveByProtobuf(String key, Object cache) {
//        CacheRoomFactory(key).SaveByProtobuf(cache);
//    }

    public static void saveByJson(String key, Object cache) {
        try {
            CacheRoomFactory(key).saveByJson(cache);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void saveByByte(String key, Object cache) throws IOException {
        CacheRoomFactory(key).saveByByte(cache);
    }


    public <T> T loadByJson(Class<T> clazz)// where T : class
    {
        String content = null;
        constructionLock.lock();
        {
            //content = File.ReadAllText(this.CacheFilePath, CahceFileEncoding);
            content = FileUtils.readFileForText(this.CacheFilePath());
        }
        constructionLock.unlock();
        return JacksonUtil.json2T(content, clazz);
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

    public <T> T loadByByte() throws IOException {
        byte[] content;
        constructionLock.lock();
        {
            //content = File.ReadAllBytes(this.CacheFilePath);
            content = FileUtils.readFileForByte(this.CacheFilePath());
        }
        constructionLock.unlock();
        return ByteUtils.byte2Obj(content);
    }

    public static <T> T loadByJson(String key, Class<T> clazz) {
        return CacheRoomFactory(key).loadByJson(clazz);
    }

//    public static <T> T LoadByProtobuf(String key) {
//        return CacheRoomFactory(key).LoadByProtobuf();
//    }

    public static <T> T loadByByte(String key) throws IOException {
        return CacheRoomFactory(key).loadByByte();
    }

    public <T> T popByJson(Class<T> clazz) {
        String content;
        constructionLock.lock();
        {
            //content = File.ReadAllText(this.CacheFilePath, CahceFileEncoding);
            content = FileUtils.readFileForText(this.CacheFilePath());
            new File(this.CacheFilePath()).delete();
            CacheRooms.remove(this.key);
            this.key = null;
        }
        constructionLock.unlock();
        return JacksonUtil.json2T(content, clazz);
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

    public <T> T popByByte() throws IOException {
        byte[] content;
        constructionLock.lock();
        {
            //content = File.ReadAllBytes(this.CacheFilePath());
            content = FileUtils.readFileForByte(this.CacheFilePath());
            new File(this.CacheFilePath()).delete();
            CacheRooms.remove(this.key);
            this.key = null;
        }
        constructionLock.unlock();
        return ByteUtils.byte2Obj(content);
    }

    public static <T> T popByJson(String key, Class<T> clazz) {
        return CacheRoomFactory(key).popByJson(clazz);
    }

//    public static <T> T PopByProtobuf(String key) {
//        return CacheRoomFactory(key).PopByProtobuf < T > ();
//    }

    public static <T> T popByByte(String key) throws IOException {
        return CacheRoomFactory(key).popByByte();
    }


    public void drop() {
        if (new File(this.CacheFilePath()).exists()) {
            constructionLock.lock();
            {
                //File.Move(this.CacheFilePath(), this.CacheFilePath() + "~");
                File newFile = new File(this.CacheFilePath() + "~");
                new File(this.CacheFilePath()).renameTo(newFile);
            }
            constructionLock.unlock();
            CacheRooms.remove(this.key);
            this.key = null;
        }
    }

    public static void drop(String key) {
        CacheRoom.CacheRoomFactory(key).drop();
    }


    public void clear() {
        File file = new File(this.CacheFilePath());
        if (file.exists()) {
            constructionLock.lock();
            {
                file.delete();
            }
            constructionLock.unlock();
            CacheRooms.remove(this.key);
            this.key = null;
        }
    }

    public static void clear(String key) {
        CacheRoom.CacheRoomFactory(key).clear();
    }

}
