package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.sandbox.a中文.AA;
import com.aio.portable.swiss.suite.bean.serializer.SerializerEnum;
import com.aio.portable.swiss.suite.bean.serializer.SerializerSelector;
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
        list = JacksonSugar.json2T(JacksonSugar.obj2Json(list));
        map = JacksonSugar.json2T(JacksonSugar.obj2Json(map));

        String listjson = new SerializerSelector(SerializerEnum.SERIALIZE_JACKSON).serialize(list);
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
