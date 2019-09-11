package com.aio.portable.swiss.data.jpa;

import com.aio.portable.swiss.bean.serializer.json.JacksonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class JacksonAttributeConverter implements AttributeConverter<Object, String> {

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Object meta) {
        return JacksonUtil.obj2Json(meta);
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        return JacksonUtil.json2T(dbData, Object.class);
    }
}
