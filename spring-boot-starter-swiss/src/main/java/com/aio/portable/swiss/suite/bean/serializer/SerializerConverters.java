package com.aio.portable.swiss.suite.bean.serializer;

import com.aio.portable.swiss.suite.bean.serializer.json.GsonSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.bean.serializer.xml.XmlSugar;
import com.aio.portable.swiss.suite.bean.serializer.yaml.YamlSugar;
import com.fasterxml.jackson.core.type.TypeReference;

public abstract class SerializerConverters {

    public final static SerializerConverter build() {
        return new JacksonConverter();
    }

    public static class JacksonConverter implements SerializerConverter {
        @Override
        public <T> String serialize(T t) {
            return JacksonSugar.obj2Json(t);
        }

        @Override
        public <T> T deserialize(String json, Class<T> clazz) {
            return JacksonSugar.json2T(json, clazz);
        }

        public <T> T deserialize(String json, TypeReference<T> valueTypeRef) {
            return JacksonSugar.json2T(json, valueTypeRef);
        }
    }

    public static class ShortJacksonConverter implements SerializerConverter {
        @Override
        public <T> String serialize(T t) {
            return JacksonSugar.obj2ShortJson(t);
        }

        @Override
        public <T> T deserialize(String json, Class<T> clazz) {
            return JacksonSugar.json2T(json, clazz);
        }

        public <T> T deserialize(String json, TypeReference<T> valueTypeRef) {
            return JacksonSugar.json2T(json, valueTypeRef);
        }
    }

    public static class LongJacksonConverter implements SerializerConverter {
        @Override
        public <T> String serialize(T t) {
            return JacksonSugar.obj2LongJson(t);
        }

        @Override
        public <T> T deserialize(String json, Class<T> clazz) {
            return JacksonSugar.json2T(json, clazz);
        }

        public <T> T deserialize(String json, TypeReference<T> valueTypeRef) {
            return JacksonSugar.json2T(json, valueTypeRef);
        }
    }

    public static class SilentJacksonConverter implements SerializerConverter {
        @Override
        public <T> String serialize(T t) {
            return JacksonSugar.obj2Json(t, false);
        }

        @Override
        public <T> T deserialize(String json, Class<T> clazz) {
            return JacksonSugar.json2T(json, clazz);
        }
    }

    public static class SilentShortJacksonConverter implements SerializerConverter {
        @Override
        public <T> String serialize(T t) {
            return JacksonSugar.obj2ShortJson(t, false);
        }

        @Override
        public <T> T deserialize(String json, Class<T> clazz) {
            return JacksonSugar.json2T(json, clazz);
        }
    }


    public static class JackXmlConverter implements SerializerConverter {
        @Override
        public <T> String serialize(T t) {
            return XmlSugar.obj2Xml(t);
        }

        @Override
        public <T> T deserialize(String xml, Class<T> clazz) {
            return XmlSugar.xml2T(xml, clazz);
        }
    }

    public static class ShortJackXmlConverter implements SerializerConverter {
        @Override
        public <T> String serialize(T t) {
            return XmlSugar.obj2ShortXml(t);
        }

        @Override
        public <T> T deserialize(String xml, Class<T> clazz) {
            return XmlSugar.xml2T(xml, clazz);
        }
    }


    public static class GsonConverter implements SerializerConverter {
        @Override
        public <T> String serialize(T t) {
            return GsonSugar.obj2Json(t);
        }

        @Override
        public <T> T deserialize(String json, Class<T> clazz) {
            return GsonSugar.json2T(json, clazz);
        }
    }

    public static class YamlConverter implements SerializerConverter {
        @Override
        public <T> String serialize(T t) {
            return YamlSugar.obj2Yaml(t);
        }

        @Override
        public <T> T deserialize(String yaml, Class<T> clazz) {
            return YamlSugar.yaml2T(yaml);
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
