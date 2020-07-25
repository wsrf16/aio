package com.aio.portable.swiss.suite.bean.serializer;

import com.aio.portable.swiss.suite.bean.serializer.Serializer;
import com.aio.portable.swiss.suite.bean.serializer.json.GsonSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.bean.serializer.xml.XmlSugar;

public class DynamicSerializer {

    public static class Jackson implements Serializer {
        @Override
        public <T> String serialize(T t) {
            return JacksonSugar.obj2Json(t);
        }

        @Override
        public <T> T deserialize(String json, Class<T> clazz) {
            return JacksonSugar.json2T(json, clazz);
        }
    }

    public static class ShortJackson implements Serializer {
        @Override
        public <T> String serialize(T t) {
            return JacksonSugar.obj2ShortJson(t);
        }

        @Override
        public <T> T deserialize(String json, Class<T> clazz) {
            return JacksonSugar.json2T(json, clazz);
        }
    }

    public static class ForceJackson implements Serializer {
        @Override
        public <T> String serialize(T t) {
            return JacksonSugar.obj2Json(t, false);
        }

        @Override
        public <T> T deserialize(String json, Class<T> clazz) {
            return JacksonSugar.json2T(json, clazz);
        }
    }

    public static class ForceShortJackson implements Serializer {
        @Override
        public <T> String serialize(T t) {
            return JacksonSugar.obj2ShortJson(t, false);
        }

        @Override
        public <T> T deserialize(String json, Class<T> clazz) {
            return JacksonSugar.json2T(json, clazz);
        }
    }


    public static class JackXml implements Serializer {
        @Override
        public <T> String serialize(T t) {
            return XmlSugar.obj2Xml(t);
        }

        @Override
        public <T> T deserialize(String xml, Class<T> clazz) {
            return XmlSugar.xml2T(xml, clazz);
        }
    }

    public static class ShortJackXml implements Serializer {
        @Override
        public <T> String serialize(T t) {
            return XmlSugar.obj2ShortXml(t);
        }

        @Override
        public <T> T deserialize(String xml, Class<T> clazz) {
            return XmlSugar.xml2T(xml, clazz);
        }
    }


    public static class Gson implements Serializer {
        @Override
        public <T> String serialize(T t) {
            return GsonSugar.obj2Json(t);
        }

        @Override
        public <T> T deserialize(String json, Class<T> clazz) {
            return GsonSugar.json2T(json, clazz);
        }
    }

//    public static class ShortGson implements Serializer {
//        @Override
//        public <T> String serialize(T t) {
//            return GsonSugar.obj2Json(t);
//        }
//
//        @Override
//        public <T> T deserialize(String json, Class<T> clazz) {
//            return GsonSugar.json2T(json, clazz);
//        }
//    }
}
