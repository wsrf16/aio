package com.aio.portable.swiss.suite.bean.serializer.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.text.DateFormat;


/**
 * Created by York on 2017/11/22.
 */
public class JacksonXmlSugar {
    public enum SerializerStyle {
        Default,
        CamelCase,
    }
    private final static ObjectMapper shortObjectMapper = getObjectMapper(false, false, null);
    private final static ObjectMapper longObjectMapper = getObjectMapper(true, true, null);
    private final static ObjectMapper normalObjectMapper = getObjectMapper(false, true, null);
    private final static ObjectMapper dumpObjectMapper = Jackson2ObjectMapperBuilder.xml().build()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//    private final static ObjectMapper objectMapper = new ObjectMapper();
//
//    static {
//        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
//        objectMapper.setSerializationInclusion(Include.NON_NULL);
//    }

    /**
     * obj2TightXml
     * @param obj
     * @return
     */
    public final static String obj2TightXml(Object obj) {
//        return obj2Json(obj, false, false);
        ObjectMapper mapper = shortObjectMapper;
        String json;
        try {
            json = obj == null ? null : mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return json;
    }


    /**
     * obj2TightXml
     * @param obj
     * @param throwException
     * @return
     */
    public final static String obj2TightXml(Object obj, Boolean throwException) {
        if (throwException)
            return obj2TightXml(obj);
        else {
            try {
                return obj2TightXml(obj);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    /**
     * obj2LongXml
     * @param obj
     * @return
     */
    public final static String obj2LongXml(Object obj) {
//        return obj2Json(obj, true, true);
        ObjectMapper mapper = longObjectMapper;
        String json;
        try {
            json = obj == null ? null : mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return json;
    }


    /**
     * obj2LongXml
     * @param obj
     * @param throwException
     * @return
     */
    public final static String obj2LongXml(Object obj, Boolean throwException) {
        if (throwException)
            return obj2LongXml(obj);
        else {
            try {
                return obj2LongXml(obj);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    /**
     * obj2Xml
     * @param obj
     * @return
     */
    public final static String obj2Xml(Object obj) {
//        return obj2Json(obj, false, true);
        ObjectMapper mapper = normalObjectMapper;
        String json;
        try {
            json = obj == null ? null : mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return json;
    }


    /**
     * obj2Xml
     * @param obj
     * @return
     */
    public final static String obj2Xml(Object obj, Boolean throwException) {
        if (throwException)
            return obj2Xml(obj, false, true);
        else {
            try {
                return obj2Xml(obj, false, true);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * obj2Xml
     * @param obj
     * @param indent
     * @param includeNullAndEmpty
     * @return
     */
    private static String obj2Xml(Object obj, Boolean indent, Boolean includeNullAndEmpty) {
        ObjectMapper mapper = getObjectMapper(indent, includeNullAndEmpty, null);
        String json;
        try {
            json = obj == null ? null : mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * getObjectMapper
     * @param indent
     * @param includeNullAndEmpty
     * @return
     */
    public final static ObjectMapper getObjectMapper(Boolean indent, Boolean includeNullAndEmpty) {
        return getObjectMapper(indent, includeNullAndEmpty, null, null);
    }

    /**
     * getObjectMapper
     * @param indent
     * @param includeNullAndEmpty
     * @param strategy
     * @return
     */
    public final static ObjectMapper getObjectMapper(Boolean indent, Boolean includeNullAndEmpty, PropertyNamingStrategy strategy) {
        return getObjectMapper(indent, includeNullAndEmpty, strategy, null);
    }

    /**
     * getObjectMapper
     * @param indent
     * @param includeNullAndEmpty
     * @param strategy
     * @param dateFormat
     * @return
     */
    public final static ObjectMapper getObjectMapper(Boolean indent, Boolean includeNullAndEmpty, PropertyNamingStrategy strategy, DateFormat dateFormat) {
        ObjectMapper mapper = Jackson2ObjectMapperBuilder.xml().build()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        if (strategy != null)
            mapper.setPropertyNamingStrategy(strategy);
        if (dateFormat != null)
            mapper.setDateFormat(dateFormat);
        if (indent != null)
            mapper.configure(SerializationFeature.INDENT_OUTPUT, indent);
        if (includeNullAndEmpty != null) {
            if (includeNullAndEmpty) {
                mapper.setSerializationInclusion(Include.ALWAYS);
            } else {
                mapper.setSerializationInclusion(Include.NON_NULL);
                mapper.setSerializationInclusion(Include.NON_EMPTY);
            }
        }
//        new DefaultSerializerProvider()
//        mapper.getSerializerProvider()
//        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//
//            @Override
//            public void serialize(Object value, JsonGenerator jg, SerializerProvider sp) throws IOException {
//                if (value == null) {
////                    jg.writeNull();
//                    jg.writeString("");
//                }
//            }
//        });
        return mapper;
    }

    /**
     * xml2T
     * @param jsonStr
     * @param clazz   {"key" : "value"}
     * @param <T>
     * @return
     */
    public final static <T> T xml2T(String jsonStr, Class<T> clazz) {
        ObjectMapper mapper = dumpObjectMapper;
        try {
            return jsonStr == null ? null : mapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    /**
//     * xml2XmlNode
//     *
//     * @param jsonStr
//     * @return
//     */
//    public final static JsonNode xml2XmlNode(String jsonStr) {
//        ObjectMapper mapper = dumpObjectMapper;
//        try {
//            return mapper.readTree(jsonStr);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    /**
     * xml2T
     * @param jsonStr
     * @param <T>
     * @return
     * @throws IOException
     */
    public final static <T> T xml2T(String jsonStr) {
        try {
            ObjectMapper mapper = dumpObjectMapper;
            TypeReference<T> valueTypeRef = new TypeReference<T>() {
            };
            return mapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * xml2T
     * @param jsonStr
     * @param valueTypeRef
     * @param <T>
     * @return
     * @throws IOException
     */
    public final static <T> T xml2T(String jsonStr, TypeReference<T> valueTypeRef) {
        try {
            ObjectMapper mapper = dumpObjectMapper;
            return mapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * can2T
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public final static <T> boolean can2T(String jsonStr, Class<T> clazz) {
        boolean can = xml2T(jsonStr, clazz) == null ? false : true;
        return can;
    }

    /**
     * can2T
     * @param jsonStr
     * @param valueTypeRef
     * @param <T>
     * @return
     * @throws IOException
     */
    public final static <T> boolean can2T(String jsonStr, TypeReference<T> valueTypeRef) {
        boolean can = xml2T(jsonStr, valueTypeRef) == null ? false : true;
        return can;
    }

    /**
     * json2JObj
     * @param jsonStr {"key" : "value"}
     * @return
     */
//    public final static JSONObject json2JObj(String jsonStr) {
//        return new JSONObject(jsonStr);
//    }

    /**
     * json2JArray
     * @param jsonStr [{"key1" : "value1"}, {"key2" : "value2"}]
     * @return
     */
//    public final static JSONArray json2JArray(String jsonStr) {
//        return new JSONArray(jsonStr);
//    }

    /**
     * deepCopy
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public final static <T> T deepCopy(Object source, Class<T> targetClass) {
        T t = JacksonXmlSugar.xml2T(JacksonXmlSugar.obj2Xml(source), targetClass);
        return t;
    }

    /**
     * deepCopy
     * @param source
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public final static <T> T deepCopy(Object source, TypeReference<T> valueTypeRef) {
        T t = JacksonXmlSugar.xml2T(JacksonXmlSugar.obj2Xml(source), valueTypeRef);
        return t;
    }

    /**
     * deepCopy
     * @param source
     * @param <T>
     * @return
     */
    public final static <T> T deepCopy(T source) {
        T t = (T) JacksonXmlSugar.xml2T(JacksonXmlSugar.obj2Xml(source), source.getClass());
        return t;
    }

    /**
     * newInstance
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public final static <T> T newInstance(TypeReference<T> valueTypeRef) {
        T t = JacksonXmlSugar.xml2T(JacksonXmlSugar.obj2Xml(new Object()), valueTypeRef);
        return t;
    }

    public final static <T> T newInstance(Class<T> clazz) {
        T t = JacksonXmlSugar.xml2T(JacksonXmlSugar.obj2Xml(new Object()), clazz);
        return t;
    }

    /**
     * newInstance
     * @param <T>
     * @return
     */
    public final static <T> T newInstance() {
        T t = JacksonXmlSugar.xml2T(JacksonXmlSugar.obj2Xml(new Object()));
        return t;
    }
}
