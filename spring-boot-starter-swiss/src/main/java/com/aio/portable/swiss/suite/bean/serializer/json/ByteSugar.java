package com.aio.portable.swiss.suite.bean.serializer.json;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

public class ByteSugar {
//    public static final Object byteToObj(byte[] data){
//        try {
//            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
//            ObjectInputStream oInputStream = new ObjectInputStream(inputStream);
//            Object obj = oInputStream.readObject();
//            return obj;
//        } catch (IOException |ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static final byte[] objToByte(Object obj){
//        try {
//            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
//            ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
//            objectOutputStream.writeObject(obj);
//            return outputStream.toByteArray();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public static final <T> T byteToObj(byte[] data, Class<T> clazz){
        String json = new String(data, StandardCharsets.UTF_8);
        return JacksonSugar.json2T(json, clazz);
    }

    public static final byte[] objToByte(Object obj){
        String json = JacksonSugar.obj2ShortJson(obj);
        return json.getBytes(StandardCharsets.UTF_8);
    }
}
