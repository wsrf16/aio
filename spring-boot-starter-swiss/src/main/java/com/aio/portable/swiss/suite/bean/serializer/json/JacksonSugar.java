package com.aio.portable.swiss.suite.bean.serializer.json;

import com.aio.portable.swiss.sugar.resource.ClassLoaderSugar;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by York on 2017/11/22.
 */
public class JacksonSugar {
    public enum SerializerStyle {
        Default,
        CamelCase,
    }

    private static class JacksonConfig {
        private final static JacksonConfig singleton = new JacksonConfig();
        private final static JacksonConfig getInstance() {
            return singleton;
        }

        private final ObjectMapper shortObjectMapper = getObjectMapper(false, false, null);
        private final ObjectMapper longObjectMapper = getObjectMapper(true, true, null);
        private final ObjectMapper normalObjectMapper = getObjectMapper(false, true, null);
        private final ObjectMapper dumpObjectMapper = new ObjectMapper()
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

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
     * obj2ShortJson
     * @param obj
     * @return
     */
    public final static String obj2ShortJson(Object obj) {
//        return obj2Json(obj, false, false);
        ObjectMapper mapper = JacksonConfig.getInstance().shortObjectMapper;
        String json;
        try {
            json = obj == null ? null : mapper.writeValueAsString(obj);
        } catch (Exception e) {
//            e.printStackTrace();
            if (e.getCause() instanceof StackOverflowError) {
                Map<String, Object> map = BeanSugar.PropertyDescriptors.toNameValueMapExceptNull(obj);
                json = obj2ShortJson(map);
            }
            else
                throw new RuntimeException(e);
        }
        return json;
    }


    /**
     * obj2ShortJson
     * @param obj
     * @param throwException
     * @return
     */
    public final static String obj2ShortJson(Object obj, Boolean throwException) {
        if (throwException)
            return obj2ShortJson(obj);
        else {
            try {
                return obj2ShortJson(obj);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    /**
     * obj2LongJson
     * @param obj
     * @return
     */
    public final static String obj2LongJson(Object obj) {
//        return obj2Json(obj, true, true);
        ObjectMapper mapper = JacksonConfig.getInstance().longObjectMapper;
        String json;
        try {
            json = obj == null ? null : mapper.writeValueAsString(obj);
        } catch (Exception e) {
//            e.printStackTrace();
            if (e.getCause() instanceof StackOverflowError) {
                Map<String, Object> map = BeanSugar.PropertyDescriptors.toNameValueMap(obj);
                json = obj2LongJson(map);
            }
            else
                throw new RuntimeException(e);
        }
        return json;
    }


    /**
     * obj2LongJson
     * @param obj
     * @param throwException
     * @return
     */
    public final static String obj2LongJson(Object obj, Boolean throwException) {
        if (throwException)
            return obj2LongJson(obj);
        else {
            try {
                return obj2LongJson(obj);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    /**
     * obj2Json
     * @param obj
     * @return
     */
    public final static String obj2Json(Object obj) {
//        return obj2Json(obj, false, true);
        ObjectMapper mapper = JacksonConfig.getInstance().normalObjectMapper;
        String json;
        try {
            json = obj == null ? null : mapper.writeValueAsString(obj);
        } catch (Exception e) {
//            e.printStackTrace();
            if (e.getCause() instanceof StackOverflowError) {
                Map<String, Object> map = BeanSugar.PropertyDescriptors.toNameValueMap(obj);
                json = obj2Json(map);
            }
            else
                throw new RuntimeException(e);
        }
        return json;
    }


    /**
     * obj2Json
     * @param obj
     * @return
     */
    public final static String obj2Json(Object obj, Boolean throwException) {
        if (throwException)
            return obj2Json(obj, false, true);
        else {
            try {
                return obj2Json(obj, false, true);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public final static LinkedHashMap obj2Map(Object obj) {
        String json = JacksonSugar.obj2Json(obj);
        return JacksonSugar.json2T(json, LinkedHashMap.class);
    }

    /**
     * obj2Json
     * @param obj
     * @param indent
     * @param includeNullAndEmpty
     * @return
     */
    private static String obj2Json(Object obj, Boolean indent, Boolean includeNullAndEmpty) {
        ObjectMapper mapper = getObjectMapper(indent, includeNullAndEmpty, null);
        String json;
        try {
            json = obj == null ? null : mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getCause() instanceof StackOverflowError) {
                Map<String, Object> map = includeNullAndEmpty ? BeanSugar.PropertyDescriptors.toNameValueMap(obj) :
                        BeanSugar.PropertyDescriptors.toNameValueMapExceptNull(obj);
                json = obj2LongJson(map);
            }
            else
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

    private final static ObjectMapper buildObjectMapper() {
        boolean present = ClassLoaderSugar.isPresent("org.springframework.http.converter.json.Jackson2ObjectMapperBuilder");
        if (present)
            return Jackson2ObjectMapperBuilder.json().build();
        else
            return new ObjectMapper();
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
        ObjectMapper mapper = buildObjectMapper()
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
     * json2T
     * @param jsonStr
     * @param clazz   {"key" : "value"}
     * @param <T>
     * @return
     */
    public final static <T> T json2T(String jsonStr, Class<T> clazz) {
        ObjectMapper mapper = JacksonConfig.getInstance().dumpObjectMapper;
        try {
            return jsonStr == null ? null : mapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json2JsonNode
     * @param jsonStr
     * @return
     */
    public final static JsonNode json2JsonNode(String jsonStr) {
        ObjectMapper mapper = JacksonConfig.getInstance().dumpObjectMapper;
        try {
            return mapper.readTree(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json2T
     * @param jsonStr
     * @param <T>
     * @return
     * @throws IOException
     */
    public final static <T> T json2T(String jsonStr) {
        try {
            ObjectMapper mapper = JacksonConfig.getInstance().dumpObjectMapper;
            TypeReference<T> valueTypeRef = new TypeReference<T>() {
            };
            return mapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * json2T
     * @param jsonStr
     * @param valueTypeRef
     * @param <T>
     * @return
     * @throws IOException
     */
    public final static <T> T json2T(String jsonStr, TypeReference<T> valueTypeRef) {
        try {
            ObjectMapper mapper = JacksonConfig.getInstance().dumpObjectMapper;
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
        boolean can = json2T(jsonStr, clazz) == null ? false : true;
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
        boolean can = json2T(jsonStr, valueTypeRef) == null ? false : true;
        return can;
    }

    /**
     * json2JObj
     *
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
        T t = JacksonSugar.json2T(JacksonSugar.obj2Json(source), targetClass);
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
        T t = JacksonSugar.json2T(JacksonSugar.obj2Json(source), valueTypeRef);
        return t;
    }

    /**
     * deepCopy
     * @param source
     * @param <T>
     * @return
     */
    public final static <T> T deepCopy(T source) {
        T t = (T) JacksonSugar.json2T(JacksonSugar.obj2Json(source), source.getClass());
        return t;
    }

    /**
     * newInstance
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public final static <T> T newInstance(TypeReference<T> valueTypeRef) {
        T t = JacksonSugar.json2T(JacksonSugar.obj2Json(new Object()), valueTypeRef);
        return t;
    }

    public final static <T> T newInstance(Class<T> clazz) {
        T t = JacksonSugar.json2T(JacksonSugar.obj2Json(new Object()), clazz);
        return t;
    }

    /**
     * newInstance
     * @param <T>
     * @return
     */
    public final static <T> T newInstance() {
        T t = JacksonSugar.json2T(JacksonSugar.obj2Json(new Object()));
        return t;
    }
}
