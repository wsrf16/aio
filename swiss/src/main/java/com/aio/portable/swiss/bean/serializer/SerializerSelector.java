package com.aio.portable.swiss.bean.serializer;

import com.aio.portable.swiss.bean.serializer.json.GsonUtil;
import com.aio.portable.swiss.bean.serializer.json.JacksonUtil;
import com.aio.portable.swiss.bean.serializer.xml.XmlUtil;
import com.aio.portable.swiss.resource.ClassWorld;

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
        setSerializer(serializerType);
    }

    private SerializerEnum serializerType;

    Map<SerializerEnum, Function<Object, String>> classicSerializers = new HashMap<>();


//    public SerializerEnum getCurrentSerializerType() {
//        return serializerType;
//    }

//    public void setCurrentSerializerType(SerializerEnum serializerType) {
//        this.serializerType = serializerType;
//    }

    public Map.Entry<SerializerEnum, Function<Object, String>> getSerializer() {
        return serializerType == null ? null : classicSerializers.entrySet().stream().filter(c -> c.getKey() == serializerType).findFirst().orElse(null);
    }

    public final synchronized void setSerializer(SerializerEnum serializerType) {
        this.serializerType = serializerType;
    }

    public final void putSerializer(SerializerEnum serializerType, Function<Object, String> function) {
        classicSerializers.put(serializerType, function);
    }

    private final void inital() {
//        classicSerializers = new HashMap<>();
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

    public synchronized <T> String serialize(T t) {
        if (getSerializer() == null && existJackson()) {
            setSerializer(SerializerEnum.SERIALIZE_JACKSON);
        } else if (getSerializer() == null && existGson()) {
            setSerializer(SerializerEnum.SERIALIZE_GSON);
        } else {
            if (serializerType == null)
                throw new NullPointerException(serializerType.getClass().getTypeName());
            else
                setSerializer(serializerType);
        }
        Function<Object, String> serializer = getSerializer().getValue();
        String ret = serializer.apply(t);
        return ret;
    }


    private final static boolean existJackson() {
//        return ClassUtils.load("com.fasterxml.jackson.databind.JsonSerializer");
        try {
            return ClassWorld.exist("com.fasterxml.jackson.databind.JsonSerializer") &&
                    ClassWorld.exist("com.fasterxml.jackson.databind.ObjectMapper");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private final static boolean existGson() {
//        return ClassUtils.load("com.google.gson.Gson");
        try {
            return ClassWorld.exist("com.google.gson.Gson");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}