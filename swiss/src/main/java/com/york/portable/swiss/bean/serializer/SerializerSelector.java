package com.york.portable.swiss.bean.serializer;

import com.york.portable.swiss.bean.serializer.json.JacksonUtil;
import com.york.portable.swiss.bean.serializer.json.GsonUtil;
import com.york.portable.swiss.bean.serializer.xml.XmlUtil;
import com.york.portable.swiss.resource.ClassUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by York on 2017/11/22.
 */
public class SerializerSelector implements ISerializerSelector {
    public SerializerSelector() {
        inital();
    }

    public SerializerSelector(SerializerEnum serializerType) {
        this();
        setCurrentSerializer(serializerType);
    }

    private SerializerEnum currentSerializerType;

    Map<SerializerEnum, Function<Object, String>> classicSerializers;


    public SerializerEnum getCurrentSerializerType() {
        return currentSerializerType;
    }

    public void setCurrentSerializerType(SerializerEnum currentSerializerType) {
        this.currentSerializerType = currentSerializerType;
    }

    public Function<Object, String> getCurrentSerializer() {
        return classicSerializers.get(currentSerializerType);
    }

    public final Function<Object, String> setCurrentSerializer(SerializerEnum serializerType) {
        this.currentSerializerType = serializerType;
        return classicSerializers.get(serializerType);
    }

    public final void setSerializer(SerializerEnum serializerType, Function<Object, String> function) {
        classicSerializers.put(serializerType, function);
    }

    private void inital() {
        classicSerializers = new HashMap<>();
        classicSerializers.put(SerializerEnum.SERIALIZE_JACKSON, bean -> JacksonUtil.obj2Json(bean));
        classicSerializers.put(SerializerEnum.SERIALIZE_SHORTJACKSON, bean -> JacksonUtil.obj2ShortJson(bean));
        classicSerializers.put(SerializerEnum.SERIALIZE_JACKXML, bean -> XmlUtil.obj2Xml(bean));
        classicSerializers.put(SerializerEnum.SERIALIZE_SHORTJACKXML, bean -> XmlUtil.obj2ShortXml(bean));
        classicSerializers.put(SerializerEnum.SERIALIZE_GSON, bean -> GsonUtil.obj2Json(bean));
        classicSerializers.put(SerializerEnum.SERIALIZE_SHORTGSON, bean -> GsonUtil.obj2Json(bean));
        classicSerializers.put(SerializerEnum.SERIALIZE_CUSTOM, null);

//        classicSerializers.put(SerializerEnum.DESERIALIZE_JACKSON, (json, clazz) -> JacksonUtil.json2T(json, clazz));
//        classicSerializers.put(SerializerEnum.DESERIALIZE_JACKXML, (json, clazz) -> XmlUtil..obj2Xml(json, clazz));
//        classicSerializers.put(SerializerEnum.DESERIALIZE_GSON, (json, clazz) -> GsonUtil.json2T(json, clazz));
//        classicSerializers.put(SerializerEnum.DESERIALIZE_CUSTOM, null);
    }

    public <T> String serialize(T t) {
        Function<Object, String> serializer;
        if (getCurrentSerializerType() == null && existJackson()) {
            serializer = setCurrentSerializer(SerializerEnum.SERIALIZE_JACKSON);
        }
        else if (getCurrentSerializerType() == null && existGson()){
            serializer = setCurrentSerializer(SerializerEnum.SERIALIZE_GSON);
        } else {
            serializer = setCurrentSerializer(currentSerializerType);
        }

        String ret = serializer.apply(t);
        return ret;
    }




    private static boolean existJackson() {
//        return ClassUtils.load("com.fasterxml.jackson.databind.JsonSerializer");
        try {
            return ClassUtils.exist("com.fasterxml.jackson.databind.JsonSerializer") &&
                    ClassUtils.exist("com.fasterxml.jackson.databind.ObjectMapper");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static boolean existGson() {
//        return ClassUtils.load("com.google.gson.Gson");
        try {
            return ClassUtils.exist("com.google.gson.Gson");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}