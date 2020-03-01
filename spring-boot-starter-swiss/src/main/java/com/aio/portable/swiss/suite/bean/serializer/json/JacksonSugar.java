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
public class JacksonSugar {
    public enum SerializerStyle {
        Default,
        CamelCase,
    }
    private final static ObjectMapper shortObjectMapper = getObjectMapper(false, false, null);
    private final static ObjectMapper longObjectMapper = getObjectMapper(true, true, null);
    private final static ObjectMapper normalObjectMapper = getObjectMapper(false, true, null);
    private final static ObjectMapper dumpObjectMapper = new ObjectMapper()
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
     * obj2ShortJson
     *
     * @param obj
     * @return
     */
    public final static String obj2ShortJson(Object obj) {
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
     * obj2LongJson
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
     *
     * @param obj
     * @return
     */
    public final static String obj2LongJson(Object obj) {
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
     *
     * @param obj
     * @return
     */
    public final static String obj2Json(Object obj) {
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
     * obj2Json
     *
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
            throw new RuntimeException(e);
        }
        return json;
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
     *
     * @param indent
     * @param includeNullAndEmpty
     * @param strategy
     * @param dateFormat
     * @return
     */
    public final static ObjectMapper getObjectMapper(Boolean indent, Boolean includeNullAndEmpty, PropertyNamingStrategy strategy, DateFormat dateFormat) {
        ObjectMapper mapper = new Jackson2ObjectMapperBuilder().build()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
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
     * json2Obj
     *
     * @param jsonStr
     * @param clazz   {"key" : "value"}
     * @param <T>
     * @return
     */
    public final static <T> T json2T(String jsonStr, Class<T> clazz) {
        ObjectMapper mapper = dumpObjectMapper;
        try {
            return jsonStr == null ? null : mapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json2JsonNode
     *
     * @param jsonStr
     * @return
     */
    public final static JsonNode json2JsonNode(String jsonStr) {
        ObjectMapper mapper = dumpObjectMapper;
        try {
            return mapper.readTree(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json2Complex
     *
     * @param jsonStr
     * @param <T>
     * @return
     * @throws IOException
     */
    public final static <T> T json2Complex(String jsonStr) {
        try {
            ObjectMapper mapper = dumpObjectMapper;

            return mapper.readValue(jsonStr, new TypeReference<T>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * json2Complex
     *
     * @param jsonStr
     * @param valueTypeRef
     * @param <T>
     * @return
     * @throws IOException
     */
    public final static <T> T json2Complex(String jsonStr, TypeReference<T> valueTypeRef) {
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
     *
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
     * can2Complex
     * @param jsonStr
     * @param valueTypeRef
     * @param <T>
     * @return
     * @throws IOException
     */
    public final static <T> boolean can2Complex(String jsonStr, TypeReference<T> valueTypeRef) {
        boolean can = json2Complex(jsonStr, valueTypeRef) == null ? false : true;
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
     *
     * @param jsonStr [{"key1" : "value1"}, {"key2" : "value2"}]
     * @return
     */
//    public final static JSONArray json2JArray(String jsonStr) {
//        return new JSONArray(jsonStr);
//    }

    /**
     * deepClone
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public final static <T> T deepClone(Object source, Class<T> targetClass) {
        T t = JacksonSugar.json2T(JacksonSugar.obj2Json(source), targetClass);
        return t;
    }



}
