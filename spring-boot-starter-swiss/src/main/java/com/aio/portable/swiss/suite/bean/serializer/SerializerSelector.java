package com.aio.portable.swiss.suite.bean.serializer;

/**
 * Created by York on 2017/11/22.
 */
//public class SerializerSelector implements ISerializerSelector {
//    public SerializerSelector() {
//        inital();
//    }
//
//    public SerializerSelector(SerializerEnum serializerType) {
//        this();
//        setSerializer(serializerType);
//    }
//
//    private SerializerEnum serializerType;
//
//    Map<SerializerEnum, SerializerConverter> classicSerializers = new HashMap<>();
//
//
////    public SerializerEnum getCurrentSerializerType() {
////        return serializerType;
////    }
//
////    public void setCurrentSerializerType(SerializerEnum serializerType) {
////        this.serializerType = serializerType;
////    }
//
//    public Map.Entry<SerializerEnum, SerializerConverter> getSerializer() {
//        return serializerType == null ? null : classicSerializers.entrySet().stream().filter(c -> c.getKey() == serializerType).findFirst().orElse(null);
//    }
//
//    public final synchronized void setSerializer(SerializerEnum serializerType) {
//        this.serializerType = serializerType;
//    }
//
//    public final void putSerializer(SerializerEnum serializerType, SerializerConverter serializer) {
//        classicSerializers.put(serializerType, serializer);
//    }
//
//    private final void inital() {
//        classicSerializers.put(SerializerEnum.SERIALIZE_JACKSON, new DynamicSerializer.JacksonConverter());
//        classicSerializers.put(SerializerEnum.SERIALIZE_SHORT_JACKSON, new DynamicSerializer.ShortJacksonConverter());
//        classicSerializers.put(SerializerEnum.SERIALIZE_FORCE_JACKSON, new DynamicSerializer.ForceJacksonConverter());
//        classicSerializers.put(SerializerEnum.SERIALIZE_FORCE_SHORT_JACKSON, new DynamicSerializer.ForceShortJacksonConverter());
//        classicSerializers.put(SerializerEnum.SERIALIZE_JACKXML, new DynamicSerializer.JackXmlConverter());
//        classicSerializers.put(SerializerEnum.SERIALIZE_SHORTJACKXML, new DynamicSerializer.ShortJackXmlConverter());
//        classicSerializers.put(SerializerEnum.SERIALIZE_GSON, new DynamicSerializer.GsonConverter());
//        classicSerializers.put(SerializerEnum.SERIALIZE_SHORT_GSON, new DynamicSerializer.GsonConverter());
//        classicSerializers.put(SerializerEnum.SERIALIZE_CUSTOM, null);
//
////        classicSerializers.put(SerializerEnum.DESERIALIZE_JACKSON, (json, clazz) -> JacksonUtil.json2T(json, clazz));
////        classicSerializers.put(SerializerEnum.DESERIALIZE_JACKXML, (json, clazz) -> XmlUtil..obj2Xml(json, clazz));
////        classicSerializers.put(SerializerEnum.DESERIALIZE_GSON, (json, clazz) -> GsonUtil.json2T(json, clazz));
////        classicSerializers.put(SerializerEnum.DESERIALIZE_CUSTOM, null);
//    }
//
//    public synchronized <T> String serialize(T t) {
//        if (getSerializer() != null) {
//            setSerializer(serializerType);
//        } else if (existJackson()) {
//            setSerializer(SerializerEnum.SERIALIZE_JACKSON);
//        } else if (existGson()) {
//            setSerializer(SerializerEnum.SERIALIZE_GSON);
//        } else {
//            throw new NullPointerException(serializerType.getClass().getTypeName());
//        }
//        SerializerConverter serializer = getSerializer().getValue();
//        String ret = serializer.serialize(t);
//        return ret;
//    }
//
//    public synchronized <T> T deserialize(String text, Class<T> clazz) {
//        if (getSerializer() != null) {
//            setSerializer(serializerType);
//        } else if (existJackson()) {
//            setSerializer(SerializerEnum.SERIALIZE_JACKSON);
//        } else if (existGson()) {
//            setSerializer(SerializerEnum.SERIALIZE_GSON);
//        } else {
//            throw new NullPointerException(serializerType.getClass().getTypeName());
//        }
//
//        SerializerConverter serializer = getSerializer().getValue();
//        T t = serializer.deserialize(text, clazz);
//        return t;
//    }
//
//
//    private final static boolean existJackson() {
////        return ClassUtils.load("com.fasterxml.jackson.databind.JsonSerializer");
//        try {
//            return ClassSugar.exist("com.fasterxml.jackson.databind.JsonSerializer") &&
//                    ClassSugar.exist("com.fasterxml.jackson.databind.ObjectMapper");
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    private final static boolean existGson() {
////        return ClassUtils.load("com.google.gson.Gson");
//        try {
//            return ClassSugar.exist("com.google.gson.Gson");
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//
//}
