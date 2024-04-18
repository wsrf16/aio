package com.aio.portable.park.unit;

import com.aio.portable.park.bean.UserInfoEntity;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.type.StreamSugar;
import com.aio.portable.swiss.suite.bean.DeepCloneSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@TestComponent
public class DeepCloneTest {
    @Test
    public void foobar() {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setName("name");
        userInfoEntity.setId(11);
        HashMap<String, Object> map = DeepCloneSugar.Json.clone2StringMap(userInfoEntity);
        System.out.println(map);
    }
}
