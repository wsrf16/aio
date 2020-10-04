package com.aio.portable.swiss.suite.bean.serializer.yaml;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class YamlSugar {
    private final static Yaml YAML = new Yaml();

    public static <T> T yaml2T(ClassLoader classLoader, String resource) {
        InputStream inputStream = classLoader
                .getResourceAsStream(resource);
        T t = YAML.load(inputStream);
        return t;
    }

    public static <T> T yaml2T(String yaml) {
        T t = YamlSugar.YAML.load(yaml);
        return t;
    }

    public static <T> T yaml2T(File file) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(file);
        T t = YAML.load(inputStream);
        return t;
    }

    public static <T> T yaml2T(InputStream inputStream) {
        T t = YAML.load(inputStream);
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
            throw new RuntimeException(e);
        }
    }
}
