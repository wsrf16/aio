package com.aio.portable.swiss.bean;

import java.io.*;
import java.io.IOException;

/**
 * Created by York on 2017/11/28.
 */
public class ByteUtils {
//    public static byte[] obj2Byte(Object obj) throws IOException {
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(bos);
//        oos.writeObject(obj);
//        oos.flush();
//        byte[] bytes = bos.toByteArray();
//        oos.close();
//        bos.close();
//        return bytes;
//    }

//    public static Object byte2Obj(byte[] bytes) throws IOException, ClassNotFoundException {
//        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
//        ObjectInputStream ois = new ObjectInputStream(bis);
//        Object obj = ois.readObject();
//        ois.close();
//        bis.close();
//        return obj;
//    }

    public static void obj2FileByByte(Object obj, String file) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        byte[] bytes = bos.toByteArray();
        oos.close();
        bos.close();
        //return bytes;

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();
    }


    public static Object file2ObjByByte(String file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[(int) new File(file).length()];
        fis.read(bytes);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        bis.close();
        return obj;
    }
}
