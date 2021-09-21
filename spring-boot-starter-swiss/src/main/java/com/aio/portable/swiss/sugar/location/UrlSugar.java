package com.aio.portable.swiss.sugar.location;

import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class UrlSugar {
//    UriComponentsBuilder

    public final static String addQueries(String uri, Object bean) {
        Map<String, Object> map = BeanSugar.PropertyDescriptors.toNameValueMap(bean);
        String queryParams = map.entrySet().stream().map(c -> MessageFormat.format("{0}={1}", c.getKey(), c.getValue() == null ? "" : c.getValue().toString())).collect(Collectors.joining("&"));

        while (StringUtils.endsWithIgnoreCase(uri, "/")) {
            uri = StringSugar.trimEnd(uri, "/");
        }
        return MessageFormat.format("{0}?{1}", uri, queryParams);
    }


    public final static String concat(String first, String... urls) {
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
}
