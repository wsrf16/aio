package com.aio.portable.park.unit;

import com.aio.portable.park.bean.Student;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@TestComponent
public class BeanSugarTest {
    public class People {
        private int age;
        private String name;
        private Date birthday;


        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }
    }

    @Test
    public void foobar() throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException {

        String typeName = People.class.getTypeName();
        Class<?> clazz = Class.forName(typeName);
        People people = (People) ClassSugar.newInstance(clazz);
        ConvertUtils.register(new DateLocaleConverter(), java.util.Date.class);

        ConvertUtils.register(
                new Converter() {
                    public Date convert(Class type, Object value) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            return simpleDateFormat.parse(value.toString());
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return null;
                    }
                }
                , java.util.Date.class);
        ClassSugar.PropertyDescriptors.setValue(people, "age", 111);
        ClassSugar.PropertyDescriptors.setValue(people, "name", "aio");
        ClassSugar.PropertyDescriptors.setValue(people, "birthday", "2018-08-08");

        Student student = new Student();
        ClassSugar.PropertyDescriptors.copyPropertiesOnly(people, student, People::getName);
        System.out.println(people);
    }

    @Test
    public void ff() {
        Student student = new Student();
        boolean b = false;
        boolean b1 = ClassSugar.PropertyDescriptors.allIsNull(student);
        boolean b2 = ClassSugar.PropertyDescriptors.allReferenceIsNull(student);
        List<PropertyDescriptor> list = ClassSugar.PropertyDescriptors.getDeclaredPropertyDescriptorIncludeParents(Student.class);
        PropertyDescriptor propertyDescriptor = list.get(0);

    }
}
