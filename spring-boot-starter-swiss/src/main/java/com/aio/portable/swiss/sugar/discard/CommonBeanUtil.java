package com.aio.portable.swiss.sugar.discard;

//import org.apache.commons.beanutils.BeanUtils;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class CommonBeanUtils {
//    public static final Map object2Map(Object bean) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//        Map<String, String> map = BeanUtils.describe(bean);
//        map.remove("class");
//        return map;
//    }
//
//    public static final Map object2Map(Object bean, boolean includeNull) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//        Map<String, String> map = BeanUtils.describe(bean);
//        map.remove("class");
//        if (!includeNull)
//            map = map.entrySet().stream().filter(c -> c.getValue() != null).collect(Collectors.toMap(c -> c.getKey(), c -> c.getValue()));
//        return map;
//    }
//
//    public static final <T> T map2Object(Map map, Object output) throws IllegalAccessException, InvocationTargetException {
//        BeanUtils.populate(output, map);
//        return (T) output;
//    }
//
//    public static final void setProperty(Object bean, String name, Object value) throws InvocationTargetException, IllegalAccessException {
//        BeanUtils.setProperty(bean, name, value);
//    }
//
//    public static final void copyProperties(Object source, Object target) throws InvocationTargetException, IllegalAccessException {
//        BeanUtils.copyProperties(target, source);
//    }
//}
