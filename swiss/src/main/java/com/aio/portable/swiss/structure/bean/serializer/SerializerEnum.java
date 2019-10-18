package com.aio.portable.swiss.structure.bean.serializer;

/**
 * Created by York on 2017/11/22.
 */
public enum SerializerEnum {
    SERIALIZE_JACKSON(0, "Jackson"),
    SERIALIZE_JACKSON_FORCE(2, "Jackson"),
    SERIALIZE_SHORTJACKSON(4, "ShortJackson"),
    SERIALIZE_SHORTJACKSON_FORCE(8, "ShortJackson"),
    SERIALIZE_JACKXML(16, "Xml"),
    SERIALIZE_SHORTJACKXML(32, "ShortXml"),
    SERIALIZE_GSON(64, "Gson"),
    SERIALIZE_SHORTGSON(128, "ShortGson"),
    SERIALIZE_CUSTOM(256, "Custom");

//    DESERIALIZE_JACKSON(1, "Jackson"),
//    DESERIALIZE_SHORTJACKSON(3, "ShortJackson"),
//    DESERIALIZE_JACKXML(5, "Xml"),
//    DESERIALIZE_SHORTJACKXML(9, "ShortXml"),
//    DESERIALIZE_GSON(17, "Gson"),
//    DESERIALIZE_SHORTGSON(33, "ShortGson"),
//    DESERIALIZE_CUSTOM(65, "Custom");


    private Integer val;
    private String name;

    SerializerEnum(Integer val, String name) {
        this.val = val;
        this.name = name;
    }
}
