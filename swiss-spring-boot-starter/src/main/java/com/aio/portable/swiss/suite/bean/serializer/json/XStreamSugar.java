//package com.aio.portable.swiss.suite.bean.serializer.json;
//
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.DomDriver;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Type;
//
//public class XStreamSugar {
//    public static <T> T xml2Class(byte[] buf, Class<T> clazz) throws UnsupportedEncodingException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
////		byte[] buf = str.getBytes("UTF-8");
//        T t = clazz.getDeclaredConstructor().newInstance();
//        XStream xStream = new XStream(new DomDriver());
//        InputStream in = new ByteArrayInputStream(buf);
//        xStream.fromXML(in, t);
//        return t;
//    }
//
//    public static <T> T xml2Class(byte[] buf, Type typeOfT) throws UnsupportedEncodingException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
////		byte[] buf = str.getBytes("UTF-8");
//        Class<T> clazz = (Class<T>) typeOfT;
//        T t = clazz.getDeclaredConstructor().newInstance();
//        XStream xStream = new XStream(new DomDriver());
//        InputStream in = new ByteArrayInputStream(buf);
//        xStream.fromXML(in, t);
//        return t;
//    }
//}
