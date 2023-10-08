package com.aio.portable.swiss.suite.bean.serializer.json;

import com.aio.portable.swiss.sugar.resource.ClassLoaderSugar;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by York on 2017/11/22.
 */
public class JacksonSugar {
    public enum SerializerStyle {
        DEFAULT,
        CAMEL_CASE,
    }

//    private static final class Instance {
//        private static final Instance singleton = new Instance();
//        private static final Instance getInstance() {
//            return singleton;
//        }
//
//    }
    private static final ObjectMapper shortObjectMapper = Builder.buildShortObjectMapper();
    private static final ObjectMapper normalObjectMapper = Builder.buildObjectMapper();
    private static final ObjectMapper longObjectMapper = Builder.buildLongObjectMapper();
    private static final ObjectMapper dumpObjectMapper = Builder.buildDumpObjectMapper();

    public static final class Builder {
        /**
         * buildObjectMapper
         * @param indent
         * @param includeNullAndEmpty
         * @param strategy
         * @param dateFormat
         * @return
         */
        public static final ObjectMapper buildObjectMapper(Boolean indent, Boolean includeNullAndEmpty, PropertyNamingStrategy strategy, DateFormat dateFormat) {
            ObjectMapper mapper = buildObjectMapper()
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            if (strategy != null)
                mapper.setPropertyNamingStrategy(strategy);
            if (dateFormat != null)
                mapper.setDateFormat(dateFormat);
            if (indent == true) {
                DefaultPrettyPrinter prettyPrinter = (DefaultPrettyPrinter) mapper.getSerializationConfig().getDefaultPrettyPrinter();
                DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("    ", DefaultIndenter.SYS_LF);
                prettyPrinter.indentArraysWith(indenter);
                prettyPrinter.indentObjectsWith(indenter);
                mapper.setDefaultPrettyPrinter(prettyPrinter);

//            mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.configure(SerializationFeature.INDENT_OUTPUT, indent);
            }
            if (includeNullAndEmpty != null) {
                if (includeNullAndEmpty) {
                    mapper.setSerializationInclusion(Include.ALWAYS);
                } else {
                    mapper.setSerializationInclusion(Include.NON_NULL);
                    mapper.setSerializationInclusion(Include.NON_EMPTY);
                }
            }
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
         * buildObjectMapper
         * @param indent
         * @param includeNullAndEmpty
         * @param strategy
         * @return
         */
        public static final ObjectMapper buildObjectMapper(Boolean indent, Boolean includeNullAndEmpty, PropertyNamingStrategy strategy) {
            return buildObjectMapper(indent, includeNullAndEmpty, strategy, null);
        }

        /**
         * buildObjectMapper
         * @param indent
         * @param includeNullAndEmpty
         * @return
         */
        public static final ObjectMapper buildObjectMapper(Boolean indent, Boolean includeNullAndEmpty) {
            return buildObjectMapper(indent, includeNullAndEmpty, null, null);
        }

        public static final ObjectMapper buildShortObjectMapper() {
            return buildObjectMapper(false, false);
        }

        public static final ObjectMapper buildObjectMapper() {
            boolean present = ClassLoaderSugar.isPresent("org.springframework.http.converter.json.Jackson2ObjectMapperBuilder");
            if (present)
//                return new ObjectMapper();
                // log circular reference
                return Jackson2ObjectMapperBuilder.json().build();
            else
                return buildObjectMapper(false, true);
        }

        public static final ObjectMapper buildLongObjectMapper() {
            return buildObjectMapper(true, true);
        }

        public static final ObjectMapper buildDumpObjectMapper() {
            return new ObjectMapper()
                    .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                    .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

    }



    /**
     * obj2ShortJson
     * @param obj
     * @return
     */
    public static final String obj2ShortJson(Object obj) {
//        return obj2Json(obj, false, false);
        ObjectMapper mapper = shortObjectMapper;
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
    public static final String obj2ShortJson(Object obj, Boolean throwException) {
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
    public static final String obj2LongJson(Object obj) {
//        return obj2Json(obj, true, true);
        ObjectMapper mapper = longObjectMapper;
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
    public static final String obj2LongJson(Object obj, Boolean throwException) {
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
    public static final String obj2Json(Object obj) {
//        return obj2Json(obj, false, true);
        ObjectMapper mapper = normalObjectMapper;
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

    public static final String obj2Json(ObjectMapper objectMapper, Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * obj2Json
     * @param obj
     * @return
     */
    public static final String obj2Json(Object obj, Boolean throwException) {
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

    public static final LinkedHashMap obj2Map(Object obj) {
        String json = JacksonSugar.obj2Json(obj);
        return JacksonSugar.json2T(json, LinkedHashMap.class);
    }

    /**
     * obj2Json
     * @param obj
     * @param indent
     * @return
     */
    private static String obj2Json(Object obj, Boolean indent, Boolean includeNullAndEmpty) {
        ObjectMapper mapper = Builder.buildObjectMapper(indent, includeNullAndEmpty);
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
     * json2T
     * @param jsonStr
     * @param clazz   {"key" : "value"}
     * @param <T>
     * @return
     */
    public static final <T> T json2T(String jsonStr, Class<T> clazz) {
        ObjectMapper mapper = dumpObjectMapper;
        try {
            return jsonStr == null ? null : mapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final <T> T json2T(ObjectMapper objectMapper, String jsonStr, Class<T> clazz) {
        try {
            return jsonStr == null ? null : objectMapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final HashMap<Object, Object> json2ObjectMap(String jsonStr) {
        return json2T(jsonStr, new HashMap<Object, Object>().getClass());
    }

    public static final HashMap<String, Object> json2StringMap(String jsonStr) {
        return json2T(jsonStr, new HashMap<String, Object>().getClass());
    }

    /**
     * json2JsonNode
     * @param jsonStr
     * @return
     */
    public static final JsonNode json2JsonNode(String jsonStr) {
        ObjectMapper mapper = dumpObjectMapper;
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
    public static final <T> T json2T(String jsonStr) {
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
     * json2T
     * @param jsonStr
     * @param valueTypeRef
     * @param <T>
     * @return
     * @throws IOException
     */
    public static final <T> T json2T(String jsonStr, TypeReference<T> valueTypeRef) {
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
    public static final <T> boolean can2T(String jsonStr, Class<T> clazz) {
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
    public static final <T> boolean can2T(String jsonStr, TypeReference<T> valueTypeRef) {
        boolean can = json2T(jsonStr, valueTypeRef) == null ? false : true;
        return can;
    }

    /**
     * json2JObj
     *
     * @param jsonStr {"key" : "value"}
     * @return
     */
//    public static final JSONObject json2JObj(String jsonStr) {
//        return new JSONObject(jsonStr);
//    }

    /**
     * json2JArray
     * @param jsonStr [{"key1" : "value1"}, {"key2" : "value2"}]
     * @return
     */
//    public static final JSONArray json2JArray(String jsonStr) {
//        return new JSONArray(jsonStr);
//    }

    /**
     * deepCopy
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static final <T> T deepCopy(Object source, Class<T> targetClass) {
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
    public static final <T> T deepCopy(Object source, TypeReference<T> valueTypeRef) {
        T t = JacksonSugar.json2T(JacksonSugar.obj2Json(source), valueTypeRef);
        return t;
    }

    /**
     * deepCopy
     * @param source
     * @param <T>
     * @return
     */
    public static final <T> T deepCopy(T source) {
        T t = (T) JacksonSugar.json2T(JacksonSugar.obj2Json(source), source.getClass());
        return t;
    }

    /**
     * formatLong
     * @param source
     * @return
     */
    public static final String formatLong(String source) {
        return JacksonSugar.obj2LongJson(JacksonSugar.json2T(source, Object.class));
    }

    /**
     * formatShort
     * @param source
     * @return
     */
    public static final String formatShort(String source) {
        return JacksonSugar.obj2ShortJson(JacksonSugar.json2T(source, Object.class));
    }

    /**
     * format
     * @param source
     * @return
     */
    public static final String format(String source) {
        return JacksonSugar.obj2Json(JacksonSugar.json2T(source, Object.class));
    }

    /**
     * newInstance
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public static final <T> T newInstance(TypeReference<T> valueTypeRef) {
        T t = JacksonSugar.json2T(JacksonSugar.obj2Json(new Object()), valueTypeRef);
        return t;
    }

    public static final <T> T newInstance(Class<T> clazz) {
        T t = JacksonSugar.json2T(JacksonSugar.obj2Json(new Object()), clazz);
        return t;
    }

    /**
     * newInstance
     * @param <T>
     * @return
     */
    public static final <T> T newInstance() {
        T t = JacksonSugar.json2T(JacksonSugar.obj2Json(new Object()));
        return t;
    }
}
