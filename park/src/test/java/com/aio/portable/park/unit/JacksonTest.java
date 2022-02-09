package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.bean.serializer.SerializerAdapterFactory;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import lombok.Data;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestComponent
public class JacksonTest {
    @Data
    static class AA {
        LevelEnum a;
    }

    @Test
    private void foobar() throws IOException {
        HashMap<Object, Object> map1 = new HashMap<>();
        map1.put("a", LevelEnum.DEBUG);
        String s = JacksonSugar.obj2Json(map1);
        AA aa = JacksonSugar.json2T(s, AA.class);


        JsonModel a = new JsonModel() {{
            setNo(88);
        }};
        List<JsonModel> list = new ArrayList<>();
        list.add(a);
        Map<Integer, JsonModel> map = new HashMap<>();
        map.put(1, a);

        a = JacksonSugar.json2T(JacksonSugar.obj2Json(a), JsonModel.class);
        {
            JsonModel _model;
            _model= JacksonSugar.json2T(JacksonSugar.obj2Json(""), JsonModel.class);
            _model = JacksonSugar.json2T(JacksonSugar.obj2Json(null), JsonModel.class);
        }
        list = JacksonSugar.json2T(JacksonSugar.obj2Json(list));
        map = JacksonSugar.json2T(JacksonSugar.obj2Json(map));

        String listjson = SerializerAdapterFactory.buildJackson().serialize(list);
    }

    class JsonModel {
        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private int no;

        private String name;
    }
}
