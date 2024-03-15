package com.aio.portable.swiss.suite.bean.serializer.json;

import com.aio.portable.swiss.sugar.resource.ClassLoaderSugar;
import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;


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
    public static final ObjectMapper SHORT_OBJECT_MAPPER = Builder.buildShortObjectMapper();
    public static final ObjectMapper NORMAL_OBJECT_MAPPER = Builder.buildObjectMapper();
    public static final ObjectMapper SILENCE_OBJECT_MAPPER = Builder.buildSilenceObjectMapper();
    public static final ObjectMapper LONG_OBJECT_MAPPER = Builder.buildLongObjectMapper();
    public static final ObjectMapper DUMP_OBJECT_MAPPER = Builder.buildDumpObjectMapper();

    private static DateFormat dateFormat = new SimpleDateFormat(DateTimeSugar.Format.FORMAT_NORMAL_LONG);
    private static TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
    public static final synchronized void setDateFormat(DateFormat dateFormat) {
        JacksonSugar.dateFormat = dateFormat;
    }

    public static class Builder {
        /**
         * buildObjectMapper
         * @param indent
         * @param includeNullAndEmpty
         * @param strategy
         * @param dateFormat
         * @return
         */
        public static final ObjectMapper buildObjectMapper(Boolean indent, Boolean includeNullAndEmpty, PropertyNamingStrategy strategy, DateFormat dateFormat, TimeZone timeZone) {
            ObjectMapper mapper = buildObjectMapper();
            return buildObjectMapper(mapper, indent, includeNullAndEmpty, strategy, dateFormat, timeZone);
        }

        private static final ObjectMapper buildObjectMapper(ObjectMapper mapper, Boolean indent, Boolean includeNullAndEmpty, PropertyNamingStrategy strategy, DateFormat dateFormat, TimeZone timeZone) {
            mapper
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
//                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            if (strategy != null)
                mapper.setPropertyNamingStrategy(strategy);
            if (dateFormat != null)
                mapper.setDateFormat(dateFormat);
            if (timeZone != null)
                mapper.setTimeZone(timeZone);
            if (indent == true) {
                DefaultPrettyPrinter prettyPrinter = (DefaultPrettyPrinter) mapper.getSerializationConfig().getDefaultPrettyPrinter();
                DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("    ", DefaultIndenter.SYS_LF);
                prettyPrinter.indentArraysWith(indenter);
                prettyPrinter.indentObjectsWith(indenter);
                mapper.setDefaultPrettyPrinter(prettyPrinter);

                mapper.enable(SerializationFeature.INDENT_OUTPUT);
//                mapper.configure(SerializationFeature.INDENT_OUTPUT, indent);
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
         *
         * @param indent
         * @param includeNullAndEmpty
         * @param strategy
         * @return
         */
        public static final ObjectMapper buildObjectMapper(Boolean indent, Boolean includeNullAndEmpty, PropertyNamingStrategy strategy) {
            return buildObjectMapper(indent, includeNullAndEmpty, strategy, dateFormat, timeZone);
        }

        /**
         * buildObjectMapper
         * @param indent
         * @param includeNullAndEmpty
         * @return
         */
        public static final ObjectMapper buildObjectMapper(Boolean indent, Boolean includeNullAndEmpty) {
            return buildObjectMapper(indent, includeNullAndEmpty, null, dateFormat, timeZone);
        }

        public static final ObjectMapper buildShortObjectMapper() {
            return buildObjectMapper(false, false);
        }

        public static final ObjectMapper buildObjectMapper() {
            boolean present = ClassLoaderSugar.isPresent("org.springframework.http.converter.json.Jackson2ObjectMapperBuilder");
            if (present) {
                // log circular reference
                ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
                return buildObjectMapper(mapper, false, true, null, dateFormat, timeZone);
            } else
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

        private static final ObjectMapper buildSilenceObjectMapper() {
            return Builder.buildObjectMapper(false, true);
        }

    }


    /**
     * beJson
     * @param json
     * @return
     */
    public static final boolean beJson(String json) {
        try {
            NORMAL_OBJECT_MAPPER.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * obj2ShortJson
     * @param obj
     * @return
     */
    public static final String obj2ShortJson(Object obj) {
//        return obj2Json(obj, false, false);
        ObjectMapper mapper = SHORT_OBJECT_MAPPER;
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
//                e.printStackTrace();
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
        ObjectMapper mapper = LONG_OBJECT_MAPPER;
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
        ObjectMapper mapper = NORMAL_OBJECT_MAPPER;
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

//    public static final String obj2Json(ObjectMapper objectMapper, Object obj) {
//        try {
//            return objectMapper.writeValueAsString(obj);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static final String obj2Json(ObjectMapper objectMapper, Object obj, Boolean throwException) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            if (throwException)
                throw new RuntimeException(e);
            else
                e.printStackTrace();
                return null;
        }
    }


    /**
     * obj2Json
     * @param obj
     * @return
     */
    public static final String obj2Json(Object obj, Boolean throwException) {
        ObjectMapper mapper = SILENCE_OBJECT_MAPPER;
        return obj2Json(mapper, obj, throwException);
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
//            e.printStackTrace();
            if (e.getCause() instanceof StackOverflowError) {
                Map<String, Object> map = includeNullAndEmpty ? BeanSugar.PropertyDescriptors.toNameValueMap(obj) : BeanSugar.PropertyDescriptors.toNameValueMapExceptNull(obj);
                json = obj2LongJson(map);
            }
            else
                throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * json2T
     * @param jsonStr   {"key" : "value"}
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> T json2T(String jsonStr, Class<T> clazz) {
        ObjectMapper mapper = DUMP_OBJECT_MAPPER;
        try {
            return jsonStr == null ? null : mapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
//            e.printStackTrace();
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
        ObjectMapper mapper = DUMP_OBJECT_MAPPER;
        try {
            return mapper.readTree(jsonStr);
        } catch (IOException e) {
//            e.printStackTrace();
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
            ObjectMapper mapper = DUMP_OBJECT_MAPPER;
            TypeReference<T> valueTypeRef = new TypeReference<T>() {
            };
            return mapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
//            e.printStackTrace();
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
            ObjectMapper mapper = DUMP_OBJECT_MAPPER;
            return mapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
//            e.printStackTrace();
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

    public static final byte[] objToJsonBytes(Object obj){
        String json = JacksonSugar.obj2Json(obj);
        return json.getBytes(StandardCharsets.UTF_8);
    }

    public static final <T> T jsonBytesToObj(byte[] data, Class<T> clazz){
        String json = new String(data, StandardCharsets.UTF_8);
        return JacksonSugar.json2T(json, clazz);
    }

    public static final String objToJsonBase64(Object obj){
        String json = JacksonSugar.obj2Json(obj);
        return JDKBase64Convert.encodeToString(json);
    }

    public static final <T> T jsonBase64ToObj(String jsonBase64, Class<T> clazz){
        String json = JDKBase64Convert.decodeToString(jsonBase64);
        return JacksonSugar.json2T(json, clazz);
    }
}
