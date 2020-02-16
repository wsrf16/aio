package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestComponent
public class JacksonTest {
    @Test
    private void todo() throws IOException {
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
        list = JacksonSugar.json2Complex(JacksonSugar.obj2Json(list));
        map = JacksonSugar.json2Complex(JacksonSugar.obj2Json(map));
//        String aListJson = JsonUtil.obj2Json(aList);
//        AA b = JsonUtil.json2Obj(aJson);
//        List<AA> bList = JsonUtil.json2Obj(aListJson);
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
