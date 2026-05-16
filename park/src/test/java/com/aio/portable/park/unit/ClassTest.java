package com.aio.portable.park.unit;

import com.aio.portable.park.bean.UserInfoEntity;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.sugar.meta.ClassLoaderSugar;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.sugar.meta.PackageSugar;
import com.aio.portable.swiss.sugar.meta.ResourceSugar;
import com.aio.portable.swiss.sugar.meta.function.ClassSetter;
import com.aio.portable.swiss.sugar.meta.function.InstanceSetter;
import com.aio.portable.swiss.sugar.meta.function.ClassGetter;
import com.aio.portable.swiss.sugar.meta.function.InstanceGetter;
import com.aio.portable.swiss.suite.bean.serializer.json.GsonSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.bean.structure.KeyValuePair;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.List;

@TestComponent
public class ClassTest {
    @Test
    public void foobar() throws IOException {
        boolean b1 = ClassLoaderSugar.isPresent("Wood");
        boolean b2 = ClassLoaderSugar.isPresent("Wood");

        String s2 = ClassUtils.convertClassNameToResourcePath("com.company.biz.Book");
        String s = ClassSugar.convertClassNameToResourcePath("com.company.biz.Book");
        String s1 = ResourceSugar.convertResourcePathToClassName("/com/company/biz/Book.class");


        if (existJackson())
            System.out.println(JacksonSugar.obj2Json(new KeyValuePair("a", "existJackson")));

        System.out.println();

        if (existGson())
            System.out.println(GsonSugar.obj2Json(new KeyValuePair("a", "existGson")));


        String fieldName1 = ClassSugar.PropertyDescriptors.getPropertyNameOf(new UserInfoEntity()::getNextId);
        String fieldName2 = ClassSugar.PropertyDescriptors.getPropertyNameOf(new UserInfoEntity()::setNextId);
        String fieldName3 = ClassSugar.PropertyDescriptors.getPropertyNameOf(UserInfoEntity::getNextId);
//        String fieldName4 = ClassSugar.PropertyDescriptors.getPropertyNameOf(UserInfoEntity::setNextId);

        InstanceGetter<Integer> getNextId = new UserInfoEntity()::getNextId;
        InstanceSetter<Integer> setNextId = new UserInfoEntity()::setNextId;
        ClassGetter<UserInfoEntity, Integer> staticGetNextId = UserInfoEntity::getNextId;
        ClassSetter<UserInfoEntity, Integer> staticSetNextId = UserInfoEntity::setNextId;

        String fieldName1_ = ClassSugar.PropertyDescriptors.getPropertyNameOf(getNextId);
        String fieldName2_ = ClassSugar.PropertyDescriptors.getPropertyNameOf(setNextId);
        String fieldName3_ = ClassSugar.PropertyDescriptors.getPropertyNameOf(staticGetNextId);
        String fieldName4_ = ClassSugar.PropertyDescriptors.getPropertyNameOf(staticSetNextId);

        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setName("name1");
        userInfoEntity.setId(1);
        UserInfoEntity userInfoEntity2 = new UserInfoEntity();
        ClassSugar.PropertyDescriptors.copyPropertiesOnly(userInfoEntity, userInfoEntity2, UserInfoEntity::getName, UserInfoEntity::getId);


        System.out.println();
    }


    public static boolean existJackson() {
        return ClassLoaderSugar.isPresent(("com.fasterxml.jackson.databind.JsonSerializer"));
    }


    public static boolean existGson() {
        return ClassLoaderSugar.isPresent(("com.google.gson.Gson"));
    }











    @Test
    public void getClassName() throws IOException {
        String packageName = StackTraceSugar.Current.getClassName();
        List<String> list = PackageSugar.getClassNameList(packageName);
    }
}
