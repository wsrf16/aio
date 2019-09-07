package com.aio.portable.swiss.bean.serializer.xml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlUtil {
    public enum SerializerStyle {
        Default,
        CamelCase,
    }

    private final static ObjectMapper mapper = new XmlMapper();

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
            e.printStackTrace();
            return null;
        }
    }


}
