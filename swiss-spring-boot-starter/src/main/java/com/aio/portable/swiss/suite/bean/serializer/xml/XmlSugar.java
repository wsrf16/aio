package com.aio.portable.swiss.suite.bean.serializer.xml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.text.DateFormat;

public abstract class XmlSugar {
    public enum SerializerStyle {
        Default,
        CamelCase,
    }

    private static final ObjectMapper mapper = new XmlMapper();

    static {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);//
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String obj2ShortXml(Object obj) {
        return obj2Xml(obj, false, JsonInclude.Include.NON_NULL);
    }

    public static String obj2Xml(Object obj) {
        return obj2Xml(obj, true, JsonInclude.Include.NON_NULL);
    }

    public static String obj2Xml(Object obj, Boolean indent, com.fasterxml.jackson.annotation.JsonInclude.Include includeNonNull) {
        // CamelCase/////////////////////
        ObjectMapper mapper = new XmlMapper()
                .configure(SerializationFeature.INDENT_OUTPUT, indent)
                .setSerializationInclusion(includeNonNull);

        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> T xml2T(String xml, Class<T> clazz) {
        ObjectMapper mapper = new XmlMapper();
        try {
            return mapper.readValue(xml, clazz);
        } catch (IOException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }






    public static final ObjectMapper getObjectMapper(Boolean indent, Boolean includeNullAndEmpty, PropertyNamingStrategy strategy, DateFormat dateFormat) {
        ObjectMapper mapper = new Jackson2ObjectMapperBuilder().build()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        if (strategy != null)
            mapper.setPropertyNamingStrategy(strategy);
        if (dateFormat != null)
            mapper.setDateFormat(dateFormat);
        if (indent != null)
            mapper.configure(SerializationFeature.INDENT_OUTPUT, indent);
        return mapper;
    }
}