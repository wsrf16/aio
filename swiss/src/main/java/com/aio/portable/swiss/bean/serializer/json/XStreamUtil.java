package com.aio.portable.swiss.bean.serializer.json;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

public class XStreamUtil {
    public static <T> T xml2Class(byte[] buf, Class<T> clazz) throws UnsupportedEncodingException, InstantiationException, IllegalAccessException {
//		byte[] buf = str.getBytes("UTF-8");
        T t = clazz.newInstance();
        XStream xStream = new XStream(new DomDriver());
        InputStream in = new ByteArrayInputStream(buf);
        xStream.fromXML(in, t);
        return t;
    }

    public static <T> T xml2Class(byte[] buf, Type typeOfT) throws UnsupportedEncodingException, InstantiationException, IllegalAccessException {
//		byte[] buf = str.getBytes("UTF-8");
        Class<T> clazz = (Class) typeOfT;
        T t = clazz.newInstance();
        XStream xStream = new XStream(new DomDriver());
        InputStream in = new ByteArrayInputStream(buf);
        xStream.fromXML(in, t);
        return t;
    }
}
