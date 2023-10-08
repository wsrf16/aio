package com.aio.portable.swiss.suite.bean.serializer.json;

import com.aio.portable.swiss.sugar.RegexSugar;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import net.minidev.json.JSONArray;

import java.util.List;

public class JsonPathSugar {
    public static final DocumentContext deleteExcept(String json, String path) {
        JSONArray targetPathList = getPathList(json, path);
        JSONArray allPathList = getAllPathList(json, targetPathList);
        List<Object> deletePathList = CollectionSugar.except(allPathList, targetPathList);

        DocumentContext dc = JsonPath.parse(json);
        deletePathList = CollectionSugar.reverse(deletePathList);
        deletePathList.forEach(c -> dc.delete(c.toString()));
        return dc;
    }

    private static final JSONArray getPathList(String json, String path) {
        Configuration conf = Configuration.builder()
                .options(Option.AS_PATH_LIST).build();
        Object read = JsonPath.using(conf).parse(json).read(path);
        if (read instanceof JSONArray) {
            JSONArray wonderPathList = (JSONArray) read;
            return wonderPathList;
        }
        return null;
    }

    private static final JSONArray getAllPathList(String json, JSONArray wonderPathList) {
        if (wonderPathList.size() > 0) {
            List<List<String>> group = RegexSugar.findGroup("[\\d]", wonderPathList.get(0).toString());
            String delete = "[" + group.get(group.size() - 1).get(0) + "]";
            String allPaths = StringSugar.trimEnd(wonderPathList.get(0).toString(), delete) + "[*]";
//            JsonPath.using(conf).parse(json).read(allRule);
            JSONArray allPathList = getPathList(json, allPaths);
            return allPathList;
        }
        return null;
    }
}
