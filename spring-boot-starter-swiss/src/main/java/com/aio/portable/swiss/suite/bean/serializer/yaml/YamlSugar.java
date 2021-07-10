package com.aio.portable.swiss.suite.bean.serializer.yaml;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class YamlSugar {
    static {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        YAML = new Yaml(new Constructor(), representer);
    }

    private final static Yaml YAML;

    public static <T> T resource2T(ClassLoader classLoader, String resource, Class<T> clazz) {
        InputStream inputStream = classLoader.getResourceAsStream(resource);
        T t = YAML.loadAs(inputStream, clazz);
        return t;
    }

    public static <T> T yaml2T(String yaml, Class<T> clazz) {
        T t = YamlSugar.YAML.loadAs(yaml, clazz);
        return t;
    }

    public final static <T> T file2T(String path, Class<T> clazz) {
        File file = new File(path);
        if (!file.exists())
            throw new RuntimeException(path);
        T t = YamlSugar.file2T(file, clazz);
        return t;
    }

    public static <T> T file2T(File file, Class<T> clazz)  {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
        T t = YAML.loadAs(inputStream, clazz);
        return t;
    }

    public static <T> T inputStream2T(InputStream inputStream, Class<T> clazz) {
        T t = YAML.loadAs(inputStream, clazz);
        return t;
    }

    public static String obj2Yaml(Object obj) {
        return YAML.dump(obj);
    }

    public static void obj2Yaml(Object obj, Writer writer) {
        YAML.dump(obj, writer);
    }



    public final static void yml2Properties(String path) {
        final String DOT = ".";
        List<String> lines = new LinkedList<>();
        try {
            YAMLFactory yamlFactory = new YAMLFactory();
            YAMLParser parser = yamlFactory.createParser(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

            String key = "";
            String value = null;
            JsonToken token = parser.nextToken();
            while (token != null) {
                if (JsonToken.START_OBJECT.equals(token)) {
                    // do nothing
                } else if (JsonToken.FIELD_NAME.equals(token)) {
                    if (key.length() > 0) {
                        key = key + DOT;
                    }
                    key = key + parser.getCurrentName();

                    token = parser.nextToken();
                    if (JsonToken.START_OBJECT.equals(token)) {
                        continue;
                    }
                    value = parser.getText();
                    lines.add(key + "=" + value);

                    int dotOffset = key.lastIndexOf(DOT);
                    if (dotOffset > 0) {
                        key = key.substring(0, dotOffset);
                    }
                    value = null;
                } else if (JsonToken.END_OBJECT.equals(token)) {
                    int dotOffset = key.lastIndexOf(DOT);
                    if (dotOffset > 0) {
                        key = key.substring(0, dotOffset);
                    } else {
                        key = "";
                        lines.add("");
                    }
                }
                token = parser.nextToken();
            }
            parser.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static void properties2Yaml(String path) {
        JsonParser parser = null;
        JavaPropsFactory factory = new JavaPropsFactory();
        try {
            parser = factory.createParser(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            YAMLFactory yamlFactory = new YAMLFactory();
            YAMLGenerator generator = yamlFactory.createGenerator(
                    new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));

            JsonToken token = parser.nextToken();

            while (token != null) {
                if (JsonToken.START_OBJECT.equals(token)) {
                    generator.writeStartObject();
                } else if (JsonToken.FIELD_NAME.equals(token)) {
                    generator.writeFieldName(parser.getCurrentName());
                } else if (JsonToken.VALUE_STRING.equals(token)) {
                    generator.writeString(parser.getText());
                } else if (JsonToken.END_OBJECT.equals(token)) {
                    generator.writeEndObject();
                }
                token = parser.nextToken();
            }
            parser.close();
            generator.flush();
            generator.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
