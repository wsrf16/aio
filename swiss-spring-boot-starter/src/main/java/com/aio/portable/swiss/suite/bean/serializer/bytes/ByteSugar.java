package com.aio.portable.swiss.suite.bean.serializer.bytes;

import java.io.*;

public class ByteSugar {

    /**
     * toByteArray
     * @param obj
     * @return
     */
    public static final byte[] toByteArray(Object obj) {
        byte[] bytes;
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
//            objectOutputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return bytes;
    }

    /**
     * toObject
     * @param bytes
     * @return
     */
    public static final Object toObject(byte[] bytes) {
        Object obj;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            obj = objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return obj;
    }





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
