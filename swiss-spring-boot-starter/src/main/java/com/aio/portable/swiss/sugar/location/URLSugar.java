package com.aio.portable.swiss.sugar.location;

import com.aio.portable.swiss.sugar.RegexSugar;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.bean.DeepCloneSugar;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class URLSugar {
//    UriComponentsBuilder.fromHttpUrl()

    public static final String appendQueries(String uri, Object bean) {
        if (bean == null)
            return uri;

        Map<String, Object> map = ClassSugar.PropertyDescriptors.toNameValueMap(bean);
        String queryParams = map.entrySet().stream().map(c -> MessageFormat.format("{0}={1}", c.getKey(), c.getValue() == null ? "" : c.getValue().toString())).collect(Collectors.joining("&"));

        while (StringUtils.endsWithIgnoreCase(uri, "/")) {
            uri = StringSugar.trimEnd(uri, "/");
        }
        boolean match = RegexSugar.match("\\?.+=.+", uri);
        return match ? MessageFormat.format("{0}&{1}", uri, queryParams) : MessageFormat.format("{0}?{1}", uri, queryParams);
    }

    public static final String convertBeanToQueries(Object bean) {
        Map<String, Object> map = ClassSugar.PropertyDescriptors.toNameValueMap(bean);
        String queryParams = map.entrySet().stream().map(c -> MessageFormat.format("{0}={1}", c.getKey(), c.getValue() == null ? "" : c.getValue().toString())).collect(Collectors.joining("&"));
        return queryParams;
    }

    public static final Map<String, Object> convertQueryStringToMap(String queryString) {
        String[] split = queryString.split("&");
        Map<String, Object> map = new HashMap<>();
        Arrays.stream(split).forEach(c -> {
            String[] query = c.split("=");
            map.put(query[0], query[1]);
        });
        return map;
    }

    public static final <T> T convertQueryStringToBean(String queryString, Class<T> clazz) {
        Map<String, Object> map = convertQueryStringToMap(queryString);
        T t = DeepCloneSugar.Json.clone(map, clazz);
        return t;
    }

    public static final String concat(String first, String... urls) {
        String reduce = Arrays.stream(urls).reduce(first, (sum, element) -> {
            String left = StringSugar.trimAllEnd(sum, "/");
            String right = StringSugar.trimAllStart(element, "/");
            if (left.endsWith("&") && right.startsWith("&"))
                return StringSugar.trimAllEnd(left, "&") + "&" + StringSugar.trimAllStart(right, "&");
            else if (left.endsWith("&") || right.startsWith("&"))
                return left + right;
            else if (left.endsWith("?") || right.startsWith("?"))
                return left + right;
            else
                return left + "/" + right;
        });
        return reduce;
    }

    public static final URL toURL(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static final URL toURL(String path) {
//        return toURL(new File(path));
        try {
            return new URL(path);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static final URL toFileURL(String path) {
        return toURL(new File(path));
    }

//    public static final URL toString(URL url) {
//        url.toString()
//    }

}
