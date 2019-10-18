package com.aio.portable.swiss.structure.bean.serializer.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by York on 2017/11/22.
 */
public class JacksonSugar {
    public enum SerializerStyle {
        Default,
        CamelCase,
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
     *
     * @param obj
     * @return
     */
    public static String obj2ShortJson(Object obj) {
        return obj2Json(obj, false, false);
    }


    /**
     * obj2LongJson
     * @param obj
     * @param throwException
     * @return
     */
    public static String obj2ShortJson(Object obj, Boolean throwException) {
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
    public static String obj2LongJson(Object obj) {
        return obj2Json(obj, true, true);
    }


    /**
     * obj2LongJson
     * @param obj
     * @param throwException
     * @return
     */
    public static String obj2LongJson(Object obj, Boolean throwException) {
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
    public static String obj2Json(Object obj) {
        return obj2Json(obj, false, true);
    }


    /**
     * obj2Json
     *
     * @param obj
     * @return
     */
    public static String obj2Json(Object obj, Boolean throwException) {
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
    public static ObjectMapper getObjectMapper(Boolean indent, Boolean includeNullAndEmpty, PropertyNamingStrategy strategy) {
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
    public static ObjectMapper getObjectMapper(Boolean indent, Boolean includeNullAndEmpty, PropertyNamingStrategy strategy, DateFormat dateFormat) {
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
    public static <T> T json2T(String jsonStr, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
    public static JsonNode json2JsonNode(String jsonStr) {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
    public static <T> T json2Complex(String jsonStr) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                    .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

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
    public static <T> T json2Complex(String jsonStr, TypeReference<T> valueTypeRef) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                    .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
    public static <T> boolean can2T(String jsonStr, Class<T> clazz) {
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
    public static <T> boolean can2Complex(String jsonStr, TypeReference<T> valueTypeRef) {
        boolean can = json2Complex(jsonStr, valueTypeRef) == null ? false : true;
        return can;
    }

    /**
     * json2JObj
     *
     * @param jsonStr {"key" : "value"}
     * @return
     */
//    public static JSONObject json2JObj(String jsonStr) {
//        return new JSONObject(jsonStr);
//    }

    /**
     * json2JArray
     *
     * @param jsonStr [{"key1" : "value1"}, {"key2" : "value2"}]
     * @return
     */
//    public static JSONArray json2JArray(String jsonStr) {
//        return new JSONArray(jsonStr);
//    }

    /**
     * deepClone
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T deepClone(Object source, Class<T> targetClass) {
        T t = JacksonSugar.json2T(JacksonSugar.obj2Json(source), targetClass);
        return t;
    }


    private static class BlahUnit {
        private void todo() throws IOException {
            JsonModel a = new JsonModel() {{
                setNo(88);
            }};
            List<JsonModel> list = new ArrayList<>();
            list.add(a);
            Map<Integer, JsonModel> map = new HashMap<>();
            map.put(1, a);

            a = JacksonSugar.json2T(JacksonSugar.obj2Json(a), JsonModel.class);
            {
                JsonModel _model;
                _model= JacksonSugar.json2T(JacksonSugar.obj2Json(""), JsonModel.class);
                _model = JacksonSugar.json2T(JacksonSugar.obj2Json(null), JsonModel.class);
            }
            list = JacksonSugar.json2Complex(JacksonSugar.obj2Json(list));
            map = JacksonSugar.json2Complex(JacksonSugar.obj2Json(map));
//        String aListJson = JsonUtil.obj2Json(aList);
//        AA b = JsonUtil.json2Obj(aJson);
//        List<AA> bList = JsonUtil.json2Obj(aListJson);
        }

        class JsonModel {
            public int getNo() {
                return no;
            }

            public void setNo(int no) {
                this.no = no;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            private int no;

            private String name;
        }
    }
}
